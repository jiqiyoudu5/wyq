<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta content="IE=8.0000" http-equiv="X-UA-Compatible">
<title>ZTB管理员后台</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<script type="text/javascript">
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
	}
	var CURRENT_USER = {
		username : '${currentAccount.username }'
	};
	var SYSCONFIG = {
		pageSize : 50
	};

	/*
	 * Date
	 */
	window.timeConvertor = function(v, record) {
		if (!v) {
			return null;
		}
		if (isNaN(v)) {
			return v;
		}
		var dateTime = new Date();
		dateTime.setTime(v);
		return dateTime;
	};
	/*
	 * 文件大小转换
	 */
	window.fileSizeTransform = function(v, record) {
		if (0 < v && v < 1024) {
			return Math.round(v * 100) / 100 + " Byte";
		} else if (1024 <= v && v < Math.pow(1024, 2)) {
			return Math.round(v * 100 / 1024) / 100 + " KB";
		} else if (Math.pow(1024, 2) <= v && v < Math.pow(1024, 3)) {
			return Math.round(v * 100 / Math.pow(1024, 2)) / 100 + " MB";
		} else if (Math.pow(1024, 3) <= v && v < Math.pow(1024, 4)) {
			return Math.round(v * 100 / Math.pow(1024, 3)) / 100 + " GB";
		} else {
			return Math.round(v * 100 / Math.pow(1024, 4)) / 100 + " TB";
		}
	}

	//获取顶层窗口
	function getRootWin() {
		var win = window;
		while (win != win.parent) {
			win = win.parent;
		}
		return win;
	}
</script>


<link rel="stylesheet" href="bootstrap.css">
<script src="ext/ext-dev.js"></script>
<script src="bootstrap.js"></script>
<script src="app.js"></script>


<!--
<link rel="stylesheet" href="build/production/EJ/resources/EJ-all.css"/>
<script type="text/javascript" src="build/production/EJ/app.js"></script>
-->
</head>
<body>
</body>
</html>
