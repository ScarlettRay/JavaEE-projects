<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/base.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head><title>发送短信消息</title>
<link rel="stylesheet" type="text/css" href="${ctximg}/static/common/jquery-ui-1.10.4/css/ui-lightness/jquery-ui-1.10.4.custom.css?v=${v}"/>
<script type="text/javascript" src="${ctximg}/static/common/jquery-ui-1.10.4/js/jquery-ui-1.10.4.custom.js?v=${v}"></script>
<script type="text/javascript" src="${ctximg}/static/common/jquery-ui-1.10.4/js/jquery.ui.datepicker-zh-CN.js?v=${v}"></script>
<script type="text/javascript" src="${ctximg}/static/common/jquery-ui-1.10.4/js/jquery-ui-timepicker-addon.js?v=${v}"></script>
<script type="text/javascript" src="${ctximg}/static/common/jquery-ui-1.10.4/js/jquery-ui-timepicker-zh-CN.js?v=${v}"></script>
<script type="text/javascript" src="${ctximg}/static/common/jquery-form.js"></script>
<script type="text/javascript" src="${ctximg}/kindeditor/kindeditor-all.js?v=1410957986989"></script>
	<%--ue编辑器--%>
	<script type="text/javascript" charset="utf-8" src="${ctx}/static/common/ueditor/ueditor.config.js"></script>
	<script type="text/javascript" charset="utf-8" src="${ctx}/static/common/ueditor/ueditor.all.js"></script>
<script type="text/javascript">
	$(function(){
		  $( "#sendDate" ).datetimepicker({
			  regional:"zh-CN",
		      changeMonth: true,
		      dateFormat:"yy-mm-dd ",
		      timeFormat: "HH:mm:ss"
		  });
		  //initKindEditor_addblog('message', 720, 300,'mobile','true');
		//实例化编辑器 UE编辑器
		initUEEditor("message",'720','300');
	});
	
	function sendmessage(){
		var sendLinkss = $("#pepole").val();
		  if(sendLinkss.trim()==''){
		 	 alert('短信接收人不能为空');
		 	 return false;
		 }
		 var valL = sendLinkss.split(",").length - 1;
		 var sendInfo=$("textarea[name='message']").val();
		 if(sendInfo.trim()==''){
		 	 alert('短信内容不能为空！');
		 	 return false;
		 }
		 if(sendInfo.length>70){
		 	alert("短信内容过长，请按要求输入！");
		 	return false;
		 }
		 var sendType=$("#type").val();
		 var sendTime=$("#sendDate").val();
		 if(sendType==2){
			 var nowTime=new Date().getTime();
			 var sendDate=Date.parse(sendTime.replace(/-/gi,"/"));
			 if(sendTime==""||sendTime==null){
				 alert("发送时间不能为空");
				 return false;
			 }else if(sendDate<nowTime){
				 alert("定时发送时间必须大于当前系统时间");
				 return false;
			 }
		 }
		 if(confirm('确定发送?')==false){
		 	return false;
		 }
			$.ajax({  
				url : "${ctx}/admin/mobile/sendMsg",  
				data : {
						"linksman" : sendLinkss,
						"content" : sendInfo,
						"sendType" : sendType,
						"sendTime" : sendTime
						 },  // 参数  
				type : "post",  
				async : false,
				dataType : "json",  //返回json数据 
				success:function (result){
                    if(result.message=='发送成功'){
                    	if(sendType==1){
                    		window.location.href = "/admin/email/progressbar?type=2";
                    	}else if(sendType==2){
                    		window.location.href = "/admin/mobile/sendMsglist";
                    	}
                    }else{
						alert(result.message);

					}

				}
			});
		}
	
		function changeType(){
			if($("#type").val()==1){
				$("#sendTr").hide();
			}else{
				$("#sendTr").show();
			}
		}
		
		//选择用户手机号
		function showNewwin(){
			window.open('${ctx}/admin/user/select_userlist/1','newwindow', 'toolbar=no,scrollbars=yes,location=no,resizable=no,top=200,left=300,width=800,height=600');
		} 
		//显示 去重
		function addnewUserId(newUserPhoneArr){
		    var phoneIds=[];
			if($("#pepole").val().trim()!=""){
				phoneIds=$("#pepole").val().split(",");
			}
			phoneIds = phoneIds.concat(newUserPhoneArr);  
			phoneIds.sort();
			phoneIds = uniqueArray(phoneIds);
			$("#pepole").val(phoneIds);
		}
		function uniqueArray(a){
		    temp = [];
		    for(var i = 0; i < a.length; i ++){
		        if(!contains(temp, a[i])){
		            temp.length+=1;
		            temp[temp.length-1] = a[i];
		        }
		    }
		    return temp;
		}
		function contains(a, e){
		    for(j=0;j<a.length;j++)if(a[j]==e)return true;
		    return false;
		}
		
		function importExcel(){
			var myFile = $("#myFile").val();
			if(myFile.length <= 0){
			alert("请选择导入内容");
			return false;
			}
			$("#importP").submit();
		}
		
		//form 以ajax提交
		$(function() {  
	        $("#importP").submit(function(){  
	            $(this).ajaxSubmit({  
	                type:"post",  //提交方式  
	                dataType:"json", //数据类型  
	                url:"${ctx}/admin/email/importMsgExcel/1", //请求url  
	                success:function(result){ //提交成功的回调函数  
	                	if(result.success==true){
	                		alert("导入成功");
	                		$("#pepole").val(result.entity);
	                	}else{
	                		alert(result.message);
	                	}
	                }  
	            });  
	            return false; //不刷新页面  
	        });  
	    });  
</script>

</head>
<body  >
<fieldset>
	<legend>
		<span>短信管理</span>
		&gt;
		<span>发送</span>
	</legend>
	<div class="">
		<form action="/admin/email/importMsgExcel/1" method="post" id="importP" enctype="multipart/form-data">
			<table style="line-height: 35px;" align="center" width="100%">
					<tr>
						<td width="10%;" align="center"><font color="red">*</font>添加联系人：</td>
						<td>
							<textarea name="numerStr" style="height: 80px;" id="pepole"></textarea>
						</td>
						<td>
							<input onclick="showNewwin()" class="button" type="button" value="添加" />
							<font color="red">
							批量导入&nbsp;&nbsp;&nbsp;&nbsp;<br />
							1、必须是excel格式,详情请参照模版sheet1<br/>
							2、格式不能有误<br/>
							3、记录要挨着输入，不能有空行<br />
							4、导入excel批量导入<a href="/static/common/admin/masterplate/mobile.xls">示例模版</a>下载
							</font>
							<input id="myFile" type="file" value="" name="myFile" />
							<input onclick="importExcel()" class="button" type="button" value="提交" />
						</td>
					</tr>
					<tr>
						<td align="center">&nbsp;</td>
						<td>
							<font color="red">
								规则<br/>
								1、手机号格式:132********  <br/>
								或者132********,138********,159********<br/>
								2、短信内容,不能超过70汉字或者英文字母 <br/>
								3、发送流程：添加手机号-&gt;设置短信内容&gt;提交发送<br/>
								4、添加手机号时，查询后可以选择添加所选学员及添加所有学员，请慎重选择。<br/>
								5、群发短信最多不能超过1000条
						        6、定时短信会有几分钟的延迟，如有延迟请耐心等待。
							</font>
						</td>
					</tr>
					<tr>
						<td align="center"><font color="red">*</font>&nbsp;发送的内容：</td>
						<td>
							<textarea name="message" style="width: 80%;height: 98px;line-height: normal" id="message"></textarea>
						</td>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td align="center"><font color="red">*</font>&nbsp;发送方式：</td>
						<td>
							<select id="type" name="type" onchange="changeType()" autocomplete="off">
								<option value="1" selected="selected">正常</option>
								<option value="2">定时</option>
							</select>
						</td>
						<td>&nbsp;</td>
					</tr>
					<tr id="sendTr" style="display:none">
						<td align="center"><font color="red">*</font>&nbsp;发送时间：</td>
						<td>
							<input type="text" readonly="readonly" name="sendDate" id="sendDate" class="AS-inp"/>
						</td>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td colspan="3" align="center">
							<input onclick="sendmessage()" class="button" type="button" value="发 送" />
						</td>
					</tr>
			</table>
		</form>
	</div>
</fieldset>
</body>
</html>