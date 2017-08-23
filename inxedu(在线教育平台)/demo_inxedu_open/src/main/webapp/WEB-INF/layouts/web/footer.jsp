<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/base.jsp"%>
<footer id="footer">
	<section class="container">
		<div class="">
			<h4 class="hLh30">
				<span class="fsize18 f-fM c-999">友情链接</span>
			</h4>
			<ul class="of flink-list">
				<c:forEach items="${navigatemap.FRIENDLINK}" var="friendLinkNavigate">
					<li><a href="${friendLinkNavigate.url}" title="${friendLinkNavigate.name}" <c:if test="${friendLinkNavigate.newPage==0}">target="_blank"</c:if>>${friendLinkNavigate.name}</a></li>
				</c:forEach>
			</ul>
			<div class="clear"></div>
		</div>
		<div class="b-foot">
			<section class="fl col-7">
				<section class="mr20">
					<section class="b-f-link">
						<c:forEach items="${navigatemap.TAB}" var="indexNavigate" varStatus="index">
							<a href="${indexNavigate.url}" title="${indexNavigate.name}" <c:if test="${indexNavigate.newPage==0}">target="_blank"</c:if>>${indexNavigate.name}</a>|
								</c:forEach>
						<span>服务热线：${websitemap.web.phone}</span> <span>Email：${websitemap.web.email}</span>
					</section>
					<section class="b-f-link mt10">
						<span>${websitemap.web.copyright}</span>
					</section>
				</section>
			</section>
			<aside class="fl col-3 tac mt15">
				<section class="gf-tx">
					<span><img src="${ctx}/static/inxweb/img/wx-icon.png" alt=""></span>
					<div class="gf-tx-ewm">
						<c:forEach var="image" items="${websiteImages.type_11}" varStatus="status">
						<c:if test="${status.count==1 }">
						<img src="<%=staticImage%>${image.imagesUrl}" alt="">
						</c:if>
					</c:forEach>
					</div>
				</section>
				<section class="gf-tx">
					<span><img src="${ctx}/static/inxweb/img/wb-icon.png" alt=""></span>
					<div class="gf-tx-ewm">
					<c:forEach var="image" items="${websiteImages.type_11}" varStatus="status">
						<c:if test="${status.count==2 }">
						<img src="<%=staticImage%>${image.imagesUrl}" alt="">
						</c:if>
					</c:forEach>
					</div>
				</section>
			</aside>
			<div class="clear"></div>
		</div>
	</section>
</footer>
<div class="r-fixed-wrap">
	<ul class="r-fixed-ul">
		<li id="goTopBtn" class="undis">
			<a href="javascript: void(0)" title="返回顶部" class="bg-orange">
				<em class="r-f-icon-3">&nbsp;</em><span>返回顶部</span>
			</a>
		</li>
		<li class="foot-zixun">
			<a href="" title="在线咨询" class="bg-orange">
				<em class="r-f-icon-1">&nbsp;</em><span>在线咨询</span>
			</a>
		</li>
		<li class="foot-zixun">
			<a href="" title="扫描关注" class="bg-orange">
				<em class="r-f-icon-2">&nbsp;</em><span>扫描关注</span>
			</a>
		</li>
	</ul>
</div>
