<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<script type="text/javascript" src="${ctximg}/kindeditor/kindeditor-all.js?v=${v}"></script>
<link rel="stylesheet" type="text/css" href="${ctximg}/kindeditor/themes/default/default.css?v=${v}" />

<script type="text/javascript">
	function addSubmit(){
		if($("#navigateName").val()==null||$("#navigateName").val()==""){
			alert("名称不能为空");
			return false;
		}
		if($("#navigateUrl").val()==null||$("#navigateUrl").val()==""){
			alert("跳转链接不能为空");
			return false;
		}
		if(isNaN($("#orderNum").val())){
			alert("排序只能为数字");
			return false;
		}
		$("#addNavigateForm").submit();
	}
</script>
</head>
<body>
	<fieldset>
		<legend>
			<span>导航管理</span>
			&gt;
			<span>导航添加</span>
		</legend>
		<!-- /tab4 begin -->
		<div class="mt20">
			<form action="${ctx}/admin/website/addNavigate" method="post" id="addNavigateForm">
				<span>
					导航基本属性
					<tt class="c_666 ml20 fsize12">
						（<font color="red">*</font>&nbsp;为必填项）
					</tt>
				</span>
				<p>
					<label for="sf"><font color="red">*</font>&nbsp;导航名称</label>
					<input type="text" name="websiteNavigate.name" id="navigateName" class="sf" />
					<span class="field_desc"></span>
				</p>
				<p>
					<label for="sf"><font color="red">*</font>&nbsp;跳转链接</label>
					<input type="text" name="websiteNavigate.url" id="navigateUrl" class="sf" />
					<span class="field_desc"></span>
				</p>
				<p>
					<label for="dropdown"><font color="red">*</font>&nbsp;在新页面打开</label> <select class="dropdown" name="websiteNavigate.newPage">
						<option value="1">否</option>
						<option value="0">是</option>
					</select>
				</p>
				<p>
					<label for="dropdown"><font color="red">*</font>&nbsp;类型</label> <select class="dropdown" name="websiteNavigate.type">
						<option value="INDEX">首页</option>
						<option value="USER">个人中心</option>
						<option value="FRIENDLINK">尾部友链</option>
						<option value="TAB">尾部标签</option>
					</select>
				</p>
				<p>
					<label for="sf"><font color="red">*</font>&nbsp;排序（由大到小显示）</label>
					<input type="text" name="websiteNavigate.orderNum" value="0" id="orderNum" class="sf" />
					<span class="field_desc"></span>
				</p>
				<p>
					<input type="button" value="提 交" class="button" onclick="javascript:addSubmit()" />
					<input type="reset" value="返 回" class="button" onclick="javascript:history.go(-1);" />
				</p>
			</form>
		</div>
</body>
</html>