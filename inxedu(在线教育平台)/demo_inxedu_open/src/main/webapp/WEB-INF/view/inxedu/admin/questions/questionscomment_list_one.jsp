<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>问答评论列表</title>
<script type="text/javascript" src="${ctx}/static/admin/questions/questions_comment.js"></script>
</head>
<body>
	<div class="">
		<a title="返回" onclick="history.go(-1)" class="button tooltip" href="javascript:void(0)">
			<span class="ui-icon ui-icon-cancel"></span>
			返回
		</a>
		<table cellspacing="0" cellpadding="0" border="0" class="fullwidth" width="100%">
			<thead>
				<tr>
					<td align="center">发表人</td>
					<!-- <td align="center" width="230">评论内容</td> -->
					<td align="center">点赞数</td>
					<td align="center">添加时间</td>
					<td align="center">操作</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${questionsCommentList}" var="questionsComment" varStatus="index">
					<tr <c:if test="${index.count%2==1 }">class="odd"</c:if>>
						<td align="center">${questionsComment.email}</td>
						<%-- <td align="center">${questionsComment.content}</td> --%>
						<td align="center">${questionsComment.praiseCount}</td>
						<td align="center">
							<fmt:formatDate value="${questionsComment.addTime}" pattern="yyyy/MM/dd HH:mm" />
						</td>
						<td align="center">
							<button onclick="getCommentContent('${questionsComment.id}')" class="ui-state-default ui-corner-all" type="button">修改</button>
							<button onclick="delQuestionsCommentSon('${questionsComment.id}')" class="ui-state-default ui-corner-all" type="button">删除</button>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	
	<!-- 修改窗口 ,开始-->
	<div id="updateWin" aria-labelledby="ui-dialog-title-dialog" role="dialog" tabindex="-1"
		class="ui-dialog ui-widget ui-widget-content ui-corner-all ui-draggable ui-resizable"
		style="display: none; position: absolute; overflow: hidden; z-index: 1010; outline: 0px none; height: auto; width: 600px; top: 173px; left: 367px;border: 3px solid #ececec;">
		<div style="-moz-user-select: none;" unselectable="on" class="ui-dialog-titlebar ui-widget-header ui-corner-all ui-helper-clearfix">
			<span style="-moz-user-select: none;" unselectable="on" id="ui-dialog-title-dialog" class="ui-dialog-title">修改问答评论</span>
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