$(function(){
	$(".ui-dialog-titlebar-close,.closeBut").click(function(){
		closedData();
	});
});

/** 
 * 显示添加窗口 
 */
function showWin(){
	closedData();
	$("#createWin").show();
}

/**
 * 点击查找用户
 */
function searchUser(){
	$("#searchForm").submit();
}

/**
 * 禁用或启用后台用户
 * @param userId 用户ID
 * @param type 1启用 2禁用
 * @param em 
 */
function disableOrstart(userId,type,em){
	closedData();
	$.ajax({
		url:baselocation+'/admin/sysuser/disableOrstart/'+userId+'/'+type,
		type:'post',
		dataType:'json',
		success:function(result){
			if(result.success==true){
				var td = $(em).parent('samp').parent('td').parent('tr').children('td')[7];
				if(type==1){
					$(td).text('正常');
					$(em).parent('samp').html('<button onclick="disableOrstart('+userId+',2,this)" class="ui-state-default ui-corner-all" type="button">冻结</button>');
				}else if(type==2){
					$(td).text('冻结');
					$(em).parent('samp').html('<button onclick="disableOrstart('+userId+',1,this)" class="ui-state-default ui-corner-all" type="button">启用</button>');
				}
			}else{
				alert(result.message);
			}
		},
		error:function(error){
			alert("系统繁忙，请稍后再操作");
		}
	});
}

/**
 * 显示修改密码窗口
 */
function updatePwd(userId){
	closedData();
	$("#updatePwdWin").show();
	$("#updatePwdWin #sysUserId").val(userId);
}

/**
 * 提交修改密码
 */
function submitUpdatePwd(){
	var passArr = $("#updatePwdWin input:password");
	if(passArr[0].value==null  || passArr[1].value==null || $.trim(passArr[1].value)==''||$.trim(passArr[0].value)==''){
		alert("请输入新密码！");
		return false;
	}
	if(passArr[0].value!=passArr[1].value){
		alert('两次密码不一致！');
		return false;
	}
	var userId = $("#updatePwdWin #sysUserId").val()
	$.ajax({
		url:baselocation+'/admin/sysuser/updatepwd/'+userId,
		type:'post',
		dataType:'json',
		data:{'newPwd':passArr[0].value},
		success:function(result){
			if(result.success==true){
				closedData();
			}
			alert(result.message);
		},
		error:function(error){
			alert('系统繁忙，请稍后再操作！');
		}
	});
}

/**
 * 执行修改用户信息
 */
function updateSysUser(){
	var params = ''
	$("#updateSysUserForm input,#updateSysUserForm select").each(function(){
		params+=$(this).serialize()+"&";
    });
	$.ajax({
		url:baselocation+'/admin/sysuser/updateuser',
		type:'post',
		dataType:'json',
		data:params,
		success:function(result){
			if(result.success==true){
				closedData();
			}
			alert(result.message);
		},
		error:function(error){
			alert("系统繁忙，请稍后再操作！");
		}
	});
}

/**
 * 初始化修改用户
 * @param userId
 */
function initUser(userId){
	closedData();
	$.ajax({
		url:baselocation+'/admin/sysuser/initupdateuser/'+userId,
		type:'post',
		dataType:'json',
		success:function(result){
			if(result.success==true){
				var user = result.entity;
				$("#updateSysUserForm input[name='sysUser.userId']").val(user.userId);
				$("#userLongName").text(user.loginName);
				$("#updateSysUserForm input[name='sysUser.userName']").val(user.userName);
				$("#updateSysUserForm input[name='sysUser.email']").val(user.email);
				$("#updateSysUserForm input[name='sysUser.tel']").val(user.tel);
				$("#updateSysUserForm select[name='sysUser.roleId']").val(user.roleId);
				$("#updateWin").show();
			}
		},
		error:function(error){
			alert("系统繁忙，请稍后再操作！");
		}
	});
}

/**
 * 创建用户
 */
function createSysUser(){
	if($("#confirmPwd").val()!=$("#loginPwd").val()){
		alert("两次密码不一致");
		return false;
	}
	var params = ''
	$("#sysUserForm input,#sysUserForm select").each(function(){
		params+=$(this).serialize()+"&";
    });
	$.ajax({
		url:baselocation+'/admin/sysuser/createuser',
		type:'post',
		dataType:'json',
		data:params,
		success:function(result){
			if(result.success==true){
				closedData();
				alert(result.message);
				window.location.reload();
			}else{
				alert(result.message);
			}

		},
		error:function(error){
			alert("系统繁忙，请稍后再操作！");
		}
	});
}

function closedData(){
	$("#createWin input:text,#updateWin input:text").val('');
	$("#createWin input:text").val('');
	$("input[name='sysUser.loginName']").val('');
	$("input:password").val('');
	$("select").val(0);
	$("#updateWin,#createWin,#updatePwdWin").hide();
}
/**
 * 删除用户
 * @param userId
 */
function delUser(userId){
	closedData();
	if(confirm("确定删除吗？")){
		$.ajax({
			url:baselocation+'/admin/sysuser/disableOrstart/'+userId+'/3',
			data:{},
			type:'post',
			dataType:'json',
			success:function(result){
				if(result.success==true){
					alert("删除成功");
					window.location.reload();
				}
			},
			error:function(error){
				alert("系统繁忙，请稍后再操作！");
			}
		});
	}
}