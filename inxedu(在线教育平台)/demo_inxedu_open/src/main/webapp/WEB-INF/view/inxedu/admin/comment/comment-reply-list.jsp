<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>评论回复列表</title>
<script>
function delcomment(commentId){
	 $.ajax({
		url : baselocation + '/admin/comment/del/'+commentId,
		type : 'post',
		async : true,
		dataType : 'text',
		success : function(result) {
			location.reload();
		}
	});
}
</script>
</head>
<body>
	<div class="">
		<form action="${ctx}/admin/commentreply/query" method="post" id="searchForm">
			<input type="hidden" id="pageCurrentPage" name="page.currentPage" value="1" />
			<input placeholder="邮箱" type="text" name="email" value="${comment.email}" />
			<input placeholder="内容" type="text" name="content" value="${comment.content}" />
			<a onclick="$('#searchForm').submit();" class="button tooltip" href="javascript:void(0)">
				<span class="ui-icon ui-icon-search"></span>
				查找评论
			</a>
			<a title="清空" onclick="$('#searchForm input:text').val('');$('#searchForm select').val(0);" class="button tooltip"
			   href="javascript:void(0)">
				<span class="ui-icon ui-icon-cancel"></span>
				清空
			</a>
			<input onclick="history.go(-1)" class="button" type="button" value="返回">
		</form>
		<form action="${ctx}/admin/article/publishOrDelete" id="publishOrDeleteForm" method="post">
			<table cellspacing="0" cellpadding="0" border="0" class="fullwidth" width="100%">
				<thead>
					<tr>
						<td align="center">
							id
						</td>
						<td align="center">邮箱</td>
						<td align="center">类型</td>
						<td align="center">点赞</td>
						<td align="center">回复</td>
						<td align="center">创建时间</td>
						<td align="center">评论内容</td>
						<td align="center" width="232">操作</td>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${commentList}" var="com" varStatus="index">
						<tr <c:if test="${index.count%2==1 }">class="odd"</c:if>>
							<td align="center">
								${com.commentId}
							</td>
							<td align="center">${com.email}</td>
							<td align="center">
								<c:if test="${com.type==1}">文章</c:if>
								<c:if test="${com.type==2}">课程</c:if>
							</td>
							<td align="center">${com.praiseCount}</td>
							<td align="center">${com.replyCount}</td>
							<td align="center">
								<fmt:formatDate value="${com.addTime}" pattern="yyyy/MM/dd HH:mm" />
							</td>
							<td align="center">${com.content}</td>
							<td align="center">
								<button onclick="delcomment('${com.commentId}')" class="ui-state-default ui-corner-all" type="button">删除</button>
								<button onclick="window.location.href='${ctx}/admin/commentreply/info/${com.commentId}'" class="ui-state-default ui-corner-all" type="button">修改</button>
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