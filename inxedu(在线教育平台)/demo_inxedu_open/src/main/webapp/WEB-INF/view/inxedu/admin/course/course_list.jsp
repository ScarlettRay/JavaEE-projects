<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>课程列表</title>
<script type="text/javascript" src="${ctx}/static/common/multilevel.js"></script>

<script src="${ctximg}/static/common/jquery-ui-1.10.4/js/jquery-ui-1.10.4.custom.js?v=${v}"></script>
<script src="${ctximg}/static/common/jquery-ui-1.10.4/js/jquery.ui.datepicker-zh-CN.js?v=${v}"></script>
<script type="text/javascript" src="${ctximg}/static/common/jquery-ui-1.10.4/js/jquery-ui-timepicker-addon.js?v=${v}"></script>
<script type="text/javascript" src="${ctximg}/static/common/jquery-ui-1.10.4/js/jquery-ui-timepicker-zh-CN.js?v=${v}"></script>

<script type="text/javascript">
var subjectList = eval('('+'${subjectList}'+')');
$(function(){
	var param={ 
			data:subjectList,	//处理的数据（必选）数据格式：[{object Object},{object Object}]  
			showId:'levelId',//显示的数据标签ID（必选）
			idKey:'subjectId',//数据的ID（必选）
			pidKey:'parentId',//数据的父ID（必选）
			nameKey:'subjectName',//数据显示的名（必选）
			returnElement:'subjectId',//返回选中的值（必选 ）
			//-----------------------------------------------------
			initVal:${queryCourse.subjectId},
			defName:'请选择',//默认显示的选项名（可选，如果不设置默认显示“请选择”）
			defValue:'0'//默认的选项值（可选，如果不设置默认是“0”）
		};
	ML._init(param);
	/* 时间控件 */
	$("#beginCreateTime,#endCreateTime").datetimepicker({
		regional:"zh-CN",
        changeMonth: true,
        dateFormat:"yy-mm-dd",
        timeFormat: "HH:mm:ss"
    });
});

/**
 * 删除课程 
 */
function avaliable(courseId,type,em){
	if(!confirm('确实要删除吗?')){
		return;
	}
	$.ajax({
		url:baselocation +'/admin/cou/avaliable/'+courseId+'/'+type,
		type:'post',
		dataType:'json',
		success:function(result){
			if(result.success==false){
				alert(result.message);
			}else{
				location.reload();
			}
		},
		error:function(error){
			alert("系统繁忙，请稍后再操作！");
		}
	});
}
</script>
</head>
<body>
	<div class="">
		<form action="${ctx}/admin/cou/list" method="post" id="searchForm">
			<input type="hidden" id="pageCurrentPage" name="page.currentPage" value="1" />
			<input type="text" name="queryCourse.courseName" value="${queryCourse.courseName}" placeholder="课程标题" />
			 状态:<select name="queryCourse.isavaliable">
				<option value="0">请选择</option>
				<option <c:if test="${queryCourse.isavaliable==1 }">selected</c:if> value="1">上架</option>
				<option <c:if test="${queryCourse.isavaliable==2 }">selected</c:if> value="2">下架</option>
			</select>
			<input type="hidden" id="subjectId" name="queryCourse.subjectId" value="${queryCourse.subjectId}" />
			专业:
			<samp id="levelId"></samp>
			创建时间:
			<input placeholder="开始创建时间" name="queryCourse.beginCreateTime"
				value="<fmt:formatDate value="${queryCourse.beginCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>" id="beginCreateTime" type="text"
				readonly="readonly" style="width: 128px;"/>-
			<input placeholder="结束创建时间" id="endCreateTime" name="queryCourse.endCreateTime"
				value="<fmt:formatDate value="${queryCourse.endCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>" type="text" readonly="readonly" style="width: 128px;"/>
			<a title="查找课程" onclick="$('#searchForm').submit();" class="button tooltip" href="javascript:void(0)">
				<span class="ui-icon ui-icon-search"></span>
				查找课程
			</a>
			<a title="清空" onclick="$('#searchForm input:text').val('');$('#searchForm select').val(0);$('select').change();"
				class="button tooltip" href="javascript:void(0)">
				<span class="ui-icon ui-icon-cancel"></span>
				清空
			</a>
			<a title="创建课程" class="button tooltip" href="${ctx}/admin/cou/toAddCourse">
				<span></span>
				创建课程
			</a>
		</form>
		<table cellspacing="0" cellpadding="0" border="0" class="fullwidth" width="100%">
			<thead>
				<tr>
					<td align="center" width="150px">课程名</td>
					<td align="center">状态</td>
					<td align="center">专业</td>
					<td align="center">原价</td>
					<td align="center">优惠价</td>
					<td align="center">课时</td>
					<td align="center">销售量</td>
					<td align="center">浏览量</td>
					<td align="center">创建时间</td>
					<td align="center">有效结束时间</td>
					<td align="center">操作</td>
				</tr>
			</thead>

			<tbody>
				<c:forEach items="${courseList}" var="course" varStatus="index">
					<tr <c:if test="${index.count%2==1 }">class="odd"</c:if>>
						<td align="center">${course.courseName}</td>
						<td align="center">
							<c:if test="${course.isavaliable==1}">上架</c:if>
							<c:if test="${course.isavaliable==2}">下架</c:if>
						</td>
						<td align="center">${course.subjectName}</td>
						<td align="center">${course.sourcePrice}</td>
						<td align="center">${course.currentPrice}</td>
						<td align="center">${course.lessionNum}</td>
						<td align="center">${course.pageBuycount}</td>
						<td align="center">${course.pageViewcount}</td>
						<td align="center">
							<fmt:formatDate value="${course.addTime}" pattern="yyyy/MM/dd HH:mm" />
						</td>
						<td align="center">
							<c:if test="${not empty course.endTime}">
								<fmt:formatDate value="${course.endTime}" pattern="yyyy/MM/dd HH:mm" />
							</c:if>
							<c:if test="${empty course.endTime}">
								购买后${course.loseTime}天
							</c:if>
						</td>
						<td align="center">
							<button onclick="avaliable(${course.courseId},3,this)" class="ui-state-default ui-corner-all" type="button">删除</button>
							<button onclick="window.location.href='${ctx}/admin/cou/initUpdate/${course.courseId}'" class="ui-state-default ui-corner-all" type="button">修改</button>
							<button onclick="window.location.href='${ctx}/admin/kpoint/list/${course.courseId}'" class="ui-state-default ui-corner-all" type="button">章节管理</button>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<jsp:include page="/WEB-INF/view/common/admin_page.jsp" />
	</div>
</body>
</html>