<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>讲师列表</title>
<script src="${ctximg}/static/common/jquery-ui-1.10.4/js/jquery-ui-1.10.4.custom.js?v=${v}"></script>
<script src="${ctximg}/static/common/jquery-ui-1.10.4/js/jquery.ui.datepicker-zh-CN.js?v=${v}"></script>
<script type="text/javascript" src="${ctximg}/static/common/jquery-ui-1.10.4/js/jquery-ui-timepicker-addon.js?v=${v}"></script>
<script type="text/javascript" src="${ctximg}/static/common/jquery-ui-1.10.4/js/jquery-ui-timepicker-zh-CN.js?v=${v}"></script>
<script type="text/javascript">

/**
 * 删除老师
 * @param tcId 老师ID
 */
function deleteTeacher(tcId){
	if(confirm('确认要删除该讲师？')){
		document.location='/admin/teacher/delete/'+tcId;
	}
}
$(function(){
	/**加载时间控件*/
	$("#beginCreateTime,#endCreateTime").datetimepicker({
		regional:"zh-CN",
        changeMonth: true,
        dateFormat:"yy-mm-dd",
        timeFormat: "HH:mm:ss"
    });
});
</script>
</head>
<body>
	<div class="">
		<form action="${ctx}/admin/teacher/list" method="post" id="searchForm">
			<input type="hidden" id="pageCurrentPage" name="page.currentPage" value="1" />
			讲师名称：
			<input placeholder="讲师名" type="text" name="queryTeacher.name" value="${queryTeacher.name}" />
			<select name="queryTeacher.isStar">
				<option <c:if test="${queryTeacher.isStar==0}"> selected="selected"</c:if> value="0">请选择</option>
				<option <c:if test="${queryTeacher.isStar==1}"> selected="selected"</c:if> value="1">高级讲师</option>
				<option <c:if test="${queryTeacher.isStar==2}"> selected="selected"</c:if> value="2">首席讲师</option>
			</select>
			添加时间:
			<input placeholder="开始添加时间" name="queryTeacher.beginCreateTime"
				value="<fmt:formatDate value="${queryTeacher.beginCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>" id="beginCreateTime" type="text"
				readonly="readonly" style="width: 128px;"/>
			-
			<input placeholder="结束添加时间" id="endCreateTime" name="queryTeacher.endCreateTime"
				value="<fmt:formatDate value="${queryTeacher.endCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>" type="text" readonly="readonly" style="width: 128px;"/>
			<a title="查找用户" onclick="$('#searchForm').submit();" class="button tooltip" href="javascript:void(0)">
				<span class="ui-icon ui-icon-search"></span>
				查找
			</a>
			<a title="清空" onclick="$('#searchForm input:text').val('');$('#searchForm select').val(0);" class="button tooltip"
			   href="javascript:void(0)">
				<span class="ui-icon ui-icon-cancel"></span>
				清空
			</a>
		</form>
		<table cellspacing="0" cellpadding="0" border="0" class="fullwidth" width="100%">
			<thead>
				<tr>
					<td align="center">ID</td>
					<td align="center">名称</td>
					<td align="center">头衔</td>
					<td align="center" width="150">资历</td>
					<td align="center" width="250">简介</td>
					<td align="center">添加时间</td>
					<td align="center">排序</td>
					<td align="center" width="124">操作</td>
				</tr>
			</thead>

			<tbody>
				<c:forEach items="${teacherList}" var="tc" varStatus="index">
					<tr <c:if test="${index.count%2==1 }">class="odd"</c:if>>
						<td align="center">${tc.id}</td>
						<td align="center">${tc.name}</td>
						<td align="center">
							<c:if test="${tc.isStar==1 }">高级讲师 </c:if>
							<c:if test="${tc.isStar==2 }">首席讲师 </c:if>
						</td>
						<td align="center">${tc.education }</td>
						<td align="center">${fn:substring(tc.career,0,30)}</td>
						<td align="center">
							<fmt:formatDate type="both" value="${tc.createTime}" pattern="yyyy/MM/dd HH:mm" />
						</td>
						<td align="center">${tc.sort}</td>
						<td align="center">
							<button onclick="deleteTeacher(${tc.id})" class="ui-state-default ui-corner-all" type="button">删除</button>
							<button onclick="window.location.href='${ctx}/admin/teacher/toUpdate/${tc.id}'" class="ui-state-default ui-corner-all" type="button">修改</button>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<jsp:include page="/WEB-INF/view/common/admin_page.jsp" />
	</div>
</body>
</html>