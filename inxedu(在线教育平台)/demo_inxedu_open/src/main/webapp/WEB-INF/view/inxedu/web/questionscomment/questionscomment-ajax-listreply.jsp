<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/base.jsp"%>
<c:forEach items="${questionsCommentRepList }" var="questionsComment" varStatus="index">
	<c:if test="${index.index<8 }">
		<dd>
			<aside class="n-reply-pic">
					<c:choose>
						<c:when test="${not empty questionsComment.picImg }">
							<img src="<%=staticImage %>${questionsComment.picImg }" alt="">
						</c:when>
						<c:otherwise>
							<img src="${ctx }/static/inxweb/img/avatar-boy.gif" alt="">
						</c:otherwise>
					</c:choose>
			</aside>
			<div class="of">
				<span class="fl">
					<font class="fsize12 c-blue">
						<c:if test="${empty questionsComment.showName }">${questionsComment.email }</c:if>
						<c:if test="${not empty questionsComment.showName }">${questionsComment.showName }</c:if>
					</font><font class="fsize12 c-999 ml5">评论：</font></span>
			</div>
			<div class="noter-txt mt5">
				<p><c:out value="${questionsComment.content }"></c:out></p>
			</div>
			<div class="of mt5">
				<span class="fr"><font class="fsize12 c-999 ml5"><fmt:formatDate type="both" value="${questionsComment.addTime }" pattern="yyyy-MM-dd HH:mm"/></font></span>
				<span class="fl">
					<tt class="noter-zan vam" title="赞一下" onclick="addPraise(${questionsComment.id },2,this)"><em class="icon18">&nbsp;</em>(<span>${questionsComment.praiseCount }</span>)</tt>
				</span>
			</div>
		</dd>
	</c:if>
</c:forEach>
<c:if test="${questionsCommentRepList.size()>8 }">
	<div class="mt20 tac bg-fa hLh30"><a href="javascript:void(0)" onclick="getAllCommentById(${questionsCommentRepList[0].commentId})" class="comm-btn c-btn-6">查看更多评论回复</a></div>
</c:if>
									