<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/base.jsp"%>
<div>
	<header class="comm-title all-article-title">
		<h2 class="fl tac">
			<span class="c-333">文章排行榜</span>
		</h2>
		<section class="c-tab-title">
			<a href="javascript: void(0)">&nbsp;</a>
		</section>
	</header>
	<section class="i-article-list r-art-wrap">
		<ul>
			<c:if test="${not empty articleList }">
				<c:forEach var="article" items="${articleList }" varStatus="index">
					<li>
						<aside class="orderNum"><span>${index.index+1}</span></aside>
						<h3 class="hLh30 txtOf">
							<a href="${ctx }/front/articleinfo/${article.articleId}.html" title="${article.title }" class="i-art-title">${article.title }</a>
						</h3>
						<%-- <section class="i-q-txt mt5">
							<p>
								<span class="c-999 f-fA">${article.summary }</span>
							</p>
						</section> --%>
						<section class="hLh30 txtOf mt5">
							<div class="fr">
								<span> <a class="noter-dy vam" title="回答" href="${ctx }/front/articleinfo/${article.articleId}.html">
										<em class="icon18">&nbsp;</em>(${article.commentNum })
									</a> <tt title="赞一下" class="noter-zan vam ml10 f-fM" onclick="addPraise('${article.articleId}',3)">
										<em class="icon18">&nbsp;</em>(<span class="addPraise${article.articleId}_3 praiseCount">${article.praiseCount }</span>)
									</tt>
								</span>
							</div>
							<span class="fl"><tt class="c-999 f-fM">
									<!-- 1小时前发布 -->
									<fmt:formatDate value="${article.publishTime }" pattern="yyyy-MM-dd HH:mm" />
								</tt></span>
						</section>
					</li>
				</c:forEach>
			</c:if>
		</ul>
	</section>
	<section style="margin-bottom: 20px;">
		<c:forEach var="image" items="${websiteImages.type_12}" varStatus="status">
			<c:if test="${status.index==0 }">
				<a href="${image.linkAddress}" title="${image.title}"><img src="<%=staticImage%>${image.imagesUrl}" width="100%" alt="${image.title}"/></a>
			</c:if>
		</c:forEach>
	</section>
</div>
<script type="text/javascript">
	$(function() {
		$(".r-art-wrap>ul>li:lt(3)").addClass("lt2");
	})
</script>