
/**
 * 发送找回密码邮件
 */
function sendPwdRecoveery(){
	var params='';
	$("#recoveryForm input:text").each(function(){
		params+=$(this).serialize()+"&";
    });
	$.ajax({
		url:baselocation+'/uc/sendEmail',
		type:'post',
		dataType:'json',
		data:params,
		success:function(result){
			$("#errorDiv").show();
			$("#errorMsg").text(result.message);
		}
	});
}
