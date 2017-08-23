<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>推荐课程列表</title>
<script type="text/javascript" src="${ctx}/static/common/multilevel.js"></script>
<script type="text/javascript">
var subjectList = eval('('+'${subjectList}'+')');
$(function(){
	$("#selectAll").click(function(){
		$("input[name='courseId']").attr('checked',$(this).attr('checked'));
	});
	$("input[name='courseId']").click(function(){
		var allarr = $("input[name='courseId']");
		var selectarr = $("input[name='courseId']:checked");
		if(selectarr!=null && allarr!=null && allarr.length==selectarr.length){
			$("#selectAll").attr('checked',true);
		}else{
			$("#selectAll").attr('checked',false);
		}
	});
	
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
});

/**
 * 关闭窗口
 */
function closeWin(){
	window.close();
}

//确认选择
function confirmSelect(){
	var courseArr = $("input[name='courseId']:checked");
	if(courseArr==null || courseArr.length==0){
		alert("请选择课程！");
		return false;
	}
	var recommendId = $("select[name='recommend']").val();
	if(recommendId<=0){
		alert("请选择推荐类型！");
		return false;
	}
	var courseIds = ',';
	for(var i=0;i<courseArr.length;i++){
		courseIds+=courseArr[i].value+",";
	}
	$.ajax({
		url:baselocation +'/admin/detail/addrecommendCourse',
		type:'post',
		dataType:'json',
		data:{'courseIds':courseIds,"recommendId":recommendId},
		success:function(result){
			if(result.success==false){
				alert(result.message);
			}else{
				window.opener.refurbishPage(result.entity);
				closeWin();
			}
		},
		error:function(eror){
			alert("系统繁忙，请稍后再操作！")
		}
	});
}
</script>
</head>
<body>
	<div class="">
		<form action="${ctx}/admin/cou/showrecommendList" method="post" id="searchForm">
			<input type="hidden" id="pageCurrentPage" name="page.currentPage" value="1" />
			<input type="text" name="queryCourse.courseName" value="${queryCourse.courseName}" placeholder="课程标题" />
			<input type="hidden" id="subjectId" name="queryCourse.subjectId" value="${queryCourse.subjectId}" />
			专业:
			<samp id="levelId"></samp>
			状态:<select name="queryCourse.isavaliable">
				<option value="0">请选择</option>
				<option <c:if test="${queryCourse.isavaliable==1 }">selected</c:if> value="1">上架</option>
				<option <c:if test="${queryCourse.isavaliable==2 }">selected</c:if> value="2">下架</option>
			</select>
			<a title="查找用户" onclick="javascript:$('#searchForm').submit();" class="button tooltip" href="javascript:void(0)">
				<span class="ui-icon ui-icon-search"></span>
				查找课程
			</a>
			<a title="清空" onclick="javascript:$('#searchForm input:text').val('');$('#searchForm select').val(0);$('select').change();"
				class="button tooltip" href="javascript:void(0)">
				<span class="ui-icon ui-icon-cancel"></span>
				清空
			</a>
		</form>
		<table cellspacing="0" cellpadding="0" border="0" class="fullwidth" width="100%">
			<thead>
				<tr>
					<td align="center">
						<input type="checkbox" id="selectAll" />
					</td>
					<td align="center">课程名</td>
					<td align="center">专业</td>
					<td align="center">状态</td>
					<%--<td align="center">创建时间</td>--%>
					<th align="center">有效期</th>
					<td align="center">原价</td>
					<td align="center">优惠价</td>
					<td align="center">课时</td>
				</tr>
			</thead>

			<tbody>
				<c:forEach items="${courseList}" var="course" varStatus="index">
					<tr <c:if test="${index.count%2==1 }">class="odd"</c:if>>
						<td align="center">
							<input type="checkbox" value="${course.courseId}" name="courseId" />
						</td>
						<td align="center">${course.courseName}</td>
						<td align="center">${course.subjectName}</td>
						<td align="center">
							<c:if test="${course.isavaliable==1}">上架</c:if>
							<c:if test="${course.isavaliable==2}">下架</c:if>
						</td>
						<%--<td align="center">
							<fmt:formatDate value="${course.addTime}" pattern="dd/MM/yyyy HH:mm:ss" />
						</td>--%>
						<td align="center">
							<c:if test="${not empty course.endTime}">
								<fmt:formatDate value="${course.endTime}" pattern="yyyy/MM/dd HH:mm"/>
							</c:if>
							<c:if test="${empty course.endTime}">
								购买后${course.loseTime}天
							</c:if>
						</td>
						<td align="center">${course.sourcePrice}</td>
						<td align="center">${course.currentPrice}</td>
						<td align="center">${course.lessionNum}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<jsp:include page="/WEB-INF/view/common/admin_page.jsp" />
	</div>
	<div style="text-align: center;">
		推荐类型:<select name="recommend">
			<option value="0">请选择类型</option>
			<c:forEach items="${webstieList}" var="list">
				<option value="${list.id }">${list.name}</option>
			</c:forEach>
		</select>
		<a title="确认" onclick="confirmSelect()" class="button tooltip" href="javascript:void(0)">
			<span></span>
			确认
		</a>
		<a title="关闭" onclick="closeWin()" class="button tooltip" href="javascript:void(0)">
			<span class="ui-icon ui-icon-cancel"></span>
			关闭
		</a>
	</div>
</body>
</html>