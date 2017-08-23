<%@page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/base.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>我的收藏</title>
<script type="text/javascript">
	/**
	 * 全选或反选 
	 */
	function selectAll(em) {
		$("input[name='favouriteId']").attr('checked', $(em).prop('checked'));
	}

	/**
	 * 批量删除收藏
	 */
	function batchDelFav() {
		var arr = $("input[name='favouriteId']:checked");
		if (arr == null || arr.length == 0) {
			dialog("删除提示", "请选择要删除的收藏课程！", 1);
			return;
		}
		var favouriteIdStr = "";
		$('input[name="favouriteId"]:checked').each(function() {
			favouriteIdStr = favouriteIdStr + $(this).val() + ",";
		});
		favouriteIdStr = favouriteIdStr.substring(0, favouriteIdStr.length - 1);
		dialog("删除提示", "确认要删除选择的收藏课程？", 2, "${ctx}/uc/deleteFaveorite/"
				+ favouriteIdStr);
	}
</script>
</head>
<body>
	<article class="col-7 fl">
		<div class="u-r-cont">
			<section>
				<div>
					<section class="c-infor-tabTitle c-tab-title">
						<a href="javascript: void(0)" title="Wo的学习" style="cursor: default;">Wo的学习</a>
						<a href="javascript: void(0)" title="Wo的收藏" class="current">Wo的收藏</a>
					</section>
				</div>
				<section class="tar hLh30 pr10" style="background-color: #F6F6F6; margin-top: -20px;">
					<label class="hand c-999 vam"><input type="checkbox" name="" value="" style="vertical-align: -2px;" onclick="selectAll(this)">全选</label>
					<a href="javascript:void(0)" onclick="batchDelFav()" title="" class="vam ml10 c-blue">取消全部</a>
				</section>
				<div class="mt40">
					<c:if test="${empty favoriteList}">
						<!-- /无数据提示 开始-->
						<section class="no-data-wrap">
							<em class="icon30 no-data-ico">&nbsp;</em> <span class="c-666 fsize14 ml10 vam">您还没有收藏任何课程哦！</span>
						</section>
						<!-- /无数据提示 结束-->
					</c:if>
					<c:if test="${not empty favoriteList}">
						<div class="u-sys-news u-collection-list">
							<form action="">
								<c:forEach items="${favoriteList}" var="favorite" varStatus="index">
									<dl>
										<dt>
											<section class="tar">
												<p class="hLh30">
													<b class="fsize14 f-fA c-red"><fmt:formatDate type="both" value="${favorite.addTime }" pattern="yyyy年" /></b>
												</p>
												<p class="hLh20">
													<span class="f-fA c-666"><fmt:formatDate type="both" value="${favorite.addTime }" pattern="MM月dd日 HH:mm" /></span>
												</p>
												<p class="hLh20">
													<span class="f-fA c-999">收藏</span>
												</p>
											</section>
										</dt>
										<dd>
											<section class="mt10">
												<div class="of cancel-colle">
													<div class="fr tac">
														<label class="hand"><input type="checkbox" style="vertical-align: -2px;" name="favouriteId" value="${favorite.favouriteId}"></label> <br>
														<a href="${ctx}/uc/deleteFaveorite/${favorite.favouriteId}" title="" class="c-blue">取消收藏</a>
													</div>
													<a href="${ctx }/front/couinfo/${favorite.courseId }" title="">
														<c:choose>
															<c:when test="${not empty favorite.logo }">
																<img src="<%=staticImage %>${favorite.logo}" width="120" alt="">
															</c:when>
															<c:otherwise>
																<img src="${ctx }/static/inxweb/img/default-img.gif" width="120" alt="">
															</c:otherwise>
														</c:choose>
													</a>
												</div>
												<div class="hLh30 txtOf">
													<a href="${ctx }/front/couinfo/${favorite.courseId }" class="c-666 fsize14">${favorite.courseName }</a>
												</div>
											</section>
										</dd>
									</dl>
								</c:forEach>
							</form>
						</div>
					</c:if>
					<!-- 公共分页 开始 -->
					<jsp:include page="/WEB-INF/view/common/front_page.jsp" />
					<!-- 公共分页 结束 -->
					<form action="${ctx}/uc/myFavorites" method="post" id="searchForm">
						<input type="hidden" name="page.currentPage" value="1" id="pageCurrentPage" />
					</form>
				</div>
			</section>
			<!-- /Wo的消息 -->
		</div>
	</article>
	<!-- /右侧内容区 结束 -->
</body>
</html>