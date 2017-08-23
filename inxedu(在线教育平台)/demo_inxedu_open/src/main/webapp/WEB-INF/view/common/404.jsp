<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>404-INXEDU 在线教育平台-中国在线教育平台第一品牌</title>
<style type="text/css">
	* {margin: 0;padding: 0;}
	body {background-color: #fff;}
	.e-4-wrap {background: #fff url(${ctximg}/static/inxweb/img/404.jpg) no-repeat 0 0;width: 1000px;height: 423px;margin: 5% auto 0;overflow: hidden;}
	.e-4-wrap .fl-1,.e-4-wrap .fl-2 {float: left;height: 423px;position: relative;}
	.e-4-wrap .fl-1 {width: 630px;text-align: center;}
	.e-4-wrap .fl-2 {width: 368px;}
	.e-4-title {position: absolute;top: 302px;width: 283px;}
	.e-4-title span {color: #fff;font: 700 90px/100px 'SimHei';}
	.e-4-txt-wrap {padding: 20px;overflow: hidden;}
	.e-4-txt-wrap h2 {font: 30px/60px 'SimHei';color: #77ABDB;margin-bottom: 10px;}
	.e-4-t-1 {font: 15px/24px 'SimHei';color: #777;}
	.e-4-txt-wrap h4 {font: 18px/26px 'SimHei';color: #aaa;}
	.e-4-t-1 a {color: #77ABDB;}
</style>
</head>
<body>
	<div class="e-4-wrap">
		<div class="fl-1">
			<div class="e-4-title"><span>&nbsp;</span></div>
		</div>
		<div class="fl-2">
			<div class="e-4-txt-wrap">
				<h2>找不到该页面！</h2>
				<div>
					<p class="e-4-t-1">无法访本页的原因是：</p>
					<p class="e-4-t-1">你使用的URL可能拼写错误或者它只是临时脱机</p>
					<p class="e-4-t-1">所访问的页面不存在或被管理员已删除</p>
				</div>
				<br>
				<br>
				<div>
					<h4>请尝试以下操作：</h4>
					<p class="e-4-t-1">1、尝试按F5进行页面刷新</p>
					<p class="e-4-t-1">2、重新键入URL地址进入访问</p>
					<p class="e-4-t-1">3、或返回 <a href="${ctx }" title="">网站首页</a></p>
				</div>
			</div>
		</div>
	</div>
	<!-- /400-demo -->
</body>
</html>