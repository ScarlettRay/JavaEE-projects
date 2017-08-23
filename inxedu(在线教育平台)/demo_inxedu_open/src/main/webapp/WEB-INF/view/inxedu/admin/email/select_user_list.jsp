<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>选择学员列表</title>
<link rel="stylesheet" type="text/css" href="${ctximg}/static/common/jquery-ui-1.10.4/css/ui-lightness/jquery-ui-1.10.4.custom.css?v=${v}"/>
<script type="text/javascript" src="${ctximg}/static/common/jquery-ui-1.10.4/js/jquery-ui-1.10.4.custom.js?v=${v}"></script>
<script type="text/javascript" src="${ctximg}/static/common/jquery-ui-1.10.4/js/jquery.ui.datepicker-zh-CN.js?v=${v}"></script>
<script type="text/javascript" src="${ctximg}/static/common/jquery-ui-1.10.4/js/jquery-ui-timepicker-addon.js?v=${v}"></script>
<script type="text/javascript" src="${ctximg}/static/common/jquery-ui-1.10.4/js/jquery-ui-timepicker-zh-CN.js?v=${v}"></script>

<script type="text/javascript">
$(function(){
	  $( "#startDate,#endDate" ).datetimepicker(
	    		{regional:"zh-CN",
	    		changeMonth: true,
	    		dateFormat:"yy-mm-dd ",
	    		timeFormat: "HH:mm:ss"
	    		});
});
function submitSearch(){
	$("#searchForm").prop("action","${ctx}/admin/email/select_userlist");
	$("#pageCurrentPage").val(1);
	$("#searchForm").submit();
	}
function clean(){
	$("#userId,#useremail,#mobile,#startDate,#endDate,#courseName").val("");
}

//关闭窗口
function quxiao() {
	window.close();
}
//存放数据的数组
var myArrayMoveStock = new Array();
//将小页面被选中的入库明细信息带到大页面
function selectQstList() {
	if (initArray()) {
		//调用父页面的方法
		window.close();
	}
}
function initArray() {
	var qstChecked = $("input[name='userId']:checked");
	if (qstChecked.length == 0) {
		alert("请选择用户");
		return;
	}
	qstChecked.each(function() {
		toParentsValue($(this).val());
	});
	opener.addnewUserId(myArrayMoveStock);
	quxiao();
}
// 把选中用户一条记录放到数组中
function toParentsValue(obj) {
	myArrayMoveStock.push(obj);
}

</script>
</head>
<body  >
<div class="">
		<form action="${ctx}/admin/email/select_userlist" method="post" id="searchForm">
			<input id="pageCurrentPage" type="hidden" name="page.currentPage" value="${page.currentPage}"/>
			姓名：
			<input type="text" name="user.userName" value="${user.userName}" id="userName" />
			邮箱：
			<input type="text" name="user.email" value="${user.email}" id="email" />
			性别：
			<select name="user.sex">
          		<option <c:if test="${user.sex == 0 }">selected="selected"</c:if> value="0">请选择</option>
          		<option <c:if test="${user.sex == 1 }">selected="selected"</c:if> value="1">男</option>
          		<option <c:if test="${user.sex == 2 }">selected="selected"</c:if> value="2">女</option>
          	</select>
			<a title="查询" onclick="submitSearch()" class="button tooltip" href="javascript:void(0)">
				<span class="ui-icon ui-icon-search"></span>
				查询
			</a>
			<a title="清空" onclick="clean()" class="button tooltip"
				href="javascript:void(0)">
				<span class="ui-icon ui-icon-cancel"></span>
				清空
			</a>
		</form>
		<table cellspacing="0" cellpadding="0" border="0" class="fullwidth" width="100%">
			<thead>
				<tr>
					<td align="center">
						<%-- <c:if test="${type=='checkbox'}">
							<input type="checkbox" id="selectAll" />
						</c:if> --%>
					</td>
					<td align="center">姓名</td>
					<td align="center">邮箱</td>
					<td align="center">性别</td>
					<td align="center">注册时间</td>
					<td align="center">状态</td>
					<td align="center"></td>
				</tr>
			</thead>

			<tbody>
				<c:forEach items="${list}" var="u" varStatus="index">
					<tr <c:if test="${index.count%2==1 }">class="odd"</c:if>>
						<td align="center">
							<input type="checkbox" name="userId" title='${u.userName}' value="${u.email}" />
						</td>
						<td align="center">${u.userName}</td>
						<td align="center">${u.email}</td>
						<td align="center">
							<c:if test="${u.sex==1}">男</c:if>
							<c:if test="${u.sex==2}">女</c:if>
						</td>
						<td align="center">
							<fmt:formatDate value="${u.createTime}" pattern="yyyy/MM/dd HH:mm" />
						</td>
						<td align="center">
							<c:if test="${u.isavalible==1}">正常</c:if>
							<c:if test="${u.isavalible==2}">冻结</c:if>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<jsp:include page="/WEB-INF/view/common/admin_page.jsp" />
	</div>
	<div style="text-align: center;">
		<a title="确定" onclick="selectQstList()" class="button tooltip" href="javascript:void(0)">
			<span></span>
			确定
		</a>
		<a title="取消" onclick="quxiao()" class="button tooltip" href="javascript:void(0)">
			<span class="ui-icon ui-icon-cancel"></span>
			取消
		</a>
	</div>
	
	
<%-- <div class="">
		<form action="${ctx}/admin/cou/list" method="post" id="searchForm">
			<input id="pageCurrentPage" type="hidden" name="page.currentPage" value="${page.currentPage}"/>
			<table cellspacing="0" cellpadding="0" border="0" class="fullwidth" width="100%">
			<div class="w50pre fl">
				<ul class="ddBar">
					<li>
					    <span class="ddTitle"><font>&nbsp;&nbsp;&nbsp;&nbsp;ID：</font></span>
						<input type="text" name="user.userId" value="${user.userId}" id="userId" />
					</li>
					<li>
                       <span class="ddTitle"><font>姓名：</font></span>
                       <input type="text" name="user.userName" value="${user.userName}" id="userName" />
                    </li>
                    <li>
                       <span class="ddTitle"><font>昵称：</font></span>
                       <input type="text" name="user.showName" value="${user.showName}" id="showName" />
                    </li>
				</ul>
				
			</div>
           <div class="w50pre fl">
               <ul class="ddBar">
               		<li>
                       <span class="ddTitle"><font>&nbsp;&nbsp;&nbsp;&nbsp;性别：</font></span>
                       	<select name="user.sex">
                       		<option <c:if test="${user.sex == 0 }">selected="selected"</c:if> value="0">请选择</option>
                       		<option <c:if test="${user.sex == 1 }">selected="selected"</c:if> value="1">男</option>
                       		<option <c:if test="${user.sex == 2 }">selected="selected"</c:if> value="2">女</option>
                       	</select>
                    </li>
              		<li>
                        <span class="ddTitle"><font>手机号：</font></span>
                        <input type="text" name="user.mobile" value="${user.mobile}" id="mobile" />
                    </li>
                    <li>
                       <span class="ddTitle"><font>&nbsp;&nbsp;&nbsp;&nbsp;邮箱：</font></span>
                       <input type="text" name="user.email" value="${user.email}" id="email" />
                   	</li>
                   	<li>
                   		<input type="button"  class="button" value="查询" name="" onclick="submitSearch()"/>
                        <input type="button"  class="button" value="清空" name="" onclick="clean()"/>
                   	</li>
               </ul>
           </div>
			<thead>
				<tr>
					<th width="13%"><span>ID</span></th>
					<th width="8%"><span>昵称</span></th>
					<th><span>姓名</span></th>
					<th><span>邮箱</span></th>
					<th><span>手机</span></th>
					<th><span>性别</span></th>
					<th><span>年龄</span></th>
					<th><span>注册时间</span></th>
					<th><span>状态</span></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${list}" var="user">
					<tr class="odd">
						<td align="center">${user.userId}</td>
						<td align="center">${user.showName}</td>
						<td align="center">${user.userName}</td>
						<td align="center">${user.email}</td>
						<td align="center">${user.mobile}</td>
						<td align="center">
							<c:if test="${user.sex==1}">男</c:if>
							<c:if test="${user.sex==2}">女</c:if>
						</td>
						<td align="center">${user.age}</td>
						<td align="center">
							<fmt:formatDate value="${course.createTime}" pattern="yyyy/MM/dd HH:mm" />
						</td>
						<td align="center">
							<c:if test="${user.isavalible==1}">正常</c:if>
							<c:if test="${user.isavalible==2}">冻结</c:if>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		</form>
		<jsp:include page="/WEB-INF/view/common/admin_page.jsp" />
	</div> --%>
</body>
</html>
