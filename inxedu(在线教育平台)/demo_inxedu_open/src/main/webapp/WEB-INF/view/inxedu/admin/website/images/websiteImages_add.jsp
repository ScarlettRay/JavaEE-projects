<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新建图片</title>
<link rel="stylesheet" type="text/css" href="${ctx}/kindeditor/themes/default/default.css" />
<script type="text/javascript" src="${ctx}/kindeditor/kindeditor-all.js"></script>
<script type="text/javascript" src="${ctx}/static/admin/website/images.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/static/common/bigcolorpicker/jquery.bigcolorpicker.css" />
<script type="text/javascript" src="${ctx}/static/common/bigcolorpicker/jquery.bigcolorpicker.js"></script>
<script>
	$(function() {
		$("#imageColor").bigColorpicker("imageColor", "L", 10);
	});
</script>
</head>
<body>
<fieldset>
	<legend>
		<span>广告图</span>
		&gt;
		<span>添加</span>
	</legend>
	<div class="">
		<form action="${ctx}/admin/website/addImages" method="post" id="saveImagesForm">
			<input type="hidden" name="websiteImages.imagesUrl" />
			<input type="hidden" name="websiteImages.previewUrl" />
			<table style="line-height: 35px;" width="100%">
				<tr>
					<td width="15%" align="center">
						<font color="red">*</font>图片标题
					</td>
					<td style="text-align: left;">
						<input name="websiteImages.title" type="text" style="width: 580px;" />
					</td>
				</tr>
				<tr>
					<td align="center">
						<font color="red">*</font>图片描述
					</td>
					<td style="text-align: left;">
						<input name="websiteImages.describe" type="text" maxlength="600" style="width: 580px;" />(仅后台描述前台没有展示效果除个别分类,如首页课程互动则会用到)
					</td>
				</tr>
				<tr>
					<td align="center">
						<font color="red">*</font>图片类型
					</td>
					<td style="text-align: left;">
						<select name="websiteImages.typeId">
							<option value="0">请选类型</option>
							<c:forEach items="${typeList}" var="type">
								<option value="${type.typeId}">${type.typeName}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td align="center">跳转链接</td>
					<td style="text-align: left;">
						<input name="websiteImages.linkAddress" type="text" style="width: 580px;" />
					</td>
				</tr>
				<tr>
					<td align="center">排序</td>
					<td style="text-align: left;">
						<input name="websiteImages.seriesNumber" value="0" type="text" style="width: 110px;" />
					</td>
				</tr>
				<tr>
					<td align="center">
						<font color="red">*</font>图片：
					</td>
					<td style="text-align: left;">
						<img id="imagesUrl" width="400px" height="210px" src="${ctx }/static/admin/assets/logo.png">
						<input type="button" value="上传" id="imageFile" />
					</td>
				</tr>
				<tr>
					<td align="center">略缩图片：</td>
					<td style="text-align: left;">
						<img id="previewUrl" width="200px" height="120px" src="${ctx }/static/admin/assets/logo.png">
						<input type="button" value="上传" id="previewFile" />
					</td>
				</tr>
				<tr>
					<td align="center">背景色：</td>
					<td style="text-align: left;">
						<input type="text" id="imageColor" name="websiteImages.color" value="${websiteImages.color}">
					</td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<input onclick="saveImage()" class="button" type="button" value="保存">
						<input onclick="javascript:history.go(-1);" class="button" type="button" value="返回">
					</td>
				</tr>
			</table>
		</form>
	</div>
</fieldset>
</body>
</html>