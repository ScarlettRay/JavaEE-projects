<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改评论</title>
</head>
<body>
<fieldset>
	<legend>
		<span>评论列表</span>
		&gt;
		<span>修改评论</span>
	</legend>
	<form action="${ctx}/admin/commentreply/update" method="post" id="commentForm">
	<input type="hidden" name="commentId" value="${comment.commentId}">
	<div class="">
		<table style="line-height: 35px;"  width="100%">
			<tr>
				<td width="10%" align="center">
					<font color="red">*</font>邮箱
				</td>
				<td>
					${comment.email }
				</td>
			</tr>
			<tr>
				<td align="center">类型：</td>
				<td>
						<c:if test="${comment.type==1}">文章</c:if>
						<c:if test="${comment.type==2}">课程</c:if>
				</td>
			</tr>
			<tr>
				<td align="center">点赞数：</td>
				<td>
					<input type="text" value="${comment.praiseCount}" name="praiseCount">
				</td>
			</tr>
			<tr>
				<td align="center">回复数：</td>
				<td>
					${comment.replyCount}
				</td>
			</tr>
			<tr>
				<td align="center">评论内容：</td>
				<td>
					<input type="text" value="<c:out value="${comment.content}"></c:out>" name="content">
				</td>
			</tr>
			<tr>
				<td align="center"></td>
				<td>
					<input onclick="history.go(-1)" class="button" type="button" value="返回">
					<input  class="button" type="submit" value="提交">
				</td>
			</tr>
		</table>
	</div>
	</form>
</fieldset>
</body>
</html>