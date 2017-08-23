$(function() {
	vP(); // 放器高度动态赋值
	sB(studyPercent); // 学习进度动画
	shareShow(); // 课程分享
	treeMenu(); // 课程树
	replyFun(); // 回复展开
	ocFun(); // 开关灯效果
	cardChange("#p-h-r-title>li", "#p-h-r-cont>section", "current"); // 菜单选项卡
	browserRedirect(); // 右侧不同设备下显隐

	queryComment();// 课程评论

	// 加载 笔记编辑器
	initKindEditornote();
	// 学过此课程的用户
	getCourseLearnedUser(otherId);
})
//课程评论和精彩评论方法
function comment(type, obj) {
	$(obj).removeClass("current").siblings().removeClass("current");
	$(obj).addClass("current");
	if (type == 1) {
		queryComment();
	}
	if (type == 2) {
		queryWonderfulComment();
	}
}
$(window).resize(function() {
	if(checkIsMobile()){// 移动端环境下效果
		/*var wH = parseInt(document.documentElement.clientHeight, 10);
		 //$("#p-h-box").css("height", wH - 258);
		 $("#p-h-r-cont").css("height", wH - 363);
		 $(".p-h-video").css("height", wH - 330);*/
	}else{
		vP();
	}
});

// 学习进度动画
var sB = function(num) { // num : 进度百分比数值
	var ik = 0, timer = null, lnb = $(".lev-num-bar"), lnm = $(".lev-num");
	lnb.css("width", 0);
	lnb.parent().attr("title", "已学习：" + num + "%");
	if (num > 0) {
		timer = setInterval(function() {
			if (ik < num) {
				lnb.css("width", ++ik + "%");
				lnm.children("big").text("已学习：" + ik + "%");
			} else {
				clearInterval(timer);
			}
			;
		}, 30);
	}
	;
}
// 播放器高度动态赋值
var vP = function() {
	var wH = parseInt(document.documentElement.clientHeight, 10);
	//$("#p-h-box").css("height", wH - 108);
	$("#p-h-r-cont").css("height", wH - 213);
	$(".p-h-video").css("height", wH - 180);
}
// 右侧菜单区域显示与隐藏
var rmFun = function() {
	$("#o-c-btn").click(function() {
		var _this = $(this);
		if (!_this.hasClass("open-o-c")) {
			$(".p-h-r-ele").animate({
				"right" : "-415px"
			}, 500);
			$("#p-h-box").animate({
				"padding-right" : "0",
				"margin-right" : "-15px"
			}, 500);
			_this.animate({
				"left" : "-30px"
			}, 500).addClass("open-o-c");
			_this.children("a").attr("title", "显示");
		} else {
			$(".p-h-r-ele").animate({
				"right" : "0"
			}, 500);
			$("#p-h-box").animate({
				"padding-right" : "400px",
				"margin-right" : "0"
			}, 500);
			_this.css("left", "0").removeClass("open-o-c");
			_this.children("a").attr("title", "展开");
		}
		;
	})
};
// 开关灯效果
var ocFun = function() {
	var bMask = $('<div class="bMask"></div>');
	bMask.css({
		"opacity" : "0.8"
	});
	$(".dpBtn").click(function() {
		var _this = $(this);
		if (!_this.hasClass("dpOpen")) {
			$("body").prepend(bMask);
			_this.addClass("dpOpen");
			_this.children("a").text("开灯").attr("title", "开灯");
		} else {
			bMask.remove();
			_this.removeClass("dpOpen");
			_this.children("a").text("关灯").attr("title", "关灯");
		}
		;
	})
};
// 移动端显示
function browserRedirect() {
	if(checkIsMobile()){// 移动端环境下效果
		$(".p-h-box").css("padding-right", "320");
		$(".p-h-r-ele").css("width", "320");
		$(".p-h-r-ele").animate({
			"right" : "-320px"
		}, 500);
		$("#p-h-box").animate({
			"padding-right" : "0",
			"margin-right" : "-15px"
		}, 500);
		$("#o-c-btn").animate({
			"left" : "-15px"
		}, 500).addClass("open-o-c");
		$("#o-c-btn").children("a").attr("title", "显示");
		var wH = parseInt(document.documentElement.clientHeight, 10);
		//$("#p-h-box").css("height", wH - 258);
		$("#p-h-r-cont").css("height", wH - 363);
		$(".p-h-video").css("height", wH - 330);

		$("#o-c-btn").click(function() {
			var _this = $(this);
			if (!_this.hasClass("open-o-c")) {
				$(".p-h-r-ele").animate({
					"right" : "-320px"
				}, 500);
				$("#p-h-box").animate({
					"padding-right" : "0",
					"margin-right" : "-15px"
				}, 500);
				_this.animate({
					"left" : "-15px"
				}, 500).addClass("open-o-c");
				_this.children("a").attr("title", "显示");
			} else {
				$(".p-h-r-ele").animate({
					"right" : "-15px"
				}, 500);
				$("#p-h-box").animate({
					"padding-right" : "0",
					"margin-right" : "-15px"
				}, 500);
				_this.css("left", "0").removeClass("open-o-c");
				_this.children("a").attr("title", "展开");
			}
			;
		})
	} else { // 非移动端环境下效果
		rmFun();
	}
};

/**
 * 获得播放器的html 
 */
function getPlayerHtml(kpointId,name,obj) {
	// 节点选中
	$(".lh-menu-stair").find("ul>li>a,ol>li>a").removeClass("current-2");
	$(obj).addClass("current-2");

	$("#contentTitle").text(name);
	currentKpointId = kpointId;
	
	$.ajax({
		url : "" + baselocation + "/front/ajax/getKopintHtml",
		data : {
			"kpointId" : kpointId,
			"courseId" : otherId
		},
		type : "post",
		dataType : "text",
		async:false,
		success : function(result) {
			if(checkIsMobile()){// 移动端环境下效果
				//音频播放 单独还原设置  播放器高度动态赋值
				var wH = parseInt(document.documentElement.clientHeight, 10);
				//$("#p-h-box").css("height", wH - 258);
				$("#p-h-r-cont").css("height", wH - 363);
				$(".p-h-video").css("height", wH - 330);
			}
			$("#videoPlay").html(result);
			// 添加播放记录
			setTimeoutflag = setTimeout('addPlayTimes(' + otherId + ',' + currentKpointId + ')', Number(countPlayTimeOut) * 1000);
		}
	});
}

/**
 * 收藏课程
 * @param courseId
 *            课程ID
 */
function favorites(courseId,obj) {
	$.ajax({
		url : baselocation + '/front/createfavorites/' + courseId,
		type : 'post',
		dataType : 'json',
		success : function(result) {
			if (result.success == false) {
				dialog('提示', result.message, 1);
			} else {
				$(obj).html("已收藏").attr("title","已收藏").parent().addClass("sc-end");
				dialog('提示', result.message, 0);
			}
		}
	});
};

 
 /**
  * 笔记编辑器初始化
  */
 function initKindEditornote(){//编辑器初始化
 	KindEditor.create('textarea[id="notesContextId"]', {
 		filterMode : true,// true时过滤HTML代码，false时允许输入任何代码。
 		pasteType:1,//设置粘贴类型，0:禁止粘贴, 1:纯文本粘贴, 2:HTML粘贴
 		allowPreviewEmoticons : false,
 		syncType : 'auto',
 		width : '99%',
 		minWidth : '10px',
 		minHeight : '10px',
 		height : '300px',
 		urlType : 'domain',// absolute
 		newlineTag : 'br',// 回车换行br|p
 		uploadJson : keuploadSimpleUrl+'&param=note',//图片上传路径
 		allowFileManager : false,
 		afterBlur : function() {
 			this.sync();
 		},
 		items : [  'forecolor', 'emoticons'],
 		afterChange : function() {
 			$('#numweiBoContent').html(140 - this.count('text'));
 			var num = 140 - this.count('text');
 			
 			if (num >= 0) {
 				$(".error").html("您还可以输入"+num+"字");
 			} else {
 				$(".error").html("您输入的文字超过140字！");
 				$('#wenzistrweiBoContent').html("你已经超过");
 				num = -num;
 			}
 			$('#numweiBoContent').html(num);
     		}
     	});
     };
     
   //添加笔记
 function addNotest() {
 	var notesContext = $("#notesContextId").val();
 	if (KindEditor.get("#notesContextId").isEmpty()) {
 		$("#notContextId").html("您还没有输入笔记");
 		$("#notContextId").show();
 		$("#notesContextId").val('');
 		//dialog('提示', '请输入内容', 1);
 		return false;
 	} else {
 		if (notesContext.length > 50000) {
 			//dialog('提示', '内容过长', 1);
 			$("#notContextId").html("笔记内容过长");
 	 		$("#notContextId").show();
            return false;
 		}
 		$.ajax({
 			url : baselocation + "/courseNote/ajax/addnote",
 			type : 'post',
 			dataType : 'json',
 			data : {
 				'kpointId' :currentKpointId,
                 'courseId':otherId,
 				'content' : notesContext
 			},
             async:false,
 			success : function(result) {
 				if (result.message == "success") {
 					//dialog('成功', '保存成功', 0);
 					$("#notContextId").html("笔记保存成功");
 		 	 		$("#notContextId").show();
 				}
 				if (result.message == "false") {
 					//dialog('失败', '保存失败，请刷新重试', 1);
 					$("#notContextId").html("笔记保存失败，请刷新重试");
 		 	 		$("#notContextId").show();
 				}
 			}
 		});
 	}
 };

/*
 * 查询笔记
 */
function queryNote(obj) {
	$(obj).removeClass("current").siblings().removeClass("current");
	$(obj).addClass("current");
	$.ajax({
		url : baselocation + "/courseNote/ajax/querynote",
		type : "post",
		dataType : "text",
		data : {
			'kpointId' : currentKpointId,
			"courseId" : otherId
		},
		success : function(result) {

			$(".note_html").html(result);
		}
	});
}

/**
 * 记录播放次数
 * 
 * @param courseId
 *            课程id
 * @param kpointId
 *            节点id
 */
function addPlayTimes(courseId, kpointId) {
	$.ajax({
		url : baselocation + "/couserStudyHistory/ajax/playertimes",
		data : {
			"kpointId" : kpointId,
			"courseId" : courseId
		},
		type : "post",
		dataType : "text",
		async : false,
		success : function(result) {
		}
	});
}

function clickNote(obj){
	$(".note_html").html("");
	$(".course_mulu").click();
	$(obj).removeClass("current").siblings().removeClass("current");
	$(obj).addClass("current");
	$(".commentHtml").next(".courseKpointHtml").hide();
	$(".commentHtml").show();
	$.ajax({
		url : baselocation + "/courseNote/ajax/querynote",
		type : "post",
		dataType : "text",
		data : {
			'kpointId' : currentKpointId,
			"courseId" : otherId
		},
		success : function(result) {
			$(".commentHtml").html(result);
		}
	});

}