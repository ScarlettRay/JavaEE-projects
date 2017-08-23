$(function() {
	cssNavigation();
	showUserInfo();
	queryUnReadNum();// 查询未读消息
});

/**
 * 头部显示用户信息
 */
function showUserInfo() {
	var user = getLoginUser();
	if(user!=null && user.userId>0){
		var showName = user.showName;
		if (showName == null || $.trim(showName) == '') {
			showName = user.email;
		}
		// 头像
		var useImg = user.picImg;
		if (useImg == null || $.trim(useImg) == '') {
			useImg = baselocation + '/static/inxweb/img/avatar-boy.gif';
		} else {
			useImg = imagesPath + useImg;
		}
		$("#userName").text(showName);
		$(".userImgPhoto").attr("src", useImg);
		$(".userImgPhoto").attr("alt", showName);
		$(".userNameClass").html(showName);
		$("#showName").text(showName);
		$("#showName").attr('title', showName);
		userBannerImage = user.bannerUrl;
		// 头部显示
		$("#userName").text(showName);
		$("#userName").attr('title', showName);
		$("#is-login-one,#is-login-two,#mobileExitDiv").show();
		$(".picImg").attr("src", useImg);
	} else {
		$("#no-login").show();
	}
}
/**
 * 设置导航选中样式
 */
function cssNavigation() {
	var url = window.document.location.pathname;
	$("a[href$='" + url + "']").parent().addClass("current");
}
