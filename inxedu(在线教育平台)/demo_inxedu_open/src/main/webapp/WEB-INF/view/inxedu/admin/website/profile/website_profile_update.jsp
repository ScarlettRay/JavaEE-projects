<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>网站配置管理</title>
<body>
	<form action="${ctx }/admin/websiteProfile/update" method="post" id="addprofileForm">
		<input type="hidden" name="type" value="${type}" />
		<fieldset>
			<legend>
				<span>网站配置管理</span>
				&gt;
				<span>修改网站配置</span>
			</legend>
			<div class="mt20">
				<p>
					<label ><span>
							<tt class="c_666 ml20 fsize12">
								（<font color="red">*</font>&nbsp;为必填项）
							</tt>
						</span></label>
					<span class="field_desc"></span>
				</p>
				<c:if test="${type=='web' }">
					<p>
						<label ><font color="red">*</font>&nbsp;网站title(网站头部)</label>
						<input type="text" name="title" value="${webSiteMap.web.title}" class="lf" />
						<span class="field_desc"></span>
					</p>
					<p>
						<label ><font color="red">*</font>&nbsp;网校名称(网站头部)</label>
						<input type="text" name="company" value="${webSiteMap.web.company}" class="lf" />
						<span class="field_desc"></span>
					</p>
					<p>
						<label ><font color="red">*</font>&nbsp;网站作者</label>
						<input type="text" name="author" value="${webSiteMap.web.author}" class="lf" />
						<span class="field_desc"></span>
					</p>
					<p>
						<label ><font color="red">*</font>&nbsp;关键词</label>
						<input type="text" name="keywords" value="${webSiteMap.web.keywords}" style="width: 75%" class="lf" />
						<span class="field_desc"></span>
					</p>
					<p>
						<label ><font color="red">*</font>&nbsp;描述</label>
						<input type="text" name="description" value="${webSiteMap.web.description}" style="width: 75%" class="lf" />
						<span class="field_desc"></span>
					</p>
					<p>
						<label ><font color="red">*</font>&nbsp;联系邮箱</label>
						<input type="text" name="email" value="${webSiteMap.web.email}" class="lf" />
						<span class="field_desc"></span>
					</p>
					<p>
						<label ><font color="red">*</font>&nbsp;24小时客服服务热线</label>
						<input type="text" name="phone" value="${webSiteMap.web.phone}" class="lf" />
						<span class="field_desc"></span>
					</p>
					<p>
						<label ><font color="red">*</font>&nbsp;工作时间</label>
						<input type="text" name="workTime" value="${webSiteMap.web.workTime}" class="lf" />
						<span class="field_desc"></span>
					</p>
					<p>
						<label ><font color="red">*</font>&nbsp;版权以及备案号(网站底部)</label>
						<input type="text" name="copyright" value="${webSiteMap.web.copyright}" style="width: 75%" class="lf" />
						<span class="field_desc"></span>
					</p>
					</tbody>
				</c:if>
				<c:if test="${type=='censusCode'}">
					<p>
						<label ><font color="red">*</font>&nbsp;统计代码</label>
						<textarea rows="6" cols="60" name="censusCodeString">${webSiteMap.censusCode.censusCodeString}</textarea>
						<span class="field_desc"></span>
					</p>
				</c:if>

				<c:if test="${type=='cc'}">
					<p>
						<label ><font color="red">*</font>&nbsp;CC appID</label>
						<input type="text" name="ccappID" value="${webSiteMap.cc.ccappID}" style="width: 15%" class="sf" />
						<span class="field_desc"></span>
					</p>
					<p>
						<label ><font color="red">*</font>&nbsp;CC appKEY</label>
						<input type="text" name="ccappKEY" value="${webSiteMap.cc.ccappKEY}" style="width: 15%" class="sf" />
						<span class="field_desc"></span>
					</p>
				</c:if>
				<c:if test="${type=='inxeduVideo'}">
					<p>
						<label ><font color="red">*</font>&nbsp;因酷云UserId</label>
						<input type="text" name="UserId" value="${webSiteMap.inxeduVideo.UserId}" style="width: 15%" class="sf" />
						<span class="field_desc"></span>
					</p>
					<p>
						<label ><font color="red">*</font>&nbsp;因酷云SecretKey</label>
						<input type="text" name="SecretKey" value="${webSiteMap.inxeduVideo.SecretKey}" style="width: 15%" class="sf" />
						<span class="field_desc"></span>
					</p>
					<p>
						<label ><font color="red">*</font>&nbsp;因酷云AccessKey</label>
						<input type="text" name="AccessKey" value="${webSiteMap.inxeduVideo.AccessKey}" style="width: 15%" class="sf" />
						<span class="field_desc"></span>
					</p>
				</c:if>
				<c:if test="${type=='emailConfigure'}">
					<p>
						<label ><font color="red">*</font>&nbsp;邮件传输协议(SMTP)</label>
						<input type="text" name="SMTP" value="${webSiteMap.emailConfigure.SMTP}" style="width: 15%" class="sf" />
						<span class="field_desc"></span>
					</p>
					<p>
						<label ><font color="red">*</font>&nbsp;邮件账号</label>
						<input type="text" name="username" value="${webSiteMap.emailConfigure.username}" style="width: 15%" class="sf" />
						<span class="field_desc"></span>
					</p>
					<p>
						<label ><font color="red">*</font>&nbsp;邮件密码</label>
						<input type="text" name="password" value="${webSiteMap.emailConfigure.password}" style="width: 15%" class="sf" />
						<span class="field_desc"></span>
					</p>
				</c:if>
				<p>
					<input class="button" type="submit" value="修改" />
					<input class="button" type="button" value="返回" onclick="javascript:history.go(-1)" />
				</p>
			</div>
			<!-- /tab4 end -->
		</fieldset>
	</form>
</body>
</html>
