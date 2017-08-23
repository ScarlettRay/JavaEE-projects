<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>批量开通学员</title>

<script type="text/javascript">
function importExcel(){
	
	var myFile = $("#myFile").val();
	if(myFile.length <= 0){
	alert("请选择导入内容");
	return false;
	}
	$("#importP").submit();
}
</script>
</head>
<body>
<fieldset>
	<legend>
		<span>学员管理</span>
		&gt;
		<span>批量开通</span>
	</legend>

	<div class="mt20">
		<form action="/admin/user/importExcel" method="post" id="importP" enctype="multipart/form-data">
				<table width="100%" cellspacing="0" cellpadding="0" border="0" class="commonTab01">
					<caption>&nbsp;</caption>
					<tbody>
						<tr>
							<td align="center"><font color="red">*</font>&nbsp;信息描述</td>
							<td>excel模版说明：<br>
                                第一列：用户的电子邮箱,必须是未注册过的<br>
                                第二列：用户的手机号<br>
                                第三列：密码 如不填写默认111111,不得填入非法字符例如“. * #”<br>
                                第四列：赠送课程ID
                                （<a href="${ctx }/static/common/import_student/import_student.xls"  style="color: red;">点击下载模版</a>）<br>
							</td>
						</tr>
						<tr>
							<td align="center">导入中出错选择项</td>
							<td>
								<select name="mark">
									<option value="1">跳过</option>
									<option value="2">全部放弃</option>
								</select>
							</td>
						</tr>
						<tr>
							<td align="center">上传</td>
							<td>
								<span class="ml10"><input id="myFile" type="file" value="" name="myFile"/><input type="button" value="提交" class="btn btn-danger"  onclick="importExcel()"/>
								</span>
							</td>
						</tr>
					</tbody>
				</table>
				</form>

			</div>
    </div>
</fieldset>
</body>
</html>