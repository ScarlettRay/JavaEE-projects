<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加文章</title>
<script src="${ctximg}/static/common/jquery-ui-1.10.4/js/jquery-ui-1.10.4.custom.js"></script>
<script src="${ctximg}/static/common/jquery-ui-1.10.4/js/jquery.ui.datepicker-zh-CN.js"></script>
<script type="text/javascript" src="${ctximg}/static/common/jquery-ui-1.10.4/js/jquery-ui-timepicker-addon.js"></script>
<script type="text/javascript" src="${ctximg}/static/common/jquery-ui-1.10.4/js/jquery-ui-timepicker-zh-CN.js"></script>
<script type="text/javascript" src="${ctx}/static/admin/article/article.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/kindeditor/themes/default/default.css" />
<script type="text/javascript" src="${ctx}/kindeditor/kindeditor-all.js"></script>

<link rel="stylesheet" href="${ctx}/static/common/nice-validator/jquery.validator.css"></link>
<script type="text/javascript" src="${ctx}/static/common/nice-validator/jquery.validator.js"></script>
<script type="text/javascript" src="${ctx}/static/common/nice-validator/local/zh-CN.js"></script>
	<%--ue编辑器--%>
	<script type="text/javascript" charset="utf-8" src="${ctx}/static/common/ueditor/ueditor.config.js"></script>
	<script type="text/javascript" charset="utf-8" src="${ctx}/static/common/ueditor/ueditor.all.js"></script>
<script type="text/javascript">
	$(function() {
		//实例化编辑器 UE编辑器
		initUEEditor("content",'580','400');
		//initKindEditor_addblog('content', 580, 400, 'articleContent', 'true');
		initSimpleImageUpload('imageFile', 'article', callback);
		
		$("#publishTime").datetimepicker({
			regional:"zh-CN",
	        changeMonth: true,
	        dateFormat:"yy-mm-dd",
	        timeFormat: "HH:mm:ss"
	    });
	});
</script>

</head>
<body>
<fieldset>
	<legend>
		<span>文章管理</span>
		&gt;
		<span>添加文章</span>
	</legend>
	<div class="">
		<form action="${ctx}/admin/article/addarticle" method="post" id="articleForm" data-validator-option="{stopOnError:false, timely:false}">
			<input type="hidden" name="article.imageUrl">
			<table style="line-height: 35px;" width="100%">
				<tr>
					<td width="10%" align="center">
						<font color="red">*</font>标题
					</td>
					<td>
						<input name="article.title" data-rule="标题:required;" type="text" style="width: 580px;" />
					</td>
				</tr>
				<tr>
					<td align="center">摘要：</td>
					<td>
						<textarea name="article.summary" style="width: 580px; height: 90px;" data-rule="required;"></textarea>
					</td>
				</tr>
				<tr>
					<td align="center">
						<font color="red">*</font>文章类型：
					</td>
					<td>
						<select name="article.type">
							<option value="2">文章</option>
						</select> 作者：
						<input name="article.author" value="" type="text" style="width: 100px;" />
						来源：
						<input type="text" value="" name="article.source" />
					</td>
				</tr>
				<tr>
					<td align="center">点击数：</td>
					<td>
						<input name="article.clickNum" id="clickNum"   type="text" style="width: 140px;" value="0"  onkeyup="this.value=this.value.replace(/\D/g,'')" data-rule="required;"/>
					</td>
				</tr>
				<tr>
					<td align="center">发布时间：</td>
					<td>
						<input name="article.publishTime" id="publishTime"   readonly="readonly"  type="text" style="width: 140px;z-index: 1000;position:relative;" data-rule="required;"/>
					</td>
				</tr>
				<tr>
					<td align="center">封面图片：</td>
					<td>
						<img id="showImage" width="180" height="100" src="${ctx }/static/admin/assets/logo.png">
						<input type="button" value="上传" id="imageFile" />
						<font color="red">(请上传宽高为： 640*357 的图片)</font>
					</td>
				</tr>
				<tr>
					<td align="center">
						<font color="red">*</font>内容：
					</td>
					<td>
						<textarea name="articleContent.content" id="content" data-rule="required;" style="background-image: none;margin-top:26px;"></textarea>
					</td>
				</tr>
				<tr>
					<td align="center">排序值：</td>
					<td>
						<input name="article.sort" id="sort"   type="text" style="width: 140px;" value="0"  onkeyup="this.value=this.value.replace(/\D/g,'')" data-rule="required;"/>
					</td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<input onclick="saveArticle()" class="button" type="button" value="保存">
					</td>
				</tr>
			</table>
		</form>
	</div>
</fieldset>
</body>
</html>