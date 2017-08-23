<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/base.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>文章详情</title>
<script src="${ctx }/static/inxweb/acticle/acticle.js" type="text/javascript"></script>
<script src="${ctx }/static/inxweb/comment/comment.js" type="text/javascript"></script>
<script type="text/javascript">
	//全局变量 
	var otherId = '${article.articleId}';
	var articleId = '${article.articleId}';
	var type = 1;
	$(function() {
		//添加浏览量
		updateArticleClickNum();
		//好文推荐
		articleRecommend();
		queryComment();
	});
</script>
</head>
<body>
	<div class="bg-fa of">
		<section class="container">
			<section class="i-article">
				<div class="fl col-7">
					<section class="mr30">
						<section class="path-wrap txtOf hLh30">
							<a href="${ctx }" title="首页" class="c-999 fsize14">首页</a>
							\
							<a href="${ctx}/front/articlelist" title="" class="c-999 fsize14">文章列表</a>
							\ <span class="c-333 fsize14">${article.title }</span>
						</section>
						<article class="article-infor-box pr20">
							<h3 class="hLh30 txtOf">
								<span class="i-art-title">${article.title }</span>
							</h3>
							<section class="hLh30 txtOf mt5 pb20 mreadnum">
								<div class="fr">
									<span> <a class="noter-dy vam" title="评论" href="#i-art-comment">
											<em class="icon18">&nbsp;</em>(<span class="totalResultComment">0</span>)
										</a> <tt title="赞一下" onclick="addPraise('${article.articleId}',3)" class="noter-zan vam ml5 f-fM">
											<em class="icon18">&nbsp;</em>(<span class="addPraise${article.articleId}_3 praiseCount">0</span>)
										</tt>
									</span>
								</div>
								<div class="fl pt3"><%--  <tt class="c-999 f-fM">来源：${article.source }</tt>  --%><tt class="c-999 f-fM">
										<!-- 1小时前发布 -->
										<fmt:formatDate value="${article.publishTime }" pattern="yyyy-MM-dd HH:mm" />
								</tt>
								</div>
							</section>
							<div id="art-infor-body">${content }
								<div>
									<tt class="c-999 f-fM">
										评论：<span class="totalResultComment">0</span>
									</tt> <tt class="c-999 f-fM ml20">
										点赞：<span class="addPraise${article.articleId}_3 praiseCount">0</span>
									</tt> <tt class="c-999 f-fM ml20">
										阅读：<span class="clickNum"></span>
									</tt>
								</div>
							</div>
							<section class="mt20 tac" id="sharebutton">
								<div class="bdsharebuttonbox clearfix disIb">
									<div class="pr disIb fl of">
										<a href="javascript:void(0)" onclick="addActiclePraise('${article.articleId}',3)"  title="" class="zan-icon dz-btn pr"></a>
										<div class="dz-num">
											<span class="c-red fsize16">+1</span>
										</div>
									</div>
									<a title="分享到微信" href="#" class="bds_weixin fl" data-cmd="weixin">
										<em class="share-fir-icon icon30"></em>
									</a>
									<a title="分享到新浪微博" href="#" class="bds_tsina fl" data-cmd="tsina"></a>
								</div>
								<script>window._bd_share_config={"common":{"bdSnsKey":{},"bdText":"","bdMini":"2","bdMiniList":false,"bdPic":"","bdStyle":"0","bdSize":"32"},"share":{}};with(document)0[(getElementsByTagName('head')[0]||body).appendChild(createElement('script')).src='http://bdimg.share.baidu.com/static/api/js/share.js?v=89860593.js?cdnversion='+~(-new Date()/36e5)];</script>
							</section>
						</article>
						<!-- 文章评论 开始 -->
						<div class="mt30 pr20 mb50 commentHtml"></div>
					</section>
				</div>
				<aside class="fl col-3 articleRecommend"></aside>
				<div class="clear"></div>
			</section>
		</section>
		<!-- /文章列表 结束 -->
	</div>
</body>
</html>