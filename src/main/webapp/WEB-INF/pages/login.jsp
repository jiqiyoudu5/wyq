<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix='sec'
	uri='http://www.springframework.org/security/tags'%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html class="login-bg">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title><spring:message code="login.title" /></title>
<link rel="stylesheet" href="resources/css/login.css" />
<!--为了让IE支持HTML5元素，做如下操作：-->
<!--[if IE]> 
<script type="text/javascript"> 
document.createElement("section"); 
document.createElement("header"); 
document.createElement("footer"); 
</script> 
<![endif]-->
</head>
<script type="text/javascript" language="javascript">
	function changeimg() {
	document.getElementById("code").src = "validate/yzm/get?rnd="+new Date().getTime();
	}
</script>
<body>
<div class="message">${sessionScope['SPRING_SECURITY_LAST_EXCEPTION'].message }${message}</div>
	<div class="wrap">
		<div class="logo">
			<a href="#"><img src="resources/img/big_logo.png" /></a>
		</div>
		<div style="height: 30px;"></div>
		<form action="./wyq_login" method="post"><!-- j_spring_security_check -->
			<section class="loginForm">
			<header>
			<h1><spring:message code="login.banner" /></h1>
			</header>
				<fieldset>
					<div class="inputWrap">
						<input type="text" name="wyq_username" placeholder="<spring:message code="login.input.username.placeholder"/>" autofocus required>
					</div>
					<div class="inputWrap">
						<input type="password" name="wyq_password" placeholder="<spring:message code="login.input.password.placeholder"/>" required>
					</div>
					<div class="inputyzm">
						<input type="text" value="ss" name="validateCode" placeholder="<spring:message code="login.input.yzm.placeholder"/>" required>
					</div>
				</fieldset>
				<fieldset>
					<img src="validate/yzm/get" id="code" border=0 /><a href="javascript:void(0);" onclick="changeimg()">看不清楚，换一张</a>
				</fieldset>
				<fieldset>
					<input type="submit" value='<spring:message code="login.button.login"/>'>
					<input id="_spring_security_remember_me" type="checkbox" name="_spring_security_remember_me" /><span><spring:message code="login.input.rememberme" /></span>
				</fieldset>
			</section>
		</form>
	</div>
</body>
</html>
