<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户列表</title>
<script type="text/javascript" src="${ctx}/static/admin/system/sys-user.js"></script>
</head>
<body>
	<div class="">
		<form action="${ctx}/admin/sysuser/userlist" method="post" id="searchForm">
			<a title="创建用户" onclick="showWin()" class="button tooltip" href="javascript:void(0)">
				<span class="ui-icon ui-icon-newwin"></span>
				创建用户
			</a>
			<input type="hidden" id="pageCurrentPage" name="page.currentPage" value="1" />
			<input placeholder="登录名/E-MAIL" type="text" name="querySysUser.keyWord" value="${querySysUser.keyWord}" />
			<a title="查找用户" onclick="searchUser()" class="button tooltip" href="javascript:void(0)">
				<span class="ui-icon ui-icon-search"></span>
				查找用户
			</a>
			<a title="清空" onclick="javascript:$('#searchForm input:text').val('');" class="button tooltip" href="javascript:void(0)">
				<span class="ui-icon ui-icon-cancel"></span>
				清空
			</a>
		</form>
		<table cellspacing="0" cellpadding="0" border="0" class="fullwidth" width="100%">
			<thead>
				<tr>
					<td align="center">登录名</td>
					<td align="center">姓名</td>
					<td align="center">E-Mail</td>
					<td align="center">电话号</td>
					<td align="center">创建时间</td>
					<td align="center">最后登录时间</td>
					<td align="center">最后登录IP</td>
					<td align="center">状态</td>
					<td align="center" width="330">操作</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${userList}" var="sysuser" varStatus="index">
					<tr <c:if test="${index.count%2==1 }">class="odd"</c:if>>
						<td align="center">${sysuser.loginName}</td>
						<td align="center">
							<c:if test="${sysuser.userName!=null && sysuser.userName!=''}">${sysuser.userName}</c:if>
							<c:if test="${sysuser.userName==null || sysuser.userName==''}">--</c:if>
						</td>
						<td align="center">
							<c:choose>
								<c:when test="${sysuser.email!=null && sysuser.email!=''}">
					${sysuser.email}
					</c:when>
								<c:otherwise>--</c:otherwise>
							</c:choose>
						</td>
						<td align="center">
							<c:choose>
								<c:when test="${sysuser.tel!=null && sysuser.tel!=''}">
					${sysuser.tel}
					</c:when>
								<c:otherwise>--</c:otherwise>
							</c:choose>
						</td>
						<td align="center">
							<fmt:formatDate value="${sysuser.createTime}" pattern="yyyy/MM/dd" />
						</td>
						<td align="center">
							<c:if test="${sysuser.lastLoginTime!=null}">
								<fmt:formatDate value="${sysuser.lastLoginTime}" pattern="yyyy/MM/dd HH:mm" />
							</c:if>
							<c:if test="${sysuser.lastLoginTime==null}">--</c:if>
						</td>
						<td align="center">
							<c:if test="${sysuser.lastLoginIp!=null && sysuser.lastLoginIp!=''}">
					${sysuser.lastLoginIp}
					</c:if>
							<c:if test="${sysuser.lastLoginIp==null || sysuser.lastLoginIp==''}">
					--
					</c:if>
						</td>
						<td align="center">
							<c:if test="${sysuser.status==0}">正常</c:if>
							<c:if test="${sysuser.status==1}">冻结</c:if>
						</td>
						<td align="center">
							<div class="c-more-box">
								<a href="javascript:void(0)" class="check-more">更多<em class="icon14 ml5 more-select"> </em></a>
								<ul class="c-more-list">
									<li><a href="javascript:void(0)" onclick="updatePwd(${sysuser.userId})">修改密码</a></li>
									<li><a href="javascript:void(0)" onclick="delUser(${sysuser.userId})">删除用户</a></li>
									<li><a href="${ctx}/admin/sysuser/looklog/${sysuser.userId}">查看日志</a></li>
								</ul>
							</div>
							<samp>
								<c:if test="${sysuser.status==0}">
									<button onclick="disableOrstart(${sysuser.userId},2,this)" class="ui-state-default ui-corner-all" type="button">冻结</button>
								</c:if>
								<c:if test="${sysuser.status==1}">
									<button onclick="disableOrstart(${sysuser.userId},1,this)" class="ui-state-default ui-corner-all" type="button">启用</button>
								</c:if>
							</samp>
							<button onclick="initUser(${sysuser.userId})" class="ui-state-default ui-corner-all" type="button">修改</button>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<jsp:include page="/WEB-INF/view/common/admin_page.jsp" />
	</div>

	<!-- 添加用户窗口 ,开始-->
	<div id="createWin" aria-labelledby="ui-dialog-title-dialog" role="dialog" tabindex="-1"
		class="ui-dialog ui-widget ui-widget-content ui-corner-all ui-draggable ui-resizable"
		style="display: none; position: absolute; overflow: hidden; z-index: 1010; outline: 0px none; height: auto; width: 511px; top: 173px; left: 367px;border: 3px solid #ececec;">
		<div style="-moz-user-select: none;" unselectable="on" class="ui-dialog-titlebar ui-widget-header ui-corner-all ui-helper-clearfix">
			<span style="-moz-user-select: none;" unselectable="on" id="ui-dialog-title-dialog" class="ui-dialog-title">创建新用户</span>
			<a style="-moz-user-select: none;" unselectable="on" role="button" class="ui-dialog-titlebar-close ui-corner-all"
				href="javascript:void(0)">
				<span style="-moz-user-select: none;" unselectable="on" class="ui-icon ui-icon-closethick">close</span>
			</a>
		</div>
		<div style="height: 300px; min-height: 42px; width: auto;" class="ui-dialog-content ui-widget-content">
			<form id="sysUserForm">
				<table style="line-height: 35px;">
					<tr>
						<td>
							<font color="red">*</font>登录名
						</td>
						<td>
							<input name="sysUser.loginName" id="loginName" type="text" />
						</td>
					</tr>
					<tr>
						<td>
							<font color="red">*</font>密码：
						</td>
						<td>
							<input name="sysUser.loginPwd" id="loginPwd" type="password" />
						</td>
					</tr>
					<tr>
						<td>
							<font color="red">*</font>确认密码：
						</td>
						<td>
							<input id="confirmPwd" type="password" />
						</td>
					</tr>
					<tr>
						<td>姓名：</td>
						<td>
							<input name="sysUser.userName" id="userName" type="text" />
						</td>
					</tr>
					<tr>
						<td>邮箱：</td>
						<td>
							<input name="sysUser.email" id="email" type="text" />
						</td>
					</tr>
					<tr>
						<td>电话：</td>
						<td>
							<input name="sysUser.tel" id="tel" type="text" />
						</td>
					</tr>
					<tr>
						<td>所属角色：</td>
						<td>
							<select name="sysUser.roleId">
								<option value="0">请选择</option>
								<c:forEach items="${sysRoleList}" var="role">
									<option value="${role.roleId}">${role.roleName}</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<td></td>
						<td></td>
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
			<button class="ui-state-default ui-corner-all" onclick="createSysUser()" type="button">确定</button>
			<button class="ui-state-default ui-corner-all closeBut" type="button">取消</button>
		</div>
		<!-- 添加用户窗口 ,结束-->
	</div>
	<!-- 修改用户窗口 ,开始-->
	<div id="updateWin" aria-labelledby="ui-dialog-title-dialog" role="dialog" tabindex="-1"
		class="ui-dialog ui-widget ui-widget-content ui-corner-all ui-draggable ui-resizable"
		style="display: none; position: absolute; overflow: hidden; z-index: 1010; outline: 0px none; height: auto; width: 511px; top: 173px; left: 367px;border: 3px solid #ececec;">
		<div style="-moz-user-select: none;" unselectable="on" class="ui-dialog-titlebar ui-widget-header ui-corner-all ui-helper-clearfix">
			<span style="-moz-user-select: none;" unselectable="on" id="ui-dialog-title-dialog" class="ui-dialog-title">修改用户信息</span>
			<a style="-moz-user-select: none;" unselectable="on" role="button" class="ui-dialog-titlebar-close ui-corner-all"
				href="javascript:void(0)">
				<span style="-moz-user-select: none;" unselectable="on" class="ui-icon ui-icon-closethick">close</span>
			</a>
		</div>
		<div style="height: 197px; min-height: 42px; width: auto;" class="ui-dialog-content ui-widget-content">
			<form id="updateSysUserForm">
				<input type="hidden" name="sysUser.userId" value="0" />
				<table style="line-height: 35px;">
					<tr>
						<td>登录名</td>
						<td id="userLongName"></td>
					</tr>
					<tr>
						<td>姓名：</td>
						<td>
							<input name="sysUser.userName" type="text" />
						</td>
					</tr>
					<tr>
						<td>邮箱：</td>
						<td>
							<input name="sysUser.email" type="text" />
						</td>
					</tr>
					<tr>
						<td>电话：</td>
						<td>
							<input name="sysUser.tel" type="text" />
						</td>
					</tr>
					<tr>
						<td>所属角色：</td>
						<td>
							<select name="sysUser.roleId">
								<option value="0">请选择</option>
								<c:forEach items="${sysRoleList}" var="role">
									<option value="${role.roleId}">${role.roleName}</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<td></td>
						<td></td>
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
			<button class="ui-state-default ui-corner-all" onclick="updateSysUser()" type="button">确定</button>
			<button class="ui-state-default ui-corner-all closeBut" type="button">取消</button>
		</div>
	</div>
	<!-- 修改用户窗口 ,结束-->

	<!-- 修改密码窗口 ,开始-->
	<div id="updatePwdWin" aria-labelledby="ui-dialog-title-dialog" role="dialog" tabindex="-1"
		class="ui-dialog ui-widget ui-widget-content ui-corner-all ui-draggable ui-resizable"
		style="display: none; position: absolute; overflow: hidden; z-index: 1010; outline: 0px none; height: auto; width: 511px; top: 173px; left: 367px;border: 3px solid #ececec;">
		<div style="-moz-user-select: none;" unselectable="on" class="ui-dialog-titlebar ui-widget-header ui-corner-all ui-helper-clearfix">
			<span style="-moz-user-select: none;" unselectable="on" id="ui-dialog-title-dialog" class="ui-dialog-title">修改用户密码</span>
			<a style="-moz-user-select: none;" unselectable="on" role="button" class="ui-dialog-titlebar-close ui-corner-all"
				href="javascript:void(0)">
				<span style="-moz-user-select: none;" unselectable="on" class="ui-icon ui-icon-closethick">close</span>
			</a>
		</div>
		<div style="height: 78px; min-height: 42px; width: auto;" class="ui-dialog-content ui-widget-content">
			<input type="hidden" id="sysUserId" value="0" />
			<table style="line-height: 35px;">
				<tr>
					<td>密码：</td>
					<td>
						<input type="password">
					</td>
				</tr>
				<tr>
					<td>密码密码：</td>
					<td>
						<input type="password" />
					</td>
				</tr>
				<tr>
					<td></td>
					<td></td>
				</tr>
			</table>
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
			<button class="ui-state-default ui-corner-all" onclick="submitUpdatePwd()" type="button">确定</button>
			<button class="ui-state-default ui-corner-all closeBut" type="button">取消</button>
		</div>
	</div>
	<!-- 修改密码窗口 ,结束-->
</body>
</html>