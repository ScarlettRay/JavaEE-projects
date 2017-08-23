$(function(){
	$("#beginCreateTime,#endCreateTime,#beginPayTime,#endPayTime,.expireTime").datetimepicker({
		regional:"zh-CN",
        changeMonth: true,
        dateFormat:"yy-mm-dd",
        timeFormat: "HH:mm:ss"
    });
});
/**
 * 审核订单
 * @param orderId 订单号
 * @param em 点击元素
 */
function auditing(orderNo,em){
	if(confirm('订单审核后将变成支付成功状态，确认要审核？')){
		$.ajax({
			url:baselocation+'/admin/order/auditing/'+orderNo,
			type:'post',
			dataType:'json',
			success:function(result){
				if(result.success==false){
					alert(result.message);
				}else{
					var order = result.entity.order;
					var state = $(em).parent("samp").parent("td").parent("tr").children("td")[5];
					$(state).html('<font color="#00e33b">已支付</font>');
					var payTime = $(em).parent("samp").parent("td").parent("tr").children("td")[6];
					$(payTime).text(result.entity.payTime);
					var loginName = $(em).parent("samp").parent("td").parent("tr").children("td")[7];
					$(loginName).text(result.entity.sysUser);
					var td = $(em).parent("samp").parent("td");
					$(td).text('--');
				}
			},
			error:function(error){
				alert("系统繁忙，请稍后再操作！");
			}
		});
	}
}

/**
 * 取消或恢复订单
 * @param state 订单状态 
 * @param orderId 订单ID
 */
function cancelOrRegain(state,orderId,orderNo,em){
	var message='确认要取消订单？';
	if(state=='INIT'){
		message='确认要恢复订单？';
	}
	if(confirm(message)){
		$.ajax({
			url:baselocation+'/admin/order/cancelOrRegain',
			type:'post',
			dataType:'json',
			data:{'order.states':state,'order.orderId':orderId},
			success:function(result){
				if(result.success==false){
					alert(result.message);
				}else{
					var td = $(em).parent("samp").parent("td").parent("tr").children("td")[5];
					if(state=='INIT'){
						$(td).html('<font color="#e33b00">未支付</font>');
						$("#auditing"+orderId).html('<a class="button tooltip" onclick="auditing(\''+orderNo+'\',this)" href="javascript:void(0)">审核</a>');
						$("#initcancel"+orderId).html('<a href="javascript:void(0)" onclick="cancelOrRegain(\'CANCEL\','+orderId+',\''+orderNo+'\',this)" class="button tooltip">取消</a>');
					}else if(state=='CANCEL'){
						$(td).html('已取消');
						$("#auditing"+orderId).html('');
						$("#initcancel"+orderId).html('<a href="javascript:void(0)" onclick="cancelOrRegain(\'INIT\','+orderId+',\''+orderNo+'\',this)" class="button tooltip">恢复</a>');
					}
				}
			},
			error:function(error){
				alert("系统繁忙，请稍后再操作！");
			}
		});
	}
}


/**
* 课程延期 订单状态成功  流水状态过期  才可以进行延期
* @param orderState detailState
*/
function delayCourse(em) {
	$(em).parent().prev().prev().prev().find("span").eq(0).hide();
	$(em).parent().prev().prev().prev().find("span").eq(1).show();
	
}

function clickStopTIme(obj,detailId) {
	var stopTime = $(obj).prev().val();
	if (stopTime == "") {
		alert("延期时间不能为空！");
		return false;
	}
	var daoqishijian = $(obj).parent().prev().html();
	if (stopTime <= daoqishijian) {
		alert('延期时间必须大于到期时间！');
		return false;
	}
	if(confirm("确定延期吗?")){
		$.ajax({
			url : baselocation+"/admin/order/delayorder",
			data : {
				"order.orderId" : detailId,
				"order.expireTime" : stopTime
			},
			dataType : "json",
			type : "post",
			async : false,
			success : function(result) {
				if (result.success == true) {
					alert("已延期");
					$(obj).parent().prev().show();
					$(obj).parent().prev().html(stopTime);
					$(obj).parent().hide();
				}else{
					alert(result.message);
				}
			},
			error : function(error) {
				alert("error");
			}

		});
	}
}

/*
* 取消延期
*/
function clickStopTImequxiao(obj){
	$(obj).parent().hide();
	$(obj).parent().prev().show();
}

/*
*关闭订单
*/
function closeOrder(orderId){
	if(confirm("确定关闭此课程订单吗?")){
		$.ajax({
			url : baselocation+"/admin/order/closeCourse/"+orderId,
			data : {
			},
			dataType : "json",
			type : "post",
			async : false,
			success : function(result) {
				if (result.success == true) {
					alert("关闭订单成功");
					window.location.reload();
				}else{
					alert(result.message);
				}
			},
			error : function(error) {
				alert("error");
			}
		});
	}
}