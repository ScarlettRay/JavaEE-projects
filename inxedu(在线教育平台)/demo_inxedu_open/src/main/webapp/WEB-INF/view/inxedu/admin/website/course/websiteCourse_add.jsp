<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>推荐课程分类添加</title>
<script type="text/javascript">
	function addSubmit(){
		if($("#websiteCourseName").val()==null||$("#websiteCourseName").val()==""){
			alert("名称不能为空");
			return;
		}
		if($("#link").val()==null||$("#link").val()==""){
			alert("更多跳转不能为空");
			return;
		}
		if(isNaN($("#courseNum").val())){
			alert("课程数量只能为数字");
			return;
		}
		$("#addWebsiteCourseForm").submit();
	}
</script>
</head>
<body>
	<fieldset>
		<legend>
			<span>推荐课程分类管理</span>
			&gt;
			<span>推荐课程分类添加</span>
		</legend>
		<div class="mt20">
			<form action="${ctx}/admin/website/addCourse" method="post" id="addWebsiteCourseForm">
				<p>
					<label for="sf"><span>
							推荐课程分类基本属性
							<tt class="c_666 ml20 fsize12">
								（<font color="red">*</font>&nbsp;为必填项）
							</tt>
						</span></label>
					<span class="field_desc"></span>
				</p>
				<p>
					<label for="sf"><font color="red">*</font>&nbsp;分类名称</label>
					<input type="text" name="websiteCourse.name" id="websiteCourseName" class="{required:true} sf" />
					<span class="field_desc"></span>
				</p>
				<p>
					<label for="sf"><font color="red">*</font>&nbsp;更多跳转</label>
					<input type="text" name="websiteCourse.link" id="link" class="{required:true,number:true,min:0,max:1000} sf" />
					<span class="field_desc"></span>
				</p>
				<p>
					<label for="sf"><font color="red">*</font>&nbsp;课程数量</label>
					<input type="text" name="websiteCourse.courseNum" id="courseNum" value="0" class="{required:true} sf" />
					<span class="field_desc"></span>
				</p>
				<p>
					<label for="sf">描述</label>
					<textarea rows="3" cols="80" name="websiteCourse.description" id="description"></textarea>
					<span class="field_desc"></span>
				</p>
				<p>
					<input type="button" value="提交" class="button" onclick="javascript:addSubmit()" />
					<input type="button" value="返回" class="button" onclick="javascript:history.go(-1);" />
				</p>
			</form>
		</div>
		<!-- /tab4 end -->
	</fieldset>
</body>
</html>