<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>问答标签列表</title>
<link rel="stylesheet" href="${ctximg}/static/common/ztree/css/zTreeStyle.css" type="text/css" />
<script type="text/javascript" src="${ctx}/static/common/ztree/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="${ctx}/static/common/ztree/jquery.ztree.excheck-3.5.min.js"></script>
<script type="text/javascript" src="${ctx}/static/common/ztree/jquery.ztree.exedit-3.5.min.js"></script>
<script type="text/javascript" src="${ctximg}/static/admin/questions/questions_tag.js"></script>
<script type="text/javascript">
questionsTagList='${questionsTagList}';
</script>
</head>
<body>
	<fieldset>
		<legend>
			<span>问答标签管理</span>
			&gt;
			<span>问答标签列表</span>
		</legend>

		<div class="mt20">
			<div class="commonWrap">
				<form action="/admin/subj/delQuestionsTags" method="post" id="updateQuestionsTagForm" name="updateQuestionsTagForm">
					<input type="hidden" name="ids" id="ids" />
					<table width="100%" cellspacing="0" cellpadding="0" border="0" class="commonTab01">
						<tbody>
							<tr>
								<td width="20%">
									<div id="ztreedemo" class="ztree"></div>
								</td>
							</tr>
							<tr>
								<td colspan="2" align="center">
									<input type="button" value="增加问答标签" onclick="addQuestionsTag();" class="button" />
								</td>
							</tr>
						</tbody>
					</table>
				</form>
			</div>
		</div>
	</fieldset>
</body>
</html>
