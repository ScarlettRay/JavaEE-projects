<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>统计图</title>


	<script src="${ctximg}/static/common/jquery-ui-1.10.4/js/jquery-ui-1.10.4.custom.js"></script>
	<script src="${ctximg}/static/common/jquery-ui-1.10.4/js/jquery.ui.datepicker-zh-CN.js"></script>
	<script type="text/javascript" src="${ctximg}/static/common/jquery-ui-1.10.4/js/jquery-ui-timepicker-addon.js"></script>
	<script type="text/javascript" src="${ctximg}/static/common/jquery-ui-1.10.4/js/jquery-ui-timepicker-zh-CN.js"></script>


	<script type="text/javascript" src="${ctximg}/static/admin/js/highChart/highcharts.js"></script>
	<script type="text/javascript" src="${ctximg}/static/admin/js/highChart/highcharts-3d.js"></script>


	<script type="text/javascript">
		var rotationVal=0;
		$(function() {
			var curDate=new Date();
			startDate=new Date(curDate.getTime()-24*60*60*1000*7);
			$("#startDate").val(startDate.getFullYear()+"-"+(startDate.getMonth()+1)+"-"+startDate.getDate());
			$("#endDate").val(curDate.getFullYear()+"-"+(curDate.getMonth()+1)+"-"+curDate.getDate());

			$("#startDate,#endDate").datepicker({
				regional:"zh-CN",
				changeMonth: true
			});
			statisticalChart();
		});

		//查询时间条件
		function time(day){
			var startDate="";
			var endDate="";
			var tian="";
			if(day!=null&&$.trim(day)!=''&&day>0){
				//当前时间
				var curDate=new Date();
				//开始时间
				startDate=new Date(curDate.getTime()-24*60*60*1000*day);
				//结束时间
				endDate=curDate;
				//格式化时间
				startDate=startDate.getFullYear()+"-"+(startDate.getMonth()+1)+"-"+startDate.getDate();
				endDate=curDate.getFullYear()+"-"+(curDate.getMonth()+1)+"-"+curDate.getDate();
			}else{
				startDate=$("#startDate").val();
				endDate=$("#endDate").val();
			}
			var startTime = new Date(Date.parse(startDate.replace(/-/g,   "/"))).getTime();
			var endTime = new Date(Date.parse(endDate.replace(/-/g,   "/"))).getTime();
			var tian= Math.abs((startTime - endTime))/(1000*60*60*24);
			return {"startDate":startDate,"endDate":endDate,"tian":tian}
		}

		function statisticalChart(day){
			if(time(day).tian>=14){
				rotationVal=90;
			}else{
				rotationVal=0;
			}
			var url="/admin/ajax/statistics/"+$("#type").val();
			var startDate=time(day).startDate;
			var endDate=time(day).endDate;
			$.ajax({
				url:baselocation+url,
				type:"post",
				data:{
					"startDate":startDate,
					"endDate":endDate,
					"type":$("#type").val()
				},
				dataType:"text",
				async:false,
				success:function(result){
					$("#chart").html(result);
				}
			})
		}
	</script>
</head>
<body>
<form action="${ctx }/admin/statistics/login" method="post">
	<div>
		<select id="type">
			<option <c:if test="${type == 'login' }">selected="selected"</c:if> value="login" onclick="statisticalChart()">学员登录数统计</option>
			<option <c:if test="${type == 'registered' }">selected="selected"</c:if> value="registered" onclick="statisticalChart()">学员注册数统计</option>
			<option <c:if test="${type == 'videoViewingNum' }">selected="selected"</c:if> value="videoViewingNum" onclick="statisticalChart()">视频观看数统计</option>
			<option <c:if test="${type == 'dailyUserNumber' }">selected="selected"</c:if> value="dailyUserNumber" onclick="statisticalChart()">每日学员数统计</option>
			<option <c:if test="${type == 'dailyCourseNumber' }">selected="selected"</c:if> value="dailyCourseNumber" onclick="statisticalChart()">每日课程数统计</option>
		</select>
		<input id="day7" type="button" onclick="statisticalChart(7)" class="button" value="近7天" />
		<input id="day15" type="button" onclick="statisticalChart(15)" class="button" value="近15天" />
		<input id="day30" type="button" onclick="statisticalChart(30)" class="button" value="近30天" />
		时间：
		<input type="text" id="startDate" name="startDate" value="${startDate }"/>
		<b>-</b>&nbsp;&nbsp;&nbsp;
		<input type="text" id="endDate" name="endDate" value="${endDate }"/>
		<input id="query" type="button" onclick="statisticalChart()" class="button" value="查询" />
	</div>
	<div class="commonWrap">
		<table cellspacing="0" cellpadding="0" border="0" width="100%" class="commonTab01">
			<tbody >
			<tr align="center">
				<td id="chart">


				</td>
			</tr>
			</tbody>
		</table>
	</div><!-- /commonWrap -->
</form>
</body>
</html>