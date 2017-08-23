<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp"%>
<script type="text/javascript" src="${ctx}/static/common/web/js/page.js"></script>
<c:if test="${page != null && page.totalResultSize>0}">
	<div class="ans_feiye" id="page_div">
		<div class="mt50"> 
			<div class="pagination tac">
				<ul class="disIb">
					<c:choose>
						<c:when test="${page.first}">
							<li id="backpage" class="disabled">
								<a href="javascript:void(0)">«</a>
							</li>
						</c:when>
						<c:otherwise>
							<li id="backpage">
								<a href="javascript:goPage(${page.currentPage-1 });">«</a>
							</li>
						</c:otherwise>
					</c:choose>
					<c:choose>
						<c:when test="${page.last}">
							<li id="nextpage" class="disabled">
								<a href="javascript:void(0)">»</a>
							</li>
						</c:when>
						<c:otherwise>
							<li id="nextpage">
								<a href="javascript:goPage(${page.currentPage+1});">»</a>
							</li>
						</c:otherwise>
					</c:choose>
				</ul>
			</div>
		</div>
	</div>


</c:if>
<script type="text/javascript">
    var totalPageSize =${page.totalPageSize};
    var currentPage =
    ${page.currentPage-1}<1 ? 1 :${page.currentPage};
    var totalPage = ${page.totalPageSize};
    showPageNumber();
</script>