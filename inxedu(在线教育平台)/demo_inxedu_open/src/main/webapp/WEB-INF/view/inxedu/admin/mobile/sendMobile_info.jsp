<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>

	<script type="text/javascript" src="${ctximg}/kindeditor/kindeditor-all.js?v=1410957986989"></script>
<link rel="stylesheet" type="text/css" href="${ctximg}/static/common/jquery-ui-1.10.4/css/ui-lightness/jquery-ui-1.10.4.custom.css?v=${v}"/>
<script type="text/javascript" src="${ctximg}/static/common/jquery-ui-1.10.4/js/jquery-ui-1.10.4.custom.js?v=${v}"></script>
<script type="text/javascript" src="${ctximg}/static/common/jquery-ui-1.10.4/js/jquery.ui.datepicker-zh-CN.js?v=${v}"></script>
<script type="text/javascript" src="${ctximg}/static/common/jquery-ui-1.10.4/js/jquery-ui-timepicker-addon.js?v=${v}"></script>
<script type="text/javascript" src="${ctximg}/static/common/jquery-ui-1.10.4/js/jquery-ui-timepicker-zh-CN.js?v=${v}"></script>
	<%--ue编辑器--%>
	<script type="text/javascript" charset="utf-8" src="${ctx}/static/common/ueditor/ueditor.config.js"></script>
	<script type="text/javascript" charset="utf-8" src="${ctx}/static/common/ueditor/ueditor.all.js"></script>

<script type="text/javascript">
	$(function(){
		  $( "#sendDate" ).datetimepicker({
			  regional:"zh-CN",
		      changeMonth: true,
		      dateFormat:"yy-mm-dd ",
		      timeFormat: "HH:mm:ss"
		  });
		  //initKindEditor_addblog('textareamobile', 720, 300,'mobile','true');
		//实例化编辑器 UE编辑器
		initUEEditor("textareamobile",'720','300');
		  
	});
	function updateSubmit(){
		alert("sadfsad");
		var sendTime=$("#sendDate").val();
		 var nowTime=new Date().getTime();
		 var sendDate=Date.parse(sendTime.replace(/-/gi,"/"));

		 if($("#mobileContent").val()==""){
			alert("短信内容不能为空！");
			return;
		}else if(sendTime==""||sendTime==null){
             alert("发送时间不能为空");
			 return;
		 }else if(sendDate<nowTime){
			 alert("定时发送时间必须大于当前系统时间");
			 return;
		 }
		var id=$("#id").val();
		var content=$("#mobileContent").val();
		$.ajax({
			url : "${ctx}/admin/mobile/updateUserMsg",
			data : {
				"msgId": id ,
				"content":content,
				"sendTime": $("#sendDate").val()
			},
			type : "post",
			dataType : "json",
			success : function(result){
				alert(result.message);
			}
		})
	}
</script>
</head>
<body >
<fieldset>
	<legend>
		<span>短信管理</span>
		&gt;
		<span>详情</span>
	</legend>
		<form action="${ctx}/admin/user/updatePwd" method="post" id="addPaperForm">
			<input name="userMobileMsg.id" id="id" type="hidden" value="${userMobileMsg.id}"/>
			<table style="line-height: 35px;" width="100%">
				<tr>
					<td align="center"><font color="red">*</font>&nbsp;短信内容：</td>
					<td>
						<textarea rows="6" cols="80" style="height: 80px;width: 500px;" name="userMobileMsg.content" id="mobileContent" <c:if test="${userMobileMsg.status!=2&&userMobileMsg.type!=2 }">readonly="readonly"</c:if>> ${userMobileMsg.content }</textarea>       
					</td>
				</tr>
				<tr>
					<td align="center"><font color="red">*</font>&nbsp;创建时间：</td>
					<td>
					
						<input type="text" disabled="disabled" value="<fmt:formatDate value="${userMobileMsg.createTime }" type="both"/>"/>
					</td>
				</tr>
				<tr>
					<td align="center"><font color="red">*</font>&nbsp;发送时间：</td>
					<td>
					
						<input type="text" <c:if test="${userMobileMsg.status!=2&&userMobileMsg.type!=2 }">disabled="disabled"</c:if> readonly="readonly" id="sendDate" value="<fmt:formatDate value="${userMobileMsg.sendTime }" type="both"/>"/>
					</td>
				</tr>
				<tr>
					<td align="center"><font color="red">*</font>&nbsp;发送状态：</td>
					<td>
						<c:if test="${userMobileMsg.status==1 }">已发送</c:if>
						<c:if test="${userMobileMsg.status==2 }">未发送</c:if>
					</td>
				</tr>
				<tr>
					<td align="center"><font color="red">*</font>&nbsp;发送人：</td>
					<td>
						<input type="text" value="${userMobileMsg.loginName }" disabled="disabled"/>
					</td>
				</tr>
				<tr>
					<td align="center"><font color="red">*</font>&nbsp;接收人：</td>
					<td>
						<textarea id="textareamobile" <c:if test="${userMobileMsg.status!=2&&userMobileMsg.type!=2 }">disabled="disabled" readonly="readonly"</c:if> rows="6" cols="80" style="line-height: normal;">${userMobileMsg.mobile}</textarea>
					</td>
				</tr>
				<tr>
					<td align="center"><font color="red">*</font>&nbsp;发送方式：</td>
					<td>
						<c:if test="${userMobileMsg.status==1 }">正常发送</c:if>
						<c:if test="${userMobileMsg.status==2 }">定时发送</c:if>
					</td>
				</tr>
				<tr>
					<td align="center" colspan="2">
						<c:if test="${userMobileMsg.status==2&&userMobileMsg.type==2 }">
							<input onclick="updateSubmit()" class="button" type="button" value="修 改" />
						</c:if>
						<input onclick="history.go(-1);" class="button" type="button" value="返回" />
					</td>
				</tr>
			</table>
		</form>
	</div>
</fieldset>
</body>
</html>
