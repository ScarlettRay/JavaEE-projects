<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/base.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>文章列表</title>
</head>
<body>
	<div id="aCoursesList" class="bg-fa of">
		<section class="container">
			<section class="i-article">
				<div class="fl col-7">
					<section class="mr30">
						<header class="comm-title all-article-title">
							<h2 class="fl tac">
								<span class="c-333">文章</span>
							</h2>
							<section class="c-tab-title">
								<a href="javascript: void(0)">&nbsp;</a>
							</section>
						</header>
						<!-- /无数据提示 开始-->
						<c:if test="${empty articleList }">
							<section class="no-data-wrap">
								<em class="icon30 no-data-ico">&nbsp;</em> <span class="c-666 fsize14 ml10 vam">没有相关数据，小编正在努力整理中...</span>
							</section>
						</c:if>
						<!-- /无数据提示 结束-->
						<article class="i-article-list">
							<!-- /文章列表 开始-->
							<ul>
								<c:if test="${not empty articleList }">
									<c:forEach var="article" items="${articleList }">
										<li>
											<aside class="i-article-pic">
												<img xSrc="<%=staticImage%>${article.imageUrl }" src="${ctx }/static/inxweb/img/default-img.gif" alt="${article.title }">
											</aside> <!-- ${ctx }/web/article/showInfor/${article.articleId } -->
											<h3 class="hLh30 txtOf">
												<a href="${ctx }/front/articleinfo/${article.articleId}.html" title="${article.title }" class="i-art-title">${article.title }</a>
											</h3>
											<section class="i-q-txt mt5 i-q-txt2">
												<p>
													<span class="c-999 f-fA">${article.summary }</span>
												</p>
											</section>
											<section class="hLh30 txtOf mt5 pr10 a-list-extrainfo">
												<span class="fr"><tt class="c-999 f-fM">
														<!-- 1小时前发布 -->
														<fmt:formatDate value="${article.publishTime }" pattern="yyyy-MM-dd HH:mm" />
													</tt></span>
												<div class="fl">
													<span> <a class="noter-dy vam" title="回答" href="${ctx }/front/articleinfo/${article.articleId}.html">
															<em class="icon18">&nbsp;</em>(<span>${article.commentNum }</span>)
														</a> <tt title="赞一下" class="noter-zan vam ml10 f-fM" onclick="addPraise('${article.articleId}',3)">
															<em class="icon18">&nbsp;</em>(<span class="addPraise${article.articleId}_3 praiseCount">${article.praiseCount }</span>)
														</tt>
													</span>
												</div>
											</section>
										</li>
									</c:forEach>
								</c:if>
							</ul>
							<!-- /文章列表 结束-->
						</article>
						<!-- 公共分页 开始 -->
						<form action="${ctx }/front/articlelist" method="post" id="searchForm">
							<input type="hidden" name="page.currentPage" id="pageCurrentPage" value="1">
						</form>
						<div>
							<jsp:include page="/WEB-INF/view/common/front_page.jsp"></jsp:include>
							<div class="clear"></div>
						</div>
						<!-- 公共分页 结束 -->
					</section>
				</div>
				<!-- 好文推荐 -->
				<aside class="fl col-3 articleRecommend"></aside>
				<div class="clear"></div>
			</section>
		</section>
		<!-- /文章列表 结束 -->
	</div>
	<script src="${ctx }/static/inxweb/acticle/acticle.js" type="text/javascript"></script>
	<script>
		$(function() {
			articleRecommend();//好文推荐
			scrollLoad(); //响应滚动加载课程图片
		});
	</script>
</body>
</html>