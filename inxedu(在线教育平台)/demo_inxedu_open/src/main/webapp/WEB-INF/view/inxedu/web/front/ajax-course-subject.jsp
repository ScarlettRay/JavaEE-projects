<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/base.jsp"%>
<c:forEach items="${courseList}" var="course" varStatus="index">
	<li <c:if test="${(index.index+1)%3==0}"> style="margin-right: 0px;"</c:if>>
		<div class="of pt10 pr20 pb10 pl20">
			<h3 class="cName1">
				<a title="${course.courseName}" href="${ctx}/front/couinfo/${course.courseId}" target="_blank">${course.courseName}</a>
			</h3>
			<section class="pl20 mt10">
				<span class="vam cpWrap disIb">
					<a title="${course.courseName}" href="${ctx}/front/couinfo/${course.courseId}" target="_blank">
						<img width="160" height="120" alt="${course.courseName}" src="<%=staticImage%>${course.logo}">
					</a>
				</span>
				<span class="vam disIb ml30">
					<a class="disIb c1Btn" title="${course.courseName}" href="${ctx}/front/couinfo/${course.courseId}" target="_blank"></a>
				</span>
			</section>
			<section class="mt10">
				<p class="cDesc1 c_999">${course.title}</p>
			</section> 
		</div>
	</li>
</c:forEach>