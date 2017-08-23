<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>修改前台主题色</title>
<link rel="stylesheet" type="text/css" href="${ctx }/static/common/bigcolorpicker/jquery.bigcolorpicker.css" />
<script type="text/javascript" src="${ctx }/static/common/bigcolorpicker/jquery.bigcolorpicker.js"></script>
<script>
	$(function() {
		$("#imageColor").bigColorpicker("imageColor", "L", 10);
	});
	function formSubmit(){
		$("#form").submit();
	}
</script>
</head>
<body>
<fieldset>
	<legend>
		<span>网站信息</span>
		&gt;
		<span>模板切换</span>
	</legend>

	<div class="">
		<form action="${ctx}/admin/theme/update" method="post" id="form">
			<input type="hidden" name="teacher.picPath" id="imagesUrl" />

			<p>
				<label for="sf"><font color="red">*</font>&nbsp;前台主题色:</label>
				<input type="text" name="color" id="imageColor" class="{required:true} sf" data-rule="required;"/>
				<span class="field_desc"></span>
			</p>
			<p>
				<input type="button" value="提 交" class="button" onclick="formSubmit()" />
				<%--<input type="button" value="返 回" class="button" onclick="javascript:history.go(-1);" />--%>
			</p>
		</form>
	</div>
</fieldset>
</body>
</html>
