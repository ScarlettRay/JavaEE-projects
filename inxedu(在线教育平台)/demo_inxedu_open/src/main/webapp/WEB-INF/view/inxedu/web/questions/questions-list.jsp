<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/base.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>问题列表</title>
</head>
<body> 
	<div class="bg-fa of">
		<section class="container">
			<section class="i-question">
				<div class="fl col-7">
					<section class="mr30 pt10">
						<section class="c-infor-tabTitle c-tab-title">
							<a href="javascript: void(0)" title="全部问答" onclick="submitForm(0,'type')" <c:if test="${questions.type==0 }">class="current"</c:if>>全部问答</a>
							<a href="javascript: void(0)" title="课程问答" onclick="submitForm(1,'type')" <c:if test="${questions.type==1 }">class="current"</c:if>>课程问答</a>
							<a href="javascript: void(0)" title="学习分享" onclick="submitForm(2,'type')" <c:if test="${questions.type==2 }">class="current"</c:if>>学习分享</a>
						</section>
						<div class="js-wrap">
							<section class="fr">
								<span class="c-ccc"> <tt class="c-master f-fM">${page.currentPage}</tt>/<tt class="c-666 f-fM">${page.totalPageSize}</tt>
								</span>
							</section>
							<section class="fl">
								<ol class="js-tap clearfix">
									<li <c:if test="${questions.orderFalg=='addTime' }">class="current bg-orange"</c:if>><a onclick="submitForm('addTime','order')" href="javascript:void(0)" title="最新">最新</a></li>
									<li <c:if test="${questions.orderFalg=='replycount' }">class="current bg-orange"</c:if>><a onclick="submitForm('replycount','order')" href="javascript:void(0)" title="热门">热门</a></li>
									<li <c:if test="${questions.orderFalg=='status0' }">class="current bg-orange"</c:if>><a onclick="submitForm('status0','order')" href="javascript:void(0)" title="等待回答">等待回答</a></li>
								</ol>
							</section>
						</div>
						<!-- /问题列表 开始 -->
						<div class="q-list">
							<c:if test="${empty questionsList }">
								<!-- /无数据提示 开始-->
								<section class="no-data-wrap">
									<em class="icon30 no-data-ico">&nbsp;</em> <span class="c-666 fsize14 ml10 vam">没有相关数据，小编正在努力整理中...</span>
								</section>
								<!-- /无数据提示 结束-->
							</c:if>

							<c:if test="${not empty questionsList }">
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
																<span class="fsize12 c-999 vam"> <tt class="c-ccc f-fM mr5">[最新回答]</tt> <c:forEach items="${question.questionsCommentList }" var="questionsComment">
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
														<span class="c-ccc fl vam">${question.modelTime }</span>
														<section class="fl ml20 pt10">
															<div class="taglist clearfix">
																<c:forEach items="${question.questionsTagRelationList }" var="questionsTag">
																	<a title="${questionsTag.tagName }" data-id="${questionsTag.questionsTagId }" onclick="submitForm('${questionsTag.questionsTagId }','questionsTagId')" class="list-tag" href="javascript:;">${questionsTag.tagName }</a>
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
							</c:if>
							<!-- 公共分页 开始 -->
							<jsp:include page="/WEB-INF/view/common/front_page.jsp" />
							<!-- 公共分页 结束 -->
							<form action="${ctx}/questions/list" id="searchForm" method="post">
								<input type="hidden" id="pageCurrentPage" name="page.currentPage" value="1" />
								<input type="hidden" name="questions.orderFalg" value="${questions.orderFalg}" />
								<input type="hidden" name="questions.type" value="${questions.type}" />
								<input type="hidden" name="questions.status" value="${questions.status}" />
								<input type="hidden" name="questions.questionsTagId" value="${questions.questionsTagId}" />
							</form>
						</div>
						<!-- /问题列表 结束 -->
					</section>
				</div>
				<aside class="fl col-3">
					<div class="mt30 pl10">
						<section class="pt10">
							<a href="javascript:void(0)" onclick="toAddQuestions()" title="我要提问" class="comm-btn c-btn-5">我要提问</a>
						</section>
						<section class="pt20">
							<div class="taglist clearfix">
								<a onclick="submitForm('0','questionsTagId')" href="javascript:;" class="list-tag <c:if test='${questions.questionsTagId==0 }' >onactive</c:if>" data-id="0" title="JAVA">全部</a>
								<c:forEach items="${questionsTagList }" var="questionsTag">
									<a title="${questionsTag.questionsTagName }" data-id="${questionsTag.questionsTagId }" class="list-tag <c:if test='${questionsTag.questionsTagId==questions.questionsTagId }' >onactive</c:if>" href="javascript:;" onclick="submitForm('${questionsTag.questionsTagId }','questionsTagId')">${questionsTag.questionsTagName }</a>
								</c:forEach>
							</div>
						</section>
						<!-- /标签云 -->
						<section class="mt30">
							<section class="c-infor-tabTitle c-tab-title">
								<a href="javascript: void(0)" title="热门问答推荐">热门问答推荐</a>
							</section>
							<div class="q-r-rank-list">
								<ul id="hotQuestions">
									<section class="no-data-wrap">
										<em class="icon30 no-data-ico">&nbsp;</em> <span class="c-666 fsize14 ml10 vam">没有相关数据，小编正在努力整理中...</span>
									</section>
								</ul>
							</div>
						</section>
						<!-- /热门问答排行 -->
					</div>
				</aside>
				<div class="clear"></div>
			</section>
		</section>
		<!-- /提问题 结束 -->
	</div>
	<script type="text/javascript" src="${ctx}/static/inxweb/questions/questions.js"></script>
</body>
</html>