<%@page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/base.jsp"%>
<menu class="col-3 fl uMenu">
	<dl>
		<dt> 
			<section class="of">
				<div class="u-face-pic">
					<img src="${ctx }/static/inxweb/img/avatar-boy.gif" alt="" class="userImgPhoto">
					<a href="${ctx}/uc/initUpdateUser/1" title="" class="c-fff">修改头像</a>
				</div>
				<h4 class="mt10"><span class="fsize16 c-666 userNameClass"><!-- 用户名 --></span></h4>
				<div class="hLh30 mt10">
					<a href="${ctx}/uc/initUpdateUser/0" title="" class="c-blue">个人资料设置</a>
				</div>
				<div class="clear"></div>
			</section><!-- /u-face-attr -->
			<!-- <section class="mt20">
				<section class="stud-time-wrap stud-time">
					<tt class="f-fM c-999">已学习：</tt>
					<big>189</big>
					<small>小时</small>
					<big>568</big>
					<small>分</small>
				</section>
			</section> -->
		</dt>
		<dd class="u-m-dd">
			<ul>
				<li>
					<span>Wo的学习</span>
					<ol>
						<li><a href="${ctx}/uc/index" title="">免费课程</a></li>
						<li><a href="${ctx}/front/showcoulist" title="" target="view_window">选课中心</a></li>
						<li><a href="${ctx}/uc/myFavorites" title="">Wo的收藏</a></li>
					</ol>
				</li>
			</ul>
			<ul>
				<li>
					<span>Wo的问答</span>
					<ol>
						<li><a href="${ctx}/uc/myquestions/list" title="">Wo的提问</a></li>
						<li><a href="${ctx}/uc/myrepquestions/list" title="">Wo的回答</a></li>
					</ol>
				</li>
			</ul>
			<ul>
				<li>
					<span>Wo的资料</span>
					<ol>
						<li><a href="${ctx}/uc/initUpdateUser/0" title="">基本资料</a></li>
						<li><a href="${ctx}/uc/initUpdateUser/1" title="">个人头像</a></li>
						<li><a href="${ctx}/uc/initUpdateUser/2" title="">密码设置</a></li>
					</ol>
				</li>
			</ul>
			<ul>
				<li>
					<span>Wo的消息</span>
					<ol>
						<li><a href="/uc/letter" title="">系统消息</a></li>
						<!-- <li><a href="" title="">其它消息</a></li> -->
					</ol>
				</li>
			</ul>
		</dd>
	</dl>
</menu>
<!-- /公共左侧菜单 结束 -->