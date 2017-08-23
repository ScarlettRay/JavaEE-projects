<%@page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/base.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>我的消息</title>
<script type="text/javascript">
function delULetter(id){//删除站内信
	$.ajax({
		type:"POST",
		dataType:"json",
		url:baselocation+"/uc/ajax/delLetterInbox",
		data:{"msgReceive.id":id},
		cache:true,
		async:false,
		success:function(result){
			if(result.message=="success"){ 
				$("#del"+id).remove();//
				dialog('提示','删除成功',0,'/uc/letter');
			}
		}
	});
}
</script>
</head>
<body>
<article class="col-7 fl">
	<div class="u-r-cont">
		<section>
			<div>
				<section class="c-infor-tabTitle c-tab-title">
					<a href="javascript: void(0)" title="Wo的消息" style="cursor: default;">Wo的消息</a>
					<a href="javascript: void(0)" title="系统消息" class="current">消息列表</a>
				</section>
			</div>
			<div class="mt40">
				<c:if test="${empty queryLetterList }">
					<!-- /无数据提示 开始-->
					<section class="no-data-wrap">
						<em class="icon30 no-data-ico">&nbsp;</em>
						<span class="c-666 fsize14 ml10 vam">您还没有收到消息哦！</span>
					</section>
					<!-- /无数据提示 结束-->
				</c:if>
				<c:if test="${not empty queryLetterList }">
					<div class="u-sys-news">
						 <c:forEach items="${queryLetterList}" var="qltl">
							<dl id="del${qltl.id }">
								<dt>
									<section class="tar">
										<p class="hLh30"><b class="fsize14 f-fA c-red">系统消息</b> </p>
										<p class="hLh20"><span class="f-fA c-666"><fmt:formatDate type="both" value="${qltl.addTime }" pattern="MM月dd日 hh:mm" /></span></p>
										<p class="hLh20"><span class="f-fA c-999"><fmt:formatDate type="both" value="${qltl.addTime }" pattern="yyyy年" /></span></p>
									</section>
								</dt>
								<dd>
									<section class="mt10">
										<div style="line-height: 200%;">
											<p><span class="c-666 f-fA">${qltl.content }</span></p>
										</div>
										<div class="mt10 tar">
											<a href="javascript:void(0)" onclick="delULetter(${qltl.id })"  title="" class="comm-btn c-btn-6">删 除</a>
										</div>
									</section>
								</dd>
							</dl>
						</c:forEach>
					</div>
				</c:if>
				<!-- 公共分页 开始 -->
				<jsp:include page="/WEB-INF/view/common/front_page.jsp" />
				<!-- 公共分页 结束 -->
				<form action="${ctx}/uc/letter"name="searchForm" id="searchForm" method="post">
			 		<input id="pageCurrentPage" type="hidden" name="page.currentPage" value="${page.currentPage}"/>
			 	</form>
			</div>
		</section>
		<!-- /Wo的消息 -->
	</div>
</article>
<!-- /右侧内容区 结束 -->
</body>
</html>

