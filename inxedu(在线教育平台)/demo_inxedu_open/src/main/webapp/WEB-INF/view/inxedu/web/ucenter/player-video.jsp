﻿<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp"%>
<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!--> <html> <!--<![endif]-->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>播放大厅-${websitemap.web.company}-${websitemap.web.title}</title>
<meta name="author" content="${websitemap.web.author}" />
<meta name="keywords" content="${websitemap.web.keywords}" />
<meta name="description" content="${websitemap.web.description}" />
<link rel="shortcut icon" href="${ctx}/favicon.ico" type="image/x-icon">
<link rel="stylesheet" type="text/css" href="${ctx}/static/inxweb/css/reset.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/inxweb/css/theme.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/inxweb/css/global.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/inxweb/css/web.css">
<link href="${ctx}/static/inxweb/css/mw_320_768.css" rel="stylesheet" type="text/css" media="screen and (min-width: 320px) and (max-width: 768px)">
<!--[if lt IE 9]><script src="js/html5.js"></script><![endif]-->
<script src="http://vod.baofengcloud.com/html/script/bfcloud.js?v=2"></script>
</head>
<body>
	<div class="of" style="background-color: #f0f0f0">
		<div class="u-body v-body">
		<!-- /学习大厅 主体 开始 -->
		<div class="p-h-v-wrap">
			<div class="pr15">
				<section id="p-h-box" class="p-h-box">
				<!-- /playing hall header begin-->
				<header class="p-h-header">
					<section class="p-h-head">
						<aside class="p-h-goback">
							<a href="javascript:void(0)" onclick="history.go(-1)" title=""><em class="icon24">&nbsp;</em><tt class="c-fff fsize14 f-fM vam">返回</tt></a>
						</aside>
						<h2 class="p-h-title"><span class="c-fff" id="contentTitle">${course.courseName }</span></h2>
						<aside class="pa" style="right: 40px;top: 22px;">
							<span class="vam dpBtn"><em class="icon18 dpIcon"></em><a class="c-fff vam" title="关灯" onclick="" href="javascript:void(0)">关灯</a></span>
						</aside>
					</section>
				</header>
				<!-- /playing hall header end-->
				<!-- /学习进度条 开始-->
				<div class="p-h-s-bar">
					<div class="time-bar-wrap">
						<div title="已学0%" class="lev-num-wrap">
							<aside class="lev-num-bar">
								<p><span class="lev-num"><big>已学习：0%</big></span></p>
							</aside>
						</div>
					</div>
				</div>
				<!-- /学习进度条 结束-->
				
					<div class="p-h-video of">
						<section class="p-h-video-box" id="videoPlay">
							<!-- <script src='http://p.bokecc.com/player?vid=28410965A68FCF5B9C33DC5901307461&siteid=F9C3434C51509878&autoStart=true&width=100%&height=100%&playerid=51A2AD3118ACAD37&playertype=1' type='text/javascript'></script> -->
						</section>
						<section class="p-h-video-tip">
							<img src="${ctx }/static/inxweb/img/v-loading.gif">
							<p class="hLh20"><span style="color: #A6B8CC;">加载中...</span></p>
						</section>
					</div>
					<!-- /播放器位置 -->
					<aside class="p-h-r-ele">
						<section class="p-h-r-wrap">
							<section class="p-h-m-tp">
								<div id="o-c-btn" class="o-c-btn"><a href="javascript: void(0)" title="展开"><em class="icon14 o-c-icon">&nbsp;</em></a></div>
								<aside class="p-h-rSc">
									<section class="kcShare pr fl hand vam">
										<tt><em class="icon18 shareIcon"></em><span class="vam c-fff f-fM">分享</span></tt>
										<div id="bdshare" class="bdsharebuttonbox"><a href="#" class="bds_tsina" data-cmd="tsina" title="分享到新浪微博"></a><a href="#" class="bds_weixin" data-cmd="weixin" title="分享到微信"></a><a href="#" class="bds_qzone" data-cmd="qzone" title="分享到QQ空间"></a><a href="#" class="bds_douban" data-cmd="douban" title="分享到豆瓣网"></a><a href="#" class="bds_renren" data-cmd="renren" title="分享到人人网"></a><a href="#" class="bds_tqq" data-cmd="tqq" title="分享到腾讯微博"></a></div>
										<script>window._bd_share_config={"common":{"bdSnsKey":{},"bdText":"","bdMini":"2","bdMiniList":false,"bdPic":"","bdStyle":"0","bdSize":"16"},"share":{}};with(document)0[(getElementsByTagName('head')[0]||body).appendChild(createElement('script')).src='http://bdimg.share.baidu.com/static/api/js/share.js?v=89860593.js?cdnversion='+~(-new Date()/36e5)];</script>
									</section>
									<c:if test="${isFavorites==true }">
										<span class="ml10 vam sc-end"><em class="icon18 scIcon"></em><a class="c-fff vam" title="已收藏" onclick="" href="javascript:void(0)">已收藏</a></span>
									</c:if>
									<c:if test="${isFavorites==false }">
										<span class="ml10 vam"><em class="icon18 scIcon"></em><a class="c-fff vam" title="收藏" onclick="favorites(${course.courseId},this)" href="javascript:void(0)">收藏</a></span>
									</c:if>
									
									
								</aside>
							</section>
							<section class="p-h-r-title mt10">
								<ol id="p-h-r-title">
									<li class="current"><a href="javascript: void(0)" title="课程目录">课程目录</a></li>
									<li><a href="javascript: void(0)" title="课程笔记" onclick="if($(this).parent('li').hasClass('current')){return false;} queryNote();">课程笔记</a></li>
								</ol>
								<div class="clear"></div>
							</section>
							<div id="p-h-r-cont">
								<section class="p-h-r-menu mt10">
									<div class="lh-menu-wrap" id="courseKpointMenu">

									</div>	
								</section>
								<!-- /课程目录 -->
								<section class="undis note_html">
									<div class="mt10">
										<!-- 课程笔记 位置 -->
										<textarea name="notesContext" onkeyup="$('#notContextId').hide();" id="notesContextId" style="width:368px;"></textarea>
										<section class="mt5 clearfix">
											<span class="fr">
												<tt class="mr5 c-orange" style="display: none;" id="notContextId"></tt>
												<a href="javascript: void(0)" onclick="addNotest()" title="保存" class="lh-reply-btn">保存</a>
											</span>
										</section>
									</div>
								</section>
								<!-- /课程笔记 -->
							</div>
						</section>
					</aside>
					<!-- /菜单+IM位置 -->
				</section>
			</div>
		</div>
		<!-- /学习大厅 主体 结束 -->
		<section class="pl15 pr15">
		<div class="mt10 c-infor-box">
			<article class="fl col-7">
				<section class="mr30">
					<div class="i-box">
						<div>
							<section class="c-infor-tabTitle c-tab-title" id="tabTitleKpoint">
								<a title="课程目录" class="current m-play-nav" href="javascript:void(0);" onclick="queryCourseKpointList(this)">课程目录</a>
								<a title="课程笔记" class="m-play-nav" href="javascript:void(0);" onclick="clickNote(this)">课程笔记</a>
								<a title="课程评论" href="javascript:void(0);" onclick="comment(1,this)">课程评论</a>
								<a title="精彩评论" href="javascript:void(0);" onclick="comment(2,this)">精彩评论</a>
							</section>
						</div>
						<article class="dis ml10 mr10 commentHtml"></article>
						<article class="dis ml10 mr10 courseKpointHtml"></article>
					</div>
				</section>
			</article>
			<aside class="fl col-3">
				<div class="i-box">
					<div>
						<section class="c-infor-tabTitle c-tab-title">
							<a title="" href="">猜你想学</a>
						</section>
						<c:if test="${empty courseList}">
							<section class="no-data-wrap">
								<em class="icon30 no-data-ico">&nbsp;</em>
								<span class="c-666 fsize14 ml10 vam">没有相关数据，小编正在努力整理中...</span>
							</section>
						</c:if>
						<c:if test="${not empty courseList}">
								<section class="course-r-list">
									<ol>
										<c:forEach items="${courseList}" var="interfixCourse">
											<li>
												<aside class="course-r-pic">
													<a href="${ctx }/front/couinfo/${interfixCourse.courseId}" title="">
														<c:choose>
															<c:when test="${interfixCourse.logo!=null && interfixCourse.logo!=''}">
																<img alt="" src="<%=staticImage%>${interfixCourse.logo}" />
															</c:when>
															<c:otherwise>
																<img alt="" src="${ctx }/static/inxweb/img/default-img.gif" />
															</c:otherwise>
														</c:choose>
													</a>
												</aside>
												<section class="hLh20 txtOf">
													<a href="${ctx }/front/couinfo/${interfixCourse.courseId}" class="c-333 fsize16">${interfixCourse.courseName}</a>
												</section>
												<section class="hLh20 mt5 txtOf">
													<span class="c-999">讲师：
														<c:if test="${interfixCourse.teacherList!=null && interfixCourse.teacherList.size()>0}">
																<c:forEach items="${interfixCourse.teacherList}" var="teacher">
																	${teacher.name}&nbsp;&nbsp;
																</c:forEach>
															</c:if>
													</span>
												</section>
												<section class="hLh20 txtOf">
													<span class="c-master">${interfixCourse.pageBuycount }人</span>
												</section>
											</li>
										</c:forEach>
										
									</ol>
								</section>
							</c:if>
					</div>
				</div>
				<div class="i-box mt20">
					<div id="courseLearnedUserDiv">
						<section class="c-infor-tabTitle c-tab-title">
							<a title="" href="">学过此课的人（0）</a>
						</section>
						<section class="no-data-wrap"><em class="icon30 no-data-ico">&nbsp;</em><span class="c-666 fsize14 ml10 vam">没有相关数据，小编正在努力整理中...</span></section>
					</div>
				</div>
			</aside>
			<div class="clear"></div>
		</div>
		</section>
	</div>
	</div>
	

	<!-- 公共底引入 -->
	<jsp:include page="/WEB-INF/layouts/web/footer.jsp" />
	<!-- 公共底引入 -->
	<script type="text/javascript" src="${ctx}/static/common/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${ctx}/static/common/jquery-1.11.1.min.js"></script>

	<script type="text/javascript" src="${ctx}/static/common/webutils.js"></script>
	<script type="text/javascript" src="${ctx }/static/inxweb/js/common.js" ></script>
	<script src="/static/inxweb/comment/comment.js" type="text/javascript"></script><!-- 评论js -->
	<script type="text/javascript" src="${ctx }/kindeditor/kindeditor-all.js"></script>
	<script type="text/javascript" src="${ctx}/static/inxweb/play/playVideo.js"></script>
	
	<script>
	//评论课程id
	var otherId = '${course.courseId}';
	//评论类型,类型2为课程
	var type = 2;
	var currentKpointId="0";//当前播放视频id(没有视频节点默认为零)
	var isok="${isok}";//是否可以播放
	var message="${message}";//提示信息
	var countPlayTimeOut='15';//播放后记录播放次数的延时
	var studyPercent="${course.studyPercent}";//学习进度百分比

	getCourseKpointList("${course.courseId}",2);
	/**
	 * 获得课程章节目录
	 */
	function getCourseKpointList(courseId,type) {
		$.ajax({
			url : baselocation + "/front/ajax/courseKpointList/"+courseId+"/"+type,
			data:{},
			type : 'post',
			dataType : 'text',
			success : function(result) {
				if(checkIsMobile()){// 移动端环境下效果
					$(".courseKpointHtml").prev().hide();
					$(".courseKpointHtml").show();
					$(".courseKpointHtml").html(result);
				}else{
					$("#courseKpointMenu").html(result);
					queryComment();// 查询课程评论
					$("#tabTitleKpoint.c-infor-tabTitle.c-tab-title").find("a").eq(2).addClass("current").siblings().removeClass("current");
				}
				treeMenu(); //课程树
			}
		});
	}
	//点击查询章节
	function queryCourseKpointList(obj){
		$(obj).removeClass("current").siblings().removeClass("current");
		$(obj).addClass("current");
		if(isNotEmpty($(".courseKpointHtml").html())){
			$(".courseKpointHtml").prev().hide();
			$(".courseKpointHtml").show();
		}else{
			getCourseKpointList("${course.courseId}",2);
		}
	}
	</script>
</body>
</html>
