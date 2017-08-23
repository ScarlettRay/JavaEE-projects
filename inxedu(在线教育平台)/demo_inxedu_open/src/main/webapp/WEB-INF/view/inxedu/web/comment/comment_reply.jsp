<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/base.jsp"%>
<dl class="n-reply-list">
	<c:forEach items="${commentList }" var="ac" varStatus="index">
		<c:if test="${index.index<8 }">
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
		</c:if>
	</c:forEach>
	<c:if test="${commentList.size()>8 }">
		<div class="mt20 tac bg-fa hLh30"><a href="javascript:void(0)" onclick="queryCommentReplyPage(${commentList[0].PCommentId})" class="comm-btn c-btn-6">查看更多评论回复</a></div>
	</c:if>
</dl>