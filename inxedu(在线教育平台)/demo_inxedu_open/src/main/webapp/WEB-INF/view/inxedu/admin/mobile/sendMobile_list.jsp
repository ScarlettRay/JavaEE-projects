<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>短信列表</title>
<script type="text/javascript" src="${ctximg}/static/common/jquery-ui-1.10.4/js/jquery-ui-1.10.4.custom.js?v=${v}"></script>
<script type="text/javascript" src="${ctximg}/static/common/jquery-ui-1.10.4/js/jquery.ui.datepicker-zh-CN.js?v=${v}"></script>

<script type="text/javascript">
$(function(){
	  $( "#startDate,#endDate" ).datepicker(
	    		{regional:"zh-CN",
	    		changeMonth: true,
	    		dateFormat:"yy-mm-dd"
	    		});
	  var type="${userMobileMsg.type}";
	  if(type!=null&&type!=""){
		  $("#type").val('${userMobileMsg.type}');
	  }
	  var status="${userMobileMsg.status}";
	  if(status!=null && status!=""){
		  $("#status").val('${userMobileMsg.status}');
	  }
	  
	  
});
function submitSearch(){
	$("#pageCurrentPage").val(1);
	$("#searchForm").submit();
	}
function clean(){
	$("#nickname,#useremail,#mobile,#startDate,#endDate").val("");
	$("#type,#status").val(0);
}

function delMsg(id){
	if(confirm("确定要删除吗？")){
		$.ajax({
			url : "${ctx}/admin/mobile/delMsgById/"+id,
			data : {},
			type : "post",
			dataType : "json",
			success : function(result){
				alert(result.message);
                if(result.success==true){
                    window.location.href="/admin/user/sendMsglist";
                }
			}
		})
	}
}
</script>
</head>
<body  >
<div class="">
		<form action="${ctx}/admin/mobile/sendMsglist" method="post" id="searchForm">
			<input id="pageCurrentPage" type="hidden" name="page.currentPage" value="${page.currentPage}"/>
			<span class="ddTitle"><font>手机号：</font></span>
			<input type="text" name="userMobileMsg.mobile" value="${userMobileMsg.mobile}" id="mobile" />
			<span class="ddTitle"><font>类型：</font></span>
            <select name="userMobileMsg.type" id="type">
                <option value="0" selected="selected">请选择</option>
                <option value="1">普通</option>
                <option value="2">定时</option>
            </select>
            <span class="ddTitle"><font>状态：</font></span>
            <select name="userMobileMsg.status" id="status">
                <option value="0" selected="selected">请选择</option>
                <option value="1">已发送</option>
                <option value="2">未发送</option>
            </select>
            <span class="ddTitle"><font>创建时间：</font></span>
			<input type="text" name="userMobileMsg.startDate" value="${userMobileMsg.startDate}"  id="startDate" class="AS-inp"/>
            <span class="ddTitle"><font>结束时间：</font></span>
			<input type="text" name="userMobileMsg.endDate" value="${userMobileMsg.endDate}"  id="endDate" class="AS-inp"/>
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
					<td align="center">短信类型</td>
					<td align="center">短信内容</td>
					<td align="center">手机号</td>
					<td align="center">创建时间</td>
					<td align="center">发送时间</td>
					<td align="center">状态</td>
					<td align="center">操作人</td>
					<td align="center">操作</td>
				</tr>
			</thead>
			<tbody>
			<c:if test="${list.size()>0}">
			<c:forEach  items="${list}" var="msg"  varStatus="index">
				<tr <c:if test="${index.count%2==1 }">class="odd"</c:if>>
				<td>${msg.id }</td>
                            <td>${msg.type==1?'普通':'定时'}</td>
				<td>${fn:substring(msg.content,0,50) }</td>
				<td>${fn:substring(msg.mobile,0,55) }<c:if test="${msg.mobile.length()>55 }">......</c:if></td>
				<td><fmt:formatDate value="${msg.createTime }" type="both"/></td>
                            <td><fmt:formatDate value="${msg.sendTime }" type="both"/></td>
				<td>
					<c:if test="${msg.status==1 }">已发送</c:if>
					<c:if test="${msg.status==2 }">未发送</c:if>
				</td>
				<td>${msg.loginName}</td>
				<td  class="c_666 czBtn" align="center">
					<button onclick="window.location.href='${ctx}/admin/mobile/sendMsgInfo/${msg.id }'" class="ui-state-default ui-corner-all" type="button">查看</button>
					<c:if test="${msg.status==2&&msg.type==2 }">
						<button onclick="delMsg(${msg.id })" class="ui-state-default ui-corner-all" type="button">删除</button>
					</c:if>
				</td>
			</tr>
			</c:forEach>
			</c:if>
			<c:if test="${list.size()==0||list==null}">
			<tr>
				<td align="center" colspan="16">
					<div class="tips">
					<span>还没有短信信息！</span>
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
