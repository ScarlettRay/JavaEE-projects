<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>问答回复管理列表</title>
<script type="text/javascript" src="${ctx}/static/common/jquery-1.11.1.min.js"></script>
<script src="${ctximg}/static/common/jquery-ui-1.10.4/js/jquery-ui-1.10.4.custom.js?v=${v}"></script>
<script src="${ctximg}/static/common/jquery-ui-1.10.4/js/jquery.ui.datepicker-zh-CN.js?v=${v}"></script>
<script type="text/javascript" src="${ctximg}/static/common/jquery-ui-1.10.4/js/jquery-ui-timepicker-addon.js?v=${v}"></script>
<script type="text/javascript" src="${ctximg}/static/common/jquery-ui-1.10.4/js/jquery-ui-timepicker-zh-CN.js?v=${v}"></script>
<script type="text/javascript" src="${ctx}/static/admin/questions/questions_comment.js"></script>
<script type="text/javascript">
$(function(){
	//时间控件
	$("#beginCreateTime,#endCreateTime").datetimepicker({
		regional:"zh-CN",
        changeMonth: true,
        dateFormat:"yy-mm-dd",
        timeFormat: "HH:mm:ss"
    });
});
</script>
</head>
<body>
	<div class="">
		<form action="${ctx}/admin/questionscomment/list" method="post" id="searchForm">
			<input type="hidden" id="pageCurrentPage" name="page.currentPage" value="1" />
			问答ID:
			<input placeholder="问答ID" name="questionsComment.questionId" value="${questionsComment.questionId}" />
			问答标题:
			<input placeholder="问答标题" name="questionsComment.questionsTitle" value="${questionsComment.questionsTitle}" />
			回复添加时间:
			<input placeholder="添加开始时间" name="questionsComment.beginCreateTime"
				value="<fmt:formatDate value="${questionsComment.beginCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>" id="beginCreateTime" type="text"
				readonly="readonly" style="width:120px;"/>
			-
			<input placeholder="添加结束时间" id="endCreateTime" name="questionsComment.endCreateTime"
				value="<fmt:formatDate value="${questionsComment.endCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>" type="text" readonly="readonly" style="width:120px;"/>
			
			是否采纳:
			<select id="" name="questionsComment.isBest">
				<option value="-1">--全部--</option>
				<option value="0">否</option>
				<option value="1">是</option>
			</select>
			
			<script>
				$(function(){
					//条件选中
					$("select[name='questionsComment.isBest']").val("${questionsComment.isBest}");
				})
			</script>
			<a title="查询问答回复" onclick="$('#searchForm').submit();" class="button tooltip" href="javascript:void(0)">
				<span class="ui-icon ui-icon-search"></span>
				查询问答回复
			</a>
			<a title="清空" onclick="$('#searchForm input:text').val('');" class="button tooltip" href="javascript:void(0)">
				<span class="ui-icon ui-icon-cancel"></span>
				清空
			</a>
			<a title="返回" onclick="history.go(-1)" class="button tooltip" href="javascript:void(0)">
				<span class="ui-icon ui-icon-cancel"></span>
				返回
			</a>
		</form>
		<table cellspacing="0" cellpadding="0" border="0" class="fullwidth" width="100%">
			<thead>
				<tr>
					<td align="center">问答id</td>
					<td align="center">问答标题</td>
					<td align="center">发表人</td>
					<!-- <td align="center" width="230">回复内容</td> -->
					<td align="center">是否采纳</td>
					<td align="center">回复数</td>
					<td align="center">点赞数</td>
					<td align="center">添加时间</td>
					<td align="center">操作</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${questionsCommentList}" var="questionsComment" varStatus="index">
					<tr <c:if test="${index.count%2==1 }">class="odd"</c:if>>
						<td align="center">${questionsComment.questionId }</td>
						<td align="center">${questionsComment.questionsTitle }</td>
						<td align="center">${questionsComment.email}</td>
						<%-- <td align="center">${questionsComment.content}</td> --%>
						<td align="center">
							<c:if test="${questionsComment.isBest==0}">否</c:if>
							<c:if test="${questionsComment.isBest==1}">是</c:if>
						</td>
						<td align="center">${questionsComment.replyCount}</td>
						<td align="center">${questionsComment.praiseCount}</td>
						<td align="center">
							<fmt:formatDate value="${questionsComment.addTime}" pattern="yyyy/MM/dd HH:mm" />
						</td>
						<td align="center">
							<%--<a href="javascript:void(0)" onclick="delQuestionsComment('${questionsComment.id}')" class="button tooltip">删除</a>
							<a href="javascript:void(0)" onclick='getCommentContent("${questionsComment.id}")' class="button tooltip">修改</a>
							<a href="${ctx }/admin/questionscomment/querybycommentid/${questionsComment.id}" class="button tooltip">查看评论</a>
							<c:if test="${questionsComment.questionsStatus==0}">
								<a href="javascript:void(0)" onclick="acceptComment(${questionsComment.id})" class="button tooltip">采纳为最佳</a>
							</c:if>--%>

							<c:if test="${questionsComment.questionsStatus==0}">
								<button onclick="acceptComment(${questionsComment.id})" class="ui-state-default ui-corner-all" type="button">采纳为最佳</button>
							</c:if>
							<button onclick="window.location.href='${ctx }/admin/questionscomment/querybycommentid/${questionsComment.id}'" class="ui-state-default ui-corner-all" type="button">查看评论</button>
							<button onclick="getCommentContent('${questionsComment.id}')" class="ui-state-default ui-corner-all" type="button">修改</button>
							<button onclick="delQuestionsComment('${questionsComment.id}')" class="ui-state-default ui-corner-all" type="button">删除</button>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<jsp:include page="/WEB-INF/view/common/admin_page.jsp" />
	</div>
	
	<!-- 修改窗口 ,开始-->
	<div id="updateWin" aria-labelledby="ui-dialog-title-dialog" role="dialog" tabindex="-1"
		class="ui-dialog ui-widget ui-widget-content ui-corner-all ui-draggable ui-resizable"
		style="display: none; position: absolute; overflow: hidden; z-index: 1010; outline: 0px none; height: auto; width: 600px; top: 173px; left: 367px;border: 3px solid #ececec;">
		<div style="-moz-user-select: none;" unselectable="on" class="ui-dialog-titlebar ui-widget-header ui-corner-all ui-helper-clearfix">
			<span style="-moz-user-select: none;" unselectable="on" id="ui-dialog-title-dialog" class="ui-dialog-title">修改问答回复</span>
			<a style="-moz-user-select: none;" unselectable="on" role="button" class="ui-dialog-titlebar-close ui-corner-all"
				href="javascript:void(0)">
				<span style="-moz-user-select: none;" unselectable="on" class="ui-icon ui-icon-closethick">close</span>
			</a>
		</div>
		<div style="height: auto; min-height: 42px; width: auto;" class="ui-dialog-content ui-widget-content">
			<form id="updateForm">
				<input type="hidden" name="questionsComment.id" value="0" />
				<table style="line-height: 35px;">
					<tr>
						<td>点赞：</td>
						<td>
							<input type="text" value="${questionsComment.praiseCount }" name="questionsComment.praiseCount" onkeyup="value=value.replace(/[^\d]/g,'') "/>
						</td>
					</tr>
					<tr>
						<td>内容：</td>
						<td>
							<textarea rows="" cols="" name="questionsComment.content"></textarea>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div style="-moz-user-select: none;" unselectable="on" class="ui-resizable-handle ui-resizable-n"></div>
		<div style="-moz-user-select: none;" unselectable="on" class="ui-resizable-handle ui-resizable-e"></div>
		<div style="-moz-user-select: none;" unselectable="on" class="ui-resizable-handle ui-resizable-s"></div>
		<div style="-moz-user-select: none;" unselectable="on" class="ui-resizable-handle ui-resizable-w"></div>
		<div unselectable="on" style="z-index: 1001; -moz-user-select: none;"
			class="ui-resizable-handle ui-resizable-se ui-icon ui-icon-gripsmall-diagonal-se ui-icon-grip-diagonal-se"></div>
		<div unselectable="on" style="z-index: 1002; -moz-user-select: none;" class="ui-resizable-handle ui-resizable-sw"></div>
		<div unselectable="on" style="z-index: 1003; -moz-user-select: none;" class="ui-resizable-handle ui-resizable-ne"></div>
		<div unselectable="on" style="z-index: 1004; -moz-user-select: none;" class="ui-resizable-handle ui-resizable-nw"></div>
		<div class="ui-dialog-buttonpane ui-widget-content ui-helper-clearfix">
			<button class="ui-state-default ui-corner-all" onclick="updateQuestionComment()" type="button">确定</button>
			<button class="ui-state-default ui-corner-all closeBut" type="button">取消</button>
		</div>
	</div>
	<!-- 修改窗口 ,结束-->
</body>
</html>