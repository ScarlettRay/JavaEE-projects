<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/base.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>找回密码</title>
<script type="text/javascript" src="${ctx}/static/inxweb/front/forget_password.js"></script>
</head>
<body>
	<div class="bg-fa of">
		<section class="container">
			<div class="u-body mt40">
				<div style="width: 80%; margin: 0 auto;" class="i-box">
					<div>
						<section class="c-infor-tabTitle c-tab-title">
							<a title="忘记密码 &gt; 找回密码" style="cursor: default;" href="javascript: void(0)">忘记密码 &gt; 找回密码</a>
						</section>
					</div>
					<form id="recoveryForm">
						<div style="width: 320px; margin: 30px auto;">
							<div class="mt10 mb10">
								<p style="visibility: visible; height: 28px; line-height: 28px;" class="e-l-jy">
									<span class="fsize12 c-red" id="errorMsg"></span>
								</p>
							</div>
							<ol class="e-login-options">
								<li><input type="text" name="email" id="u-email" placeholder="请输入注册邮箱" />
									<p class="lr-tip-wrap"></p></li>
								<li class="clearfix"><input type="text" id="u-password" class="fl" style="width: 120px;" placeholder="请输入验证码" name="pageCode" value=""> <img width="86" height="40" onclick="this.src='${ctx}/ran/random?random='+Math.random();" alt="验证码，点击图片更换" src="${ctx}/ran/random">
									<p class="lr-tip-wrap"></p></li>
							</ol>
							<section class="mt20 tac">
								<a href="javascript: void(0)" onclick="sendPwdRecoveery()" title="提 交" class="e-login-btn">提 交</a>
							</section>
						</div>
					</form>
				</div>
			</div>
		</section>
	</div>
</body>
</html>