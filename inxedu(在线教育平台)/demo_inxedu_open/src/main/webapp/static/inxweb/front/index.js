//首页响应式幻灯片调取方法
function sSwiperFun() {
    var _sWrap = $('.swiper-container');
    var mySwiper = _sWrap.swiper({
        loop: true, //无缝连接滚动
        autoplay : 5000, //自动滚动
        autoplayDisableOnInteraction : false, //设置点击后是否继续滚动
        speed:300, //滚动速度
        pagination : '.pagination', //设置分页
        paginationClickable :true //设置true分页点击执行swiper
    });
    $('.arrow-left').on('click', function(e){
        e.preventDefault();
        mySwiper.swipePrev();
      });
    $('.arrow-right').on('click', function(e){
        e.preventDefault();
        mySwiper.swipeNext();
    });
    
    $(".imgload").eq(0).get(0).onload=function(){
    	$(".i-slide").css("height",$(".imgload").eq(0).height());
    }
    
};

var lodingHtml = '<div class="tac"><img width="100" height="108" alt="" src="/static/inxweb/img/loading.gif"></div>',
	_timer = null;
//为你推荐    换一换
function huanyihuan() {
	$.ajax({
		url : baselocation + "/index/ajax/huanyihuan",
		type : 'post',
		dataType : 'text',
		beforeSend:function(){
			$("#weinituijian").html(lodingHtml);
			clearTimeout(_timer);
		},
		success : function(result) {
			_timer = setTimeout(function(){
				$("#weinituijian").html(result);
				scrollLoad(); // 调用 滚动记载图片方法
			}, 300);
		}
	});
}
//精品、最新、全部课程
function bna(type,obj) {
	$.ajax({
		url : baselocation + "/index/ajax/bna",
		data : {
			"order" : type
		},
		type : 'post',
		dataType : 'text',
		beforeSend:function(){
			$("#bna").html(lodingHtml);
			clearTimeout(_timer);
		},
		success : function(result) {
			$(obj).addClass("current").siblings().removeClass("current");
			_timer = setTimeout(function(){
				$("#bna").html(result);
				scrollLoad(); // 调用 滚动记载图片方法
			}, 300);
		}
	});
}
//学生动态
function studentDynamic() {
	$.ajax({
		url :baselocation +  "/index/ajax/studentDynamic",
		data : {
		},
		type : 'post',
		dataType : 'text',
		beforeSend:function(){
			$(".studentDynamicHtml").html(lodingHtml);
		},
		success : function(result) {
			$(".studentDynamicHtml").html(result);
		}
	});
}