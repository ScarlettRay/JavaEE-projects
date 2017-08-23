$(function(){
	/**关闭弹出*/
	$(".ui-dialog-titlebar-close,.closeBut").click(function(){
		closedData();
	});
});

/* 删除问答回复的 评论 */
function delQuestionsCommentSon(commentId){
	if(confirm("是否删除此问答评论吗?")){
		$.ajax({
			url:"/admin/questionscomment/delComment/"+commentId,
			data:{
			},
			dataType:"json",
			type:"post",
			async:true,
			success:function(result){
				if(result.success==true){
					window.location.reload();
				}else{
					alert(result.message);
				}
			}
		})
	}
}

/* 删除问答回复 */
function delQuestionsComment(commentId){
	if(confirm("是否删除此问答回复,及问答回复下的所有评论吗?")){
		$.ajax({
			url:"/admin/questionscomment/del/"+commentId,
			data:{
			},
			dataType:"json",
			type:"post",
			async:true,
			success:function(result){
				if(result.success==true){
					window.location.reload();
				}else{
					alert(result.message);
				}
			}
		})
	}
}

/**采纳为最佳答案**/
function acceptComment(id){
	$.ajax({
		url:"/admin/questionscomment/ajax/acceptComment/"+id,
		data:{
		},
		type:"post",
		dataType:"json",
		async:true,
		success:function(result){
			if(result.success==true){
				alert("采纳最佳答案成功!");
				window.location.reload();
			}else{
				alert(result.message);
			}
		}
	});
}
/**弹出框关闭*/
function closedData(){
	$("#createWin input:text,#updateWin input:text").val('');
	$("#createWin input:text").val('');
	$("input[name='sysUser.loginName']").val('');
	$("input:password").val('');
	$("select").val(0);
	$("#updateWin,#createWin,#updatePwdWin").hide();
}

/**
 * 根据id 获得 问答回复
 */
function getCommentContent(questionsCommentId){
	//closedData();
	$.ajax({
		url:baselocation+'/admin/questionscomment/getCommentById/'+questionsCommentId,
		type:'post',
		dataType:'json',
		success:function(result){
			if(result.success==true){
				var questionsComment = result.entity;
				$("#updateForm input[name='questionsComment.id']").val(questionsComment.id);
				$("#updateForm textarea[name='questionsComment.content']").val(questionsComment.content);
				$("#updateForm input[name='questionsComment.praiseCount']").val(questionsComment.praiseCount);
				$("#updateWin").show();
			}
		},
		error:function(error){
			alert("系统繁忙，请稍后再操作！");
		}
	});
	$("#commentContent").val(content);
	$("#updateWin").show();
}

/**
 * 修改问答回复
 */
function updateQuestionComment(){
	var content=$("#updateForm textarea[name='questionsComment.content']").val();
	if(content==""||content==null){
		alert("请输入回复内容");
		return;
	}
	var id=$("#updateForm input[name='questionsComment.id']").val();
	var praiseCount=$("#updateForm input[name='questionsComment.praiseCount']").val();
	$.ajax({
		url:"/admin/questionscomment/updQuestionComment",
		data:{
			"questionsComment.id":id,
			"questionsComment.content":content,
			"questionsComment.praiseCount":praiseCount
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
