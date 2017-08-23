<%@ page import="com.inxedu.os.common.listener.MySessionListener" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/base.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" http-equiv="Content-Type" />
<title>后台管理系统 - 因酷在线教育软件 - 在线教育整体解决方案提供商</title>
<script type="text/javascript" src="${ctximg}/static/admin/js/highChart/highcharts.js"></script>
<script type="text/javascript" src="${ctximg}/static/admin/js/highChart/highcharts-3d.js"></script>
<script type="text/javascript" language="javascript">
$(function() {
	//活跃度 15天
	drawCartogramLogin(15);
});

//活跃度按天数
function drawCartogramLogin(days) {
    var dateTime = '';
    var studentNum =  "" ;
    $.ajax({
        url:baselocation +"/admin/statistics/web/detailajax",
        type:"post",
        data:{"days":days,"type":"LOGIN_NUM"},
        dataType:"json",
        async:false,
        success:function(result){
            if(result.success){
                dateTime=result.message;
                studentNum= "["+result.entity+"]" ;
            }else{
                alert("请求错误!");
            }
        }
    })
    $('#container').highcharts({
	        title: {
	            text: '最近'+days+'天活跃学员',
	            x: -20 //center
	        },
	        subtitle: {
	            text: '',
	            x: -20
	        },
	        xAxis: {
	        	categories : eval("(" + dateTime + ")")
	        },

        yAxis: [{ // left y axis
            title: {
                text: null
            },
            labels: {
                align: 'left',
                x: 3,
                y: 16,
                format: '{value:.,0f}'
            },
            showFirstLabel: false,
            min:0
        }, { // right y axis
            linkedTo: 0,
            gridLineWidth: 0,
            opposite: true,
            title: {
                text: null
            },
            labels: {
                align: 'right',
                x: -3,
                y: 16,
                format: '{value:.,0f}'
            },
            showFirstLabel: false  ,
            min:0
        }],

	        tooltip: {
	            valueSuffix: '人'
	        },
	        legend: {
	            layout: 'vertical',
	            align: 'right',
	            verticalAlign: 'middle',
	            borderWidth: 0
	        },
	        series: [{
	        	name : '活跃人数',
				data : eval("(" + studentNum + ")")
	        }]
	    });
}

//注册人数活跃度按天数
function drawCartogramRegistered(days) {
    var dateTime = '';
    var studentNum =  "" ;
    $.ajax({
        url:baselocation +"/admin/statistics/web/detailajax",
        type:"post",
        data:{"days":days,"type":"REGISTERED_NUM"},
        dataType:"json",
        async:false,
        success:function(result){
            if(result.success){
                dateTime=result.message;
                studentNum= "["+result.entity+"]" ;
            }else{
                alert("请求错误!");
            }
        }
    })
    $('#container').highcharts({
	        title: {
	            text: '最近'+days+'天注册学员',
	            x: -20 //center
	        },
	        subtitle: {
	            text: '',
	            x: -20
	        },
	        xAxis: {
	        	categories : eval("(" + dateTime + ")")
	        },
        yAxis: [{ // left y axis
            title: {
                text: null
            },
            labels: {
                align: 'left',
                x: 3,
                y: 16,
                format: '{value:.,0f}'
            },
            showFirstLabel: false,
            min:0
        }, { // right y axis
            linkedTo: 0,
            gridLineWidth: 0,
            opposite: true,
            title: {
                text: null
            },
            labels: {
                align: 'right',
                x: -3,
                y: 16,
                format: '{value:.,0f}'
            },
            showFirstLabel: false  ,
            min:0
        }],

	        tooltip: {
	            valueSuffix: '人'
	        },
	        legend: {
	            layout: 'vertical',
	            align: 'right',
	            verticalAlign: 'middle',
	            borderWidth: 0
	        },
	        series: [{
	        	name : '注册人数',
				data : eval("(" + studentNum + ")")
	        }]
	    });
}

//订单数
function drawCartogramOrder(days) {
    var dateTime = '';
    var orderNum =  "" ;//订单数量
    var orderSuccessNum = "";//支付成功的订单数量
    var orderInitNum = "";//未支付成功的订单数量
    var orderClosedNum = "";//已取消支付的订单数量
    $.ajax({
        url:baselocation +"/admin/statistics/web/detailajax",
        type:"post",
        data:{"days":days,"type":"ORDER_NUM"},
        dataType:"json",
        async:false,
        success:function(result){
            if(result.success){
                dateTime=result.message;
                orderNum= "["+result.entity.orderNum+"]" ;
                orderSuccessNum= "["+result.entity.orderSuccessNum+"]" ;
                orderInitNum= "["+result.entity.orderInitNum+"]" ;
                orderClosedNum= "["+result.entity.orderClosedNum+"]" ;
            }else{
                alert("请求错误!");
            }
        }
    })
    $('#container').highcharts({
        title: {
            text: '最近'+days+'天订单数量',
            x: -20 //center
        },
        subtitle: {
            text: '',
            x: -20
        },
        xAxis: {
        	categories : eval("(" + dateTime + ")")
        },
        yAxis: [{ // left y axis
            title: {
                text: null
            },
            labels: {
                align: 'left',
                x: 3,
                y: 16,
                format: '{value:.,0f}'
            },
            showFirstLabel: false,
            min:0
        }, { // right y axis
            linkedTo: 0,
            gridLineWidth: 0,
            opposite: true,
            title: {
                text: null
            },
            labels: {
                align: 'right',
                x: -3,
                y: 16,
                format: '{value:.,0f}'
            },
            showFirstLabel: false  ,
            min:0
        }],

	        tooltip: {
	            valueSuffix: '条'
	        },
	        legend: {
	            layout: 'vertical',
	            align: 'right',
	            verticalAlign: 'middle',
	            borderWidth: 0
	        },
	        series: [{
	        	name : '订单数量',
				data : eval("(" + orderNum + ")")
	        },{
	        	name : '已支付订单数量',
	        	data : eval("("+orderSuccessNum+")")
	        },{
	        	name : '未支付订单数量',
	        	data : eval("("+orderInitNum+")")
	        },{
	        	name : '已取消订单数量',
	        	data : eval("("+orderClosedNum+")")
	        }]
	    });
}

//今天登录人数
function logintoday(){
 $('#container').highcharts({
        chart: {
            type: 'column'
        },
        title: {
            text: '今天活跃人数'
        },
        subtitle: {
            text: ''
        },
        xAxis: {
            type: 'category',
            labels: {
                rotation: 0,
                style: {
                    fontSize: '13px',
                    fontFamily: 'Verdana, sans-serif'
                }
            }
        },
        yAxis: {
            min: 0,
            title: {
                text: ''
            }
        },
        legend: {
            enabled: false
        },
        tooltip: {
            pointFormat: '今天活跃人数: <b>{point.y} 人</b>'
        },
        series: [{
            name: 'Population',
            data: [
                ['活跃人数', ${todayloginnum}]
            ],
            dataLabels: {
                enabled: true,
                rotation: -360,
                color: '#FFFFFF',
                align: 'right',
                x: 4,
                y: 10,
                style: {
                    fontSize: '13px',
                    fontFamily: 'Verdana, sans-serif',
                    textShadow: '0 0 3px black'
                }
            }
        }]
    });
}
//今天登录人数 ==/

//今天注册人数
function registeredday(){
	var registeredNum=0;
	$.ajax({
		url:baselocation +"/admin/todayRegisteredNum",
        type:"post",
        dataType:"json",
        async:false,
        success:function(result){
            if(result.success){
                registeredNum=result.entity;
            }else{
                alert("请求错误!");
            }
        }
	})
	$('#container').highcharts({
        chart: {
            type: 'column'
        },
        title: {
            text: '今天注册人数'
        },
        subtitle: {
            text: ''
        },
        xAxis: {
            type: 'category',
            labels: {
                rotation: 0,
                style: {
                    fontSize: '13px',
                    fontFamily: 'Verdana, sans-serif'
                }
            }
        },
        yAxis: {
            min: 0,
            title: {
                text: ''
            }
        },
        legend: {
            enabled: false
        },
        tooltip: {
            pointFormat: '今天注册人数: <b>{point.y} 人</b>'
        },
        series: [{
            name: 'Population',
            data: [
                ['注册人数', registeredNum]
            ],
            dataLabels: {
                enabled: true,
                rotation: -360,
                color: '#FFFFFF',
                align: 'right',
                x: 4,
                y: 10,
                style: {
                    fontSize: '13px',
                    fontFamily: 'Verdana, sans-serif',
                    textShadow: '0 0 3px black'
                }
            }
        }]
    });
}
//今天订单数
function orderday(){
	var orderNum =  "" ;//订单数量
    var orderSuccessNum = "";//支付成功的订单数量
    var orderInitNum = "";//未支付成功的订单数量
    var orderClosedNum = "";//已取消支付的订单数量
	$.ajax({
		url:baselocation +"/admin/todayOrderNum",
        type:"post",
        dataType:"json",
        async:false,
        success:function(result){
            if(result.success){
            	orderNum=result.entity.orderNum;
            	orderSuccessNum=result.entity.orderSuccessNum;
            	orderInitNum=result.entity.orderInitNum;
            	orderClosedNum=result.entity.orderClosedNum;
            }else{
                alert("请求错误!");
            }
        }
	})
	$('#container').highcharts({
        chart: {
            type: 'column'
        },
        title: {
            text: '今天订单数'
        },
        subtitle: {
            text: ''
        },
        xAxis: {
        	categories: [ '订单数', '已支付订单数', '未支付订单数', '已取消订单数']

        },
        yAxis: {
            min: 0,
            title: {
                text: ''
            }
        },
        legend: {
            enabled: false
        },
        tooltip: {
            pointFormat: '今天订单数: <b>{point.y} 人</b>'
        },
        series: [{data: [orderNum, orderSuccessNum, orderInitNum,orderClosedNum]}]
    });
}
function xuanze(str){
    if(str=='REGISTERED_NUM'){
		$("#countName").html("学员注册数走势");
		$("#day").attr("onclick","registeredday()");
		$("#day7").attr("onclick","drawCartogramRegistered(7)");
		$("#day30").attr("onclick","drawCartogramRegistered(30)");
		drawCartogramRegistered(15);
	}else{
		$("#countName").html("学员活跃度走势");
		$("#day").attr("onclick","logintoday()");
		$("#day7").attr("onclick","drawCartogramLogin(7)");
		$("#day30").attr("onclick","drawCartogramLogin(30)");
		drawCartogramLogin(15);
	}
}
</script>
</head>
<body style="background: none;">
	<div class="rMain" style="margin-top: 8px">
		<h1>
			欢迎你,
			<span>超级管理员</span>
			!
		</h1>
		<p>点击快捷入口即可管理操作！</p>

		<div class="">
			<!-- Big buttons -->
			<ul class="dash">
				<li>
					<a class="tooltip" title="新增课程" href="${ctx}/admin/cou/toAddCourse">
						<img alt="" src="${ctx}/static/admin/assets/icons/4_48x48.png">
						<span>新增课程</span>
					</a>
				</li>
				<li>
					<a href="${ctx}/admin/article/initcreate" target="content" title="新增文章" class="tooltip">
						<img src="${ctx}/static/admin/assets/icons/8_48x48.png" alt="" />
						<span>新增文章</span>
					</a>
				</li>
				<li>
					<a href="${ctx}/admin/article/showlist" target="content" title="文章列表" class="tooltip">
						<img src="${ctx}/static/admin/assets/icons/7_48x48.png" alt="" />
						<span>文章列表</span>
					</a>
				</li>
				<li>
					<a href="${ctx}/admin/sysuser/userlist" title="管理用户" target="content" class="tooltip">
						<img src="${ctx}/static/admin/assets/icons/16_48x48.png" alt="" />
						<span>管理用户</span>
					</a>
				</li>
				<li>
					<a href="${ctx}/admin/website/imagesPage" title="媒体图片管理" class="tooltip">
						<img src="${ctx}/static/admin/assets/icons/20_48x48.png" alt="" />
						<span>媒体图片管理</span>
					</a>
				</li>
			</ul>
			<!-- End of Big buttons -->
		</div>
		<br/><br/><br/><br/><br/><br/>
		<span>
            <b>全站在线人数:</b> <%=MySessionListener.num%>&nbsp;
			<b>文章数:</b> ${webCountMap.articleCount}&nbsp;
			<b>课程数:</b> ${webCountMap.courseCount}&nbsp;
			<b>用户数:</b> ${webCountMap.userCount}&nbsp;
			<b>问答数:</b> ${webCountMap.questionsCount}&nbsp;
		</span>
		<fieldset>
			<legend>
				<span>网站统计</span>
				&gt;
				<span id="countName">学员活跃度走势</span>
			</legend>
            <span onclick=""><button onclick="xuanze('LOGIN_NUM')" class="ui-state-default ui-corner-all" type="button" style="float: left">学员活跃度走势</button></span>
            <span onclick=""><button onclick="xuanze('REGISTERED_NUM')" class="ui-state-default ui-corner-all" type="button" style="float: left">学员注册数走势</button></span>
			<div class="mt20">
				<div class="commonWrap">
					<div>
						<div class="mt20">
							<div class="commonWrap">
								<table cellspacing="0" cellpadding="0" border="0" width="100%" class="commonTab01">
									<caption><span><input type="button" onclick="logintoday()" id="day" value="今天" class="btn btn-y ml10">
										<input type="button" onclick="drawCartogramLogin(7)" id="day7" value="最近7天" class="btn btn-y ml10">
										 <input type="button" onclick="drawCartogramLogin(30)" id="day30" value="最近30天" class="btn btn-y ml10">
										</span>
									</caption>
									<tbody >
									<tr align="center">
										<td>
											<div id="container" style="max-width: 85%;margin: auto;height: 260px;"></div>
										</td>
									</tr>
									</tbody>
								</table>
							</div><!-- /commonWrap -->
						</div>
						<div class="page_head">
							<h4></h4>
						</div>
					</div>
				</div>
			</div>
		</fieldset>
	</div>
</body>
</html>