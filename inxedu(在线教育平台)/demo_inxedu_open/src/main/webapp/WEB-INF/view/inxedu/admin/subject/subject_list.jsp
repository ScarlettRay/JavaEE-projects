<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>专业列表</title>
<link rel="stylesheet" href="${ctximg}/static/common/ztree/css/zTreeStyle.css" type="text/css" />
<script type="text/javascript" src="${ctx}/static/common/ztree/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="${ctx}/static/common/ztree/jquery.ztree.excheck-3.5.min.js"></script>
<script type="text/javascript" src="${ctx}/static/common/ztree/jquery.ztree.exedit-3.5.min.js"></script>
<script type="text/javascript" src="${ctximg}/static/admin/subject/subject.js"></script>
<script type="text/javascript">
subjectList='${subjectList}';
</script>
</head>
<body>
	<fieldset>
		<legend>
			<span>专业管理</span>
			&gt;
			<span>专业列表</span>
		</legend>
		<div class="mt20">
			<div class="commonWrap">
				<span style="color:red;">注:排序顺序为倒序,只能输入数字,输入后离开焦点则自动保存</span>
				<form action="/admin/subj/delSubjects" method="post" id="updateSubjectForm" name="updateSubjectForm">
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
									<input type="button" value="增加专业" onclick="addSubject();" class="button" />
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
