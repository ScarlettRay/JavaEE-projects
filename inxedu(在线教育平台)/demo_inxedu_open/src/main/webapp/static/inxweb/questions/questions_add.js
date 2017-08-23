$(function() {
	tagFun();
	tagType();//分类选中
})
// 标签选择交互
var tagFun = function() {
	var tLi = $("#js-tags>a"),
		tCon = $("#tags-content"),
		tSp = $("#label-default"),
		tCol = '<em title="关闭" class="icon14 ml5 icon-close"></em>';
		tLi.each(function() {
			var _this = $(this);
			_this.bind("click",function() {
				var _tDom = _this.clone(false);
				tSp.hide();
				//判断是否已经选择了
				if(_this.hasClass("onactive")){
					return false;
				}
				if (tCon.find(".list-tag").length <= 2) {
					_this.addClass("onactive");
					_tDom.appendTo(tCon).append(tCol);
					
					//保存 选中的标签到 隐藏域
					var questionsTag=$("#questionsTag").val();
					var seelctTagId=_this.attr("data-id");
					$("#questionsTag").val(questionsTag+","+seelctTagId);
				};
			})
		})
		
		tCon.on("click",".list-tag",function() {
			var _this = $(this),
				_lt = _this.attr("data-id");
			_this.remove();
			$('#js-tags>a[data-id='+_lt+']').removeClass("onactive");
			if (tCon.find(".list-tag").length === 0) {
				tSp.show();
			};
			
			//移除隐藏域的值
			var questionsTag=$("#questionsTag").val();
			questionsTag=questionsTag.replace(","+_lt,"");
			$("#questionsTag").val(questionsTag);
		})
};

// 分类标签选择交互
var tagType = function() {
	var tLi = $(".q-c-list .q-sort-wrap>span");
	tLi.each(function() {
		var _this = $(this);
		tLi.each(function() {
			var _this = $(this);
			_this.bind("click",function() {
				$(_this).addClass("current");
				$(_this).siblings().removeClass("current");
			})
		})
	})
};

/* 提交问答 */
function addQuestions(){
	var type=$(".q-c-list .q-sort-wrap span.current").attr("name");
	var title=$("input[name='questions.title']").val().trim();
	if(title.trim()==""){
		$("input[name='questions.title']").parent().next().html('<img width="16" height="16" alt="错误" src="'+ctx+'/static/inxweb/img/c-icon.png">');
		dialog('提示信息',"问答标题不能为空",1);
		return;
	}else if(title.length<16){
		$("input[name='questions.title']").parent().next().html('<img width="16" height="16" alt="错误" src="'+ctx+'/static/inxweb/img/c-icon.png">'); 
		dialog('提示信息',"问答标题长度不能小于16个字",1);
		return;
	}else{
		$("input[name='questions.title']").parent().next().html('<img width="16" height="16" alt="正确" src="'+ctx+'/static/inxweb/img/d-icon.png">');
	}
	
	var questionsContent=$("textarea[name='questions.content']").val();
	if(questionsContent.trim()==""){
		$("textarea[name='questions.content']").next().html('<img width="16" height="16" alt="错误" src="'+ctx+'/static/inxweb/img/c-icon.png">');
		dialog('提示信息',"问答内容不能为空",1);
		return;
	}else{
		$("textarea[name='questions.content']").next().html('<img width="16" height="16" alt="正确" src="'+ctx+'/static/inxweb/img/d-icon.png">');
	}
	
	var questionsTag=$("#questionsTag").val().trim();
	if(questionsTag==""){
		$("#tags-content").next().html('<img width="16" height="16" alt="错误" src="'+ctx+'/static/inxweb/img/c-icon.png">');
		dialog('提示信息',"请至少选择一个标签",1);
		return;
	}else{
		$("#tags-content").next().html('<img width="16" height="16" alt="正确" src="'+ctx+'/static/inxweb/img/d-icon.png">');
	}
	
	var randomCode=$("input[name='randomCode']").val();
	if(randomCode.trim()==""){
		$("input[name='randomCode']").parent().next().next().html('<img width="16" height="16" alt="错误" src="'+ctx+'/static/inxweb/img/c-icon.png">');
		dialog('提示信息',"请输入验证码",1);
		return;
	}else{
		$("input[name='randomCode']").parent().next().next().html('<img width="16" height="16" alt="正确" src="'+ctx+'/static/inxweb/img/d-icon.png">');
	}
	
	
	$.ajax({
		url:baselocation + "/questions/ajax/add",
		data:{
			"questions.title":title,
			"questions.content":questionsContent,
			"questions.type":type,
			"randomCode":randomCode,
			"questionsTag":$("#questionsTag").val()
		},
		dataType:"json",
		type:"post",
		async:true,
		success:function(result){
			if(result.success==true){
				window.location.href="/questions/info/"+result.entity;
			}else{
				dialog('提示信息',result.message,1);
			}
		}
	})
}
		
/* 检查问题标题长度 */
function checkTitleLength(obj){
	var title=$(obj).val().trim();
	if(title.length<16){
		$(obj).parent().next().html('<img width="16" height="16" alt="错误" src="'+ctx+'/static/inxweb/img/c-icon.png">');
	}else{
		$(obj).parent().next().html('<img width="16" height="16" alt="正确" src="'+ctx+'/static/inxweb/img/d-icon.png">');
	}
}

/**
 * 检查问答内容
 * @param obj
 */
function checkQuestionContent(obj){
	var title=$(obj).val().trim();
	if(title==""){
		$(obj).next().html('<img width="16" height="16" alt="错误" src="'+ctx+'/static/inxweb/img/c-icon.png">');
	}else{
		alert(); 
		$(obj).next().html('<img width="16" height="16" alt="正确" src="'+ctx+'/static/inxweb/img/d-icon.png">');
	}
}