﻿<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp"%>
<div class="mt10">
	<!-- 课程笔记 位置 -->
	<textarea name="notesContext" onkeyup="$('#notContextId').hide();" id="notesContextId" style="width:368px;">
		<c:if test="${!empty courseNote.content}">${courseNote.content}</c:if>
	</textarea>
	<section class="mt5 clearfix">
	<span class="fr">
		<tt class="mr5 c-orange" style="display: none;" id="notContextId"></tt>
		<a href="javascript: void(0)" onclick="addNotest()" title="保存" class="lh-reply-btn">保存</a>
	</span>
	</section>
</div>
<script>
	$(function(){
		initKindEditornote("notesContextId");
	});
</script>