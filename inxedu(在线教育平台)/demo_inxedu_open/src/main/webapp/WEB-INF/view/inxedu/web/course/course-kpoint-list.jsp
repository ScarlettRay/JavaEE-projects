<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp"%>
<c:if test="${not empty parentKpointList }">
	<menu id="lh-menu" class="lh-menu">
		<ul>
			<c:set var="folderIndex" value="1"/>
			<c:forEach items="${parentKpointList }" var="parentKpoint" varStatus="index">
				<c:if test="${parentKpoint.kpointType==0 }"><!-- 文件目录 -->
					<li class="lh-menu-stair">
						<a href="javascript: void(0)" title="${parentKpoint.name }"
						   <c:if test="${index.first==true}">class="current-1"</c:if>
						><span class="fr"><em class="icon14 m-tree-icon">&nbsp;</em></span><em class="lh-menu-i-1 icon24 mr5"><font>${folderIndex }</font></em>${parentKpoint.name }</a>
						<ol class="lh-menu-ol"
							<c:if test="${index.first==true}">style="display: block;"</c:if>
							<c:if test="${index.first==false}">style="display: none;"</c:if>
						>
							<c:forEach items="${parentKpoint.kpointList}" var="sonKpoint">
								<li class="lh-menu-second ml30">
									<a href="javascript:void(0)" onclick="getPlayerHtml(${sonKpoint.kpointId },'${sonKpoint.name }',this)" class="" title="">
																				<span class="fr">
																					<%-- <c:if test="${sonKpoint.free==1 }">
																						<tt class="free-icon vam mr10">免费试听</tt>
																					</c:if> --%>
																					<c:if test="${!empty sonKpoint.playTime}">
																						<em class="lh-p-icon icon14 ml5">&nbsp;</em>
																						${sonKpoint.playTime}
																					</c:if>
																				</span><em class="lh-menu-i-2 icon14 mr5">&nbsp;</em>${sonKpoint.name }
									</a>
								</li>
							</c:forEach>
						</ol>
					</li>
					<c:set var="folderIndex" value="${folderIndex+1 }"/>
				</c:if>
				<c:if test="${parentKpoint.kpointType==1 }"><!-- 视频 -->
					<li class="lh-menu-stair">
						<ul class="lh-menu-ol no-parent-node">
							<li class="lh-menu-second"><a title="" onclick="getPlayerHtml(${parentKpoint.kpointId },'${parentKpoint.name }',this)" href="javascript:void(0)">
								<span class="fr">
									<%-- <c:if test="${parentKpoint.free==1 }">
										<tt class="free-icon vam mr10">免费试听</tt>
									</c:if> --%>
									<c:if test="${!empty parentKpoint.playTime}">
										<em class="lh-p-icon icon14 ml5">&nbsp;</em>
										${parentKpoint.playTime}
									</c:if>
									</span><em class="lh-menu-i-2 icon14 mr5">&nbsp;</em>${parentKpoint.name }</a>
							</li>
						</ul>
					</li>
				</c:if>
			</c:forEach>
		</ul>
	</menu>
</c:if>
<!-- /无数据提示 开始-->
<c:if test="${empty parentKpointList}">
	<section class="no-data-wrap">
		<em class="icon30 no-data-ico">&nbsp;</em> <span class="c-666 fsize14 ml10 vam">没有相关数据，小编正在努力整理中...</span>
	</section>
</c:if>
<script>
	$(function () {
		/*if (isok == false) {
			dialog('播放提示', message, 1);
			return false;
		} else {*/
			// 播放第一个视频节点
			$("#lh-menu").find("ul>li:eq(0)").find("ul>li:eq(0),ol>li:eq(0)").find("a").click();
		//}
	})
</script>