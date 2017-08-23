<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp"%>
<c:forEach var="courseStudyhistory" items="${courseStudyhistoryList }">
	<li>
		<div class="u-face">
			<c:if test="${empty courseStudyhistory.picImg}">
				<img src="${ctx }/static/inxweb/img/avatar-boy.gif" width="50" height="50" alt="">
			</c:if>
			<c:if test="${!empty courseStudyhistory.picImg}">
				<img src="<%=staticImage%>${courseStudyhistory.picImg }" width="50" height="50" alt="">
			</c:if>
		</div>
		<section class="hLh30 txtOf">
			<span class="c-ccc fr">${courseStudyhistory.updateTimeFormat }</span>
			<span class="c-333 fsize16 fl">${courseStudyhistory.showName}
			</span> 
		</section>
		<section class="hLh20 txtOf">
			<span class="c-999">播放了</span> <span class="c-master">“${courseStudyhistory.courseName }”</span>
		</section>
	</li> 
</c:forEach>
