<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="UTF-8">
	<title>学生列表</title>
	<link rel="stylesheet" type="text/css" href="easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="easyui/css/demo.css">
	<script type="text/javascript" src="easyui/jquery.min.js"></script>
	<script type="text/javascript" src="easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="easyui/js/validateExtends.js"></script>
	<script type="text/javascript">
	$(function() {	
		//datagrid初始化 
	    $('#dataList').datagrid({ 
	        title:'调动列表', 
	        iconCls:'icon-more',//图标 
	        border: true, 
	        collapsible:false,//是否可折叠的 
	        fit: true,//自动大小 
	        method: "post",
	        url:"JobtransferServlet?method=JobtransferList&t="+new Date().getTime(),
	        idField:'id', 
	        singleSelect:false,//是否单选 
	        pagination:true,//分页控件 
	        rownumbers:true,//行号 
	        sortName:'id',
	        sortOrder:'', 
	        remoteSort: false,
	        columns: [[  
				{field:'chk',checkbox: true,width:50},
 		        {field:'id',title:'ID',width:50, sortable: true},  
 		        {field:'employee',title:'姓名',width:100,
 		        	formatter: function(value,row,index){
 						if (row.employee){
 							return row.employee.name;
 						} else {
 							return value;
 						}
 		        	}
 		        },
 		       {field:'olddepartment',title:'原部门',width:200,
 		        	formatter: function(value,row,index){
 						if (row.olddepartment){
 							return row.olddepartment.name;
 						} else {
 							return value;
 						}
 		        	}
 		        },
 		       {field:'department',title:'现部门',width:200,
 		        	formatter: function(value,row,index){
 						if (row.department){
 							return row.department.name;
 						} else {
 							return value;
 						}
 		        	}
 		        },
 		        {field:'transferdate',title:'调动日期',width:100}, 		        		       
 		        {field:'transferreson',title:'调动原因',width:150},
 		       {field:'operator',title:'负责人',width:100,
 		        	formatter: function(value,row,index){
 						if (row.operator){
 							return row.operator.name;
 						} else {
 							return value;
 						}
 		        	}
 		        },
 		        {field:'remark',title:'备注',width:150},
 		       
	 		]], 
	        toolbar: "#toolbar",
	        onDblClickRow:function(rowIndex){  //双击某一行，打开该行的修改界面。
	            $('#dataList').datagrid('selectRow',rowIndex);  //指定行选中
	            var selectRows =$("#dataList").datagrid("getSelected");
	            $("#editDialog").dialog("open");
	         
	        }
	    }); 
	    //设置分页控件 
	    var p = $('#dataList').datagrid('getPager'); 
	    $(p).pagination({ 
	        pageSize: 10,//每页显示的记录条数，默认为10 
	        pageList: [10,20,30,50,100],//可以设置每页记录条数的列表 
	        beforePageText: '第',//页数文本框前显示的汉字 
	        afterPageText: '页    共 {pages} 页', 
	        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录', 
	    }); 
	    //设置工具类按钮
	   
	    //修改
	    $("#edit").click(function(){
	    	var selectRows = $("#dataList").datagrid("getSelections");
        	if(selectRows.length != 1){
            	$.messager.alert("消息提醒", "请选择一条数据进行操作!", "warning");
            } else{
		    	$("#editDialog").dialog("open");
            }
	    });
	    
	    
	  	//原部门下拉框
	  	$("#olddepartmentList").combobox({
	  		width: "100",
	  		height: "25",
	  		valueField: "id",
	  		textField: "name",
	  		multiple: false, //可多选
	  		editable: false, //不可编辑
	  		method: "post",
	  		url: "DepartmentServlet?method=DepartmenttoEmployeeList&t="+new Date().getTime(),
	  		onChange: function(newValue, oldValue){
	  			//加载该年级下的学生
	  			$('#dataList').datagrid("options").queryParams = {olddepartmentid: newValue};
	  			$('#dataList').datagrid("reload");
	  		}
	  	});
	
	  //原部门下拉框
	  	$("#departmentList").combobox({
	  		width: "100",
	  		height: "25",
	  		valueField: "id",
	  		textField: "name",
	  		multiple: false, //可多选
	  		editable: false, //不可编辑
	  		method: "post",
	  		url: "DepartmentServlet?method=DepartmenttoEmployeeList&t="+new Date().getTime(),
	  		onChange: function(newValue, oldValue){
	  			//加载该年级下的学生
	  			$('#dataList').datagrid("options").queryParams = {departmentid: newValue};
	  			$('#dataList').datagrid("reload");
	  		}
	  	});
	  	
	  	 //查询
	     $("#query").click(function(){
	    
	    	var olddepartmentid = $("#olddepartmentList").combobox("getValue");
	    	var departmentid = $("#departmentList").combobox("getValue");
	    	var name = $("#qname").val();
	    	
	    	var startdate = $("#startdate").textbox("getText");
	    	var enddate =  $("#enddate").textbox("getText");
	    	var reson= $("#reson").val();
	    	//alert(startdate);
	    	var page =1;
	    	var rows=10;
	    	var data = {name:name,olddepartmentid:olddepartmentid,startdate:startdate,enddate:enddate,departmentid:departmentid,reson:reson,page:page,rows:rows};
	 
         			$.ajax({
							type: "post",
							url: "JobtransferServlet?method=JobtransferListQuery&t="+new Date().getTime(),
							data: data,
							dataType:"json",
							success: function (data) {
								$("#dataList").datagrid("loadData", data);  //动态取数据
								//$("#dataList").datagrid("reload");
							}
					});
	    });
	  	 
	   //重置查询条件
	     $("#qreset").click(function(){
	    
	    	$("#olddepartmentList").textbox('setValue', "");
			$("#departmentList").textbox('setValue', "");
			$("#qname").textbox('setValue', "");
			$("#startdate").textbox('setValue', "");
			$("#enddate").textbox('setValue', "");
			$("#reson").textbox('setValue', "");
	    
	    });
	  	 
	  	
	  	//设置编辑部门窗口
	    $("#editDialog").dialog({
	    	title: "修改调动原因",
	    	width: 450,
	    	height: 350,
	    	iconCls: "icon-edit",
	    	modal: true,
	    	collapsible: false,
	    	minimizable: false,
	    	maximizable: false,
	    	draggable: true,
	    	closed: true,
	    	buttons: [
	    		{
					text:'提交',
					plain: true,
					iconCls:'icon-user_add',
					handler:function(){
						var validate = $("#editForm").form("validate");
						//var gradeid = $("#edit_gradeList").combobox("getValue");
						//var clazzid = $("#edit_clazzList").combobox("getValue");
						
						if(!validate){
							$.messager.alert("消息提醒","请检查你输入的数据!","warning");
							return;
						} else{
							
							var id = $("#dataList").datagrid("getSelected").id;
							
							var transferreson = $("#edit_reson").textbox("getText");
							var remark = $("#edit_remark").textbox("getText");
							
							var data = {id:id,transferreson:transferreson,remark:remark};
										
							
							$.ajax({
								type: "post",
								url: "JobtransferServlet?method=EditJobtransfer&t="+new Date().getTime(),
								data: data,
								success: function(msg){
									if(msg == "success"){
										$.messager.alert("消息提醒","更新成功!","info");
										//关闭窗口
										$("#editDialog").dialog("close");
										//刷新表格
										//$('#dataList').datagrid("options").queryParams = {clazzid: clazzid};
										$("#dataList").datagrid("reload");
										$("#dataList").datagrid("uncheckAll");
										
										//$("#gradeList").combobox('setValue', gradeid);
							  			//setTimeout(function(){
										//	$("#clazzList").combobox('setValue', clazzid);
										//}, 100);
							  			
									} else{
										$.messager.alert("消息提醒","更新失败!","warning");
										return;
									}
								}
							});
						}
					}
				},
				{
					text:'重置',
					plain: true,
					iconCls:'icon-reload',
					handler:function(){
						//清空表单
						$("#edit_reson").textbox('setValue', "");
						$("#edit_remark").textbox('setValue', "");
					}
				}
			],
			onBeforeOpen: function(){
				var selectRow = $("#dataList").datagrid("getSelected");
				//设置值
				//$("#edit_id").textbox('setValue', selectRow.id);
				$("#edit_name").textbox('setValue', selectRow.employee.name);
				//$("#edit_leadid").textbox('setValue', selectRow.employee.name);
				$("#edit_olddepartment").textbox("setValue", selectRow.olddepartment.name);
				$("#edit_department").textbox("setValue", selectRow.department.name);
				$("#edit_reson").textbox('setValue', selectRow.transferreson);
				$("#edit_remark").textbox('setValue', selectRow.remark);
				
			}
	    });
	  	
	   
	   
	});
	</script>
</head>
<body>
	<!-- 学生列表 -->
	<table id="dataList" cellspacing="0" cellpadding="0"> 
	    
	</table> 
	<!-- 工具栏 -->
	<div id="toolbar">
		
		<div style="float: left;"><a id="edit" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true">调动原因修改</a></div>
		<div style="float: left; margin: 0 5px 0 5px">原部门：<input id="olddepartmentList" class="easyui-textbox" name="olddepartment" /></div>
		<div style="float: left; margin: 0 5px 0 5px">现部门：<input id="departmentList" class="easyui-textbox" name="department" /></div>
		<div style="float: left; margin: 0 5px 0 5px">姓名：<input id="qname" style="width: 100px; height: 25px;" class="easyui-textbox" name="qname" /></div>
		<div style="float: left; margin: 0 5px 0 5px">调动日期：<input id="startdate" style="width: 100px; height: 25px;" class="easyui-datebox" name=""startdate"" /></div>
		<div style="float: left; margin: 0 5px 0 5px">至<input id="enddate" style="width: 100px; height: 25px;" class="easyui-datebox" name="enddate" /></div>
		<div style="float: left; margin: 0 5px 0 5px">原因：<input id="reson" style="width: 100px; height: 25px;" class="easyui-textbox" name="reson" /></div>
		<div style="float: left; margin: 0 5px 0 5px"><a id="query" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true">查询</a></div>
		<div style="margin-left: 10px;"><a id="qreset" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true">重置</a></div>
	</div>
	
	<!-- 修改学生窗口 -->
	<div id="editDialog" style="padding: 10px">
    	<form id="editForm" method="post">
	    	<table cellpadding="3" >
	    	  	<tr>
	    			<td>姓名:</td>
	    			<td><input id="edit_name" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="name" data-options="readonly:true" /></td>
	    		</tr>
	    		<tr>
	    			<td>原部门:</td>
	    			<td><input id="edit_olddepartment" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="olddepartment" data-options="readonly:true"  /></td>
	    		</tr>
	    		<tr>
	    			<td>现部门:</td>
	    			<td><input id="edit_department" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="department"  data-options="readonly:true"/></td>
	    		</tr>
	    		<tr>
	    			<td>调动原因:</td>
	    			<td><input id="edit_reson" style="width: 300px; height: 60px;" class="easyui-textbox" type="text" name="reson" data-options=" multiline: true"/></td>
	    		</tr>
	    		<tr>
	    			<td>备注:</td>
	    			<td><input id="edit_remark" style="width: 300px; height: 60px;" class="easyui-textbox" type="text" name="remark" data-options=" multiline: true"/></td>
	    		</tr>
	    		
	    	</table>
	    </form>
	</div>
	
</body>
</html>