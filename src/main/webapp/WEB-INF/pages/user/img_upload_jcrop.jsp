<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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

<link href="<%=basePath%>plugin/fineuploader/fineuploader-3.1.css" rel="stylesheet" />
<link href="<%=basePath%>plugin/bootstrap/bootstrap-2.3.2.min.css" rel="stylesheet" />
<link href="<%=basePath%>plugin/bootstrap/bootstrap-responsive-2.3.2.min.css" rel="stylesheet" />
<link href="<%=basePath%>plugin/Jcrop/jquery.Jcrop.min.css" rel="stylesheet" />
<link href="<%=basePath%>user/css/crop.min.css" rel="stylesheet" />

<script src="<%=basePath%>plugin/jquery/jquery-1.11.3.main.js"></script>
<script src="<%=basePath%>plugin/bootstrap/bootstrap-2.3.2.min.js"></script>
<script src="<%=basePath%>plugin/fineuploader/jquery.fineuploader-3.3.0.min.js"></script>
<script src="<%=basePath%>plugin/Jcrop/jquery.Jcrop.min.js"></script>
<script src="<%=basePath%>plugin/jquery/jquery.form.min.js"></script>
<script src="<%=basePath%>user/js/crop.min.js"></script>
<script type="text/javascript">
	/*
	 * ifram中获取父页面中元素并更新值
	 */
	//方法1	
	//var w = parent.document.getElementById('user_logo');
	//w.src="123";
	//方法2
	$(function() {
		//获取ifram父页面
		//var w = $(window.parent.document).find("#user_logo");
		//获取ifram父页面iframe的父页面
		var w = $(window.parent.parent.document).find("#user_logo");
		//console.log(w.attr("src"));
		$("#preview_small").attr("src", w.attr('src'));
		$("#preview_large").attr("src", w.attr('src'));
		//w.attr("src", 'ssss');
	});
</script>
</head>
<body>
	<div id="jquery-wrapped-fine-uploader"></div>
	<div style="clear: both"></div>
	<div id="message" style="font-size: 18px; color: #F4080C; margin-left: 30px;"></div>
	<div id="crop_wrap">
		<div id="crop_holder">
			<div id="crop_area" class="border">
				<img id="crop_image" alt="" src="" class="preview-image" style="display: none" />
			</div>
			<div id="preview_area">
				<div id="preview_title">当前Logo</div>
				<div id="preview_small_text" class="preview-text">80px × 40px</div>
				<div id="preview_small_wrap" class="border">
					<img id="preview_small" alt="" src="" class="preview-image" style="" />
				</div>
				<div id="preview_large_text" class="preview-text">160px × 80px</div>
				<div id="preview_large_wrap" class="border">
					<img id="preview_large" alt="" src="" class="preview-image" style="" />
				</div>
			</div>
		</div>
		<div id="crop_operation" style="display: none;">
			<form id="form_crop" action="<%=basePath%>sys/file/crop_form" method="post">
				<input type="hidden" name="x" id="x">
				<input type="hidden" name="y" id="y">
				<input type="hidden" name="w" id="w">
				<input type="hidden" name="h" id="h">
				<input type="hidden" name="imgsrc" id="imgsrc">
				<input type="hidden" name="type" value="402883e04edc8370014edcd050340000">
				<input id="crop_operation_submit" type="submit" class="btn btn-primary btn-large" value="裁切并保存"/>
				<span id="crop_operation_msg" style="display: none" class="green"></span>
			</form>
		</div>
		<div id="croped_message" class="green"></div>
	</div>
</body>
</html>