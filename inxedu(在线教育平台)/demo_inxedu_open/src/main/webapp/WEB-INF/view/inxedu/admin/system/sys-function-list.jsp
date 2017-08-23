<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>功能权限管理</title>
<link type="text/css" rel="stylesheet" href="${ctx}/static/common/ztree/css/zTreeStyle.css" />
<script type="text/javascript" src="${ctx}/static/common/ztree/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="${ctx}/static/common/ztree/jquery.ztree.excheck-3.5.min.js"></script>
<script type="text/javascript" src="${ctx}/static/common/ztree/jquery.ztree.exedit-3.5.min.js"></script>
<script type="text/javascript" src="${ctx}/static/admin/system/sys-function.js"></script>
	<link rel="stylesheet" href="${ctx}/kindeditor/themes/default/default.css"/>
	<script type="text/javascript" src="${ctximg}/kindeditor/kindeditor-all.js"></script>
<script type="text/javascript">
		var ztree='${jsonFunction}';
		$(function(){
			showFunctionZtree(ztree);
			$(".ui-dialog-titlebar-close,.closeBut,").click(function(){
				closeData();
			});
			initSimpleImageUpload('imageFile','function',callback,false);
		});
		/**
		 * 图片上传回调
		 */
		function callback(imgUrl){
			$("input[name='sysFunction.imageUrl']").val(imgUrl);
			$("#showImage").attr('src',imagesPath+imgUrl);
		}
	</script>
</head>
<body>
<fieldset>
	<legend>
		<span>权限管理</span>
		&gt;
		<span>权限设置</span>
	</legend>
	<div class="mt20">
		<table width="100%" cellspacing="0" cellpadding="0" border="0" class="commonTab01">
			<tbody>
				<tr>
					<td width="20%">
						<div id="ztreedemo" class="ztree"></div>
					</td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<input type="button" value="增加权限" onclick="addFunction();" class="btn btn-danger" />
						<input type="button" value="删除选中权限" onclick="delFunctions();" class="btn btn-danger" />
						<input type="button" onclick="checkNodeFalse();" value="清空" class="btn btn-danger" />
						<font color="red">(*红字为功能权限，其他为菜单权限，拖拽可修改权限节点父级)</font>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
</fieldset>
	<!-- 修改权限信息窗口，开始 -->
	<div id="updateWin" aria-labelledby="ui-dialog-title-dialog" role="dialog" tabindex="-1"
		class="ui-dialog ui-widget ui-widget-content ui-corner-all ui-draggable ui-resizable"
		style="display: none; position: absolute; overflow: hidden; z-index: 1010; outline: 0px none; height: auto; width: 511px; top: 173px; left: 367px;border: 3px solid #ececec;">
		<div style="-moz-user-select: none;" unselectable="on" class="ui-dialog-titlebar ui-widget-header ui-corner-all ui-helper-clearfix">
			<span style="-moz-user-select: none;" unselectable="on" id="ui-dialog-title-dialog" class="ui-dialog-title">修改权限</span>
			<a style="-moz-user-select: none;" unselectable="on" role="button" class="ui-dialog-titlebar-close ui-corner-all"
				href="javascript:void(0)">
				<span style="-moz-user-select: none;" unselectable="on" class="ui-icon ui-icon-closethick">close</span>
			</a>
		</div>
		<div style="height: 212px; min-height: 42px; width: auto;" class="ui-dialog-content ui-widget-content">
			<form id="updateForm">
				<input type="hidden" id="functionId" name="sysFunction.functionId" />
				<table style="line-height: 35px;">
					<tr>
						<td>权限名:</td>
						<td>
							<input id="functionName" name="sysFunction.functionName" type="text" />
							<samp></samp>
						</td>
					</tr>
					<tr>
						<td>权限URL:</td>
						<td>
							<input id="functionUrl" name="sysFunction.functionUrl" type="text" />
						</td>
					</tr>
					<tr>
						<td>权限类型:</td>
						<td>
							<select id="functionType" name="sysFunction.functionType"><option value="1">菜单类型</option>
								<option value="2">功能类型</option>
							</select>
						</td>
					</tr>
					<tr>
						<td>排序:</td>
						<td>
							<input id="sort" type="text" value="0" name="sysFunction.sort" />
						</td>
					</tr>
					<tr>
						<td>图标:</td>
						<td>
							<input id="imageUrl" type="hidden" value="" name="sysFunction.imageUrl" />
							<img id="showImage" width="20" height="20" src="${ctx}/static/admin/assets/logo.png" />
							<input type="button" value="上传" id="imageFile" />
							<font color="red">(请上传 20*20(长X宽)像素 的图片)</font>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div style="-moz-user-select: none;" unselectable="on" class="ui-resizable-handle ui-resizable-n"></div>
		<div style="-moz-user-select: none;" unselectable="on" class="ui-resizable-handle ui-resizable-e"></div>
		<div style="-moz-user-select: none;" unselectable="on" class="ui-resizable-handle ui-resizable-s"></div>
		<div style="-moz-user-select: none;" unselectable="on" class="ui-resizable-handle ui-resizable-w"></div>
		<div unselectable="on" style="z-index: 1001; -moz-user-select: none;"
			class="ui-resizable-handle ui-resizable-se ui-icon ui-icon-gripsmall-diagonal-se ui-icon-grip-diagonal-se"></div>
		<div unselectable="on" style="z-index: 1002; -moz-user-select: none;" class="ui-resizable-handle ui-resizable-sw"></div>
		<div unselectable="on" style="z-index: 1003; -moz-user-select: none;" class="ui-resizable-handle ui-resizable-ne"></div>
		<div unselectable="on" style="z-index: 1004; -moz-user-select: none;" class="ui-resizable-handle ui-resizable-nw"></div>
		<div class="ui-dialog-buttonpane ui-widget-content ui-helper-clearfix">
			<button class="ui-state-default ui-corner-all" onclick="updateFunction()" type="button">确定</button>
			<button class="ui-state-default ui-corner-all closeBut" type="button">取消</button>
		</div>
	</div>
	<!-- 修改权限信息窗口，结束 -->
</body>
</html>