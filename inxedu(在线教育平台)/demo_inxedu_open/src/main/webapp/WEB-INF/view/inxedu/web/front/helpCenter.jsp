<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>帮助中心</title>
</head>
<body>
	<div id="aCoursesList" class="bg-fa of">
		<div class="container">
			<section class="path-wrap txtOf hLh30"> 
				<a class="c-999 fsize14" title="" href="http://127.0.0.1">首页</a>
				 \<span class="c-333 fsize14">帮助中心</span> 
			</section>
			<div class="clearfix">
				<div class="fl h-left">
					<header class="h-l-title">
						<span class="c-333">帮助中心</span>
					</header>
					<ul class="h-l-nav">
						<c:forEach items="${helpMenus}" var="helpMenu" varStatus="index">
							<li>
								<c:if test="${helpMenu.size()==1}">
									<a href="
											<c:if test="${not empty helpMenu[0].linkBuilding}">${helpMenu[0].linkBuilding}" target="_blank </c:if>
											<c:if test="${empty helpMenu[0].linkBuilding}">${ctx}/front/helpCenter?id=${helpMenu[0].id}</c:if>
										" id="helpMenu${helpMenu[0].id}">${helpMenu[0].name}</a>
								</c:if>
								<c:if test="${helpMenu.size()>1}">
									<span>${helpMenu[0].name}</span>
					                <dl class="hl-sec-nav">
										<c:forEach items="${helpMenu}" var="helpMenuSon" varStatus="indexSon">
											<c:if test="${indexSon.index!=0}">
												<dd><a href="
													<c:if test="${not empty helpMenuSon.linkBuilding}">${helpMenuSon.linkBuilding}" target="_blank</c:if>
													<c:if test="${empty helpMenuSon.linkBuilding}">${ctx}/front/helpCenter?id=${helpMenuSon.id}</c:if>
													" id="helpMenu${helpMenuSon.id}"><em></em><tt>${helpMenuSon.name}</tt></a></dd>
											</c:if>
										</c:forEach>
									</dl>
								</c:if>
							</li>
						</c:forEach>
					</ul>
				</div>
				<div class="fr h-right">
					<div class="h-r-wrap">
						<header class="h-l-title h-r-title">
							<span class="c-333">${helpMenuContent.name}</span>
						</header>
						<div class="h-l-cont">
							<p> ${helpMenuContent.content}
							 </p>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script>
		$(function(){
			$("#helpMenu${helpMenuContent.id}").parent().addClass("current");
		})
	</script>
</body>
</html>