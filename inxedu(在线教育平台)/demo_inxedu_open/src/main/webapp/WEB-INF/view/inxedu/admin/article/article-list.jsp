<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>文章列表</title>
<script type="text/javascript" src="${ctx}/static/admin/article/article.js"></script>
<script src="${ctximg}/static/common/jquery-ui-1.10.4/js/jquery-ui-1.10.4.custom.js?v=${v}"></script>
<script src="${ctximg}/static/common/jquery-ui-1.10.4/js/jquery.ui.datepicker-zh-CN.js?v=${v}"></script>
<script type="text/javascript" src="${ctximg}/static/common/jquery-ui-1.10.4/js/jquery-ui-timepicker-addon.js?v=${v}"></script>
<script type="text/javascript" src="${ctximg}/static/common/jquery-ui-1.10.4/js/jquery-ui-timepicker-zh-CN.js?v=${v}"></script>
</head>
<body>
	<div class="">
		<form action="${ctx}/admin/article/showlist" method="post" id="searchForm">
			<input type="hidden" id="pageCurrentPage" name="page.currentPage" value="1" />
			<input placeholder="标题/作者/来源" type="text" name="queryArticle.queryKey" value="${queryArticle.queryKey}" />
			<select name="queryArticle.type">
				<option value="0">请选择类型</option>
				<option value="2">文章</option>
			</select>
			创建时间:
			<input placeholder="开始创建时间" name="queryArticle.beginCreateTime"
				value="<fmt:formatDate value="${queryArticle.beginCreateTime}" pattern="yyyy-MM-dd HH:mm"/>" id="beginCreateTime" type="text"
				readonly="readonly" style="width: 128px;"/>-
			<input placeholder="结束创建时间" id="endCreateTime" name="queryArticle.endCreateTime"
				value="<fmt:formatDate value="${queryArticle.endCreateTime}" pattern="yyyy-MM-dd HH:mm"/>" type="text" readonly="readonly" style="width: 128px;"/>
			<a title="查找用户" onclick="$('#searchForm').submit();" class="button tooltip" href="javascript:void(0)">
				<span class="ui-icon ui-icon-search"></span> 查找文章
			</a>
			<a title="清空" onclick="$('#searchForm input:text').val('');$('#searchForm select').val(0);" class="button tooltip" href="javascript:void(0)">
				<span class="ui-icon ui-icon-cancel"></span> 清空
			</a>
			<a title="批量删除" onclick="deleteArticle()" class="button tooltip" href="javascript:void(0)">
				<span></span> 批量删除
			</a>
		</form>
		<form action="${ctx}/admin/article/delete" id="deleteForm" method="post">
			<table cellspacing="0" cellpadding="0" border="0" class="fullwidth" width="100%">
				<thead>
					<tr>
						<td align="center">
							<input name="allck" type="checkbox" onclick="selectAll(this)" />
						</td>
						<td align="center">标题</td>
						<td align="center">作者</td>
						<td align="center">来源</td>
						<td align="center">类型</td>
						<td align="center">创建时间</td>
						<td align="center">发布时间</td>
						<td align="center">点击量</td>
						<td align="center" width="232">操作</td>
					</tr>
				</thead>

				<tbody>
					<c:forEach items="${articleList}" var="article" varStatus="index">
						<tr <c:if test="${index.count%2==1 }">class="odd"</c:if>>
							<td align="center">
								<input type="checkbox" name="articelId" value="${article.articleId}" />
							</td>
							<td align="center">${article.title}</td>
							<td align="center">${article.author}</td>
							<td align="center">${article.source}</td>
							<td align="center">
								<c:if test="${article.type==1}">公告</c:if>
								<c:if test="${article.type==2}">文章</c:if>
							</td>
							<td align="center">
								<fmt:formatDate value="${article.createTime}" pattern="yyyy/MM/dd HH:mm" />
							</td>
							<td align="center">
								<fmt:formatDate value="${article.publishTime}" pattern="yyyy/MM/dd HH:mm" />
							</td>
							<td align="center">${article.clickNum}</td>
							<td align="center">
								<button onclick="thisDelete(this)" class="ui-state-default ui-corner-all" type="button">删除</button>
								<button onclick="window.location.href='${ctx}/admin/article/initupdate/${article.articleId}'" class="ui-state-default ui-corner-all" type="button">修改</button>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</form>
		<jsp:include page="/WEB-INF/view/common/admin_page.jsp" />
	</div>
</body>
</html>