<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>推荐课程管理列表</title>
<script type="text/javascript" src="${ctx}/static/admin/website/detail.js"></script>
</head>
<body>
	<div class="">
		<form action="${ctx}/admin/detail/list" method="post" id="searchForm">
			<input type="hidden" id="pageCurrentPage" name="page.currentPage" value="1" />
			<input placeholder="课程名" type="text" name="dto.courseName" value="${dto.courseName}" />
			<select name="dto.id">
				<option value="0">请选择类型</option>
				<c:forEach items="${websiteCourseList}" var="list">
					<c:choose>
						<c:when test="${dto.id==list.id}">
							<option selected="selected" value="${list.id}">${list.name}</option>
						</c:when>
						<c:otherwise>
							<option value="${list.id}">${list.name}</option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</select> <select name="dto.isavaliable">
				<option value="0">请选择状态</option>
				<option <c:if test="${dto.isavaliable==1}"> selected="selected" </c:if> value="1">上架</option>
				<option <c:if test="${dto.isavaliable==2}"> selected="selected" </c:if> value="2">下架</option>
			</select>
			<a title="查找" onclick="$('#searchForm').submit();" class="button tooltip" href="javascript:void(0)">
				<span class="ui-icon ui-icon-search"></span>
				查找
			</a>
			<a title="清空" onclick="$('#searchForm input:text').val('');$('#searchForm select').val(0);" class="button tooltip"
			   href="javascript:void(0)">
				<span class="ui-icon ui-icon-cancel"></span>
				清空
			</a>
			<a title="添加推荐课程" onclick="selectCousre()" class="button tooltip" href="javascript:void(0)">
				<span class="ui-icon ui-icon-newwin"></span>
				添加推荐课程
			</a>
		</form>
		<table cellspacing="0" cellpadding="0" border="0" class="fullwidth" width="100%">
			<thead>
				<tr>
					<td align="center">ID</td>
					<td align="center">课程名称</td>
					<td align="center">推荐分类</td>
					<td align="center" width="70">排序值</td>
					<td align="center">课程状态</td>
					<th align="center">有效结束时间</th>
					<td align="center">操作</td>
				</tr>
			</thead>

			<tbody>
				<c:choose>
					<c:when test="${dtoList!=null && dtoList.size()>0}">
						<c:forEach items="${dtoList}" var="dto" varStatus="index">
							<tr <c:if test="${index.count%2==1 }">class="odd"</c:if>>
								<td align="center">${dto.id}</td>
								<td align="center">${dto.courseName}</td>
								<td align="center">${dto.recommendName}</td>
								<td align="center">${dto.orderNum}</td>
								<td align="center">
									<c:if test="${dto.isavaliable==1}">上架</c:if>
									<c:if test="${dto.isavaliable==2}">下架</c:if>
								</td>
								<td align="center">
									<c:if test="${not empty dto.endTime}">
										<fmt:formatDate value="${dto.endTime}" pattern="yyyy/MM/dd HH:mm"/>
									</c:if>
									<c:if test="${empty dto.endTime}">
										购买后${dto.loseTime}天
									</c:if>
								</td>
								<td align="center">
									<button onclick="updateSort(${dto.id},this)" class="ui-state-default ui-corner-all" type="button">修改排序</button>
									<button onclick="deleteDetail(${dto.id})" class="ui-state-default ui-corner-all" type="button">删除</button>
								</td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr class="odd">
							<td align="center" colspan="6">
								<div class="tips">
									<span>还没有相关推荐课程！</span>
								</div>
							</td>
						</tr>
					</c:otherwise>
				</c:choose>
			</tbody>
		</table>
		<jsp:include page="/WEB-INF/view/common/admin_page.jsp" />
	</div>
</body>
</html>