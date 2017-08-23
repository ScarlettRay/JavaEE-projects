<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>问答详情</title>
<script>
/*
 *修改问答 
 */
function updQuestions(){
	var id=$("#updateForm input[name='questions.id']").val();
	var title=$("#updateForm input[name='questions.title']").val();
	var browseCount=$("#updateForm input[name='questions.browseCount']").val();
	var praiseCount=$("#updateForm input[name='questions.praiseCount']").val();
	var content=$("#updateForm textarea[name='questions.content']").val();
	if(title==""){
		alert("请输入问答标题");
		return;
	}else if(title.trim().length<16){
		alert("问答标题长度必须大于16");
		return;
	}
	
	if(content==""||content==null){
		alert("请输入问答内容");
		return;
	}
	
	$.ajax({
		url:baselocation +"/admin/questions/updQuestions",
		data:{
			"questions.id":id,
			"questions.title":title,
			"questions.content":content,
			"questions.praiseCount":praiseCount,
			"questions.browseCount":browseCount
		},
		type:"post",
		dataType:"json",
		async:true,
		success:function(result){
			if(result.success==true){
				alert("修改成功!");
			}else{
				alert(result.message);
			}
		}
	});
}
</script>
</head>
<body>
<fieldset>
	<legend>
		<span>问答管理</span>
		&gt;
		<span>修改问答</span>
	</legend>
	<div class="">
		<form action="/admin/questions/updQuestions" method="post" id="updateForm">
			<table style="line-height: 35px;" width="100%">
				<tr>
					<td align="center" width="10%">
						<font color="red">*</font>问答标题:
					</td>
					<td>
						<input id="" name="questions.id" value="${questions.id }" type="hidden"/>
						<input id="" name="questions.title" value="${questions.title }" style="width: 507px;" type="text"/>
					</td>
				</tr>
				<tr>
					<td align="center">
						<font color="red">*</font>发表人昵称:
					</td>
					<td>
						${questions.showName }
					</td>
				</tr>
				<tr>
					<td align="center">
						<font color="red">*</font>发表人邮箱:
					</td>
					<td>
						${questions.email }
					</td>
				</tr>
				<tr>
					<td align="center">
						<font color="red">*</font>问答分类:
					</td>
					<td style="text-align: left;">
						<c:if test="${questions.type==1}">课程问答</c:if>
						<c:if test="${questions.type==2}">学习分享</c:if>
					</td>
				</tr>
				<tr>
					<td align="center">
						<font color="red">*</font>问答标签:
					</td>
					<td>
						<c:forEach items="${questions.questionsTagRelationList }" var="questionsTag">
							${questionsTag.tagName }&nbsp;&nbsp;&nbsp;
						</c:forEach>
						
					</td>
				</tr>
				<tr>
					<td align="center">
						<font color="red">*</font>是否采纳:
					</td>
					<td style="text-align: left;">
						<c:if test="${questions.status==0}">否</c:if>
						<c:if test="${questions.status==1}">是</c:if>
					</td>
				</tr>
				<c:if test="${questions.status==1}">
					<c:forEach items="${questions.questionsCommentList }" var="questionsComment">
						<tr>
							<td align="center">
								<font color="red">*</font>最佳答案:
							</td>
							<td style="text-align: left;">
								<c:out value="${questionsComment.content }"></c:out>
							</td>
						</tr>
						<tr>
							<td align="center">
								<font color="red">*</font>提供者邮箱:
							</td>
							<td style="text-align: left;">
								${questionsComment.email }
							</td>
						</tr>
						<tr>
							<td align="center">
								<font color="red">*</font>提供者昵称:
							</td>
							<td style="text-align: left;">
								${questionsComment.showName }
							</td>
						</tr>
					</c:forEach>
				</c:if>
				<tr>
					<td align="center">
						<font color="red">*</font>回复数:
					</td>
					<td style="text-align: left;">
						${questions.replyCount }
					</td>
				</tr>
				<tr>
					<td align="center">
						<font color="red">*</font>点赞数:
					</td>
					<td style="text-align: left;">
						<input id="" name="questions.praiseCount" value="${questions.praiseCount }" type="text" onkeyup="value=value.replace(/[^\d]/g,'') "/>
					</td>
				</tr>
				<tr>
					<td align="center">
						<font color="red">*</font>浏览数:
					</td>
					<td style="text-align: left;">
						<input id="" name="questions.browseCount" value="${questions.browseCount }" type="text" onkeyup="value=value.replace(/[^\d]/g,'') "/>
					</td>
				</tr>
				<tr>
					<td align="center">问答内容:</td>
					<td style="text-align: left;">
						<textarea name="questions.content" rows="100" cols="100">${questions.content}</textarea>
					</td>
				</tr>
				<tr>
					<td align="center">
						<font color="red">*</font>添加时间:
					</td>
					<td style="text-align: left;">
						<fmt:formatDate value="${questions.addTime}" pattern="yyyy/MM/dd HH:mm" />
					</td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<input onclick="updQuestions()" class="button" type="button" value="修改" />
						<input onclick="history.go(-1);" class="button" type="button" value="返回" />
					</td>
				</tr>
			</table>
		</form>
	</div>
</fieldset>
</body>
</html>
