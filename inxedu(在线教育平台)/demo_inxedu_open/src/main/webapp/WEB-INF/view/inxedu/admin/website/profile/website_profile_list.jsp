<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/base.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>网站管理</title>
<link rel="stylesheet" type="text/css" href="${ctximg}/kindeditor/themes/default/default.css" />
<script type="text/javascript" src="${ctximg}/kindeditor/kindeditor-all.js"></script>
<script type="text/javascript">
	var type='';
	$(document).ready(function() {
		type='${type}';
		$("#"+type).attr("href","javascript:void(0)");
		if(type=='logo'){
			initSimpleImageUpload('fileuploadButton','websiteLogo',callback);
		}
	});
	function callback(imgUrl){
		$('#subjcetpic').attr("src",'<%=staticImage%>'+imgUrl);
		$("#subjcetpic").show();
		$('#imagesUrl').val(imgUrl);
	}
	function submit(){
		if(type!=''){
			if(type=='logo'){
				if($("#imagesUrl").val()==''){
					alert("请上传logo");
					return ;
				}
				$("#searchForm").attr("action","${ctximg}/admin/websiteProfile/update");
			}else if(type=='ico'){
				var icoFile=$("#icoFile").val();
				if(icoFile!=null&&icoFile!=''){
					var fileNames=icoFile.split('.');
			  		var fileName=fileNames[0];
			  		var fileNameSuffix=fileNames[1];
			  		if(!(fileNameSuffix == "ico" || fileNameSuffix == "ICO")) {
			  			alert("请选择ico格式的图片!");
			  			return;
			  		}else if(fileName.indexOf('favicon')==-1)
			  		{
			  			alert("文件必须命名为favicon");
			  			return;
			  		}else{
			  			$("#icoForm").attr("action","${ctx}/admin/websiteProfile/uploadIco");
			  			$("#icoForm").submit();
			  			return;
			  		}
				}else{
					alert("请上传ioc文件");
					return;
				}
			}else{
				$("#searchForm").attr("action","${ctx}/admin/websiteProfile/find/"+type+"");
			}
			$("#searchForm").submit();
		}
	}
</script>
</head>
<body>

	<div class="mt20">
		<div class="commonWrap">
			<table class="fullwidth" width="100%" width="100%" cellspacing="0" cellpadding="0" border="0">
				<caption>
					<div class="capHead">
						<div class="clearfix">
							<div class="optionList">
								<button type="button" class="ui-state-default ui-corner-all" onclick="window.location.href='${ctx}/admin/websiteProfile/find/emailConfigure'"
									>邮件配置</button>
								<button type="button" class="ui-state-default ui-corner-all" onclick="window.location.href='${ctx}/admin/websiteProfile/find/cc'"
									id="web">CC视频配置</button>
								&nbsp;&nbsp;&nbsp;
								<button type="button" class="ui-state-default ui-corner-all" onclick="window.location.href='${ctx}/admin/websiteProfile/find/inxeduVideo'"
										id="web">因酷云视频配置</button>
								&nbsp;&nbsp;&nbsp;
								<button type="button" class="ui-state-default ui-corner-all" onclick="window.location.href='${ctx}/admin/websiteProfile/find/logo'"
									id="logo">logo</button>
								&nbsp;&nbsp;&nbsp;
								<button type="button" class="ui-state-default ui-corner-all" onclick="window.location.href='${ctx}/admin/websiteProfile/find/ico'"
									id="ico">ico</button>
								&nbsp;&nbsp;&nbsp;
								<button type="button" class="ui-state-default ui-corner-all"
									onclick="window.location.href='${ctx}/admin/websiteProfile/find/censusCode'" id="censusCode">统计代码</button>
								&nbsp;&nbsp;&nbsp;
								<button type="button" class="ui-state-default ui-corner-all" onclick="window.location.href='${ctx}/admin/websiteProfile/find/web'"
										id="web">基本信息</button>
							</div>
						</div>
					</div>
				</caption>
				<thead align="center">
					<tr>
						<td width="30%">
							<span>名称</span>
						</td>
						<td width="70%">
							<span>描述</span>
						</td>
					</tr>
				</thead>
				<c:if test="${type=='web' }">
					<tbody id="tabS_web" align="center">
						<tr class="odd">
							<td>网站title(网站头部)</td>
							<td>${webSiteMap.web.title}</td>
						</tr>
						<tr>
							<td>网校名称(网站头部)</td>
							<td>${webSiteMap.web.company}</td>
						</tr>
						<tr class="odd">
							<td>网站作者</td>
							<td>${webSiteMap.web.author}</td>
						</tr>
						<tr>
							<td>关键词</td>
							<td>${webSiteMap.web.keywords}</td>
						</tr>
						<tr class="odd">
							<td>描述</td>
							<td>${webSiteMap.web.description}</td>
						</tr>
						<tr>
							<td>联系邮箱(网站底部)</td>
							<td>${webSiteMap.web.email}</td>
						</tr>
						<tr class="odd">
							<td>联系电话(网站底部)</td>
							<td>${webSiteMap.web.phone}</td>
						</tr>
						<tr>
							<td>工作时间(网站底部)</td>
							<td>${webSiteMap.web.workTime}</td>
						</tr>
						<tr class="odd">
							<td>版权以及备案号(网站底部)</td>
							<td>${webSiteMap.web.copyright}</td>
						</tr>
					</tbody>
				</c:if>
				<form action="?" id="searchForm" method="post">
					<input type="hidden" name="flag" id="flag" value="flag" />
					<input type="hidden" name="type" id="type" value="${type}" />
					<c:if test="${type=='logo'}">
						<tbody id="tabS_logo" align="center">
							<tr class="odd">
								<td>图片地址</td>
								<td>
									<input type="text" name="url" id="imagesUrl" value="${webSiteMap.logo.url}" style="width: 450px;" />
									<font color="red">*LOGO链接,可直接修改</font>
								</td>
							</tr>
							<tr>
								<td>设置新logo</td>
								<td>
									<img alt="" src="<%=staticImage%>${webSiteMap.logo.url}" id="subjcetpic" width="144px" height="90px" />
									<input type="button" id="fileuploadButton" value="上传" />
									<font color="red">*LOGO链接，支持JPG、PNG格式</font>
								</td>
							</tr>
						</tbody>
					</c:if>
				</form>
				<c:if test="${type=='censusCode' }">
					<tbody id="tabS_censusCode" align="center">
						<tr class="odd">
							<td>统计代码</td>
							<td>
								<textarea rows="6" cols="60" disabled="disabled">${webSiteMap.censusCode.censusCodeString}</textarea>
							</td>
						</tr>
					</tbody>
				</c:if>
				<form action="?" method="post" enctype="multipart/form-data" id="icoForm">
					<c:if test="${type=='ico' }">
						<tbody id="tabS_censusCode" align="center">
							<tr class="odd">
								<td>ico文件</td>
								<td>
									<img alt="" src="${ctx}/favicon.ico">
								</td>
							</tr>
							<tr>
								<td>设置新的ico文件</td>
								<td>
									<font color="red">（请美工制作图片的大小标为32*32的ico图片,否则图片会失真）</font>
									<input type="file" name="icoFile" id="icoFile" />
								</td>
							</tr>
						</tbody>
					</c:if>
					<c:if test="${type=='cc'}">
						<tbody id="tabS_alipay" align="center">
							<tr class="odd">
								<td>CC appID</td>
								<td>${webSiteMap.cc.ccappID}</td>
							</tr>
							<tr>
								<td>CC appKEY</td>
								<td>${webSiteMap.cc.ccappKEY}</td>
							</tr>
						</tbody>
					</c:if>
					<c:if test="${type=='inxeduVideo'}">
						<tbody align="center">
						<tr class="odd">
							<td>因酷云UserId</td>
							<td>${webSiteMap.inxeduVideo.UserId}</td>
						</tr>
						<tr class="">
							<td>因酷云SecretKey</td>
							<td>${webSiteMap.inxeduVideo.SecretKey}</td>
						</tr>
						<tr class="odd">
							<td>因酷云AccessKey</td>
							<td>${webSiteMap.inxeduVideo.AccessKey}</td>
						</tr>
						</tbody>
					</c:if>
					<c:if test="${type=='emailConfigure'}">
						<tbody align="center">
						<tr class="odd">
							<td>邮件传输协议(SMTP)</td>
							<td>${webSiteMap.emailConfigure.SMTP}</td>
						</tr>
						<tr class="">
							<td>邮件账号</td>
							<td>${webSiteMap.emailConfigure.username}</td>
						</tr>
						<tr class="odd">
							<td>邮件密码</td>
							<td>${webSiteMap.emailConfigure.password}</td>
						</tr>
						</tbody>
					</c:if>
				</form>
				<tr>
					<td colspan="2" align="center">
						<input class="button" type="button" value="修改" onclick="javascript:submit(-1)">
						<input class="button" type="button" value="返回" onclick="javascript:history.go(-1)">
					</td>
				</tr>
			</table>
		</div>
	</div>
</body>
</html>