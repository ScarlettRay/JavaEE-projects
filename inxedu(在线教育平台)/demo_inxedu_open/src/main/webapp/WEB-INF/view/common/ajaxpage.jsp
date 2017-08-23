<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp"%>
<script type="text/javascript" src="${ctx}/static/common/web/js/page.js"></script>
<c:if test="${page != null && page.totalResultSize>0}">
	<div class="paging">
		<a href="javascript:goPageAjax(1);" title="">首</a>
		<c:choose>
			<c:when test="${page.first}">
				<a id="backpage" class="undisable" href="javascript:void(0)" title="">&lt;</a>
			</c:when>
			<c:otherwise>
				<a id="backpage" href="javascript:goPageAjax(${page.currentPage-1 });" title="">&lt;</a>
			</c:otherwise>
		</c:choose>
		<c:choose>
			<c:when test="${page.last}">
				<a id="nextpage"href="javascript:void(0)" title="" class="undisable">&gt;</a>
			</c:when>
			<c:otherwise>
				<a id="nextpage" href="javascript:goPageAjax(${page.currentPage+1});" title="">&gt;</a>
			</c:otherwise>
		</c:choose>
		<a href="javascript:goPageAjax(${page.totalPageSize});" title="">末</a>
		<div class="clear"></div>
	</div>


</c:if>
<script type="text/javascript">
    var totalPageSize =${page.totalPageSize};
    var currentPage =${page.currentPage-1}<1 ? 1 :${page.currentPage};
    var totalPage = ${page.totalPageSize};
    showAjaxPageNumber();
</script>