<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <link rel="stylesheet" type="text/css" href="${ctximg}/static/common/jquery-ui-1.10.4/css/ui-lightness/jquery-ui-1.10.4.custom.css?v=${v}" />
    <link rel="stylesheet" type="text/css" href="${ctximg}/static/common/jquery-ui-1.10.4/css/ui-lightness/jquery-ui-timepicker-addon.css?v=${v}" />
    <script type="text/javascript" src="${ctximg}/static/common/jquery-ui-1.10.4/js/jquery-ui-1.10.4.custom.js?v=${v}"></script>
    <script type="text/javascript" src="${ctximg}/static/common/jquery-ui-1.10.4/js/jquery-ui-timepicker-addon.js?v=${v}"></script>
    <script type="text/javascript" src="${ctximg}/static/common/jquery-ui-1.10.4/js/jquery.ui.datepicker-zh-CN.js?v=${v}"></script>
    <script type="text/javascript" src="${ctximg}/static/common/jquery-ui-1.10.4/js/jquery-ui-timepicker-zh-CN.js?v=${v}"></script>
    <script type="text/javascript" src="${ctximg}/kindeditor/kindeditor-all.js?v=1410957986989"></script>
	<%--ue编辑器--%>
	<script type="text/javascript" charset="utf-8" src="${ctx}/static/common/ueditor/ueditor.config.js"></script>
	<script type="text/javascript" charset="utf-8" src="${ctx}/static/common/ueditor/ueditor.all.js"></script>
    <script type="text/javascript">
        $(function(){
            $("#startTime").datetimepicker({
                regional:"zh-CN",
                changeMonth: true,
                dateFormat:"yy-mm-dd",
                timeFormat : 'HH:mm:ss',
                timeFormat : 'HH:mm:ss'
            });
            //initKindEditor_addblog('content', 720, 300,'email','true');
			//实例化编辑器 UE编辑器
			initUEEditor("content",'720','300');
            //initKindEditor_addblog('textareaemail', 720, 300,'email','true');
			//实例化编辑器 UE编辑器
			initUEEditor("textareaemail",'720','300');
        });

        function update(){
            var content = $("#content").val();
            var title = $("#title").val();

            if(title.trim()==''){
                alert('邮件标题不能为空！');
                return false;
            }
            if(content.trim()==''){
                alert('邮件内容不能为空！');
                return false;
            }

            if(confirm('确定修改?')==false){
                return false;
            }
            var startTime = $("#startTime").val();
            $.ajax({
                url : "${ctx}/admin/email/sendEmailMsg/update",
                data : {
                    "id" : '${userEmailMsg.id}',
                    "content" : content,
                    "title" : title,
                    "sendTime" : startTime
                },  // 参数
                type : "post",
                async : false,
                dataType : "json",  //返回json数据
                success:function (result){
                    alert(result.message);
                    if(result.message=='成功'){
                        window.location.href="/admin/email/sendEmaillist";
                    }
                }
            });
        }
    </script>
</head>
<body >
<fieldset>
	<legend>
		<span>邮件管理</span>
		&gt;
		<span>详情</span>
	</legend>
	<div class="" >
		<form action="${ctx}/admin/user/updatePwd" method="post" id="addPaperForm">
			<input name="user.id" id="id" type="hidden" value="${user.id}"/>
			<table style="line-height: 35px;" width="100%">
					<tr>
						<td width="10%"><font color="red">*</font>&nbsp;邮件标题：</td>
						<td width="90%">
							<input  type="text" id="title" value="${userEmailMsg.title }"/>
						</td>
					</tr>
		            <tr>
		                <td><font color="red">*</font>&nbsp;邮件类型：</td>
		                <td style="text-align: left;">
		                    ${userEmailMsg.type==1?'普通':'定时' }
		                </td>
		            </tr>
		            <tr>
		                <td><font color="red">*</font>&nbsp;是否发送：</td>
		                <td>
		                    ${userEmailMsg.status==1?'已发送':'未发送' }
		                </td>
		            </tr>
		            <c:if test="${userEmailMsg.type==2}">
		            <tr>
		                <td width="93px;"><font color="red">*</font>&nbsp;定时发送时间：</td>
		                <td>
		                    <input type="text" class="" readonly="readonly" <c:if test="${userEmailMsg.status!=2&&userEmailMsg.type!=2 }">disabled="disabled"</c:if> value="<fmt:formatDate value="${userEmailMsg.sendTime}" type="both"/>" id="startTime" name=""/>
		
		                </td>
		            </tr>
		            </c:if>
					<tr>
						<td><font color="red">*</font>&nbsp;邮件内容：</td>
						<td>
							<textarea id="content" rows="6" cols="80" > ${userEmailMsg.content }</textarea>
						</td>
					</tr>
					<tr>
						<td><font color="red">*</font>&nbsp;创建时间：</td>
						<td>
						
							<input type="text" disabled="disabled" value="<fmt:formatDate value="${userEmailMsg.createTime }" type="both"/>"/>
						</td>
					</tr>
					<tr>
						<td><font color="red">*</font>&nbsp;发送人：</td>
						<td>
							<input type="text" value="${userEmailMsg.loginName }" disabled="disabled"/>
						</td>
					</tr>
					<tr>
						<td><font color="red">*</font>&nbsp;接收人：</td>
						<td >
		                    <textarea id="textareaemail" rows="6" cols="80" style="line-height:normal; ">${userEmailMsg.email}</textarea>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							 <c:if test="${userEmailMsg.type==2&&userEmailMsg.status==2}">
								<input onclick="update()" class="button" type="button" value="修 改" />
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
