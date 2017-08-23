<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>邮箱列表</title>

<script type="text/javascript" src="${ctximg}/static/common/jquery-ui-1.10.4/js/jquery-ui-1.10.4.custom.js?v=${v}"></script>
<script type="text/javascript" src="${ctximg}/static/common/jquery-ui-1.10.4/js/jquery.ui.datepicker-zh-CN.js?v=${v}"></script>

<script type="text/javascript">
$(function(){
	  $( "#startDate,#endDate" ).datepicker(
		{regional:"zh-CN",
		changeMonth: true,
		dateFormat:"yy-mm-dd"
		});
	
});
function submitSearch(){
	$("#pageCurrentPage").val(1);
	$("#searchForm").submit();
}
function clean(){
	$("#email,#status,#startDate,#endDate").val("");
    $("#type,#status").val(0);
}
</script>
</head>
<body>

<div class="">
		<form action="${ctx}/admin/email/sendEmaillist" method="post" id="searchForm">
			<input id="pageCurrentPage" type="hidden" name="page.currentPage" value="${page.currentPage}"/>
			<input type="hidden" id="subjectId" name="queryCourse.subjectId" value="${queryCourse.subjectId}" />
			<span class="ddTitle"><font>邮箱：</font></span>
			<input type="text" name="userEmailMsg.email" value="${userEmailMsg.email}" id="email" />
			<span class="ddTitle"><font>类型：</font></span>
            <select name="userEmailMsg.type" id="type">
                <option value="0">请选择</option>
                <option value="1">普通</option>
                <option value="2">定时</option>
            </select>
            <script>
                $("#type").val('${userEmailMsg.type}');
            </script>
            <span class="ddTitle"><font>是否发送：</font></span>
			<select name="userEmailMsg.status" id="status">
				<option value="0">请选择</option>
				<option value="1">已发送</option>
				<option value="2">未发送</option>
			</select>
			<script>
			$("#status").val('${userEmailMsg.status}');
			</script>
			<span class="ddTitle"><font>发送时间：</font></span>
			<input type="text" name="userEmailMsg.startDate" value="${userEmailMsg.startDate}"  id="startDate" class="AS-inp"/>
			<span class="ddTitle"><font>结束时间：</font></span>
			<input type="text" name="userEmailMsg.endDate" value="${userEmailMsg.endDate}"  id="endDate" class="AS-inp"/>
			<a title="查询" onclick="submitSearch()"
				class="button tooltip" href="javascript:void(0)">
				<span class="ui-icon ui-icon-cancel"></span>
				查询
			</a>
			<a title="清空" class="button tooltip" onclick="clean()" href="javascript:void(0)">
				<span></span>
				清空
			</a>
		</form>
		<table cellspacing="0" cellpadding="0" border="0" class="fullwidth" width="100%">
			<thead>
				<tr>
					<td align="center" width="150px">ID</td>
					<td align="center">邮件类型</td>
					<td align="center">是否发送</td>
					<td align="center">邮件标题</td>
					<td align="center">邮箱</td>
					<td align="center">创建时间</td>
					<td align="center">发送时间</td>
					<td align="center">操作人</td>
					<td align="center">操作</td>
				</tr>
			</thead>

			<tbody>
				<c:if test="${list.size()>0}">
				<c:forEach  items="${list}" var="msg"  varStatus="index">
					<tr <c:if test="${index.count%2==1 }">class="odd"</c:if>>
						<%-- <td><input type="checkbox" class="questionIds" id="${list.id }"/>&nbsp;${list.id }</td> --%>
						<td align="center">${msg.id }</td>
                        <td align="center">${msg.type==1?'普通':'定时' }</td>
                        <td align="center">${msg.status==1?'已发送':'未发送' }</td>
						<td align="center">${msg.title }</td>
						<td align="center">${fn:substring(msg.email, 0, 50)}</td>
						<td align="center"><fmt:formatDate value="${msg.createTime }" type="both"/></td>
                        <td align="center"><fmt:formatDate value="${msg.sendTime }" type="both"/></td>
						<td align="center">${msg.loginName}</td>
						<td align="center" class="c_666 czBtn" align="center">
							<button onclick="window.location.href='${ctx}/admin/email/sendEmailMsgInfo/${msg.id }'" class="ui-state-default ui-corner-all" type="button">查看</button>
							<c:if test="${msg.type==2&&msg.status==2}">
								<button onclick="window.location.href='${ctx}/admin/email/sendEmail/del?id=${msg.id }'" class="ui-state-default ui-corner-all" type="button">删除</button>
							</c:if>
						</td>
					</tr>
					</c:forEach>
					</c:if>
					<c:if test="${list.size()==0||list==null}">
					<tr>
						<td align="center" colspan="16">
							<div class="tips">
							<span>还没有邮件信息！</span>
							</div>
						</td>
					</tr>
					</c:if>
			</tbody>
		</table>
		<jsp:include page="/WEB-INF/view/common/admin_page.jsp" />
	</div>
</body>
</html>
