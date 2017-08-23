<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>问答管理列表</title>
<script type="text/javascript" src="${ctx}/static/common/jquery-1.11.1.min.js"></script>
<script src="${ctximg}/static/common/jquery-ui-1.10.4/js/jquery-ui-1.10.4.custom.js?v=${v}"></script>
<script src="${ctximg}/static/common/jquery-ui-1.10.4/js/jquery.ui.datepicker-zh-CN.js?v=${v}"></script>
<script type="text/javascript" src="${ctximg}/static/common/jquery-ui-1.10.4/js/jquery-ui-timepicker-addon.js?v=${v}"></script>
<script type="text/javascript" src="${ctximg}/static/common/jquery-ui-1.10.4/js/jquery-ui-timepicker-zh-CN.js?v=${v}"></script>
<script type="text/javascript" src="${ctx}/static/admin/questions/questions.js"></script>
</head>
<body>
	<div class="">
		<form action="${ctx}/admin/questions/list" method="post" id="searchForm">
			<input type="hidden" id="pageCurrentPage" name="page.currentPage" value="1" />
			标题:
			<input placeholder="标题" name="questions.title" value="${questions.title}" />
			添加时间:
			<input placeholder="添加开始时间" name="questions.beginCreateTime"
				value="<fmt:formatDate value="${questions.beginCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>" id="beginCreateTime" type="text"
				readonly="readonly" style="width:120px;"/>
			-
			<input placeholder="添加结束时间" id="endCreateTime" name="questions.endCreateTime"
				value="<fmt:formatDate value="${questions.endCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>" type="text" readonly="readonly" style="width:120px;"/>
			
			类型:
			<select id="" name="questions.type">
				<option value="0">--全部--</option>
				<option value="1">课程问答</option>
				<option value="2">学习分享</option>
			</select>
			
			问答标签:
			<select id="" name="questions.questionsTagId">
				<option value="0">--全部--</option>
				<c:forEach items="${questionsTagList }" var="questionsTag">
					<option value="${questionsTag.questionsTagId }">${questionsTag.questionsTagName }</option>
				</c:forEach>
			</select>
			
			<script>
				$(function(){
					//条件选中
					$("select[name='questions.type']").val("${questions.type}");
					if("${questions.questionsTagId}"!=""&&"${questions.questionsTagId}"!=null){
						$("select[name='questions.questionsTagId']").val("${questions.questionsTagId}");
					}
				})
			</script>
			<a title="查询问答" onclick="$('#searchForm').submit();" class="button tooltip" href="javascript:void(0)">
				<span class="ui-icon ui-icon-search"></span>
				查询问答
			</a>
			<a title="清空" onclick="$('#searchForm input:text').val('');" class="button tooltip" href="javascript:void(0)">
				<span class="ui-icon ui-icon-cancel"></span>
				清空
			</a>
		</form>
		<table cellspacing="0" cellpadding="0" border="0" class="fullwidth" width="100%">
			<thead>
				<tr>
					<td align="center">id</td>
					<td align="center">发表人</td>
					<td align="center">问答标题</td>
					<td align="center">类型</td>
					<td align="center">是否采纳</td>
					<td align="center">回复数</td>
					<td align="center">浏览数</td>
					<td align="center">点赞数</td>
					<td align="center">添加时间</td>
					<td align="center">操作</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${questionsList}" var="questions" varStatus="index">
					<tr <c:if test="${index.count%2==1 }">class="odd"</c:if>>
						<td align="center">${questions.id}</td>
						<td align="center">${questions.email}</td>
						<td align="center">${questions.title}</td>
						<td align="center">
							<c:if test="${questions.type==1}">课程问答</c:if>
							<c:if test="${questions.type==2}">学习分享</c:if>
						</td>
						<td align="center">
							<c:if test="${questions.status==0}">否</c:if>
							<c:if test="${questions.status==1}">是</c:if>
						</td>
						<td align="center">${questions.replyCount}</td>
						<td align="center">${questions.browseCount}</td>
						<td align="center">${questions.praiseCount}</td>
						<td align="center">
							<fmt:formatDate value="${questions.addTime}" pattern="yyyy/MM/dd HH:mm" />
						</td>
						<td align="center">
							<button onclick="window.location.href='${ctx }/admin/questionscomment/list?questionsComment.questionId=${questions.id}'" class="ui-state-default ui-corner-all" type="button">查看回复</button>
							<button onclick="window.location.href='${ctx }/admin/questions/info/${questions.id}'" class="ui-state-default ui-corner-all" type="button">修改</button>
							<button onclick="delQuestions('${questions.id}')" class="ui-state-default ui-corner-all" type="button">删除</button>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<jsp:include page="/WEB-INF/view/common/admin_page.jsp" />
	</div>
</body>
</html>