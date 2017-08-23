<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/base.jsp"%>
<!DOCTYPE html> 
<html>
<head>
<title>讲师详情</title>
</head>
<body>
	<div id="aCoursesList" class="bg-fa of">
		<!-- /课程列表 开始 -->
		<section class="container">
			<header class="comm-title">
				<h2 class="fl tac">
					<span class="c-333">讲师介绍</span>
				</h2>
			</header>
			<div class="t-infor-wrap">
				<div class="fl t-infor-menu">
					<section id="t-infor-menu" class="c-tab-title">
						<p>
							<a class="current" name="c-desc" title="讲师详情" href="javascript: void(0)">讲师详情</a>
						</p>
						<p>
							<a title="主讲课程" name="c-course" href="javascript: void(0)">主讲课程</a>
						</p>
					</section>
				</div>
				<section class="fl t-infor-box c-desc-content">
					<div class="mt20 ml20">
						<section class="t-infor-pic">
							<c:choose>
								<c:when test="${not empty teacher.picPath }">
									<img src="${ctx }/static/inxweb/img/default-tea-img.gif" xsrc="<%=staticImage %>${teacher.picPath}" alt="">
								</c:when>
								<c:otherwise>
									<img xSrc="${ctx }/static/inxweb/img/default-tea-img.gif" src="${ctx }/static/inxweb/img/default-tea-img.gif" class="img-responsive" alt="">
								</c:otherwise>
							</c:choose>
						</section>
						<h3 class="hLh30">
							<span class="fsize24 c-333">${teacher.name } <c:if test="${teacher.isStar ==1}">&nbsp;高级讲师</c:if> <c:if test="${teacher.isStar ==2}">&nbsp;首席讲师</c:if></span>
						</h3>
						<section class="mt10">
							<span class="t-tag-bg">${teacher.education }</span>
						</section>
						<section class="t-infor-txt">
							<p class="mt20">${teacher.career }</p>
						</section>
						<div class="clear"></div>
					</div>
				</section>
				<div class="clear"></div>
			</div>
			<section class="mt30">
				<div>
					<header class="comm-title all-teacher-title c-course-content">
						<h2 class="fl tac">
							<span class="c-333">主讲课程</span>
						</h2>
						<section class="c-tab-title">
							<a href="javascript: void(0)">&nbsp;</a>
						</section>
					</header>
					<!-- /无数据提示 开始-->
					<c:if test="${empty courseList }">
						<section class="no-data-wrap">
							<em class="icon30 no-data-ico">&nbsp;</em> <span class="c-666 fsize14 ml10 vam">没有相关数据，小编正在努力整理中...</span>
						</section>
					</c:if>
					<!-- /无数据提示 结束-->
					<article class="comm-course-list">
						<ul class="of">
							<c:if test="${not empty courseList }">
								<c:forEach var="course" items="${courseList }">
									<li>
										<div class="cc-l-wrap">
											<section class="course-img">
												<c:choose>
													<c:when test="${not empty course.logo }">
														<img xSrc="<%=staticImage%>${course.logo}" src="${ctx }/static/inxweb/img/default-img.gif" class="img-responsive" alt="">
													</c:when>
													<c:otherwise>
														<img xSrc="/static/inxweb/img/default-img.gif" src="${ctx }/static/inxweb/img/default-img.gif" class="img-responsive" alt="">
													</c:otherwise>
												</c:choose>
												<div class="cc-mask">
													<a href="${ctx }/front/couinfo/${course.courseId}" title="开始学习" class="comm-btn c-btn-1">开始学习</a>
												</div>
											</section>
											<h3 class="hLh30 txtOf mt10">
												<a href="${ctx }/front/couinfo/${course.courseId}" title="${course.courseName }" class="course-title fsize18 c-333">${course.courseName }</a>
											</h3>
										</div>
									</li>
								</c:forEach>
							</c:if>
						</ul>
						<div class="clear"></div>
					</article>
				</div>
				<!-- 公共分页 开始 -->
				<div>
					<form action="${ctx }/front/teacher/${teacher.id}" method="post" id="searchForm">
						<input type="hidden" name="page.currentPage" id="pageCurrentPage" value="1">
					</form>
					<jsp:include page="/WEB-INF/view/common/front_page.jsp"></jsp:include>
				</div>
				<!-- 公共分页 结束 -->
			</section>
		</section>
		<!-- /课程列表 结束 -->
	</div>
	<script>
		$(function() {
			cTabFun($("#t-infor-menu>p>a")); // 滚动定位
			scrollLoad(); //响应滚动加载课程图片
		})
	</script>
</body>
</html>