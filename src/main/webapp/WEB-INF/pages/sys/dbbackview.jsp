<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!doctype html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>数据备份</title>
<style type="text/css">
body {
	font-family: '微软雅黑';
	font-size: 19px;
	margin: 0 auto;
}

.content {
	margin: 10% 10% 0 0;
	text-align: center;
}
</style>
<script type="text/javascript"
	src="/wyq/resources/js/jquery-1.11.3.main.js"></script>
<script type="text/javascript">
	$(function() {
		$(".content img").click(function() {
			$.get("../back");
			$(".content").html("");
			$(".content").append("<h2>后台正在备份数据库...,请稍后在【附件管理】中查看！</h2>");
		});
	});
</script>
</head>
<body>
	<div class="content">
		<img alt="备份数据库" src="/wyq/resources/img/dbback.png"><br />
		点击↑图片备份
	</div>
</body>
</html>