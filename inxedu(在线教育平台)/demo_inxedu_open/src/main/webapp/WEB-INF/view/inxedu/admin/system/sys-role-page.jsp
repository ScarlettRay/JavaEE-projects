<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>角色列表</title>
<link type="text/css" rel="stylesheet" href="${ctx}/static/common/ztree/css/zTreeStyle.css" />
<script type="text/javascript" src="${ctx}/static/common/ztree/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="${ctx}/static/common/ztree/jquery.ztree.excheck-3.5.min.js"></script>
<script type="text/javascript" src="${ctx}/static/admin/system/sys-role.js"></script>
<script type="text/javascript">
	var treedata='${functionList}';
	$(function(){
		initFunction(treedata);
	});
</script>

<!--  添加角色 弹窗  -->
<style type="text/css">
#overlay {
	background: #303030;
	opacity: 0.50;
	filter: Alpha(opacity =   50);
	display: none;
	position: absolute;
	top: 0px;
	left: 0px;
	z-index: 100;
}

#addRole {
	margin-left: auto;
	margin-right: auto;
	border: 2px solid #FFFFFF;
	font-size: 12px;
	font-family: "宋体";
	color: #990000;
	padding-top: 20px;
	width: 400px;
	height: 200px;
	position: absolute;
	z-index: 110;
	display: none;
	background: #e7e7e7;
	left: 35%;
	top: 20%;
	opacity: 0.85;
	filter: Alpha(opacity =   85);
}

#updateRoleName {
	margin-left: auto;
	margin-right: auto;
	border: 2px solid #FFFFFF;
	font-size: 12px;
	font-family: "宋体";
	color: #990000;
	padding-top: 20px;
	width: 400px;
	height: 200px;
	position: absolute;
	z-index: 110;
	display: none;
	background: #e7e7e7;
	left: 35%;
	top: 20%;
	opacity: 0.85;
	filter: Alpha(opacity =   85);
}
</style>

</head>
<body>
<fieldset>
	<legend>
		<span>角色</span>
		&gt;
		<span>角色管理</span>
	</legend>
	<div class="mt20">
		<form action="" method="post" id="updateRoleFunctionForm" onsubmit="return updateRole();">
			<table width="100%" cellspacing="0" cellpadding="0" border="0" class="commonTab01">
				<tbody>
					<tr>
						<td width="20%" align="center">
							<select id="roleId" onchange="onchangeRole(this.value);">
								<option value="0">请选择角色</option>
								<c:forEach items="${roleList}" var="roleList">
									<option value="${roleList.roleId}">${roleList.roleName}</option>
								</c:forEach>
							</select>
						</td>

						<td width="80%">
							<input type="hidden" value="" name="selectedFunctionIds" id="selectedFunctionIds" />
							<div id="ztreedemo" class="ztree"></div>
						</td>
					</tr>
					<tr>
						<td colspan="2" align="center">
							<input type="button" value="修改角色名称" onclick="editRole(2)" class="btn btn-danger" />
							<input type="button" value="添加角色" onclick="editRole(1)" class="btn btn-danger" />
							<input type="button" value="保存" onclick="saveRoleFunction()" class="btn btn-danger" />
							<input type="button" value="删除选中角色" onclick="delRole();" class="btn btn-danger" />
							<font color="red">(*红字为功能权限，其他为菜单权限)</font>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
</fieldset>

	<!-- 添加角色 -->
	<div id="addRole" class="addRole" style="display: none">
		<table border="0">
			<tbody>
				<tr>
					<td>新角色名称：</td>
					<td>
						<input id="roleName" />
					</td>
				</tr>
				<tr>
					<td align="center" colspan="2">
						<input type="button" onclick="saveRole()" id="save-btn" value="确定" class="btn btn-danger" />
						<input type="button" onclick="cancelSave();" value="取消" class="btn btn-danger" />
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	<div>
		<div id="overlay" class="overlay" style="display: none" />
	</div>
	<!-- 添加角色 -->
</body>
</html>
