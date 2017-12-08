<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="UTF-8">
	<title>设备列表</title>
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
	        title:'设备列表', 
	        iconCls:'icon-more',//图标 
	        border: true, 
	        collapsible:false,//是否可折叠的 
	        fit: true,//自动大小 
	        method: "post",
	        url:"DeviceServlet?method=DeviceList&t="+new Date().getTime(),
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
 		       {field:'deviceid',title:'设备编号',width:150},
 		        {field:'name',title:'设备名称',width:200}, 		        		       
 		        {field:'modal',title:'型号',width:150},
 		       {field:'num',title:'数量',width:150},
 		      {field:'price',title:'单价',width:150},
 		        {field:'remark',title:'备注',width:250},
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
            	$.messager.confirm("消息提醒", "将删除与设备相关的所有数据，确认继续？", function(r){
            		if(r){
            			$.ajax({
							type: "post",
							url: "DeviceServlet?method=DeleteDevice",
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
	    
	  	
	  	//设置添加设备窗口
	    $("#addDialog").dialog({
	    	title: "添加设备",
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
								url: "DeviceServlet?method=AddDevice",
								data: $("#addForm").serialize(),
								success: function(msg){
									if(msg == "success"){
										$.messager.alert("消息提醒","添加成功!","info");
										//关闭窗口
										$("#addDialog").dialog("close");
										//清空原表格数据
										$("#add_deviceid").textbox('setValue', "");
										$("#add_name").textbox('setValue', "");
										$("#add_modal").textbox('setValue', "");
										$("#add_num").textbox('setValue', "");
										$("#add_price").textbox('setValue', "");
										$("#add_remark").textbox('setValue', "");
										
										//重新刷新页面数据
										//$('#dataList').datagrid("options").queryParams = {clazzid: clazzid};
							  			$('#dataList').datagrid("reload");
							  			//$("#gradeList").combobox('setValue', gradeid);
							  			//setTimeout(function(){
										//	$("#clazzList").combobox('setValue', clazzid);
										//}, 100);
										
									} else if(msg=="duplicate"){
										$.messager.alert("消息提醒","添加设备名称已经存在!","warning");
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
						$("#add_deviceid").textbox('setValue', "");
						$("#add_name").textbox('setValue', "");
						$("#add_modal").textbox('setValue', "");
						$("#add_num").textbox('setValue', "");
						$("#add_price").textbox('setValue', "");
						$("#add_remark").textbox('setValue', "");
					}
				},
			]
	    });
	  	
	  	//设置编辑设备窗口
	    $("#editDialog").dialog({
	    	title: "修改设备信息",
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
							var deviceid=$("edit_deviceid").textbox("getText");
							var name = $("#edit_name").textbox("getText");
							var modal = $("#edit_modal").textbox("getText");
							var num = $("#edit_num").textbox("getText");
							var price = $("#edit_price").textbox("getText");
							var remark = $("#edit_remark").textbox("getText");
							
							var data = {id:id,deviceid:deviceid,name:name,modal:modal,num:num,price:price,remark:remark};
							
							
							
							$.ajax({
								type: "post",
								url: "DeviceServlet?method=EditDevice&t="+new Date().getTime(),
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
						$("#edit_deviceid").textbox('setValue', "");
						$("#edit_name").textbox('setValue', "");
						$("#edit_modal").textbox('setValue', "");
						$("#edit_num").textbox('setValue', "");
						$("#edit_price").textbox('setValue', "");
						$("#edit_remark").textbox('setValue', "");
					}
				}
			],
			onBeforeOpen: function(){
				var selectRow = $("#dataList").datagrid("getSelected");
				//设置值
				$("#edit_deviceid").textbox('setValue', selectRow.deviceid);
						$("#edit_name").textbox('setValue',selectRow.name);
						$("#edit_modal").textbox('setValue', selectRow.modal);
						$("#edit_num").textbox('setValue', selectRow.num);
						$("#edit_price").textbox('setValue', selectRow.price);
						$("#edit_remark").textbox('setValue', selectRow.remark);
				
			}
	    });
	  	
	  //查询
	     $("#query").click(function(){
	    		//var selectRow = $("#dataList").datagrid("getSelected");
	    	var deviceid = $("#qdeviceid").val();
	    	var name = $("#qname").val();
	    	var modal= $("#qmodal").val();
	    	var page =1;
	    	var rows=10;
	    	var data = {deviceid:deviceid,name:name,modal:modal,page:page,rows:rows};
		    $.ajax({
					type : "post",
					url : "DeviceServlet?method=DeviceListQuery",
					data : data,
					dataType : "json",
					success : function(data) {
						$("#dataList").datagrid("loadData", data); //动态取数据
					}
				});

			});
			//重置查询条件
			$("#qreset").click(function() {

				$("#qdeviceid").textbox('setValue', "");
				$("#qname").textbox('setValue', "");
				$("#qmodal").textbox('setValue', "");
			});

		});
	</script>
</head>
<body>
	<!-- 设备列表 -->
	<table id="dataList" cellspacing="0" cellpadding="0"> 
	    
	</table> 
	<!-- 工具栏 -->
	<div id="toolbar">
		<div style="float: left;"><a id="add" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">添加</a></div>
			<div style="float: left;" class="datagrid-btn-separator"></div>
		<div style="float: left;"><a id="edit" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true">修改</a></div>
			<div style="float: left;" class="datagrid-btn-separator"></div>
		<div style="float: left;"><a id="delete" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-some-delete',plain:true">删除</a></div>
		
			<div style="float: left; margin: 2px 5px 0 5px">编号：<input id="qdeviceid" class="easyui-textbox" name="qdeviceid" /></div>
		<div style="float: left; margin: 2px 5px 0 5px">名称：<input id="qname" class="easyui-textbox" name="qname" /></div>
		<div style="float: left; margin: 2px 5px 0 5px">型号：<input id="qmodal" class="easyui-textbox" name="qmodal" /></div>
		<div style="float: left; margin: 0px 5px 0 5px"><a id="query" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true">查询</a></div>
		<div style="margin-left: 10px;"><a id="qreset" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true">重置</a></div>
		
	
	</div>
	
	<!-- 添加设备窗口 -->
	<div id="addDialog" style="padding: 10px">  
		<!-- <div style="float: right; margin: 20px 20px 0 0; width: 200px; border: 1px solid #EBF3FF" id="photo">
	    	<img alt="照片" style="max-width: 200px; max-height: 400px;" title="照片" src="photo/student.jpg" />
	    </div>  -->
    	<form id="addForm" method="post">
	    	<table cellpadding="3" >
	    	    <tr>
	    			<td>编号:</td>
	    			<td><input id="add_deviceid" style="width: 200px; height: 25px;" class="easyui-textbox" type="text" name="deviceid" data-options="required:true, missingMessage:'请填写设备编号'" /></td>
	    		</tr>
	    		<tr>
	    			<td>名称:</td>
	    			<td><input id="add_name" style="width: 200px; height: 25px;" class="easyui-textbox" type="text" name="name" data-options="required:true, missingMessage:'请填写设备名称'" /></td>
	    		</tr>
	    		<tr>
	    			<td>型号:</td>
	    			<td><input id="add_modal" style="width: 200px; height: 25px;" class="easyui-textbox" type="text" name="modal" data-options="required:true, missingMessage:'请填写设备型号'"  /></td>
	    		</tr>
	    		<tr>
	    			<td>数量:</td>
	    			<td><input id="add_num" style="width: 200px; height: 25px;" class="easyui-textbox" type="text" name="num" data-options="required:true, missingMessage:'请填写设备数量'"  /></td>
	    		</tr>
	    		<tr>
	    			<td>单价:</td>
	    			<td><input id="add_price" style="width: 200px; height: 25px;" class="easyui-textbox" type="text" name="price" data-options="required:true, missingMessage:'请填写设备单价'"  /></td>
	    		</tr>
	    		<tr>
	    			<td>备注:</td>
	    			<td><input id="add_remark" style="width: 200px; height: 80px;" class="easyui-textbox" data-options="multiline: true," type="text" name="remark" validType="text" /></td>
	    		</tr>
	    	</table>
	    </form>
	</div>
	
	<!-- 修改设备窗口 -->
	<div id="editDialog" style="padding: 10px">
    	<form id="editForm" method="post">
	    	<table cellpadding="3" >
	    	
	    		 <tr>
	    			<td>编号:</td>
	    			<td><input id="edit_deviceid" style="width: 200px; height: 25px;" class="easyui-textbox" type="text" name="deviceid" data-options="required:true, missingMessage:'请填写设备编号'" /></td>
	    		</tr>
	    		<tr>
	    			<td>名称:</td>
	    			<td><input id="edit_name" style="width: 200px; height: 25px;" class="easyui-textbox" type="text" name="name" data-options="required:true, missingMessage:'请填写设备名称'" /></td>
	    		</tr>
	    		<tr>
	    			<td>型号:</td>
	    			<td><input id="edit_modal" style="width: 200px; height: 25px;" class="easyui-textbox" type="text" name="modal" data-options="required:true, missingMessage:'请填写设备型号'"  /></td>
	    		</tr>
	    		<tr>
	    			<td>数量:</td>
	    			<td><input id="edit_num" style="width: 200px; height: 25px;" class="easyui-textbox" type="text" name="num" data-options="required:true, missingMessage:'请填写设备数量'"  /></td>
	    		</tr>
	    		<tr>
	    			<td>单价:</td>
	    			<td><input id="edit_price" style="width: 200px; height: 25px;" class="easyui-textbox" type="text" name="price" data-options="required:true, missingMessage:'请填写设备单价'"  /></td>
	    		</tr>
	    		<tr>
	    			<td>备注:</td>
	    			<td><input id="edit_remark" style="width: 200px; height: 80px;" class="easyui-textbox" data-options="multiline: true," type="text" name="remark" validType="text" /></td>
	    		</tr>
	    		
	    	</table>
	    </form>
	</div>
	
</body>
</html>