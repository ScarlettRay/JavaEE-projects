<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp"%>
<%@page isErrorPage="true"%>
<html>
<head>
<title>系统异常</title>
<style type="text/css"> 
* {
	margin: 0;
	padding: 0;
}
/*body {background: #666666;color: #FEFEFE;}
		.W-body {background: #666;}*/
.e-4-wrap {
	background: #3b3b3b url(${ctximg}/static/sns/images/err-404.jpg)
		no-repeat 0 0;
	margin: 5% auto 0;
	width: 640px;
	overflow: hidden;
}

.e-4-wrap .fl-1, .e-4-wrap .fl-2 {
	float: left;
	height: 420px;
	position: relative;
}

.e-4-wrap .fl-1 {
	width: 283px;
	text-align: center;
}

.e-4-wrap .fl-2 {
	width: 357px;
}

.e-4-title {
	position: absolute;
	top: 302px;
	width: 283px;
}

.e-4-title span {
	color: #fff;
	font: 700 90px/100px 'SimHei';
}

.e-4-txt-wrap {
	padding: 20px;
	overflow: hidden;
}

.e-4-txt-wrap h2 {
	font: 30px/60px 'SimHei';
	color: #DD4C39;
	margin-bottom: 10px;
}

.e-4-t-1 {
	font: 15px/24px 'SimHei';
	color: #777;
}

.e-4-txt-wrap h4 {
	font: 18px/26px 'SimHei';
	color: #aaa;
}

.e-4-t-1 a {
	color: #2F75BB;
}
</style>
<script type="text/javascript">
	function showOrCloseErrorInfo() {
		var div = document.getElementById("error_div");
		if(div.style.display == "none") {
			div.style.display = "block";
		} else {
			div.style.display = "none";
		}
	}
</script>
</head>
<body>

	<!-- 主体 开始-->
	<div class="e-4-wrap">
		<div class="fl-1">
			<div class="e-4-title">
				<span>ERROR</span>
			</div>
		</div>
		<div class="fl-2">
			<div class="e-4-txt-wrap">
				<h2>系统访问异常！</h2>
				<div>
					<p class="e-4-t-1">无法访本页的原因是：</p>
					<p class="e-4-t-1">你使用的URL可能拼写错误或者它只是临时脱机</p>
					<p class="e-4-t-1">所访问的页面不存在或被管理员已删除</p>
				</div>
				<br> <br>
				<div>
					<h4>请尝试以下操作：</h4>
					<p class="e-4-t-1">1、尝试按F5进行页面刷新</p>
					<p class="e-4-t-1">2、重新键入URL地址进入访问</p>
					<p class="e-4-t-1">
						3、或者去
						<a href="${ctx }" title="首页">首页</a>
						,
						<a href="javascript:window.history.go(-1)" title="返回">返回</a>
					</p>
					<p class="e-4-t-1">
						<a href="javascript:void(0)" onclick="showOrCloseErrorInfo()" class="c-green"> 查看详细异常</a>
					</p>
				</div>
			</div>
		</div>
	</div>
	<div id="error_div" style="display: none" align="left">
		<%
			 	//show error message
			 	if(exception!=null){
			 	   StackTraceElement[] messages = exception.getStackTrace();
			        if (messages!=null) {
			            StringBuffer buffer = new StringBuffer();
			            buffer.append(exception.toString()).append("<br/>");
			            for (int i = 0; i < messages.length; i++) {
			                buffer.append(messages[i].toString()).append("<br/>");
			            }
			            out.println(buffer.toString());
			        }
			 	}
			 	 else{
		            out.println(request.getAttribute("myexception").toString());
		        }  
	           %>
	</div>
</body>
</html>