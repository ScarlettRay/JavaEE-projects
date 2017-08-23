<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/base.jsp"%>
<div class="nav-wrap">
	<div class="navList">
		<ul>
			<%--<li class="current"><div class="line-1"><a href="" title=""><img src="/static/admin/assets/kcgl.png" ><span>课程管理</span></a></div></li>--%>
			<c:forEach items="${userFunctionList}" var="fun">
				<c:if test="${fun.parentId==0}">
				<li><div class="line-1">
					<a class="menu" href="javascript:void(0)" data-href="${ctx}/admin/main/left?parentId=${fun.functionId}" data-id="${fun.functionId}">
					<c:if test="${!empty fun.imageUrl}">
						<img src="${ctx}${fun.imageUrl}" >
					</c:if>
					<span>${fun.functionName}</span></a></div></li>
				</c:if>
			</c:forEach>
		</ul>
	</div>
	<!-- <a class="prev" title="左" href="javascript: void(0)"> </a>
    <a class="next" title="右" href="javascript: void(0)"> </a> -->
</div>
<script>

	$(function(){
		// 绑定菜单单击事件
		$("#menuHeader a.menu").click(function(){
			// 显示二级菜单
			var menuId = "#menu-" + $(this).attr("data-id");
			//查看是否加载过
			if ($(menuId).length > 0){
				//隐藏所有
				$("#ui-sMenu .accordion").hide();
				//显示当前的 子菜单
				$(menuId).show();
			}else{
				// 获取二级菜单数据
				$.get($(this).attr("data-href"), function(data){
					$("#ui-sMenu .accordion").hide();
					$("#ui-sMenu").append(data);

					$(menuId + " .ui-accordion-content a").click(function(){
						return addTab($(this));
					});

				});
			}
			return false;
		});

		// 初始化点击第一个一级菜单
		$(".nav-wrap .navList li a").get(0).click();
	})
</script>