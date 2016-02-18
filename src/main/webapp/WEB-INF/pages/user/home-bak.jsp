<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>WYQ普通用户员后台</title>
<link href="../resources/css/userhome.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript"
	src="../resources/js/jquery-1.11.3.main.js"></script>
<script type="text/javascript">
	$(function() {
		//导航切换
		$(".menuson li").click(function() {
			$(".menuson li.active").removeClass("active")
			$(this).addClass("active");
		});

		$('.title').click(function() {
			var $ul = $(this).next('ul');
			$('dd').find('ul').slideUp();
			if ($ul.is(':visible')) {
				$(this).next('ul').slideUp();
			} else {
				$(this).next('ul').slideDown();
			}
		});
	})

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
	}
	var CURRENT_USER = {
		username : '${currentAccount.username }'
	};
</script>

</head>

<body>
	<div class="container">

		<!-- header -->
		<div class="header">
			<img src="../resources/img/logo2.png" />
		</div>
		<!-- header end -->

		<!-- main -->
		<div class="main">

			<!-- left -->
			<div class="left">
				<div class="lefttop">
					<span></span>菜单栏
				</div>

				<dl class="leftmenu">

					<dd>
						<div class="title">
							<span><img src="../resources/img/user.png" /></span>管理信息
						</div>
						<ul class="menuson">
							<li><cite></cite><a href="index.html" target="rightFrame">首页模版</a><i></i></li>
							<li class="active"><cite></cite><a href="right.html"
								target="rightFrame">数据列表</a><i></i></li>
							<li><cite></cite><a href="imgtable.html" target="rightFrame">图片数据表</a><i></i></li>
							<li><cite></cite><a href="form.html" target="rightFrame">添加编辑</a><i></i></li>
							<li><cite></cite><a href="imglist.html" target="rightFrame">图片列表</a><i></i></li>
							<li><cite></cite><a href="imglist1.html" target="rightFrame">自定义</a><i></i></li>
							<li><cite></cite><a href="tools.html" target="rightFrame">常用工具</a><i></i></li>
							<li><cite></cite><a href="filelist.html" target="rightFrame">信息管理</a><i></i></li>
							<li><cite></cite><a href="tab.html" target="rightFrame">Tab页</a><i></i></li>
							<li><cite></cite><a href="error.html" target="rightFrame">404页面</a><i></i></li>
						</ul>
					</dd>


					<dd>
						<div class="title">
							<span><img src="images/leftico02.png" /></span>其他设置
						</div>
						<ul class="menuson">
							<li><cite></cite><a href="#">编辑内容</a><i></i></li>
							<li><cite></cite><a href="#">发布信息</a><i></i></li>
							<li><cite></cite><a href="#">档案列表显示</a><i></i></li>
						</ul>
					</dd>


					<dd>
						<div class="title">
							<span><img src="images/leftico03.png" /></span>编辑器
						</div>
						<ul class="menuson">
							<li><cite></cite><a href="#">自定义</a><i></i></li>
							<li><cite></cite><a href="#">常用资料</a><i></i></li>
							<li><cite></cite><a href="#">信息列表</a><i></i></li>
							<li><cite></cite><a href="#">其他</a><i></i></li>
						</ul>
					</dd>


					<dd>
						<div class="title">
							<span><img src="images/leftico04.png" /></span>日期管理
						</div>
						<ul class="menuson">
							<li><cite></cite><a href="#">自定义</a><i></i></li>
							<li><cite></cite><a href="#">常用资料</a><i></i></li>
							<li><cite></cite><a href="#">信息列表</a><i></i></li>
							<li><cite></cite><a href="#">其他</a><i></i></li>
						</ul>

					</dd>

				</dl>

			</div>
			<!-- left end -->

			<!-- content -->
			<div class="content">
				<iframe name="content" frameborder="0" height="100%" width="100%"
					scrolling="auto"></iframe>
			</div>
			<!-- content end -->

		</div>
		<!-- mian end -->

	</div>
	<!-- container end -->

</body>
</html>