<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="UTF-8">
	<title>员工列表</title>
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
	    	 title:'员工列表', 
		        iconCls:'icon-more',//图标 
		        border: true, 
		        collapsible:false,//是否可折叠的 
		        fit: true,//自动大小 
		        method: "post",
		        url:"EmployeeServlet?method=EmployeeList&t="+new Date().getTime(),
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
	 		        {field:'name',title:'姓名',width:50},	 		       
	 		        {field:'loginname',title:'用户名',width:60},
	 		        {field:'sex',title:'性别',width:50},
	 		        {field:'birthday',title:'出生日期',width:100},
	 		        {field:'tel',title:'电话',width:100},      
	 		        {field:'address',title:'地址',width:250},      
	 		        {field:'jobstate',title:'在职情况',width:80},      
	 		        {field:'cardnumber',title:'卡号',width:200},
	 		        {field:'department',title:'部门',width:100, 
	 		        	formatter: function(value,row,index){
	 						if (row.department){
	 							return row.department.name;
	 						} else {
	 							return value;
	 						}
	 					}
					},
	 		        {field:'profession',title:'工种',width:150, 
						formatter: function(value,row,index){
	 						if (row.profession){
	 							return row.profession.name;
	 						} else {
	 							return value;
	 						}
	 					}	
	 		       	},
	 		       {field:'authority',title:'权限',width:100, 
						formatter: function(value,row,index){
	 						if (row.authority){
	 							return row.authority.name;
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
            	var ids = [];
            	$(selectRows).each(function(i, row){
            		ids[i] = row.id;
            	});
            	$.messager.confirm("消息提醒", "将删除与员工相关的所有数据，确认继续？", function(r){
            		if(r){
            			$.ajax({
							type: "post",
							url: "EmployeeServlet?method=DeleteEmployee",
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
	    	var name = $("#queryname").val();
	    	var tel = $("#querytel").val();
	    	var cardnumber= $("#querycard").val();
	    	var departmentid = $("#departmentList").combobox("getValue");
	    	var professionid = $("#professionList").combobox("getValue");
	    	var authorityid = $("#authorityList").combobox("getValue");
	    	var page =1;
	    	var rows=10;
	    	var data = {name:name,tel:tel,cardnumber:cardnumber,departmentid:departmentid,professionid:professionid,authorityid:authorityid,page:page,rows:rows};
	    	//alert(name);
        	/* if(name == null){
            	$.messager.alert("消息提醒", "请输入要查询的名称", "warning");
            } else{ */
            			$.ajax({
							type: "post",
							url: "EmployeeServlet?method=EmployeeListQuery",
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
		  			//加载该年级下的员工
		  			$('#dataList').datagrid("options").queryParams = {departmentid: newValue};
		  			$('#dataList').datagrid("reload");
		  			
		  			/* //加载该年级下的班级
		  			 $("#professionList").combobox("clear");
		  			$("#professionList").combobox("options").queryParams = {departmentid: newValue};
		  			$("#professionList").combobox("reload")  */
		  		}
		  	});
		  	//工种下拉框
		  	$("#professionList").combobox({
		  		width: "100",
		  		height: "25",
		  		valueField: "id",
		  		textField: "name",
		  		multiple: false, //可多选
		  		editable: false, //不可编辑
		  		method: "post",
		  		url: "ProfessionServlet?method=ProfessionList&t="+new Date().getTime(),
		  		onChange: function(newValue, oldValue){
		  			//加载班级下的员工
		  			$('#dataList').datagrid("options").queryParams = {professionid: newValue};
		  			$('#dataList').datagrid("reload");
		  		}
		  	});
		  	
		  //工种下拉框
		  	$("#authorityList").combobox({
		  		width: "100",
		  		height: "25",
		  		valueField: "id",
		  		textField: "name",
		  		multiple: false, //可多选
		  		editable: false, //不可编辑
		  		method: "post",
		  		url: "AuthorityServlet?method=AuthorityList&t="+new Date().getTime(),
		  		onChange: function(newValue, oldValue){
		  			//加载班级下的员工
		  			$('#dataList').datagrid("options").queryParams = {authorityid: newValue};
		  			$('#dataList').datagrid("reload");
		  		}
		  	});
	  	
		  //下拉框通用属性
		  	$("#add_departmentList, #edit_departmentList, #add_professionList, #edit_professionList, #add_authorityList, #edit_authorityList, #new_departmentList").combobox({
		  		width: "200",
		  		height: "25",
		  		valueField: "id",
		  		textField: "name",
		  		multiple: false, //可多选
		  		editable: false, //不可编辑
		  		method: "post",
		  	});
		  	
		  	$("#add_departmentList").combobox({
		  		url: "DepartmentServlet?method=DepartmenttoEmployeeList&t="+new Date().getTime(),
				onLoadSuccess: function(){
					//默认选择第一条数据
					var data = $(this).combobox("getData");
					$(this).combobox("setValue", data[0].id);
		  		}
		  	});
		  	$("#add_professionList").combobox({
		  		url: "ProfessionServlet?method=ProfessionList&t="+new Date().getTime(),
		  		onLoadSuccess: function(){
			  		//默认选择第一条数据
					var data = $(this).combobox("getData");;
					$(this).combobox("setValue", data[0].id);
		  		}
		  	});
		  	
		  	$("#add_authorityList").combobox({
		  		url: "AuthorityServlet?method=AuthorityList&t="+new Date().getTime(),
		  		onLoadSuccess: function(){
			  		//默认选择第一条数据
					var data = $(this).combobox("getData");;
					$(this).combobox("setValue", data[0].id);
		  		}
		  	});
		  	$("#edit_departmentList").combobox({
		  		url: "DepartmentServlet?method=DepartmenttoEmployeeList&t="+new Date().getTime(),
		  		/* onChange: function(newValue, oldValue){
		  			//加载该年级下的班级
		  			$("#edit_clazzList").combobox("clear");
		  			$("#edit_clazzList").combobox("options").queryParams = {gradeid: newValue};
		  			$("#edit_clazzList").combobox("reload");
		  		}, */
				onLoadSuccess: function(){
					//默认选择第一条数据
					var data = $(this).combobox("getData");
					$(this).combobox("setValue", data[0].id);
		  		}
		  	});
		  	
		  	$("#new_departmentList").combobox({
		  		url: "DepartmentServlet?method=DepartmenttoEmployeeList&t="+new Date().getTime(),
		  	
				onLoadSuccess: function(){
					//默认选择第一条数据
					var data = $(this).combobox("getData");
					$(this).combobox("setValue", data[0].id);
		  		}
		  	});
		  	
		  	$("#edit_professionList").combobox({
		  		url: "ProfessionServlet?method=ProfessionList&t="+new Date().getTime(),
				onLoadSuccess: function(){
					//默认选择第一条数据
					var data = $(this).combobox("getData");
					$(this).combobox("setValue", data[0].id);
		  		}
		  	});
		  	$("#edit_authorityList").combobox({
		  		url: "AuthorityServlet?method=AuthorityList&t="+new Date().getTime(),
				onLoadSuccess: function(){
					//默认选择第一条数据
					var data = $(this).combobox("getData");
					$(this).combobox("setValue", data[0].id);
		  		}
		  	});
	  	
		  //设置添加员工窗口
		    $("#addDialog").dialog({
		    	title: "添加员工",
		    	width: 650,
		    	height: 520,
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
								var departmentid = $("#add_departmentList").combobox("getValue");
								var professionid = $("#add_professionList").combobox("getValue");
								var authorityid = $("#add_authorityList").combobox("getValue");
								$.ajax({
									type: "post",
									url: "EmployeeServlet?method=AddEmployee",
									data: $("#addForm").serialize(),
									success: function(msg){
										if(msg == "success"){
											$.messager.alert("消息提醒","添加成功!","info");
											//关闭窗口
											$("#addDialog").dialog("close");
											//清空原表格数据										
											$("#add_name").textbox('setValue', "");
											$("#add_loginname").textbox('setValue', "");
											$("#add_sex").textbox('setValue', "男");
											$("#add_birthday").textbox('setValue', "");
											$("#add_tel").textbox('setValue', "");
											$("#add_address").textbox('setValue', "");
											var data = $("#add_jobstate").combobox("getData");
											$("#add_jobstate").combobox("setValue", data[0].id);
											$("#add_cardnumber").textbox('setValue', "");
											$("#add_remark").textbox('setValue', "");
											//重新刷新页面数据
											//$('#dataList').datagrid("options").queryParams = {clazzid: clazzid};
								  			$('#dataList').datagrid("reload");
								  			/* $("#departmentList").combobox('setValue', gradeid);
								  			setTimeout(function(){
												$("#clazzList").combobox('setValue', clazzid);
											}, 100); */
											
										} else if(msg=="duplicate"){
											$.messager.alert("消息提醒","添加登录名已经存在!","warning");
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
							$("#add_loginname").textbox('setValue', "");
							$("#add_sex").textbox('setValue', "男");
							$("#add_birthday").textbox('setValue', "");
							$("#add_tel").textbox('setValue', "");
							$("#add_address").textbox('setValue', "");
							var data = $("#add_jobstate").combobox("getData");
							$("#add_jobstate").combobox("setValue", data[0].id);
							$("#add_cardnumber").textbox('setValue', "");
							$("#add_remark").textbox('setValue', "");
							//重新部门、工种和权限
							$("#add_departmentList").combobox("clear");
							$("#add_departmentList").combobox("reload");
							$("#add_professionList").combobox("clear");
							$("#add_professionList").combobox("reload");
							$("#add_authorityList").combobox("clear");
							$("#add_authorityList").combobox("reload");
						}
					},
				]
		    });
	  	
		  //设置编辑员工窗口
		    $("#editDialog").dialog({
		    	title: "修改员工信息",
		    	width: 680,
		    	height: 520,
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
							var departmentid = $("#edit_departmentList").combobox("getValue");
							var newdepartmentid = $("#new_departmentList").combobox("getValue");
							var professionid = $("#edit_professionList").combobox("getValue");
							var authorityid = $("#edit_authorityList").combobox("getValue");
							
							if(!validate){
								$.messager.alert("消息提醒","请检查你输入的数据!","warning");
								return;
							} else{
								$.ajax({
									type: "post",
									url: "EmployeeServlet?method=EditEmployee&t="+new Date().getTime(),
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
							$("#edit_name").textbox('setValue', "");
							$("#edit_loginname").textbox('setValue', "");
							var data = $("#edit_sex").combobox("getData");
							$("#edit_sex").combobox("select", data[0].value);							
							$("#edit_birthday").textbox('setValue', "");
							$("#edit_tel").textbox('setValue', "");
							$("#edit_address").textbox('setValue', "");
							var data = $("#edit_jobstate").combobox("getData");
							$("#edit_jobstate").combobox("select", data[0].value);
							$("#edit_cardnumber").textbox('setValue', "");
							$("#edit_remark").textbox('setValue', "");
							//重新部门、工种和权限
							$("#new_departmentList").combobox("clear");
							$("#new_departmentList").combobox("reload");
							setTimeout(function(){
								$("#edit_professionList").combobox("clear");
								$("#edit_professionList").combobox("reload");
								}, 100);
							setTimeout(function(){
								$("#edit_authorityList").combobox("clear");
								$("#edit_authorityList").combobox("reload");
								}, 200);
							
						}
					}
				],
				onBeforeOpen: function(){
					var selectRow = $("#dataList").datagrid("getSelected");
					//设置值
						$("#edit_name").textbox('setValue', selectRow.name);
							$("#edit_loginname").textbox('setValue', selectRow.loginname);
							$('#edit_loginname').textbox('textbox').attr('readonly',true); 
							$("#edit_sex").textbox('setValue',selectRow.sex);
							$("#edit_birthday").textbox('setValue', selectRow.birthday);
							$("#edit_tel").textbox('setValue', selectRow.tel);
							$("#edit_address").textbox('setValue', selectRow.address);
							$("#edit_jobstate").combobox("setValue",selectRow.jobstate);
							$("#edit_cardnumber").textbox('setValue', selectRow.cardnumber);
							$("#edit_remark").textbox('setValue', selectRow.remark);
							
							var departmentid = selectRow.departmentid;
							var professionid = selectRow.professionid;
							var authorityid = selectRow.authorityid;
							//重新部门、工种和权限
							$("#edit_departmentList").combobox("setValue",departmentid);
							 setTimeout(function(){
									$("#new_departmentList").combobox("setValue",departmentid);
								}, 100);  
							 setTimeout(function(){
								 $("#edit_professionList").combobox("setValue",professionid);
								}, 100); 
							
							 setTimeout(function(){
								$("#edit_authorityList").combobox("setValue",authorityid);
							}, 100);  
							
					
				}
		    });
		  
		  //重置查询条件
		     $("#qreset").click(function(){
		    
		    	$("#departmentList").textbox('setValue', "");
				$("#professionList").textbox('setValue', "");
				$("#authorityList").textbox('setValue', "");
				$("#queryname").textbox('setValue', "");
				$("#querytel").textbox('setValue', "");
				$("#querycard").textbox('setValue', "");
		    });
		});
	
	</script>
</head>
<body>
	<!-- 员工列表 -->
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
		<div style="float: left; margin: 0 5px 0 5px">部门：<input id="departmentList" class="easyui-textbox" name="department" /></div>
		<div style="float: left; margin: 0 5px 0 5px">工种：<input id="professionList" class="easyui-textbox" name="profession" /></div>
		<div style="float: left; margin: 0 5px 0 5px">权限：<input id="authorityList" class="easyui-textbox" name="authority" /></div>
		<div style="float: left; margin: 0 5px 0 5px">姓名：<input id="queryname" style="width: 100px; height: 25px;" class="easyui-textbox" name="qname" /></div>
		<div style="float: left; margin: 0 5px 0 5px">电话：<input id="querytel" style="width: 100px; height: 25px;" class="easyui-textbox" name="qtel" /></div>
		<div style="float: left; margin: 0 5px 0 5px">卡号：<input id="querycard" style="width: 100px; height: 25px;" class="easyui-textbox" name="qcardnumber" /></div>
		<div style="float: left; margin: 0 5px 0 5px"><a id="query" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true">查询</a></div>
		<div style="margin-left: 10px;"><a id="qreset" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true">重置</a></div>
		
	</div>
	
	<!-- 添加员工窗口 -->
	<div id="addDialog" style="padding: 10px">  
		<div style="float: right; margin: 20px 20px 0 0; width: 200px; border: 1px solid #EBF3FF" id="photo">
	    	<img alt="照片" style="max-width: 200px; max-height: 400px;" title="照片" src="photo/student.jpg" />
	    </div> 
    	<form id="addForm" method="post">
	    	<table cellpadding="2" >
	    		<tr>
	    			<td>姓名:</td>
	    			<td><input id="add_name" style="width: 200px; height: 25px;" class="easyui-textbox" type="text" name="name" data-options="required:true, missingMessage:'请填写姓名'" value="王利" /></td>
	    		</tr>
	    		<tr>
	    			<td>用户名:</td>
	    			<td><input id="add_loginname" style="width: 200px; height: 25px;" class="easyui-textbox" type="text" name="loginname" data-options="required:true, missingMessage:'请填写用户名'" value="wangli"  /></td>
	    		</tr>
	    		<tr>
	    			<td>性别:</td>
	    			<td><select id="add_sex" class="easyui-combobox" data-options="editable: false, panelHeight: 50, width: 60, height: 30" name="sex"><option value="男">男</option><option value="女">女</option></select></td>
	    		</tr>
	    		<tr>
	    			<td>出生日期:</td>
	    			<td><input id="add_birthday" style="width: 200px; height: 25px;" class="easyui-datebox" type="text" name="birthday" data-options="required:true, missingMessage:'请填写出生日期'" /></td>
	    		</tr>
	    		<tr>
	    			<td>电话:</td>
	    			<td><input id="add_tel" style="width: 200px; height: 25px;" class="easyui-textbox" type="text" name="tel" validType="mobile" data-options="required:true, missingMessage:'请填写电话号码'"  value="18055442266" /></td>
	    		</tr>
	    		<tr>
	    			<td>地址:</td>
	    			<td><input id="add_address" style="width: 200px; height: 50px;" class="easyui-textbox" data-options="multiline: true,"  type="text" name="address" data-options="required:true, missingMessage:'请填写地址"  value="安徽省淮南市田家庵区"  /></td>
	    		</tr>
	    		<tr>
	    			<td>部门:</td>
	    			<td><input id="add_departmentList" style="width: 200px; height: 25px;" class="easyui-textbox" name="departmentid" /></td>
	    		</tr>
	    		<tr>
	    			<td>工种:</td>
	    			<td><input id="add_professionList" style="width: 200px; height: 25px;" class="easyui-textbox" name="professionid" /></td>
	    		</tr>
	    		<tr>
	    			<td>权限:</td>
	    			<td><input id="add_authorityList" style="width: 200px; height: 25px;" class="easyui-textbox" name="authorityid" /></td>
	    		</tr>
	    		<tr>
	    			<td>在职情况:</td>
	    			<td><select id="add_jobstate" class="easyui-combobox" data-options="editable: false, panelHeight: 50, width: 60, height: 25" name="jobstate"><option value="在职">在职</option><option value="退休">退休</option><option value="离岗">离岗</option></select></td>
	    		</tr>
	    		<tr>
	    			<td>卡号:</td>
	    			<td><input id="add_cardnumber" style="width: 200px; height: 25px;" class="easyui-textbox" type="text" name="cardnumber" validType="mobile" data-options="required:true, missingMessage:'请填写卡号'"  value="15055449988" /></td>
	    		</tr>
	    		<tr>
	    			<td>备注:</td>
	    			<td><input id="add_remark" style="width: 200px; height: 50px;" class="easyui-textbox" data-options="multiline: true,"  type="text" name="remark"/></td>
	    		</tr>
	    	</table>
	    </form>
	</div>
	
	<!-- 修改员工窗口 -->
	<div id="editDialog" style="padding: 10px">
		<div style="float: right; margin: 20px 20px 0 0; width: 200px; border: 1px solid #EBF3FF">
	    	<img id="edit_photo" alt="照片" style="max-width: 200px; max-height: 400px;" title="照片" src="photo/student.jpg" />
	    </div>   
    	<form id="editForm" method="post">
	    	<table cellpadding="2" >
	    		<tr>
	    			<td>姓名:</td>
	    			<td><input id="edit_name" style="width: 200px; height: 25px;" class="easyui-textbox" type="text" name="name" data-options="required:true, missingMessage:'请填写姓名'" value="王利" /></td>
	    		</tr>
	    		<tr>
	    			<td>用户名:</td>
	    			<td><input id="edit_loginname" style="width: 200px; height: 25px;" class="easyui-textbox" type="text" name="loginname" data-options="required:true, missingMessage:'请填写用户名'" value="wangli"  /></td>
	    		</tr>
	    		<tr>
	    			<td>性别:</td>
	    			<td><select id="edit_sex" class="easyui-combobox" data-options="editable: false, panelHeight: 50, width: 60, height: 30" name="sex"><option value="男">男</option><option value="女">女</option></select></td>
	    		</tr>
	    		<tr>
	    			<td>出生日期:</td>
	    			<td><input id="edit_birthday" style="width: 200px; height: 25px;" class="easyui-datebox" type="text" name="birthday" data-options="required:true, missingMessage:'请填写出生日期'" /></td>
	    		</tr>
	    		<tr>
	    			<td>电话:</td>
	    			<td><input id="edit_tel" style="width: 200px; height: 25px;" class="easyui-textbox" type="text" name="tel" validType="mobile" data-options="required:true, missingMessage:'请填写电话号码'"  value="18055442266" /></td>
	    		</tr>
	    		<tr>
	    			<td>地址:</td>
	    			<td><input id="edit_address" style="width: 200px; height: 50px;" class="easyui-textbox" data-options="multiline: true,"  type="text" name="address" data-options="required:true, missingMessage:'请填写地址"  value="安徽省淮南市田家庵区"  /></td>
	    		</tr>
	    		<tr>
	    			<td>部门:</td>
	    			<td><input id="edit_departmentList" style="width: 200px; height: 25px;" class="easyui-textbox" name="departmentid" data-options="readonly:true"/></td>
	    		</tr>
	    		<tr>
	    			<td>新部门:</td>
	    			<td><input id="new_departmentList" style="width: 200px; height: 25px;" class="easyui-textbox" name="newdepartmentid" /></td>
	    		</tr>
	    		<tr>
	    			<td>工种:</td>
	    			<td><input id="edit_professionList" style="width: 200px; height: 25px;" class="easyui-textbox" name="professionid" /></td>
	    		</tr>
	    		<tr>
	    			<td>权限:</td>
	    			<td><input id="edit_authorityList" style="width: 200px; height: 25px;" class="easyui-textbox" name="authorityid" /></td>
	    		</tr>
	    		<tr>
	    			<td>在职情况:</td>
	    			<td><select id="edit_jobstate" class="easyui-combobox" data-options="editable: false, panelHeight: 50, width: 60, height: 25" name="jobstate"><option value="在职">在职</option><option value="退休">退休</option><option value="离岗">离岗</option></select></td>
	    		</tr>
	    		<tr>
	    			<td>卡号:</td>
	    			<td><input id="edit_cardnumber" style="width: 200px; height: 25px;" class="easyui-textbox" type="text" name="cardnumber" validType="mobile" data-options="required:true, missingMessage:'请填写卡号'"  value="15055449988" /></td>
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