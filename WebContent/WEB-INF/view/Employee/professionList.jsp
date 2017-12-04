<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>工种管理</title>
<link rel="stylesheet" type="text/css" href="easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="easyui/css/demo.css">
	<script type="text/javascript" src="easyui/jquery.min.js" charset="utf-8"></script>
	<script type="text/javascript" src="easyui/jquery.easyui.min.js" charset="utf-8"></script>
	<script type="text/javascript" src="easyui/js/validateExtends.js" charset="utf-8"></script>
	<script type="text/javascript">
	$(function() {	
		//datagrid初始化 
	    $('#dataList').datagrid({
	        title:'工种', 
	        iconCls:'icon-more',//图标 
	        border: true, 
	        collapsible: false,//是否可折叠的 
	        fit: true,//自动大小 
	        method: "post",
	        url:"ProfessionServlet?method=ProfessionList&t="+new Date().getTime(),
	        idField:'id', 
	        singleSelect: true,//是否单选 
	        pagination: false,//分页控件 
	        rownumbers: true,//行号 
	        sortName:'id',
	        sortOrder:'DESC', 
	        remoteSort: false,
	        columns: [[  
				{field:'chk',checkbox: true,width:50},
 		        {field:'id',title:'ID',width:50, sortable: true},    
 		        {field:'name',title:'工种名称',width:200},
 		      {field:'remark',title:'备注',width:500},
	 		]], 
	        toolbar: "#toolbar"
	    }); 
	   	
	    //设置工具类按钮
	    $("#add").click(function(){
	    	$("#addDialog").dialog("open");
	    });
	  //修改按钮
	    $("#edit").click(function(){
	    	table = $("#editTable");
	    	var selectRows = $("#dataList").datagrid("getSelections");
        	if(selectRows.length != 1){
            	$.messager.alert("消息提醒", "请选择一条数据进行操作!", "warning");
            } else{
		    	$("#editDialog").dialog("open");
            }
	    });
	    //删除
	    $("#delete").click(function(){
	    	var selectRow = $("#dataList").datagrid("getSelected");
        	if(selectRow == null){
            	$.messager.alert("消息提醒", "请选择数据进行删除!", "warning");
            } else{
            	var professionid = selectRow.id;
            	$.messager.confirm("消息提醒", "将删除与课程相关的所有数据，确认继续？", function(r){
            		if(r){
            			$.ajax({
							type: "post",
							url: "ProfessionServlet?method=DeleteProfession",
							data: {professionid: professionid},
							success: function(msg){
								if(msg == "success"){
									$.messager.alert("消息提醒","删除成功!","info");
									//刷新表格
									$("#dataList").datagrid("reload");
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
	  	
	  	//设置添加窗口
	    $("#addDialog").dialog({
	    	title: "添加工种",
	    	width: 450,
	    	height: 250,
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
					iconCls:'icon-book-add',
					handler:function(){
						var validate = $("#addForm").form("validate");
						if(!validate){
							$.messager.alert("消息提醒","请检查你输入的数据!","warning");
							return;
						} else{
							$.ajax({
								type: "post",
								url: "ProfessionServlet?method=AddProfession",
								data: $("#addForm").serialize(),
								success: function(msg){
									if(msg == "success"){
										$.messager.alert("消息提醒","添加成功!","info");
										//关闭窗口
										$("#addDialog").dialog("close");
										//清空原表格数据
										$("#add_name").textbox('setValue', "");
										$("#add_remark").textbox('setValue', "");
										//刷新
										$('#dataList').datagrid("reload");
									} else if(msg=="duplicate"){
										$.messager.alert("消息提醒","添加工种名称已经存在!","warning");
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
					iconCls:'icon-book-reset',
					handler:function(){
						$("#add_name").textbox('setValue', "");
					}
				},
			]
	    });
	  	
	  //编辑教师信息
	  	$("#editDialog").dialog({
	  		title: "修改工种信息",
	    	width: 450,
	    	height: 250,
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
						if(!validate){
							$.messager.alert("消息提醒","请检查你输入的数据!","warning");
							return;
						} else{
							var id = $("#dataList").datagrid("getSelected").id;
				
							var name = $("#edit_name").textbox("getText");
							
							var remark = $("#edit_remark").textbox("getText");
							
							var data = {id:id,name:name,remark:remark};
							
							$.ajax({
								type: "post",
								url: "ProfessionServlet?method=editProfession",
								data: data,
								success: function(msg){
									if(msg == "success"){
										$.messager.alert("消息提醒","修改成功!","info");
										//关闭窗口
										$("#editDialog").dialog("close");
										//清空原表格数据
										
										$("#edit_name").textbox('setValue', "");
										$("#edit_remark").textbox('setValue', "");
										
										//重新刷新页面数据
							  			$('#dataList').datagrid("reload");
							  			$('#dataList').datagrid("uncheckAll");
										
									} else{
										$.messager.alert("消息提醒","修改失败!","warning");
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
						$("#edit_name").textbox('setValue', "");
						$("#edit_remark").textbox('setValue', "");
						
						
					}
				},
			],
			onBeforeOpen: function(){
				
				var selectRow = $("#dataList").datagrid("getSelected");
				
				//设置值
				$("#edit_name").textbox('setValue', selectRow.name);
				$("#edit_remark").textbox('setValue', selectRow.remark);
			},
			onClose: function(){
				$("#edit_name").textbox('setValue', "");
				$("#edit_remark").textbox('setValue', "");
				
			}
	    });
	});
	</script>
</head>
<body>
	<!-- 数据列表 -->
	<table id="dataList" cellspacing="0" cellpadding="0"> 
	    
	</table> 
	<!-- 工具栏 -->
	<div id="toolbar">
		<div style="float: left;"><a id="add" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">添加</a></div>
			<div style="float: left;" class="datagrid-btn-separator"></div>
			<div style="float: left;"><a id="edit" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true">修改</a></div>
			<div style="float: left;" class="datagrid-btn-separator"></div>
		<div><a id="delete" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-some-delete',plain:true">删除</a></div>
	</div>
	
	<!-- 添加数据窗口 -->
	<div id="addDialog" style="padding: 10px">  
    	<form id="addForm" method="post">
	    	<table cellpadding="8" >
	    		<tr>
	    			<td>工种名称:</td>
	    			<td><input id="add_name" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="name" data-options="required:true, validType:'repeat_course', missingMessage:'不能为空'" /></td>
	    		</tr>
	    		<tr>
	    			<td>工种备注:</td>
	    			<td><input id="add_remark" style="width: 200px; height: 60px;" class="easyui-textbox" data-options="multiline: true," type="text" name="remark"  /></td>
	    		</tr>
	    	</table>
	    </form>
	</div>
	
	<!-- 修改窗口 -->
	<div id="editDialog" style="padding: 10px">
			<form id="editForm" method="post">
	    	<table id="editTable" border=0 style=" table-layout:fixed;" cellpadding="8" >
	    		<tr>
	    			<td>名称:</td>
	    			<td><input id="edit_name" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="name" data-options="required:true, missingMessage:'请填写工种名称'" /></td>
	    		</tr>
	    		<tr>
	    			<td>备注:</td>
	    			<td ><input id="edit_remark" style="width: 200px; height: 60px;" class="easyui-textbox" data-options="multiline: true," type="text" name="remark"  /></td>
	    		</tr>
	    	</table>
	    </form>
	</div>
</body>
</html>