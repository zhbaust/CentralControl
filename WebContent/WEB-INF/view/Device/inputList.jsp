<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="UTF-8">
	<title>入库记录列表</title>
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
	    	 title:'入库记录列表', 
		        iconCls:'icon-more',//图标 
		        border: true, 
		        collapsible:false,//是否可折叠的 
		        fit: true,//自动大小 
		        method: "post",
		        url:"InputServlet?method=InputList&t="+new Date().getTime(),
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
	 		        {field:'inputdate',title:'入库日期',width:100},	 		       
	 		        {field:'productiondate',title:'生产日期',width:100},
	 		        {field:'num',title:'数量',width:50},
	 		       {field:'operator',title:'采购人',width:100, 
	 		        	formatter: function(value,row,index){
	 						if (row.operator){
	 							return row.operator.name;
	 						} else {
	 							return value;
	 						}
	 					}
					},
	 		        {field:'validity',title:'有效期限',width:100},
	 		       {field:'supplier',title:'供应商',width:200, 
	 		        	formatter: function(value,row,index){
	 						if (row.supplier){
	 							return row.supplier.name;
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
            	$.messager.confirm("消息提醒", "将删除与入库记录相关的所有数据，确认继续？", function(r){
            		if(r){
            			$.ajax({
							type: "post",
							url: "InputServlet?method=DeleteInput",
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
	    	var supplierid = $("#supplierList").combobox("getValue");
	    	var page =1;
	    	var rows=10;
	    	var data = {deviceid:deviceid,operatorid:operatorid,supplierid:supplierid,page:page,rows:rows};
	    	//alert(name);
        	/* if(name == null){
            	$.messager.alert("消息提醒", "请输入要查询的名称", "warning");
            } else{ */
            			$.ajax({
							type: "post",
							url: "InputServlet?method=InputList&t="+new Date().getTime(),
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
		  		url: "DeviceServlet?method=DevicetoInputList&t="+new Date().getTime(),
		  		onChange: function(newValue, oldValue){
		  			//加载该年级下的入库记录
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
		  		url: "EmployeeServlet?method=EmployeeInputList&t="+new Date().getTime(),
		  		onChange: function(newValue, oldValue){
		  			//加载班级下的入库记录
		  			$('#dataList').datagrid("options").queryParams = {operatorid: newValue};
		  			$('#dataList').datagrid("reload");
		  		}
		  	});
		  	
		  //工种下拉框
		  	$("#supplierList").combobox({
		  		width: "100",
		  		height: "25",
		  		valueField: "id",
		  		textField: "name",
		  		multiple: false, //可多选
		  		editable: false, //不可编辑
		  		method: "post",
		  		url: "SupplierServlet?method=SupplierInputList&t="+new Date().getTime(),
		  		onChange: function(newValue, oldValue){
		  			//加载班级下的入库记录
		  			$('#dataList').datagrid("options").queryParams = {supplierid: newValue};
		  			$('#dataList').datagrid("reload");
		  		}
		  	});
	  	
		  //下拉框通用属性
		  	$("#add_deviceList, #edit_deviceList, #add_operatorList, #edit_operatorList, #add_supplierList, #edit_supplierList").combobox({
		  		width: "200",
		  		height: "25",
		  		valueField: "id",
		  		textField: "name",
		  		multiple: false, //可多选
		  		editable: false, //不可编辑
		  		method: "post",
		  	});
		  	
		  	$("#add_deviceList").combobox({
		  		url: "DeviceServlet?method=DevicetoInputList&t="+new Date().getTime(),
				onLoadSuccess: function(){
					//默认选择第一条数据
					var data = $(this).combobox("getData");
					$(this).combobox("setValue", data[0].id);
		  		}
		  	});
		  	$("#add_operatorList").combobox({
		  		url: "EmployeeServlet?method=EmployeeInputList&t="+new Date().getTime(),
		  		onLoadSuccess: function(){
			  		//默认选择第一条数据
					var data = $(this).combobox("getData");;
					$(this).combobox("setValue", data[0].id);
		  		}
		  	});
		  	
		  	$("#add_supplierList").combobox({
		  		url: "SupplierServlet?method=SupplierInputList&t="+new Date().getTime(),
		  		onLoadSuccess: function(){
			  		//默认选择第一条数据
					var data = $(this).combobox("getData");;
					$(this).combobox("setValue", data[0].id);
		  		}
		  	});
		  	$("#edit_deviceList").combobox({
		  		url: "DeviceServlet?method=DevicetoInputList&t="+new Date().getTime(),
				onLoadSuccess: function(){
					//默认选择第一条数据
					var data = $(this).combobox("getData");
					$(this).combobox("setValue", data[0].id);
		  		}
		  	});
		  	$("#edit_operatorList").combobox({
		  		url: "EmployeeServlet?method=EmployeeInputList&t="+new Date().getTime(),
		  		onLoadSuccess: function(){
			  		//默认选择第一条数据
					var data = $(this).combobox("getData");;
					$(this).combobox("setValue", data[0].id);
		  		}
		  	});
		  	
		  	$("#edit_supplierList").combobox({
		  		url: "SupplierServlet?method=SupplierInputList&t="+new Date().getTime(),
		  		onLoadSuccess: function(){
			  		//默认选择第一条数据
					var data = $(this).combobox("getData");;
					$(this).combobox("setValue", data[0].id);
		  		}
		  	});
	  	
		  //设置添加入库记录窗口
		    $("#addDialog").dialog({
		    	title: "添加入库记录",
		    	width: 450,
		    	height: 380,
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
								var supplierid = $("#add_supplierList").combobox("getValue");
								
								$.ajax({
									type: "post",
									url: "InputServlet?method=AddInput&t="+new Date().getTime(),
									data: $("#addForm").serialize(),
									success: function(msg){
										if(msg == "success"){
											$.messager.alert("消息提醒","添加成功!","info");
											//关闭窗口
											$("#addDialog").dialog("close");
											//清空原表格数据										
											var data = $("#add_deviceList").combobox("getData");
											$("#add_deviceList").combobox("setValue", data[0].id);
											
											$("#add_inputdate").textbox('setValue', "");
											$("#add_productiondate").textbox('setValue', "");
											$("#add_num").textbox('setValue', "");
											
											var data = $("#add_operatorList").combobox("getData");
											$("#add_operatorList").combobox("setValue", data[0].id);
											
											$("#add_validity").textbox('setValue', "");
											
											var data = $("#add_supplierList").combobox("getData");
											$("#add_supplierList").combobox("setValue", data[0].id);
											
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
								
								$("#add_inputdate").textbox('setValue', "");
								$("#add_productiondate").textbox('setValue', "");
								$("#add_num").textbox('setValue', "");
								
								var data = $("#add_operatorList").combobox("getData");
								$("#add_operatorList").combobox("setValue", data[0].id);
								
								$("#add_validity").textbox('setValue', "");
								
								var data = $("#add_supplierList").combobox("getData");
								$("#add_supplierList").combobox("setValue", data[0].id);
								
								$("#add_remark").textbox('setValue', ""); 
						}
					},
				]
		    });
	  	
		  //设置编辑入库记录窗口
		    $("#editDialog").dialog({
		    	title: "修改入库记录信息",
		    	width: 450,
		    	height: 380,
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
							var supplierid = $("#edit_supplierList").combobox("getValue");
							
							
							if(!validate){
								$.messager.alert("消息提醒","请检查你输入的数据!","warning");
								return;
							} else{
								$.ajax({
									type: "post",
									url: "InputServlet?method=EditInput&t="+new Date().getTime(),
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
							$("#edit_inputdate").textbox('setValue', "");
							$("#edit_productiondate").textbox('setValue', "");
							$("#edit_num").textbox('setValue', "");
							
							var data = $("#edit_operatorList").combobox("getData");
							$("#edit_operatorList").combobox("setValue", data[0].id);
							
							$("#edit_validity").textbox('setValue', "");
							
							var data = $("#edit_supplierList").combobox("getData");
							$("#edit_supplierList").combobox("setValue", data[0].id);
							
							$("#edit_remark").textbox('setValue', ""); 
							
						}
					}
				],
				onBeforeOpen: function(){
					var selectRow = $("#dataList").datagrid("getSelected");
					//设置值
						    $("#edit_deviceList").combobox("setValue", selectRow.device.id);
							$("#edit_inputdate").textbox('setValue', selectRow.inputdate);
							$("#edit_productiondate").textbox('setValue', selectRow.productiondate);
							$("#edit_num").textbox('setValue', selectRow.num);


							$("#edit_operatorList").combobox("setValue", selectRow.operator.id);
							
							$("#edit_validity").textbox('setValue', selectRow.validity);


							$("#edit_supplierList").combobox("setValue", selectRow.supplier.id);
							
							$("#edit_remark").textbox('setValue', selectRow.remark);
							
					
				}
		    });
		  
		  //重置查询条件
		     $("#qreset").click(function(){
		    
		    	$("#deviceList").textbox('setValue', "");
				$("#operatorList").textbox('setValue', "");
				$("#supplierList").textbox('setValue', "");
		    });
		});
	
	</script>
</head>
<body>
	<!-- 入库记录列表 -->
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
		<div style="float: left; margin: 0 5px 0 5px">采购人：<input id="operatorList" class="easyui-textbox" name="operator" /></div>
		<div style="float: left; margin: 0 5px 0 5px">供应商：<input id="supplierList" class="easyui-textbox" name="supplier" /></div>
		<div style="float: left; margin: 0 5px 0 5px"><a id="query" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true">查询</a></div>
		<div style="margin-left: 10px;"><a id="qreset" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true">重置</a></div>
		
	</div>
	
	<!-- 添加入库记录窗口 -->
	<div id="addDialog" style="padding: 10px">
    	<form id="addForm" method="post">
	    	<table cellpadding="2" >
	    		<tr>
	    			<td>设备名称:</td>
	    			<td><input id="add_deviceList" style="width: 200px; height: 25px;" class="easyui-textbox" name="deviceid" /></td>
	    		</tr>
	    		<tr>
	    			<td>入库日期:</td>
	    			<td><input id="add_inputdate" style="width: 200px; height: 25px;" class="easyui-datebox" type="text" name="inputdate" data-options="required:true, missingMessage:'请填写入库日期'" /></td>
	    		</tr>
	    		<tr>
	    			<td>生产日期:</td>
	    			<td><input id="add_productiondate" style="width: 200px; height: 25px;" class="easyui-datebox" type="text" name="productiondate" data-options="required:true, missingMessage:'请填写生产日期'" /></td>
	    		</tr>
	    		<tr>
	    			<td>采购数量:</td>
	    			<td><input id="add_num" style="width: 200px; height: 25px;" class="easyui-textbox" type="text" name="num" data-options="required:true, missingMessage:'请填写数量'" value="15" /></td>
	    		</tr>
	    		<tr>
	    			<td>采购人员:</td>
	    			<td><input id="add_operatorList" style="width: 200px; height: 25px;" class="easyui-textbox" name="operatorid" /></td>
	    		</tr>
	    		<tr>
	    			<td>有效期(月):</td>
	    			<td><input id="add_validity" style="width: 200px; height: 25px;" class="easyui-textbox" type="text" name="validity" data-options="required:true, missingMessage:'请填写有效期'" value="15" /></td>
	    		</tr>
	    		<tr>
	    			<td>供应商:</td>
	    			<td><input id="add_supplierList" style="width: 200px; height: 25px;" class="easyui-textbox" name="supplierid" /></td>
	    		</tr>
	    		<tr>
	    			<td>备注:</td>
	    			<td><input id="add_remark" style="width: 200px; height: 50px;" class="easyui-textbox" data-options="multiline: true,"  type="text" name="remark"/></td>
	    		</tr>
	    	</table>
	    </form>
	</div>
	
	<!-- 修改入库记录窗口 -->
	<div id="editDialog" style="padding: 10px">
    	<form id="editForm" method="post">
	    	<table cellpadding="2" >
	    		<tr>
	    			<td>设备名称:</td>
	    			<td><input id="edit_deviceList" style="width: 200px; height: 25px;" class="easyui-textbox" name="deviceid" data-options="readonly:true"/></td>
	    		</tr>
	    		<tr>
	    			<td>入库日期:</td>
	    			<td><input id="edit_inputdate" style="width: 200px; height: 25px;" class="easyui-datebox" type="text" name="inputdate" data-options="required:true, missingMessage:'请填写入库日期'" /></td>
	    		</tr>
	    		<tr>
	    			<td>生产日期:</td>
	    			<td><input id="edit_productiondate" style="width: 200px; height: 25px;" class="easyui-datebox" type="text" name="productiondate" data-options="required:true, missingMessage:'请填写生产日期'" /></td>
	    		</tr>
	    		<tr>
	    			<td>采购数量:</td>
	    			<td><input id="edit_num" style="width: 200px; height: 25px;" class="easyui-textbox" type="text" name="num" data-options="required:true, missingMessage:'请填写数量'" value="15" /></td>
	    		</tr>
	    		<tr>
	    			<td>采购人员:</td>
	    			<td><input id="edit_operatorList" style="width: 200px; height: 25px;" class="easyui-textbox" name="operatorid" /></td>
	    		</tr>
	    		<tr>
	    			<td>有效期(月):</td>
	    			<td><input id="edit_validity" style="width: 200px; height: 25px;" class="easyui-textbox" type="text" name="validity" data-options="required:true, missingMessage:'请填写有效期'" value="15" /></td>
	    		</tr>
	    		<tr>
	    			<td>供应商:</td>
	    			<td><input id="edit_supplierList" style="width: 200px; height: 25px;" class="easyui-textbox" name="supplierid" /></td>
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