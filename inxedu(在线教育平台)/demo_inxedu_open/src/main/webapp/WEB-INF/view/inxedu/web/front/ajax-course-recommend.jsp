<%@ page language="java" contentType="text/html; charset=UTF-8"%>
 <%@ include file="/base.jsp"%>   
<c:if test="${not empty courseDtoList}">
						<c:forEach items="${courseDtoList}" var="course" varStatus="index">
							<li>
								<div class="cc-l-wrap">
									<section class="course-img">
											<c:if test="${not empty course.logo }">
											<img xsrc="<%=staticImage%>${course.logo }" src="${ctx}/static/inxweb/img/default-img.gif" class="img-responsive" alt="${course.courseName }">
											</c:if>
											<c:if test="${empty course.logo }">
									 		<img xsrc="${ctx}/static/inxweb/img/default-img.gif" src="${ctx}/static/inxweb/img/default-img.gif" class="img-responsive" alt="${course.courseName }">
											</c:if>
										<div class="cc-mask">
											<a href="${ctx}/front/couinfo/${course.courseId}" title="开始学习" class="comm-btn c-btn-1">开始学习</a>
										</div>
									</section>
									<h3 class="hLh30 txtOf mt10">
										<a href="${ctx}/front/couinfo/${course.courseId}" title="${course.courseName }" class="course-title fsize18 c-333">${course.courseName }</a>
									</h3>
									<section class="mt10 hLh20 of">
									<c:if test="${course.currentPrice=='0.00' }">
										<span class="fr jgTag bg-green"><tt class="c-fff fsize12 f-fA">免费</tt></span>
										</c:if>
										<c:if test="${course.currentPrice!='0.00' }">
										<span class="fr jgTag bg-orange"><tt class="c-fff fsize14 f-fG">￥${course.currentPrice }</tt></span>
										</c:if>
										<span class="fl jgAttr c-ccc f-fA">
											<tt class="c-999 f-fA"><c:if test="${course.currentPrice=='0.00' }">${course.pageViewcount }</c:if><c:if test="${course.currentPrice!='0.00' }">${course.pageBuycount }</c:if>人学习</tt>
											|
											<tt class="c-999 f-fA"><c:if test="${course.currentPrice=='0.00' }">${course.pageViewcount }</c:if><c:if test="${course.currentPrice!='0.00' }">${course.pageViewcount }</c:if>评论</tt>
										</span>
									</section>
								</div>
							</li>
							</c:forEach>
							</c:if>