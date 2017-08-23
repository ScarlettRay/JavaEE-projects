$(function(){
	//热门问答推荐
	hotQuestions();
})

/* 提问前先登录 */
function toAddQuestions(){
	if(isLogin()){
		window.location.href="/questions/toadd";
	}else{
		lrFun();
	}
}

/**
 * 不同条件查询问答
 * @param type order排序 status等待回答 type(课程问答,学习分享) questionsTagId 问答标签id
 */
function submitForm(keyWord,type){
	if(type=="order"){
		if("status0"==keyWord){
			$("input[name='questions.status']").val(0);
			$("input[name='questions.orderFalg']").val(keyWord);
		}else{
			$("input[name='questions.orderFalg']").val(keyWord);
		}
	}else if(type=="type"){
		$("input[name='questions.type']").val(keyWord);
	}else if(type=="questionsTagId"){
		$("input[name='questions.questionsTagId']").val(keyWord);
	}
	$("input[name='queryCourse.courseName']").val('');
	$("#searchForm").submit();
}

/**
 *热门问答
 */
function hotQuestions(){
	$.ajax({
		url:baselocation + "/questions/ajax/hotRecommend",
		data:{
		},
		type:"post",
		dataType:"json",
		async:true,
		success:function(result){
			if(result.success==true){
				var resultList=result.entity;
				var str="";
				for(var i=0;i<resultList.length;i++){
					str+='<li>'
						+'	<aside class="q-r-r-num">'
						+'		<div class="replyNum">'
						+'			<span class="r-b-num">'+resultList[i].replyCount+'</span>'
						+'			<p class="hLh20">'
						+'				<span class="c-999 f-fA">回答数</span>'
						+'			</p>'
						+'		</div>'
						+'	</aside>'
						+'	<h4 class="hLh30 txtOf">'
						+'		<em class="icon16 q-tw">&nbsp;</em>'
						+'		<a href="/questions/info/'+resultList[i].id+'" title="" class="fsize14 c-333 ml5">'+resultList[i].title+'</a>'
						+'	</h4>'
						+'</li>';
				}
				$("#hotQuestions").html(str);
			}
		}
	});
}
