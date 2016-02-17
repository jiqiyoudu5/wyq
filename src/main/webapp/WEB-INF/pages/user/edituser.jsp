<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta content="IE=8.0000" http-equiv="X-UA-Compatible">
<base href="<%=basePath%>">
<title>无标题文档</title>
<link href="<%=basePath%>user/css/edituser_tab.css" rel="stylesheet" type="text/css" />
<script src="<%=basePath%>plugin/jquery/jquery-1.11.3.main.js"></script>
  
<script type="text/javascript">
$(function(){	
	//tab切换
	$(".itab li").click(function(){
		$(".itab li.selected").removeClass("selected")
		$(this).addClass("selected");
	});
})	
</script>
</head>

<body>
    <div class="tabbody">
    
    <div class="itab">
  	<ul> 
    <li class="selected"><a href="<%=basePath%>headphoto" target="tabFrame">上传Logo</a></li> 
    <li><a href="mb.html" target="tabFrame">基本信息</a></li> 
     <li><a href="#tab2" target="tabFrame">密码</a></li>
      <li><a href="<%=basePath%>headphoto" target="tabFrame">自定义</a></li>
  	</ul>
    </div> 
    
    <div class="tabcontent">
    <iframe src="<%=basePath%>headphoto" name="tabFrame" frameborder="0" scrolling="no" height="100%" width="100%"/>
    </div>
     
    </div>
</body>

</html>
