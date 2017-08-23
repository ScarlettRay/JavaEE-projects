<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<script type="text/javascript" src="${ctximg}/kindeditor/kindeditor-all.js?v=${v}"></script>
<link rel="stylesheet" type="text/css" href="${ctximg}/kindeditor/themes/default/default.css?v=${v}" />

<script type="text/javascript">
	function updateSubmit(){
		if($("#navigateName").val()==null||$("#navigateName").val()==""){
			alert("名称不能为空");
			return;
		}
		if($("#navigateUrl").val()==null||$("#navigateUrl").val()==""){
			alert("跳转链接不能为空");
			return;
		}
		if(isNaN($("#orderNum").val())){
			alert("排序只能为数字");
			return;
		}
		$("#updateNavigateForm").submit();
	}
</script>
</head>
<body>
	<fieldset>
		<legend>
			<span>导航管理</span>
			&gt;
			<span>导航更新</span>
		</legend>
		<!-- /tab4 begin -->
		<div class="mt20">
			<form action="${ctx}/admin/website/updateNavigate" method="post" id="updateNavigateForm">
				<input type="hidden" name="websiteNavigate.id" value="${websiteNavigate.id}" />
				<p>
					<label><span>
							导航基本属性
							<tt class="c_666 ml20 fsize12">
								（<font color="red">*</font>&nbsp;为必填项）
							</tt>
						</span></label>
					<span class="field_desc"></span>
				</p>
				<p>
					<label><font color="red">*</font>&nbsp;导航名称</label>
					<input type="text" name="websiteNavigate.name" id="navigateName" value="${websiteNavigate.name}" class="{required:true}" class="sf" />
					<span class="field_desc"></span>
				</p>
				<p>
					<label><font color="red">*</font>&nbsp;跳转链接</label>
					<input type="text" name="websiteNavigate.url" id="navigateUrl" value="${websiteNavigate.url}"
						class="{required:true,number:true,min:0,max:1000} sf" />
					<span class="field_desc"></span>
				</p>
				<p>
					<label><font color="red">*</font>&nbsp;在新页面打开</label> <select class="dropdown" name="websiteNavigate.newPage">
						<option value="1">否</option>
						<option value="0" <c:if test="${websiteNavigate.newPage==0}">selected="selected"</c:if>>是</option>
					</select>
				</p>
				<p>
					<label><font color="red">*</font>&nbsp;类型</label> <select class="dropdown" name="websiteNavigate.type">
						<option value="INDEX" <c:if test="${websiteNavigate.type=='INDEX'}">selected="selected"</c:if>>首页</option>
						<option value="USER" <c:if test="${websiteNavigate.type=='USER'}">selected="selected"</c:if>>个人中心</option>
						<option value="FRIENDLINK" <c:if test="${websiteNavigate.type=='FRIENDLINK'}">selected="selected"</c:if>>尾部友链</option>
						<option value="TAB" <c:if test="${websiteNavigate.type=='TAB'}">selected="selected"</c:if>>尾部标签</option>
					</select>
				</p>
				<tr>
					<td align="center">
						<font color="red">*</font>&nbsp;排序（由大到小显示）
					</td>
					<td>
						<input type="text" name="websiteNavigate.orderNum" value="${websiteNavigate.orderNum}" class="{required:true,number:true}" value="0"
							id="orderNum" />
					</td>
				</tr>
				<p>
					<input type="button" value="提 交" class="button" onclick="javascript:updateSubmit()" />
					<input type="button" value="返 回" class="button" onclick="javascript:history.go(-1);" />
				</p>
			</form>
		</div>
		<!-- /tab4 end -->
	</fieldset>
</body>
</html>