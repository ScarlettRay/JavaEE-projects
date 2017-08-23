<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp"%>
<script type="text/javascript" src="${ctx}/static/common/admin/js/page.js"></script>
<c:if test="${page != null && page.totalResultSize>0}">
	<hr />
	<div>
		<div class="paginationWrap">
			<div style="overflow: hidden; clear: both;">
				<div style="float: right;">
					<div class="pagination pagination-large">
						<ul>
							<c:choose>
								<c:when test="${page.first}">
									<li class="disabled">
										<a href="javascript:void(0)">首页</a>
									</li>
									<li id="backpage" class="disabled">
										<a href="javascript:void(0)">←上一页</a>
									</li>
								</c:when>
								<c:otherwise>
									<li>
										<a href="javascript:goPage(1);">首页</a>
									</li>
									<li id="backpage">
										<a href="javascript:goPage(${page.currentPage-1 });">←上一页</a>
									</li>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${page.last}">
									<li id="nextpage" class="disabled">
										<a href="javascript:void(0)">下一页→</a>
									</li>
									<li class="disabled">
										<a href="javascript:void(0)">尾页 </a>
									</li>
								</c:when>
								<c:otherwise>
									<li id="nextpage">
										<a href="javascript:goPage(${page.currentPage+1});">下一页→</a>
									</li>
									<li>
										<a href="javascript:goPage(${page.totalPageSize})">尾页 </a>
									</li>
								</c:otherwise>
							</c:choose>
							<li class="c_333">
								<tt class="ml10 disIb">&nbsp;第</tt>
								<input type="text" id="pageNoIpt" size="4" style="height: 16px; margin-top: 2px; width: 24px; border: 1px solid #999999;">
								<tt class="ml10 disIb">页&nbsp;</tt>
								<input type="button" onclick="goPageByInput()" value="确定" class="button">
								&nbsp;&nbsp;
							</li>
						</ul>
					</div>
				</div>
				<div style="float: left;" class="pageDesc">
					<span>共查询到&nbsp;${page.totalResultSize}&nbsp;条记录，当前第&nbsp;${page.currentPage}/${page.totalPageSize}&nbsp;页</span>
				</div>
			</div>
		</div>
	</div>
</c:if>
<script type="text/javascript">	
	var totalPageSize=${page.totalPageSize};
	var currentPage = ${page.currentPage-1}<1?1:${page.currentPage};
	var totalPage = ${page.totalPageSize};
	showPageNumber();
</script>