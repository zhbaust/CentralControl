<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="UTF-8">
	<title>部门列表</title>
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
	        title:'部门列表', 
	        iconCls:'icon-more',//图标 
	        border: true, 
	        collapsible:false,//是否可折叠的 
	        fit: true,//自动大小 
	        method: "post",
	        url:"DepartmentServlet?method=DepartmentList&t="+new Date().getTime(),
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
 		        {field:'name',title:'部门名称',width:200}, 		        		       
 		        {field:'tel',title:'电话',width:150},
 		        {field:'remark',title:'备注',width:150},
 		        {field:'employee',title:'领导姓名',width:200,
 		        	formatter: function(value,row,index){
 						if (row.employee){
 							return row.employee.name;
 						} else {
 							return value;
 						}
 		        	}
 		        },
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
	    $("#add").click(function(){
	    	$("#addDialog").dialog("open");
	    });
	    //修改
	    $("#edit").click(function(){
	    	var selectRows = $("#dataList").datagrid("getSelections");
        	if(selectRows.length != 1){
            	$.messager.alert("消息提醒", "请选择一条数据进行操作!", "warning");
            } else{
		    	$("#editDialog").dialog("open");
            }
	    });
	    //删除
	    $("#delete").click(function(){
	    	var selectRows = $("#dataList").datagrid("getSelections");
        	var selectLength = selectRows.length;
        	if(selectLength == 0){
            	$.messager.alert("消息提醒", "请选择数据进行删除!", "warning");
            } else{
            	//var numbers = [];
            	//$(selectRows).each(function(i, row){
            	//	numbers[i] = row.number;
            	//});
            	var ids = [];
            	$(selectRows).each(function(i, row){
            		ids[i] = row.id;
            	});
            	$.messager.confirm("消息提醒", "将删除与部门相关的所有数据(包括人员信息)，确认继续？", function(r){
            		if(r){
            			$.ajax({
							type: "post",
							url: "DepartmentServlet?method=DeleteDepartment",
							data: {ids: ids},
							success: function(msg){
								if(msg == "success"){
									$.messager.alert("消息提醒","删除成功!","info");
									//刷新表格
									$("#dataList").datagrid("reload");
									$("#dataList").datagrid("uncheckAll");
								} else{
									$.messager.alert("消息提醒","删除失败!","warning");
									return;
								}
							}
						});
            		}
            	});
            }
	    });
	    
	  	
	  	
	  	//设置添加部门窗口
	    $("#addDialog").dialog({
	    	title: "添加部门",
	    	width: 450,
	    	height: 350,
	    	iconCls: "icon-add",
	    	modal: true,
	    	collapsible: false,
	    	minimizable: false,
	    	maximizable: false,
	    	draggable: true,
	    	closed: true,
	    	buttons: [
	    		{
					text:'添加',
					plain: true,
					iconCls:'icon-user_add',
					handler:function(){
						var validate = $("#addForm").form("validate");
						if(!validate){
							$.messager.alert("消息提醒","请检查你输入的数据!","warning");
							return;
						} else{
							//var gradeid = $("#add_gradeList").combobox("getValue");
							//var clazzid = $("#add_clazzList").combobox("getValue");
							$.ajax({
								type: "post",
								url: "DepartmentServlet?method=AddDepartment",
								data: $("#addForm").serialize(),
								success: function(msg){
									if(msg == "success"){
										$.messager.alert("消息提醒","添加成功!","info");
										//关闭窗口
										$("#addDialog").dialog("close");
										//清空原表格数据
										$("#add_name").textbox('setValue', "");
										$("#add_leadid").textbox('setValue', "");
										$("#add_tel").textbox('setValue', "");
										$("#add_remark").textbox('setValue', "");
										
										//重新刷新页面数据
										//$('#dataList').datagrid("options").queryParams = {clazzid: clazzid};
							  			$('#dataList').datagrid("reload");
							  			//$("#gradeList").combobox('setValue', gradeid);
							  			//setTimeout(function(){
										//	$("#clazzList").combobox('setValue', clazzid);
										//}, 100);
										
									} else if(msg=="duplicate"){
										$.messager.alert("消息提醒","添加部门名称已经存在!","warning");
									}else{
										$.messager.alert("消息提醒","添加失败!","warning");
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
						$("#add_name").textbox('setValue', "");
						$("#add_tel").textbox('setValue', "");
						$("#add_remark").textbox('setValue', "");
						//重新加载年级
						//$("#add_gradeList").combobox("clear");
					    //$("#add_gradeList").combobox("reload");
					}
				},
			]
	    });
	  	
	  	//设置编辑部门窗口
	    $("#editDialog").dialog({
	    	title: "修改部门信息",
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
							
							var name = $("#edit_name").textbox("getText");
							var leadid = $("#edit_leadid").combobox("getValue");
							var tel = $("#edit_tel").textbox("getText");
							var remark = $("#edit_remark").textbox("getText");
							
							var data = {id:id,name:name,leadid:leadid,tel:tel,remark:remark};
							
							
							
							$.ajax({
								type: "post",
								url: "DepartmentServlet?method=EditDepartment&t="+new Date().getTime(),
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
						$("#add_name").textbox('setValue', "");
						$("#add_tel").textbox('setValue', "");
						$("#add_remark").textbox('setValue', "");
						
						//$("#edit_gradeList").combobox("clear");
						//$("#edit_gradeList").combobox("reload");
					}
				}
			],
			onBeforeOpen: function(){
				var selectRow = $("#dataList").datagrid("getSelected");
				//设置值
				$("#edit_name").textbox('setValue', selectRow.name);
				//$("#edit_leadid").textbox('setValue', selectRow.employee.name);
				$("#edit_leadid").combobox("setValue", selectRow.employee.id);
				$("#edit_tel").textbox('setValue', selectRow.tel);
				$("#edit_remark").textbox('setValue', selectRow.remark);
				//$("#edit_photo").attr("src", "PhotoServlet?method=GetPhoto&type=2&number="+selectRow.number);
				//var gradeid = selectRow.gradeid;
				//var clazzid = selectRow.clazzid;
				//$("#edit_gradeList").combobox('setValue', gradeid);
				//setTimeout(function(){
				//	$("#edit_clazzList").combobox('setValue', clazzid);
				//}, 100);
				
			}
	    });
	   
	});
	</script>
</head>
<body>
	<!-- 部门列表 -->
	<table id="dataList" cellspacing="0" cellpadding="0"> 
	    
	</table> 
	<!-- 工具栏 -->
	<div id="toolbar">
		<div style="float: left;"><a id="add" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">添加</a></div>
			<div style="float: left;" class="datagrid-btn-separator"></div>
		<div style="float: left;"><a id="edit" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true">修改</a></div>
			<div style="float: left;" class="datagrid-btn-separator"></div>
		<div style="float: left;"><a id="delete" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-some-delete',plain:true">删除</a></div>
		
		<!-- <div style="float: left; margin: 0 10px 0 10px">年级：<input id="gradeList" class="easyui-textbox" name="grade" /></div>
		<div style="margin-left: 10px;">班级：<input id="clazzList" class="easyui-textbox" name="clazz" /></div>
	     -->
	</div>
	
	<!-- 添加部门窗口 -->
	<div id="addDialog" style="padding: 10px">  
		<!-- <div style="float: right; margin: 20px 20px 0 0; width: 200px; border: 1px solid #EBF3FF" id="photo">
	    	<img alt="照片" style="max-width: 200px; max-height: 400px;" title="照片" src="photo/student.jpg" />
	    </div>  -->
    	<form id="addForm" method="post">
	    	<table cellpadding="8" >
	    		<tr>
	    			<td>名称:</td>
	    			<td><input id="add_name" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="name" data-options="required:true, missingMessage:'请填写部门名称'" /></td>
	    		</tr>
	    		<tr>
	    			<td>领导:</td>
	    			<td><select id="add_leadid" class="easyui-combobox" data-options="editable: false, panelHeight: 50, width: 100, height: 30" name="leadid"><option value="1">赵宝</option><option value="2">林俊杰</option></select></td>
	    		</tr>
	    		<tr>
	    			<td>电话:</td>
	    			<td><input id="add_tel" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="tel" validType="mobile" /></td>
	    		</tr>
	    		<tr>
	    			<td>备注:</td>
	    			<td><input id="add_remark" style="width: 200px; height: 80px;" class="easyui-textbox" data-options="multiline: true," type="text" name="remark" validType="text" /></td>
	    		</tr>
	    		<!--  <tr>
	    			<td>年级:</td>
	    			<td><input id="add_gradeList" style="width: 200px; height: 30px;" class="easyui-textbox" name="gradeid" /></td>
	    		</tr>
	    		<tr>
	    			<td>班级:</td>
	    			<td><input id="add_clazzList" style="width: 200px; height: 30px;" class="easyui-textbox" name="clazzid" /></td>
	    		</tr>-->
	    	</table>
	    </form>
	</div>
	
	<!-- 修改部门窗口 -->
	<div id="editDialog" style="padding: 10px">
    	<form id="editForm" method="post">
	    	<table cellpadding="8" >
	    	
	    		<tr>
	    			<td>名称:</td>
	    			<td><input id="edit_name" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="name" data-options="required:true, missingMessage:'请填写部门名称'" /></td>
	    		</tr>
	    		<tr>
	    			<td>领导:</td>
	    			<td><select id="edit_leadid" class="easyui-combobox" data-options="editable: false, panelHeight: 50, width: 100, height: 30" name="leadid"><option value="1">赵宝</option><option value="2">林俊杰</option></select></td>
	    		</tr>
	    		<tr>
	    			<td>电话:</td>
	    			<td><input id="edit_tel" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="tel" validType="mobile" /></td>
	    		</tr>
	    		<tr>
	    			<td>备注:</td>
	    			<td><input id="edit_remark" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="remark" /></td>
	    		</tr>
	    		
	    	</table>
	    </form>
	</div>
	
</body>
</html>