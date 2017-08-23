$(function() {
	$(".changeTheme").show();
	if(theme_color=='orange'){
		$("#themeColororange").addClass("current");
	}
	if(theme_color=='blue'){
		$("#themeColorblue").addClass("current");
		}
	if(theme_color=='green'){
		$("#themeColorgreen").addClass("current");
	}
	
	$(function() {
		var _eiD = $(".ei-i-dialog"),
			_eiC = $(".ei-i-close"),
			_eiImg = $(".dialog-box-boy-in .box-boy-in-i img").eq(0),
			_eiIdiv = $(".dialog-box-boy-in li").eq(0),
			_eiDheight = _eiIdiv.height()+32;
		_eiD.css({
			"height" : _eiDheight,
			"top" : "-" + _eiDheight+"px",
			"opacity" : 0,
			"zIndex" : -1
		});
		$("#Replacement").click(function() {
			if(navigator.userAgent.indexOf("MSIE")>0) {
				_eiD.css({
					"width":"100%",
					"filter":"alpha(opacity=100)",
					"width":"100%",
					"top" : "0px",
					"zIndex" : "999999"
				});
				_eiD.animate({
					"opacity" : "1",
					"filter":"alpha(opacity=100)",
					"zIndex" : "999999"
				}, 200);
			} else {
				_eiD.css({
					"opacity" : "1",
					"filter":"alpha(opacity=100)",
					"zIndex" : "999999",
					"width":"100%",
					"top" : "0px"
				})
			}
		});
		_eiC.click(function() {
			if(navigator.userAgent.indexOf("MSIE")>0) { 
				_eiD.animate({
					"opacity" : "0",
					"zIndex" : "-1"
				}, 200);
			} else {
				_eiD.css({
					"top" : "-" + _eiDheight+"px" 
				})
			}
		});
	});



	
});

/**
 *修改主题色
 */
function changeThemeColor(color){
	$(".dialog-box-boy-in").find("div").removeClass("current");
	$("#themeColor"+color).addClass("current");
	$.ajax({
		url :baselocation +  "/theme/ajax/update",
		data : {
			"color":color
		},
		type : 'post',
		dataType : 'json',
		async:false,
		success : function(result) {
			if(result.success==true){
				//dialog('提示信息', "转换中,请稍等", 0);
				$("#themeColor"+color).find("div").show();
				setTimeout('myrefresh()',2500); //指定3秒刷新一次
				//alert("成功");
				//myrefresh();
				//myrefresh();
				//setTimeout('myrefresh()',3000); //指定3秒刷新一次
			}
		}
	});
}

function myrefresh()
{
	location.reload(true); 
}