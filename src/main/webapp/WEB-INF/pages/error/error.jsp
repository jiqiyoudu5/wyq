<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<title>${title }</title>
	<link type="text/css" rel="stylesheet"
		href="<%=basePath%>resources/css/error.css">
</head>
<body>
	<div class="w">
		<div id="logo">
			<!-- <a href="<%=basePath%>"><img src="<%=basePath%>resources/img/logo.png"
				alt="WYQ" width="190" height="80"></a> --><b></b>
		</div>
	</div>
	<div class="w1" id="entry">
		<div class="extra-en">
			<a href="#">首页 </a> | 
			<a href="./login"> 登录</a>
		</div>
		<div class="mc">
			<div id="entry-bg" style="width: 511px; height: 455px; position: absolute; left: -44px; top: -44px; background: url(<%=basePath%>resources/img/board.png) 0px 0px no-repeat;">
			<div class="content"><h1>广告位出租</h1></div>
			</div>
			<div class="form">
				<h1>${title }</h1>
				<p>${message }</p>
			</div>
		</div>
		<div class="free-regist">
			<span onclick="history.back()">返回 &gt;&gt;</span>
		</div>
	</div>
	<div class="w1">
		<div id="mb-bg" class="mb"></div>
	</div>
	<div class="w">
		<div id="footer-2013">
			<div class="links">
				<a rel="nofollow" target="_blank" href="#">关于我们</a>| <a
					rel="nofollow" target="_blank" href="#">联系我们</a>| <a rel="nofollow"
					target="_blank" href="#">商家入驻</a>| <a rel="nofollow"
					target="_blank" href="#">广告服务</a>| <a target="_blank" href="#">友情链接</a>|
				<a href="#" target="_blank">WYQ</a>
			</div>
			<div class="copyright">Copyright&#169;2004-2014&nbsp;&nbsp;WYQ.com&nbsp;版权所有</div>
		</div>
	</div>
</body>
</html>