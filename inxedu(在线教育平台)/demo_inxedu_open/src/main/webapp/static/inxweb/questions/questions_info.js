$(function() {
	ganswerFun();
	ajaxPage("/questionscomment/ajax/list","&questionsComment.questionId="+questionsId,1,commentCallBack);//ajax获得评论
})
var ganswerFun = function() {
	$(".good-answer .noter-dy").bind("click", function() {
		var _gNreply = $(".good-answer .n-reply");
		if (_gNreply.is(":hidden")) {
			_gNreply.slideDown(200);
		} else {
			_gNreply.hide();
		};
	})
}

/* ajax获得评论回调 */
function commentCallBack(result){
	$("#questionsCommentSpan").html(result);
	replyFun(); //回复展开(重新绑定)
}
/* 添加回答评论 */
function addComment(obj){
	if(isLogin()){
		//$(".n-reply-wrap .c-red").html("");
		var questionsCommentContent=$("textarea[name='questionsComment.content']").val();
		if(questionsCommentContent.trim()==""){
			$(obj).parent().find("tt").html("回复内容不能为空");
			return;
		}
		$.ajax({
			url:baselocation + "/questionscomment/ajax/add",
			data:{
				"questionsComment.questionId":questionsId,
				"questionsComment.content":questionsCommentContent
			},
			type:"post",
			dataType:"json",
			async:true,
			success:function(result){
				if(result.success==true){
					$("textarea[name='questionsComment.content']").val("");
					ajaxPage("/questionscomment/ajax/list","&questionsComment.questionId="+questionsId,1,commentCallBack);//ajax获得评论
					//修改评论数
					var questionsReplyCount=parseInt($("#questionsReplyCount").html());
					$("#questionsReplyCount").html(questionsReplyCount+1);
					$(obj).parent().find("tt").html("发表成功");
				}else{
					//$(".n-reply-wrap .c-red").html(result.message);
					$(obj).parent().find("tt").html(result.message);
				}
			}
		});
	}else{
		lrFun();
	}
}

/* 添加回答评论的评论(子评论) */
function addReply(obj){
	if(isLogin()){
		var replyCotent=$(obj).parent().prev().children("textarea").val();
		var commentId=$(obj).parent().parent().next().val();
		if(replyCotent.trim()==""){
			//dialog('提示',"回复内容不能为空",1);
			$(obj).parent().find("tt").html("回复内容不能为空");
			return;
		}
		$.ajax({
			url:baselocation + "/questionscomment/ajax/addReply",
			data:{
				"questionsComment.commentId":commentId,
				"questionsComment.content":replyCotent
			},
			type:"post",
			dataType:"json",
			async:true,
			success:function(result){
				if(result.success==true){
					$(obj).parent().prev().find("textarea").val("");
					//重新加载该问答回复的子评论
					var tempObj=$(obj).parent().parent().parent().parent().prev().find("a.noter-dy");
					getCommentById(tempObj,commentId);
					//修改该问答回复的 评论数
					var questionsReplyCount=parseInt($(tempObj).children("span").html());
					$(tempObj).children("span").html(questionsReplyCount+1);
					$(obj).parent().find("tt").html("发表成功");
				}else{
					//dialog('提示',result.message,1);
					$(obj).parent().find("tt").html(result.message);
				}
			}
		});
	}else{
		lrFun();
	}
}

/**采纳为最佳答案**/
function acceptComment(commentId){
	$.ajax({
		url:baselocation + "/questionscomment/ajax/acceptComment",
		data:{
			"questionsComment.commentId":commentId,
			"questionsComment.questionId":questionsId
		},
		type:"post",
		dataType:"json",
		async:true,
		success:function(result){
			if(result.success==true){
				$(obj).parent().prev().find("textarea").val("");
				ajaxPage("/questionscomment/ajax/list","&questionsComment.questionId="+questionsId,1,commentCallBack);//ajax获得评论
			}else{
				dialog('提示',result.message,1);
			}
		}
	});
}

/**
*根据问答回复id  获取子评论 
*/
function getCommentById(obj,commentId){
	$.ajax({
		url:baselocation + "/questionscomment/ajax/getCommentById/"+commentId,
		data:{
		},
		type:"post",
		dataType:"text",
		async:true,
		success:function(result){
			$(obj).parent().parent().next().find("dl.n-reply-list").html(result);
		}
	});
}

/**
 * 根据问答回复id  获取所有子评论  分页弹出
 */
function getAllCommentById(pCommentId) {
	//ajaxPage("/questionscommentall/ajax/getCommentById/"+pCommentId, "" , 1, dialog("评论列表",result,5));
	$.ajax({
		url : baselocation + '/questionscommentall/ajax/getCommentById/'+pCommentId,
		data : {
		},
		type : 'post',
		async : true,
		dataType : 'text',
		success : function(result) {
			//弹出
			dialog("评论列表",result,5);
		}
	});
}