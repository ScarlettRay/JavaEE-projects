<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/base.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head><title>发送系统消息</title>
<script type="text/javascript" src="${ctx }/kindeditor/kindeditor-all.js"></script>
<script type="text/javascript">
KindEditor.ready(function(K) {
	window.EditorObject = K.create('textarea[id="message"]', {
			resizeType  : 1,
	       filterMode : false,//true时过滤HTML代码，false时允许输入任何代码。
	       allowPreviewEmoticons : false,
	       allowUpload : true,//允许上传 
	       syncType : 'auto',
	       urlType : 'domain',//absolute
	       newlineTag :'br',//回车换行br|p
	       uploadJson : '<%=keuploadSimpleUrl%>&param=question',//图片上传路径
	       allowFileManager : false,
	       afterBlur:function(){EditorObject.sync();}, 
	       items : ['emoticons']
	});
});
	function sendmessage(){
		var content = $("#message").val();
		if(content==null||content.trim()==""){
			alert("请填写消息内容在发送");
			return false;
		}
		 $.ajax({
             url:"${ctx}/admin/user/letter/sendJoinGroup",
             type:"post",
             data:{"content":content},
             dataType:"json",
             success:function(result){
             	if(result.message=='success'){
             		KindEditor.html('#message', '');
             		 alert("发送成功");
             	}
             }
         });
	}
</script>

</head>
<body  >
<fieldset>
	<legend>
		<span>系统管理</span>
		&gt;
		<span>发送系统消息</span>
	</legend>
	<div class="">
		<form action="${ctx}/admin/cou/addCourse" method="post" id="saveCourseForm">
			<input type="hidden" name="course.logo" />
			<table style="line-height: 35px;">
				<tr>
					<td>
						<font color="red">*</font>发送系统消息:
					</td>
					<td>
						<textarea name="" id="message"></textarea>
					</td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<input onclick="sendmessage()" class="button" type="button" value="保存" />
						<%--<input onclick="history.go(-1);" class="button" type="button" value="返回" />--%>
					</td>
				</tr>
			</table>
		</form>
	</div>
</fieldset>
</body>
</html>