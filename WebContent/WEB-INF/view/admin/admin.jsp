<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>中控管理系统</title>
    <link rel="shortcut icon" href="favicon.ico"/>
	<link rel="bookmark" href="favicon.ico"/>
    <link rel="stylesheet" type="text/css" href="easyui/css/default.css" />
    <link rel="stylesheet" type="text/css" href="easyui/themes/default/easyui.css" />
    <link rel="stylesheet" type="text/css" href="easyui/themes/icon.css" />
    <script type="text/javascript" src="easyui/jquery.min.js"></script>
    <script type="text/javascript" src="easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src='easyui/js/outlook2.js'> </script>
    <script type="text/javascript">
	 var _menus = {"menus":[
						{"menuid":"1","icon":"","menuname":"人员管理",
							"menus":[
									{"menuid":"11","menuname":"工种管理","icon":"icon-exam","url":"ProfessionServlet?method=toProfessionListView"},
									{"menuid":"12","menuname":"权限管理","icon":"icon-exam","url":"AuthorityServlet?method=toAuthorityListView"},
									{"menuid":"13","menuname":"部门管理","icon":"icon-exam","url":"DepartmentServlet?method=toDepartmentListView"},
									{"menuid":"14","menuname":"人员管理","icon":"icon-exam","url":"EmployeeServlet?method=toEmployeeListView"},
									{"menuid":"15","menuname":"调动管理","icon":"icon-exam","url":"JobtransferServlet?method=toJobtransferListView"},
									{"menuid":"16","menuname":"考勤管理","icon":"icon-exam","url":"AuthorityServlet?method=toAuthorityListView"}
								]
						},
						{"menuid":"2","icon":"","menuname":"工艺管理",
							"menus":[
									{"menuid":"21","menuname":"工艺管理","icon":"icon-user-student","url":"AuthorityServlet?method=toAuthorityListView"},
								]
						},
						{"menuid":"3","icon":"","menuname":"设备管理",
							"menus":[
									{"menuid":"31","menuname":"设备管理","icon":"icon-user-teacher","url":"DeviceServlet?method=toDeviceListView"},
									{"menuid":"32","menuname":"入库管理","icon":"icon-user-teacher","url":"AuthorityServlet?method=toAuthorityListView"},
									{"menuid":"33","menuname":"出库管理","icon":"icon-user-teacher","url":"AuthorityServlet?method=toAuthorityListView"},
									{"menuid":"34","menuname":"厂家管理","icon":"icon-user-teacher","url":"SupplierServlet?method=toSupplierListView"},
									{"menuid":"35","menuname":"维护计划管理","icon":"icon-user-teacher","url":"AuthorityServlet?method=toAuthorityListView"},
									{"menuid":"36","menuname":"维护管理","icon":"icon-user-teacher","url":"AuthorityServlet?method=toAuthorityListView"},
									{"menuid":"37","menuname":"巡检管理","icon":"icon-user-teacher","url":"AuthorityServlet?method=toAuthorityListView"},
									{"menuid":"37","menuname":"巡检计划管理","icon":"icon-user-teacher","url":"AuthorityServlet?method=toAuthorityListView"}
								]
						},
						{"menuid":"4","icon":"","menuname":"异常管理",
							"menus":[
									{"menuid":"41","menuname":"异常管理","icon":"icon-world","url":"AuthorityServlet?method=toAuthorityListView"}
								]
						},
						{"menuid":"5","icon":"","menuname":"质量管理",
							"menus":[
							        {"menuid":"51","menuname":"质量设置","icon":"icon-set","url":"AuthorityServlet?method=toAuthorityListView"}
								]
						},
						{"menuid":"6","icon":"","menuname":"能耗管理",
							"menus":[
									{"menuid":"61","menuname":"能耗统计","icon":"icon-world","url":"AuthorityServlet?method=toAuthorityListView"}
								]
						},
						{"menuid":"7","icon":"","menuname":"看板管理",
							"menus":[
							        {"menuid":"71","menuname":"看板设置","icon":"icon-set","url":"AuthorityServlet?method=toAuthorityListView"},
							        {"menuid":"72","menuname":"发布指令","icon":"icon-set","url":"AuthorityServlet?method=toAuthorityListView"},
							        {"menuid":"73","menuname":"指令类型","icon":"icon-set","url":"AuthorityServlet?method=toAuthorityListView"}
								]
						}
				]};


    </script>

</head>
<body class="easyui-layout" style="overflow-y: hidden"  scroll="no">
	<noscript>
		<div style=" position:absolute; z-index:100000; height:2046px;top:0px;left:0px; width:100%; background:white; text-align:center;">
		    <img src="images/noscript.gif" alt='抱歉，请开启脚本支持！' />
		</div>
	</noscript>
    <div region="north" split="true" border="false" style="overflow: hidden; height: 80px;
        background: url(images/layout-browser-hd-bg.gif) #7f99be repeat-x center 50%;
        line-height: 20px;color: #fff; font-family: Verdana, 微软雅黑,黑体">
        <span style="float:right; padding-right:20px;" class="head"><span style="color:red; font-weight:bold;">${user.name}&nbsp;</span>您好&nbsp;&nbsp;&nbsp;<a href="SystemServlet?method=LoginOut" id="loginOut">安全退出</a></span>
        <span style="padding-left:10px; font-size: 16px; ">中控管理系统</span>
    </div>
    <div region="south" split="true" style="height: 30px; background: #D2E0F2; ">
        <div class="footer">Copyright &copy; zhb @ Aust</div>
    </div>
    <div region="west" hide="true" split="true" title="导航菜单" style="width:180px;" id="west">
	<div id="nav" class="easyui-accordion" fit="true" border="false">
		<!--  导航内容 -->
	</div>
	
    </div>
    <div id="mainPanle" region="center" style="background: #eee; overflow-y:hidden">
        <div id="tabs" class="easyui-tabs"  fit="true" border="false" >
			<jsp:include page="/WEB-INF/view/admin/welcome.jsp" />
		</div>
    </div>
	
	<iframe width=0 height=0 src="refresh.jsp"></iframe>
	
</body>
</html>