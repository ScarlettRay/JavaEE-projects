//好文推荐
function articleRecommend() {
	$.ajax({
		url : baselocation + '/front/ajax/recommend',
		type : 'post',
		async : true,
		dataType : 'text',
		success : function(result) {
			$(".articleRecommend").html(result);
		}
	});
}

// 增加文章浏览量
function updateArticleClickNum() {
	jQuery.ajax({
		url : baselocation+'/front/updateArticleClickNum/' + articleId,
		type : 'post',
		dataType : 'json',
		success : function(result) {
			if (result.success == true) {
				jQuery(".clickNum").text(result.entity.clickNum);
				$(".praiseCount").html(result.entity.praiseCount);
			}
		}
	});
}
/*
 * 点赞
 * targetId 点赞的目标id
 * type 点赞类型 1问答点赞 2问答评论点赞
 * obj 当前标签对象
 */
function addActiclePraise(targetId,type){
	if(isLogin()){
		$.ajax({
			url:"/praise/ajax/add",
			data:{
				"praise.targetId":targetId,
				"praise.type":type
			},
			type:"post",
			dataType:"json",
			async:true,
			success:function(result){
				if(result.success==true){
					//点赞效果
					var _dznum = $(".dz-btn").siblings(".dz-num");
			        _dznum.stop().animate({"opacity" : "0" , "bottom" : "90px"}, 1000, function() {
			            _dznum.css({"opacity" : "1" , "bottom" : "-24px"})
			        });
			        
					dialog('提示',"点赞成功",0);
					//点赞数加一
					var praiseNum = $(".addPraise"+targetId+"_"+type).html();
					$(".addPraise"+targetId+"_"+type).html(praiseNum*1+1);
				}else{
					dialog('提示',result.message,1);
				}
			}
		})
	}else{
		//先关闭 弹出
		 $("#tisbutt,.dClose,#qujiao").click();
		lrFun();
	}
}
