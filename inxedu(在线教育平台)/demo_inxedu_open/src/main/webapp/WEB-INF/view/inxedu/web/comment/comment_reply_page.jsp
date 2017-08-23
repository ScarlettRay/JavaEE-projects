<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/base.jsp"%>
<section class="">
	<section class="question-list lh-bj-list pr">
		<ul class="pr10">
				<li>
					<aside class="noter-pic">
						<c:if test="${empty parentComment.picImg }">
							<img width="50" height="50" class="picImg" src="${ctx }/static/inxweb/img/avatar-boy.gif">
						</c:if>
						<c:if test="${!empty parentComment.picImg }">
							<img width="50" height="50" class="picImg" src="<%=staticImage%>${parentComment.picImg }">
						</c:if>
					</aside>
					<div class="of">
						<span class="fl"> 
						<font class="fsize12 c-blue"> 
							<c:if test="${empty parentComment.userName }">
								${parentComment.email}
							</c:if>
							<c:if test="${!empty parentComment.userName }">
								${parentComment.userName}
							</c:if>
						</font>
						<font class="fsize12 c-999 ml5">评论：</font></span>
					</div>
					<div class="noter-txt mt5">
						<p><c:out value="${parentComment.content}"></c:out></p>
					</div>
					<div class="of mt5">
						<span class="fr"><font class="fsize12 c-999 ml5"><fmt:formatDate value="${parentComment.addTime}" pattern="yyyy/MM/dd HH:mm" /></font></span> <span class="fl"> <a class="noter-dy vam" title="回复" href="javascript: void(0)">
								<em class="icon18">&nbsp;</em>(<span class="replyCount${parentComment.commentId}">${parentComment.replyCount}</span>)
							</a> <tt title="赞一下" class="noter-zan vam ml10" onclick="addPraise('${parentComment.commentId}',4)">
								<em class="icon18">&nbsp;</em>(<span class="addPraise${parentComment.commentId}_4 praiseCount">${parentComment.praiseCount }</span>)
							</tt>
						</span>
					</div>
					<div class="">
						<div class="mt10 pl10 pr10 pb10 commentReply${parentComment.commentId}">
							<dl class="n-reply-list">
								<c:forEach items="${commentList }" var="ac" varStatus="index">
									<dd>
										<aside class="n-reply-pic">
											<c:if test="${empty ac.picImg }">
												<img width="50" height="50" class="picImg" src="${ctx }/static/inxweb/img/avatar-boy.gif">
											</c:if>
											<c:if test="${!empty ac.picImg }">
												<img width="50" height="50" class="picImg" src="<%=staticImage%>${ac.picImg }">
											</c:if>
										</aside>
										<div class="of">
											<span class="fl"><font class="fsize12 c-blue">
											<c:if test="${empty ac.userName }">
												${ac.email}
											</c:if>
											<c:if test="${!empty ac.userName }">
												${ac.userName}
											</c:if>
											</font><font class="fsize12 c-999 ml5" >回复：</font></span>
										</div>
										<div class="noter-txt mt5">
											<p><c:out value="${ac.content}"></c:out></p>
										</div>
										<div class="of mt5">
											<span class="fr"><font class="fsize12 c-999 ml5"><fmt:formatDate value="${ac.addTime}" pattern="yyyy/MM/dd HH:mm" /></font></span> <span class="fl"> 
											<tt class="noter-zan vam" title="赞一下" onclick="addPraise('${ac.commentId}',4)">
													<em class="icon18">&nbsp;</em><span class="addPraise${ac.commentId}_4 praiseCount">${ac.praiseCount }</span>
												</tt>
											</span>
										</div>
									</dd>
								</c:forEach>
								<!-- 公共分页 开始 -->
								<%-- <jsp:include page="/WEB-INF/view/common/ajaxpage2.jsp"></jsp:include> --%>
								<!-- 公共分页 结束 -->
							</dl>
						</div>
					</div> <!-- /回复盒子 -->
				</li>
		</ul>
	</section>
</section>
