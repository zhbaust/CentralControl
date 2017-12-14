<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="UTF-8">
	<title>维护记录列表</title>
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
	    	 title:'维护记录列表', 
		        iconCls:'icon-more',//图标 
		        border: true, 
		        collapsible:false,//是否可折叠的 
		        fit: true,//自动大小 
		        method: "post",
		        url:"MaintenanceServlet?method=MaintenanceList&t="+new Date().getTime(),
		        idField:'id', 
		        singleSelect:false,//是否单选 
		        pagination:true,//分页控件 
		        rownumbers:true,//行号 
		        sortName:'id',
		        sortOrder:'DESC', 
		        remoteSort: false,
		        columns: [[  
		        	{field:'chk',checkbox: true,width:20},
	 		        {field:'id',title:'ID',width:50, sortable: true}, 
	 		       {field:'device',title:'设备名',width:100, 
	 		        	formatter: function(value,row,index){
	 						if (row.device){
	 							return row.device.name;
	 						} else {
	 							return value;
	 						}
	 					}
					},
	 		        {field:'maintenancetime',title:'维护时间',width:200},
	 		       {field:'dispose',title:'维护处理',width:300},
	 		       {field:'operator',title:'维护人员',width:100, 
	 		        	formatter: function(value,row,index){
	 						if (row.operator){
	 							return row.operator.name;
	 						} else {
	 							return value;
	 						}
	 					}
					},
	 		        {field:'remark',title:'备注',width:200},
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
            	var ids = [];
            	$(selectRows).each(function(i, row){
            		ids[i] = row.id;
            	});
            	$.messager.confirm("消息提醒", "将删除与维护记录相关的所有数据，确认继续？", function(r){
            		if(r){
            			$.ajax({
							type: "post",
							url: "MaintenanceServlet?method=DeleteMaintenance",
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
	    
	   //查询
	     $("#query").click(function(){
	    		//var selectRow = $("#dataList").datagrid("getSelected");
	    	var deviceid = $("#deviceList").combobox("getValue");
	    	var operatorid = $("#operatorList").combobox("getValue");
	    	var dispose = $("#dispose").textbox("getValue");
	    	var page =1;
	    	var rows=10;
	    	var data = {deviceid:deviceid,operatorid:operatorid,dispose:dispose,page:page,rows:rows};
	    	//alert(name);
        	/* if(name == null){
            	$.messager.alert("消息提醒", "请输入要查询的名称", "warning");
            } else{ */
            			$.ajax({
							type: "post",
							url: "MaintenanceServlet?method=MaintenanceList&t="+new Date().getTime(),
							data: data,
							dataType:"json",
							success: function (data) {
								$("#dataList").datagrid("loadData", data);  //动态取数据
								//$("#dataList").datagrid("reload");
							}
							
							
						});
            		//}
	    });
	    
	   //部门下拉框
		  	$("#deviceList").combobox({
		  		width: "100",
		  		height: "25",
		  		valueField: "id",
		  		textField: "name",
		  		multiple: false, //可多选
		  		editable: false, //不可编辑
		  		method: "post",
		  		url: "DeviceServlet?method=DevicetoinputList&t="+new Date().getTime(),
		  		onChange: function(newValue, oldValue){
		  			//加载该年级下的维护记录
		  			$('#dataList').datagrid("options").queryParams = {deviceid: newValue};
		  			$('#dataList').datagrid("reload");
		  			
		  			/* //加载该年级下的班级
		  			 $("#professionList").combobox("clear");
		  			$("#professionList").combobox("options").queryParams = {departmentid: newValue};
		  			$("#professionList").combobox("reload")  */
		  		}
		  	});
		  	//工种下拉框
		  	$("#operatorList").combobox({
		  		width: "100",
		  		height: "25",
		  		valueField: "id",
		  		textField: "name",
		  		multiple: false, //可多选
		  		editable: false, //不可编辑
		  		method: "post",
		  		url: "EmployeeServlet?method=EmployeeinputList&t="+new Date().getTime(),
		  		onChange: function(newValue, oldValue){
		  			//加载班级下的维护记录
		  			$('#dataList').datagrid("options").queryParams = {operatorid: newValue};
		  			$('#dataList').datagrid("reload");
		  		}
		  	});
	  	
		  //下拉框通用属性
		  	$("#add_deviceList, #edit_deviceList, #add_operatorList, #edit_operatorList").combobox({
		  		width: "200",
		  		height: "25",
		  		valueField: "id",
		  		textField: "name",
		  		multiple: false, //可多选
		  		editable: false, //不可编辑
		  		method: "post",
		  	});
		  	
		  	$("#add_deviceList").combobox({
		  		url: "DeviceServlet?method=DevicetoinputList&t="+new Date().getTime(),
				onLoadSuccess: function(){
					//默认选择第一条数据
					var data = $(this).combobox("getData");
					$(this).combobox("setValue", data[0].id);
		  		}
		  	});
		  	$("#add_operatorList").combobox({
		  		url: "EmployeeServlet?method=EmployeeinputList&t="+new Date().getTime(),
		  		onLoadSuccess: function(){
			  		//默认选择第一条数据
					var data = $(this).combobox("getData");;
					$(this).combobox("setValue", data[0].id);
		  		}
		  	});
		  	$("#edit_deviceList").combobox({
		  		url: "DeviceServlet?method=DevicetoinputList&t="+new Date().getTime(),
				onLoadSuccess: function(){
					//默认选择第一条数据
					var data = $(this).combobox("getData");
					$(this).combobox("setValue", data[0].id);
		  		}
		  	});
		  	$("#edit_operatorList").combobox({
		  		url: "EmployeeServlet?method=EmployeeinputList&t="+new Date().getTime(),
		  		onLoadSuccess: function(){
			  		//默认选择第一条数据
					var data = $(this).combobox("getData");;
					$(this).combobox("setValue", data[0].id);
		  		}
		  	});
	  	
		  //设置添加维护记录窗口
		    $("#addDialog").dialog({
		    	title: "添加维护记录",
		    	width: 400,
		    	height: 320,
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
								var deviceid = $("#add_deviceList").combobox("getValue");
								var operatorid = $("#add_operatorList").combobox("getValue");
								
								$.ajax({
									type: "post",
									url: "MaintenanceServlet?method=AddMaintenance&t="+new Date().getTime(),
									data: $("#addForm").serialize(),
									success: function(msg){
										if(msg == "success"){
											$.messager.alert("消息提醒","添加成功!","info");
											//关闭窗口
											$("#addDialog").dialog("close");
											//清空原表格数据										
											var data = $("#add_deviceList").combobox("getData");
											$("#add_deviceList").combobox("setValue", data[0].id);
											
											$("#add_maintenancetime").textbox('setValue', "");
											
											var data = $("#add_operatorList").combobox("getData");
											$("#add_operatorList").combobox("setValue", data[0].id);
											$("#add_dispose").textbox('setValue', "");											
											$("#add_remark").textbox('setValue', "");
											//重新刷新页面数据
											
								  			$('#dataList').datagrid("reload");
								  			
											
										} else if(msg=="duplicate"){
											$.messager.alert("消息提醒","添加设备记录已经存在!","warning");
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
							var data = $("#add_deviceList").combobox("getData");
							$("#add_deviceList").combobox("setValue", data[0].id);
							
							$("#add_maintenancetime").textbox('setValue', "");
							
							var data = $("#add_operatorList").combobox("getData");
							$("#add_operatorList").combobox("setValue", data[0].id);
							$("#add_dispose").textbox('setValue', "");									
							$("#add_remark").textbox('setValue', "");
						}
					},
				]
		    });
		    
		    //设置编辑入库记录窗口
		    $("#editDialog").dialog({
		    	title: "修改维护记录信息",
		    	width: 400,
		    	height: 320,
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
							
							var deviceid = $("#edit_deviceList").combobox("getValue");
							var operatorid = $("#edit_operatorList").combobox("getValue");
							
							
							if(!validate){
								$.messager.alert("消息提醒","请检查你输入的数据!","warning");
								return;
							} else{
								$.ajax({
									type: "post",
									url: "MaintenanceServlet?method=EditMaintenance&t="+new Date().getTime(),
									data: $("#editForm").serialize(),
									success: function(msg){
										if(msg == "success"){
											$.messager.alert("消息提醒","更新成功!","info");
											//关闭窗口
											$("#editDialog").dialog("close");
											//刷新表格
											$("#dataList").datagrid("reload");
											$("#dataList").datagrid("uncheckAll");
								  			
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
							$("#edit_maintenancetime").textbox('setValue', "");
							$("#edit_num").textbox('setValue', "");
							
							var data = $("#edit_receiverList").combobox("getData");
							$("#edit_receiverList").combobox("setValue", data[0].id);
							
							$("#edit_dispose").textbox('setValue', "");
							$("#edit_remark").textbox('setValue', ""); 
							
						}
					}
				],
				onBeforeOpen: function(){
					var selectRow = $("#dataList").datagrid("getSelected");
					
					//设置值
						    $("#edit_deviceList").combobox("setValue", selectRow.device.id);
							$("#edit_maintenancetime").textbox('setValue', selectRow.maintenancetime);	 
							$("#edit_operatorList").combobox("setValue",selectRow.operator.id);
							$("#edit_dispose").textbox('setValue', selectRow.dispose);
							$("#edit_remark").textbox("setValue", selectRow.remark);
							
							
				}
		    });
		  
		  //重置查询条件
		     $("#qreset").click(function(){
		    
		    	$("#deviceList").textbox('setValue', "");
				$("#operatorList").textbox('setValue', "");
				$("#dispose").textbox('setValue', "");
		    });
		});
	
	</script>
</head>
<body>
	<!-- 维护记录列表 -->
	<table id="dataList" cellspacing="0" cellpadding="0"> 
	    
	</table> 
	<!-- 工具栏 -->
	<div id="toolbar">
		<div style="float: left;"><a id="add" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">添加</a></div>
			<div style="float: left;" class="datagrid-btn-separator"></div>
		<div style="float: left;"><a id="edit" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true">修改</a></div>
			<div style="float: left;" class="datagrid-btn-separator"></div>
		<div style="float: left;"><a id="delete" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-some-delete',plain:true">删除</a></div>
			<div style="float: left;" class="datagrid-btn-separator"></div>
		<!-- <div style="float: left;"><a id="transfer" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-book-reset',plain:true">调整部门</a></div> -->
		<div style="float: left; margin: 0 5px 0 5px">设备：<input id="deviceList" class="easyui-textbox" name="device" /></div>
		<div style="float: left; margin: 0 5px 0 5px">维护人员：<input id="operatorList" class="easyui-textbox" name="operator" /></div>
		<div style="float: left; margin: 0 5px 0 5px">维护处理：<input id="dispose" class="easyui-textbox" name="dispose" /></div>
		<div style="float: left; margin: 0 5px 0 5px"><a id="query" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true">查询</a></div>
		<div style="margin-left: 10px;"><a id="qreset" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true">重置</a></div>
		
	</div>
	
	<!-- 添加维护记录窗口 -->
	<div id="addDialog" style="padding: 10px">
    	<form id="addForm" method="post">
	    	<table cellpadding="2" >
	    		<tr>
	    			<td>设备名称:</td>
	    			<td><input id="add_deviceList" style="width: 200px; height: 25px;" class="easyui-textbox" name="deviceid" /></td>
	    		</tr>
	    		<tr>
	    			<td>维护日期:</td>
	    			<td><input id="add_maintenancetime" style="width: 200px; height: 25px;" class="easyui-datetimebox" type="text" name="maintenancetime" data-options="required:true, missingMessage:'请填写维护日期'" /></td>
	    		</tr>
	    		<tr>
	    			<td>维护人员:</td>
	    			<td><input id="add_operatorList" style="width: 200px; height: 25px;" class="easyui-textbox" name="operatorid" /></td>
	    		</tr>
	    		<tr>
	    			<td>维护处理:</td>
	    			<td><input id="add_dispose" style="width: 200px; height: 75px;" class="easyui-textbox" type="text"  name="dispose"  data-options="multiline: true," data-options="required:true, missingMessage:'请填写维护日期'"  /></td>
	    		</tr>
	    		<tr>
	    			<td>备注:</td>
	    			<td><input id="add_remark" style="width: 200px; height: 50px;" class="easyui-textbox" data-options="multiline: true,"  type="text" name="remark"/></td>
	    		</tr>
	    	</table>
	    </form>
	</div>
	
	<!-- 修改维护记录窗口 -->
	<div id="editDialog" style="padding: 10px">
    	<form id="editForm" method="post">
	    	<table cellpadding="2" >
	    		<tr>
	    			<td>设备名称:</td>
	    			<td><input id="edit_deviceList" style="width: 200px; height: 25px;" class="easyui-textbox" name="deviceid" /></td>
	    		</tr>
	    		<tr>
	    			<td>维护日期:</td>
	    			<td><input id="edit_maintenancetime" style="width: 200px; height: 25px;" class="easyui-datetimebox" type="text" name="maintenancetime" data-options="required:true, missingMessage:'请填写维护日期'" /></td>
	    		</tr>
	    		<tr>
	    			<td>维护人员:</td>
	    			<td><input id="edit_operatorList" style="width: 200px; height: 25px;" class="easyui-textbox" name="operatorid" /></td>
	    		</tr>
	    		<tr>
	    			<td>维护处理:</td>
	    			<td><input id="edit_dispose" style="width: 200px; height: 75px;" class="easyui-textbox" type="text"  name="dispose"  data-options="multiline: true," data-options="required:true, missingMessage:'请填写维护日期'"  /></td>
	    		</tr>
	    		<tr>
	    			<td>备注:</td>
	    			<td><input id="edit_remark" style="width: 200px; height: 50px;" class="easyui-textbox" data-options="multiline: true,"  type="text" name="remark"/></td>
	    		</tr>
	    	</table>
	    </form>
	</div>
	
	

</body>
</html>