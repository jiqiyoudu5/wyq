<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta content="IE=8.0000" http-equiv="X-UA-Compatible">
<base href="<%=basePath%>">
<title></title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<title>WYQ普通用户员后台</title>
<link href="<%=basePath%>user/css/userhome.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=basePath%>plugin/jquery/jquery-1.11.3.main.js"></script>
<script type="text/javascript">
	//登录用户属性
	var LOGGEDUSERS = [];
	<c:forEach items="${loggedUsers}" var="uinfo">
	LOGGEDUSERS
			.push({
				username : '${uinfo.key.user.username}',
				lastActivity : '<fmt:formatDate value="${uinfo.value}" pattern="yyyy-MM-dd HH:mm:ss"/>'
			});
	</c:forEach>
	var LOGGEDUSERS_SIZE = {
		size : '${loggedUserCount}'
	};
	var CURRENT_USER = {
		username : '${currentAccount.username }'
	};

	//退出
	function exit() {
		window.location = "<%=basePath%>sys/wyq_logout";
	}
	
	/*
	* 通用方法对json数据排序
	*
	* filed 排序依据的字段
	* order 排序方式(desc、asc)
	* primer 排序字符类型(parseInt/String/...等)
	*
	*/
	var sortBy = function (filed, order, primer) {
		var rev = false;
		if(order==='asc'){
			rev = true;
		}
	    rev = (rev) ? -1 : 1;
	    return function (a, b) {
	        a = a[filed];
	        b = b[filed];
	        if (typeof (primer) != 'undefined') {
	            a = primer(a);
	            b = primer(b);
	        }
	        if (a < b) { return rev * -1; }
	        if (a > b) { return rev * 1; }
	        return 1;
	    }
	};
	

	//jquery代码
	$(function() {
		
		//页面显示用户名
		$("#username").append(CURRENT_USER.username);
		
		//加载用户菜单、logo
		$.getJSON("<%=basePath%>user/start/loadmenu/byuser", function(data) {
			//logo
			var src ="sys/file/img/"+data.logourl;
			$("#user_logo").attr("src",src);
			//菜单
			data.menus.sort(sortBy('orderValue', "desc", parseInt));
			$(".menuson").html("");
			$.each(data.menus, function(i, item) {
				var src = "sys/"+item.icon;
				var href = "<%=basePath%>"+item.value;
				if(item.text==="首页"){
					$(".menuson").append("<li class='active'><a href='"+href+"' target='userhome_frame'><img src="+src+" /><span>"+item.text+"</span></a><i></i></li>");
				}else{
					$(".menuson").append("<li><a href='"+href+"' target='userhome_frame'><img src="+src+" /><span>"+item.text+"</span></a><i></i></li>");
				}
			});
		});
		
		//菜单切换效果
		$(".menuson").on("click","li", function() {
			$(".menuson li.active").removeClass("active")
			$(this).addClass("active");
		});
		
		
	});
	
</script>
</head>
<body>

	<div class="container">

		<!-- header -->
		<div class="header">
			<div class="header_logo">
				<a href="#"><img src="<%=basePath%>resources/img/logo2.png" /></a>
			</div>
			<ul class="header_right">
				<li><a href='<%=basePath%>edituser' target='userhome_frame'><span id="username"></span></a></li>
				<li><a href=""><img src="<%=basePath%>user/img/news.png" /></a></li>
				<li><a href='javascript:void(0);' onclick='exit()'><img
						src="<%=basePath%>user/img/user_exit.png" /></a></li>
			</ul>
		</div>
		<!-- header end -->

		<!-- main -->
		<div class="main">

			<!-- content -->
			<div class="content">
			<div class="contentmain">
				<iframe name="userhome_frame" frameborder="0" scrolling="no" height="100%" width="100%"></iframe>
			</div>
			</div>
			<!-- content end -->
			
			<!-- left -->
			<div class="leftmenu">
				<div class="leftmenu_user_logo">
					<img id="user_logo" src="" width="180px" height="90px" />
				</div>
				<ul class="menuson"></ul>
			</div>
			<!-- left end -->

		</div>
		<!-- mian end -->

	</div>
	<!-- container end -->
</body>
</html>