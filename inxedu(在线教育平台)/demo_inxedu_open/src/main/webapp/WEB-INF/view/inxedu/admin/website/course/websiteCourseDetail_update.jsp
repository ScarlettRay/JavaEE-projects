<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<script type="text/javascript" src="${ctximg}/kindeditor/kindeditor-all.js"></script>
<link rel="stylesheet" type="text/css" href="${ctximg}/kindeditor/themes/default/default.css" />

<script type="text/javascript">
	function updateSubmit(){
		if(isNaN($("#orderNum").val())){
			alert("课程数量只能为数字");
			return;
		}
		$("#updateWebsiteCourseDetailForm").submit();
	}
</script>
</head>
<body>
	<fieldset>
		<legend>
			<span>推荐课程管理</span>
			&gt;
			<span>推荐课程修改</span>
		</legend>
		<div class="mt20">
			<form action="${ctx}/admin/website/updateWebsiteCourseDetail" method="post" id="updateWebsiteCourseDetailForm">
				<input type="hidden" name="websiteCourseDetail.id" value="${websiteCourseDetailDTO.id}" />
				<p>
					<label for="sf"><span>
							推荐课程基本属性
							<tt class="c_666 ml20 fsize12">
								（<font color="red">*</font>&nbsp;为必填项）
							</tt>
						</span></label>
				</p>
				<p>
					<label for="sf"><font color="red">*</font>&nbsp;课程名称 </label> ${websiteCourseDetailDTO.courseName }
					<span class="field_desc"></span>
				</p>
				<p>
					<label for="sf"><font color="red">*</font>&nbsp;分类</label> ${websiteCourseDetailDTO.recommendName }
					<span class="field_desc"></span>
				</p>
				<p>
					<label for="sf"><font color="red">*</font>&nbsp;排序值</label>
					<input type="text" name="websiteCourseDetail.orderNum" value="${websiteCourseDetailDTO.orderNum }" id="orderNum" value="0"
						class="{required:true} sf" />
					<span class="field_desc">倒序</span>
				</p>
				<p>
					<input type="button" value="提 交" class="button" onclick="updateSubmit()">
						<input type="button" value="返 回" class="button" onclick="history.go(-1);">
				</p>
			</form>
		</div>
		<!-- /tab4 end -->
	</fieldset>
</body>
</html>