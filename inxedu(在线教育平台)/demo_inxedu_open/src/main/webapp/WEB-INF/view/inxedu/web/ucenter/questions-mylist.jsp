<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/base.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>我的提问</title>
</head>
<body>
	<article class="col-7 fl">
		<div class="u-r-cont">
			<section>
				<div>
					<span class="fr"><a href="${ctx }/questions/toadd" title="" class="comm-btn c-btn-6" style="font-size: 16px; height: 22px; line-height: 22px; padding: 0 20px;">去提问</a></span>
					<section class="c-infor-tabTitle c-tab-title cnew-tab-title">
						<a href="javascript: void(0)" title="Wo的问答" style="cursor: default;">Wo的问答</a>
						<a href="${ctx }/uc/myquestions/list" title="Wo的提问" class="current">Wo的提问</a>
						<a href="${ctx }/uc/myrepquestions/list" title="Wo的回答">Wo的回答</a>
					</section>
				</div>
				<div class="mt40">
					<c:if test="${empty questionsList }">
						<!-- /无数据提示 开始-->
						<section class="no-data-wrap">
							<em class="icon30 no-data-ico">&nbsp;</em> <span class="c-666 fsize14 ml10 vam">亲，您还没有提过问题，快去提问吧！</span>
						</section>
						<!-- /无数据提示 结束-->
					</c:if>
					<c:if test="${not empty questionsList }">
						<div class="u-question-wrap">
							<section class="q-all-list">
								<ul>
									<c:forEach items="${questionsList }" var="question">
										<li>
											<aside class="q-head-pic">
												<c:choose>
													<c:when test="${not empty question.picImg }">
														<img src="<%=staticImage %>${question.picImg }" alt="">
													</c:when>
													<c:otherwise>
														<img src="${ctx }/static/inxweb/img/avatar-boy.gif" alt="">
													</c:otherwise>
												</c:choose>
												<p class="hLh30 txtOf">
													<span class="c-999"> <c:if test="${empty question.showName }">${question.email }</c:if> <c:if test="${not empty question.showName }">${question.showName }</c:if>
													</span>
												</p>
											</aside>
											<section class="q-txt-box">
												<a class="replyBrowseNum" href="${ctx }/questions/info/${question.id }" title="">
													<div class="replyNum">
														<span class="r-b-num">${question.replyCount }</span>
														<p class="hLh30">
															<span class="c-999 f-fA">回答数</span>
														</p>
													</div>
													<div class="browseNum">
														<span class="r-b-num">${question.browseCount }</span>
														<p class="hLh30">
															<span class="c-999 f-fA">浏览数</span>
														</p>
													</div>
												</a>
												<h3 class="hLh30 txtOf">
													<em class="icon16 q-tw">&nbsp;</em>
													<a href="${ctx }/questions/info/${question.id }" title="" class="fsize16 c-333 vam">${question.title }</a>
												</h3>
												<h3 class="hLh30 txtOf mt5">
													<em class="icon16 q-hd">&nbsp;</em>
													<c:if test="${empty question.questionsCommentList }">
														<span class="fsize12 c-999 vam">哈~~~ 此问题大家还有苦思冥想中...</span>
														<!-- 没有回答时的内容 -->
													</c:if>
													<c:if test="${not empty question.questionsCommentList }">
														<c:if test="${question.status==0 }">
															<span class="fsize12 c-999 vam"> <tt class="c-ccc f-fM mr5">[最新回答]</tt> 
																	<c:forEach items="${question.questionsCommentList }" var="questionsComment">
																		<c:out value="${questionsComment.content }"></c:out>
																	</c:forEach>
															</span>
															<!-- 有回答时显示最新一条的回答内容 -->
														</c:if>

														<c:if test="${question.status==1 }">
															<span class="fsize12 c-999 vam"> <tt class="c-green f-fM mr5">[最佳回答]</tt> 
																	<c:forEach items="${question.questionsCommentList }" var="questionsComment">
																		<c:out value="${questionsComment.content }"></c:out>
																	</c:forEach>
															</span>
															<!-- 采纳最佳显示最佳答案内容 -->
														</c:if>
													</c:if>
												</h3>
												<div class="mt15">
													<span class="c-ccc fl vam">时间：${question.modelTime }</span>
													<section class="fl ml20 pt10">
														<div class="taglist clearfix">
															<c:forEach items="${question.questionsTagRelationList }" var="questionsTag">
																<a title="${questionsTag.tagName }" data-id="${questionsTag.questionsTagId }" class="list-tag" href="${ctx}/questions/list?questions.questionsTagId=${questionsTag.questionsTagId }">${questionsTag.tagName }</a>
															</c:forEach>
														</div>
													</section>
													<div class="clear"></div>
												</div>
											</section>
										</li>
									</c:forEach>
								</ul>
							</section>
						</div>
					</c:if>
					<!-- 公共分页 开始 -->
					<jsp:include page="/WEB-INF/view/common/front_page.jsp" />
					<!-- 公共分页 结束 -->
					<form action="${ctx}/uc/myquestions/list" id="searchForm" method="post">
						<input type="hidden" id="pageCurrentPage" name="page.currentPage" value="1" />
					</form>
				</div>
			</section>
			<!-- /Wo的消息 -->
		</div>
	</article>
</body>
</html>