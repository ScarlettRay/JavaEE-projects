//查询评论
function queryComment() {
	ajaxPage("/web/comment/ajax/query", "&otherId=" + otherId + "&type=" + type+ "&order=commentId", 1, commentCallBack);
}
//查询评论
function queryWonderfulComment() {
	ajaxPage("/web/comment/ajax/query", "&otherId=" + otherId + "&type=" + type+ "&order=praiseCount", 1, commentCallBack);
}
function commentCallBack(result) {
	$(".commentHtml").next(".courseKpointHtml").hide();
	$(".commentHtml").show();
	$(".commentHtml").html(result);
}
// 查询文章评论回复
function queryCommentReply(pCommentId) {
	$.ajax({
		url : baselocation + '/web/comment/ajax/commentreply',
		data : {
			"otherId" : otherId,
			"pCommentId" : pCommentId
		},
		type : 'post',
		async : true,
		dataType : 'text',
		success : function(result) {
			// 刷新回复
			$(".commentReply" + pCommentId).html(result);
		}
	});
}

function addCommentReply(commentId) {
	// 点击时清空错误提示
	$(".commentReplyMsg" + commentId).val("").hide();

	// 评论必须先登陆
	if (!isLogin()) {
		lrFun();
		return;
	}
	// 评论
	var content = $(".commentReplyMsgContent" + commentId).val().trim();
	// 评论内容不能为空
	if (content == "") {
		$(".commentReplyMsg" + commentId).html("内容不能为空！").show();
		return;
	}
	if (content.length>800) {
		$(".commentReplyMsg" + commentId).html("内容不能大于800字符！").show();
		return;
	}
	$.ajax({
		url : baselocation + '/web/comment/ajax/addcomment',
		type : 'post',
		data : {
			"pCommentId" : commentId,
			"content" : content,
			"type" : type,
			"otherId" : otherId
		},
		async : true,
		dataType : 'text',
		success : function(result) {
			var content = $(".commentReplyMsgContent" + commentId).val("");

			queryCommentReply(commentId)
			// 添加回复数
			var replyCount = $(".replyCount" + commentId).html();
			$(".replyCount" + commentId).html(replyCount * 1 + 1);
			dialog('提示信息', "成功", 0);
		}
	});

}
// 添加评论
function addComment() {
	// 点击时清空错误提示
	$(".commentContentmeg").val("").hide();

	// 评论必须先登陆
	if (!isLogin()) {
		lrFun();
		return;
	}
	// 评论
	var content = $("#commentContent").val().trim();
	// 评论内容不能为空
	if (content == "") {
		$(".commentContentmeg").html("内容不能为空！").show();
		return;
	}
	$.ajax({
		url : baselocation + '/web/comment/ajax/addcomment',
		type : 'post',
		data : {
			"pCommentId" : "0",
			"content" : content,
			"type" : type,
			"otherId" : otherId
		},
		async : true,
		dataType : 'text',
		success : function(result) {
			var content = $("#commentContent").val("");
			queryComment();
			dialog('提示信息', "成功", 0);
		}
	});
}

//查询文章评论回复 分页弹出
function queryCommentReplyPage(pCommentId) {
	//ajaxPage("/web/comment/ajax/commentreplypage", "&otherId=" + otherId + "&pCommentId=" + pCommentId , 1, dialog("评论列表",result,5));
	$.ajax({
		url : baselocation + '/web/comment/ajax/commentreplypage',
		data : {
			"otherId" : otherId,
			"pCommentId" : pCommentId
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
