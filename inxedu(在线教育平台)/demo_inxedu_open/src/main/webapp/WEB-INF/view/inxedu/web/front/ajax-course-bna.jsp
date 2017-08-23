<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp"%>
<c:if test="${not empty courseDtoBNAList }">
	<c:forEach var="course_bna" items="${courseDtoBNAList }" varStatus="index">
		<c:if test="${index.index<8 }">
			<li> 
				<div class="cc-l-wrap">
					<section class="course-img">
						<c:if test="${not empty course_bna.logo&& course_bna.logo!=''}">
							<img xSrc="<%=staticImage %>${course_bna.logo }" src="${ctx}/static/inxweb/img/default-img.gif" class="img-responsive" alt="${course_bna.courseName }">
						</c:if>
						<c:if test="${empty course_bna.logo||course_bna.logo=='' }">
							<img xSrc="${ctx}/static/inxweb/img/default-img.gif" src="${ctx}/static/inxweb/img/default-img.gif" class="img-responsive" alt="${course_bna.courseName }">
						</c:if>
						<div class="cc-mask">
							<a href="${ctx}/front/couinfo/${course_bna.courseId}" title="开始学习" class="comm-btn c-btn-1">开始学习</a>
						</div>
					</section>
					<h3 class="hLh30 txtOf mt10">
						<a href="${ctx}/front/couinfo/${course_bna.courseId}" title="${course_bna.courseName }" class="course-title fsize18 c-333">${course_bna.courseName }</a>
					</h3>
					<section class="mt10 hLh20 of">
						<c:if test="${course_bna.currentPrice=='0.00' }">
							<span class="fr jgTag bg-green"><tt class="c-fff fsize12 f-fA">免费</tt></span>
						</c:if>
						<c:if test="${course_bna.currentPrice!='0.00' }">
							<span class="fr jgTag bg-orange"><tt class="c-fff fsize14 f-fG">￥${course_bna.currentPrice }</tt></span>
						</c:if>
						<span class="fl jgAttr c-ccc f-fA"> <tt class="c-999 f-fA">
								<c:if test="${course_bna.currentPrice=='0.00' }">${course_bna.pageViewcount}</c:if><c:if test="${course_bna.currentPrice!='0.00' }">${course_bna.pageBuycount}</c:if>人学习
							</tt> | <tt class="c-999 f-fA">
								<c:if test="${course_bna.currentPrice=='0.00' }">${course_bna.pageViewcount}</c:if><c:if test="${course_bna.currentPrice!='0.00' }">${course_bna.pageViewcount}</c:if>评论
							</tt>
						</span>
					</section>
				</div>
			</li>
		</c:if>
	</c:forEach>
</c:if>