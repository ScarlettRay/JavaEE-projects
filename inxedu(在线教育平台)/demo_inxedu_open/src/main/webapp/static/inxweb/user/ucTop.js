var userBannerImage;
var selectBannerImage;
$(function(){
	//设置个人中心左侧导航选中样式 
	cssLeftNavigation();
});

/**
 * 设置个人中心左侧导航选中样式 
 */
function cssLeftNavigation(){
	var url=window.document.location.pathname;
	$("a[href$='"+url+"']").parent().addClass("current");
}
