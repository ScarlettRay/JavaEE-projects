<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/base.jsp"%>
<!DOCTYPE html>
<html>
<head>
<!-- Meta -->
<meta charset="utf-8" http-equiv="Content-Type" />
<!-- End of Meta -->

<!-- Page title -->
<title>${websitemap.web.company}-${websitemap.web.title}</title>
<!-- End of Page title -->
<meta name="author" content="${websitemap.web.author}" />
<meta name="keywords" content="${websitemap.web.keywords}" />
<meta name="description" content="${websitemap.web.description}" />
<link rel="shortcut icon" href="${ctx}/favicon.ico" type="image/x-icon">
	<link type="text/css" href="${ctx}/static/common/jerichotab/css/jquery.jerichotab.css" rel="stylesheet" />
	<script type="text/javascript" src="${ctx}/static/common/jerichotab/js/jquery.jerichotab.js"></script>
<script type="text/javascript" src="${ctx}/static/admin/js/easyTooltip.js"></script>
<script type="text/javascript" src="${ctx}/static/admin/js/jquery-ui-1.7.2.custom.min.js"></script>
<script type="text/javascript" src="${ctx}/static/admin/js/jquery.wysiwyg.js"></script>
<script type="text/javascript" src="${ctx}/static/admin/js/hoverIntent.js"></script>
<script type="text/javascript" src="${ctx}/static/admin/js/superfish.js"></script>
<script type="text/javascript" src="${ctx}/static/admin/js/custom.js"></script>
<!-- End of Libraries -->
	<script type="text/javascript">
		var tabTitleHeight = 33; // 页签的高度
		/*sMenu*/
		$(function() {
			queryheader();
			navFun();
			$(window).resize(function() {navFun();});

			$.fn.initJerichoTab({
				renderTo: '#right', uniqueId: 'jerichotab',
				contentCss: { 'height': $('#right').height() - tabTitleHeight },
				tabs: [], loadOnce: false, tabWidth: 110, titleHeight: tabTitleHeight
			});
			//先隐藏，第一次显示iframe
			$("#jerichotab").hide();
		});
		function navFun() {
			var winW = parseInt(document.documentElement.clientWidth, 10) + parseInt(document.documentElement.scrollLeft || document.body.scrollLeft, 10),
					nlW = winW - 585,
					ulW = $(".navList>ul").width()+320,
					oPN = $('<a href="javascript: void(0)" title="左" class="prev">&nbsp;</a><a href="javascript: void(0)" title="右" class="next">&nbsp;</a>');
			$(".navList").css("width" , nlW);
			if (nlW > ulW) {
				$(".prev,.next").remove();
			} else {
				oPN.appendTo(".nav-wrap");
				slideScroll();
			}
		}
		function slideScroll() {
			var prev = $(".prev"),
					next = $(".next"),
					oUl = $(".navList>ul"),
					w = oUl.find("li").outerWidth(true),
					l = oUl.find("li").length;
			oUl.css("width" , w * l + "px");

			//click left
			prev.click(function() {
				if(!oUl.is(":animated")) {
					oUl.animate({"margin-left" : -w}, function() {
						oUl.find("li").eq(0).appendTo(oUl);
						oUl.css("margin-left" , 0);
					});
				}
			});
			//click right
			next.click(function() {
				if(!oUl.is(":animated")) {
					oUl.find("li:last").prependTo(oUl);
					oUl.css("margin-left" , -w);
					oUl.animate({"margin-left" : 0});
				}
			});
		}

		//查询头部
		function queryheader(){
			$.ajax({
				url:baselocation+'/admin/main/header',
				type:'post',
				async:false,
				dataType:'text',
				success:function(result){
					$(".headerhtml").html(result);
				}
			});
		}
		//查询头部
		function queryleft(parentId){
			$.ajax({
				url:baselocation+'/admin/main/left?parentId='+parentId,
				type:'post',
				async:false,
				dataType:'text',
				success:function(result){
					$("#ui-sMenu").html(result);
				}
			});
		}

		function addTab($this, refresh){
			$(".jericho_tab").show();
			$("#mainFrame").hide();
			$.fn.jerichoTab.addTab({
				tabFirer: $($this),
				title: $($this).text().trim(),
				closeable: true,
				data: {
					dataType: 'iframe',
					dataLink: $($this).attr('data-href')
				}
			}).loadData(refresh);
			return false;
		}
	</script>
<%--<script type="text/javascript">
	 /*sMenu*/
	 $(function() {
	     $("#ui-sMenu>div").each(function() {
	         var _this = $(this),
	                 _oT = _this.find("h3"),
	                 _oTi = _oT.find(".ui-icon"),
	                 _oC = _oT.next("div");
	         _oT.click(function() {
	             if(_oC.is(":hidden")) {
            		 $("#ui-sMenu>div h3").removeClass("ui-state-active ui-corner-top").addClass("ui-state-default ui-corner-all");
            		 $("#ui-sMenu>div .ui-icon").removeClass("ui-icon-triangle-1-s").addClass("ui-icon-triangle-1-e");
            		 $("#ui-sMenu>div div").slideUp("fast");
	            		 
	                 _oT.removeClass("ui-state-default ui-corner-all").addClass("ui-state-active ui-corner-top");
	                 _oTi.removeClass("ui-icon-triangle-1-e").addClass("ui-icon-triangle-1-s");
	                 _oC.slideDown("fast");
	             } else {
	                 _oT.removeClass("ui-state-active ui-corner-top").addClass("ui-state-default ui-corner-all");
	                 _oTi.removeClass("ui-icon-triangle-1-s").addClass("ui-icon-triangle-1-e");
	                 _oC.slideUp("fast");
	             }
	         });
	     });
	 });
</script>--%>
</head>
<body>
	<!-- Container -->
	<div class="tHeader headerimg">
		<div>
			<!-- Header -->
			<div id="header">

				<!-- Top -->
				<div id="top">
					<!-- Logo -->
					<div class="logo">
						<a href="http://demo1.inxedu.com/" target="_blank" title="因酷在线教育软件 - 在线教育整体解决方案提供商" class="tooltip">
							<img src="${ctx}/static/admin/assets/logo.png" height="60" alt="因酷在线教育软件 - 在线教育整体解决方案提供商" />
						</a>
					</div>
					<!-- End of Logo -->

					<div class=" nav-bar headerhtml" id="menuHeader">
						<div class="nav-wrap">
							<div class="navList">
								<ul>
								</ul>
							</div>
							<!-- <a class="prev" title="左" href="javascript: void(0)"> </a>
							<a class="next" title="右" href="javascript: void(0)"> </a> -->
						</div>
					</div>

					<!-- Meta information -->
					<div class="meta">
						<p>欢迎来到${websitemap.web.company}后台管理系统!</p>
						<ul>
							<li>
								<a href="${ctx}/admin/outlogin" title="退出系统" class="tooltip">
									<span class="ui-icon ui-icon-power"></span>
									退出系统
								</a>
							</li>
							<li>
								<a href="${ctx}/admin/sysrole/showroleList" target="content" title="权限设置" class="tooltip">
									<span class="ui-icon ui-icon-wrench"></span>
									权限设置
								</a>
							</li>
							<li>
								<a href="" title="${sysuser.loginName}" class="tooltip">
									<span class="ui-icon ui-icon-person"></span>${sysuser.loginName}</a>
							</li>
						</ul>
					</div>
					<!-- End of Meta information -->
				</div>
				<!-- End of Top-->

			</div>
		</div>
	</div>
	<!-- End of Header -->
	<div id="container">

		<!-- Background wrapper -->
		<div id="bgwrap">
			<!-- Main Content -->
			<div id="content">
				<div id="main">
					<div id="right">
						<iframe id="mainFrame" name="mainFrame" src="${ctx}/admin/main/index" style="overflow:visible;" scrolling="yes" frameborder="no" width="100%" height="750"></iframe>
					</div>
				</div>
			</div>
			<!-- End of Main Content -->

			<!-- Sidebar -->
			<div id="sidebar">

				<div id="ui-sMenu" class="ui-accordion ui-widget ui-helper-reset" style="height:760px;overflow-y: auto">
				</div>

		</div>
		<!-- End of bgwrap -->
	</div>
	<!-- End of Container -->

	<!-- Footer -->
	<div id="footer">
		<p class="mid">${websitemap.web.copyright}</p>
		<p class="mid">
			Powered By <a target="_blank" href="http://demo1.inxedu.com/" style="color: #666;">${websitemap.web.company}</a>
		</p>
	</div>
</body>
</html>


