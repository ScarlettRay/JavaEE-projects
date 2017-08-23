<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>选择讲师列表</title>
<script type="text/javascript" src="${ctximg}/static/admin/teacher/select_teacher_list.js"></script>
</head>
<body>
	<div class="">
		<form action="${ctx}/admin/teacher/selectlist/${type}" method="post" id="searchForm">
			<input type="hidden" id="pageCurrentPage" name="page.currentPage" value="1" />
			讲师名称：
			<input placeholder="讲师名" type="text" name="queryTeacher.name" value="${queryTeacher.name}" />
			<select name="queryTeacher.isStar">
				<option <c:if test="${queryTeacher.isStar==0}"> selected="selected"</c:if> value="0">请选择</option>
				<option <c:if test="${queryTeacher.isStar==1}"> selected="selected"</c:if> value="1">高级讲师</option>
				<option <c:if test="${queryTeacher.isStar==2}"> selected="selected"</c:if> value="2">首席讲师</option>
			</select>
			<a title="查找用户" onclick="javascript:$('#searchForm').submit();" class="button tooltip" href="javascript:void(0)">
				<span class="ui-icon ui-icon-search"></span>
				查找
			</a>
			<a title="清空" onclick="javascript:$('#searchForm input:text').val('');$('#searchForm select').val(0);" class="button tooltip"
				href="javascript:void(0)">
				<span class="ui-icon ui-icon-cancel"></span>
				清空
			</a>
		</form>
		<table cellspacing="0" cellpadding="0" border="0" class="fullwidth" width="100%">
			<thead>
				<tr>
					<td align="center">
						<c:if test="${type=='checkbox'}">
							<input type="checkbox" id="selectAll" />
						</c:if>
					</td>
					<td align="center">名称</td>
					<td align="center">头衔</td>
					<td align="center">资历</td>
					<td align="center">简介</td>
				</tr>
			</thead>

			<tbody>
				<c:forEach items="${teacherList}" var="tc" varStatus="index">
					<tr <c:if test="${index.count%2==1 }">class="odd"</c:if>>
						<td align="center">
							<c:if test="${type=='checkbox'}">
								<input type="checkbox" name="teacherId" title='${tc.name}' value="${tc.id}" />
							</c:if>
							<c:if test="${type=='radio'}">
								<input type="radio" name="teacherId" title='${tc.name}' value="${tc.id}" />
							</c:if>
						</td>
						<td align="center">${tc.name}</td>
						<td align="center">
							<c:if test="${tc.isStar==1 }">高级讲师 </c:if>
							<c:if test="${tc.isStar==2 }">首席讲师 </c:if>
						</td>
						<td align="center">${tc.education }</td>
						<td align="center">${fn:substring(tc.career,0,30)}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<jsp:include page="/WEB-INF/view/common/admin_page.jsp" />
	</div>
	<div style="text-align: center;">
		<a title="确认" onclick="confirmSelect()" class="button tooltip" href="javascript:void(0)">
			<span></span>
			确认
		</a>
		<a title="关闭" onclick="closeWin()" class="button tooltip" href="javascript:void(0)">
			<span class="ui-icon ui-icon-cancel"></span>
			关闭
		</a>
	</div>
</body>
</html>