<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>推荐课程分类列表</title>
<script type="text/javascript">
    //删除分类
	function del(id){
		if(confirm("确定删除该分类吗？")){
			document.location="${ctx}/admin/website/delWebsiteCourseById/"+id;
		}
	}
</script>
</head>
<body>
	<!-- 内容 开始  -->
	<!-- /tab1 begin-->
	<div class="mt20">
		<div class="commonWrap">
			<a href="${ctx}/admin/website/doAddWebsiteCourse" class="button tooltip" title="添加">
				<span class="ui-icon ui-icon-newwin"></span>
				添加
			</a>
			<table cellspacing="0" cellpadding="0" border="0" width="100%" class="fullwidth" width="100%">
				<thead>
					<tr>
						<td width="10%">
							<span>ID</span>
						</td>
						<td>
							<span>分类名称</span>
						</td>
						<td>
							<span>更多跳转</span>
						</td>
						<td>
							<span>课程数量</span>
						</td>
						<td>
							<span>描述</span>
						</td>
						<td>
							<span>操作</span>
						</td>
					</tr>
				</thead>
				<tbody id="tabS_02" align="center">
					<c:if test="${websiteCourseList.size()>0}">
						<c:forEach items="${websiteCourseList}" var="websiteCourse" varStatus="index">
							<tr <c:if test="${index.count%2==1 }">class="odd"</c:if>>
								<td>${websiteCourse.id }</td>
								<td>${websiteCourse.name }</td>
								<td>${websiteCourse.link }</td>
								<td>${websiteCourse.courseNum }</td>
								<td>
									<c:if test="${websiteCourse.description.length()<30}">
							  		${websiteCourse.description}
			 					</c:if>
									<c:if test="${websiteCourse.description.length()>30}">
			 						${fn:substring(websiteCourse.description,0,30)}…
			 					</c:if>
								</td>
								<td class="c_666 czBtn" align="center">
									<button type="button" class="ui-state-default ui-corner-all"
										onclick="window.location.href='${ctx}/admin/website/doUpdateWebsiteCourse/${websiteCourse.id}'">修改</button>
									<button type="button" class="ui-state-default ui-corner-all" onclick="javascript:del(${websiteCourse.id})">删除</button>
								</td>
							</tr>
						</c:forEach>
					</c:if>
					<c:if test="${websiteCourseList.size()==0||websiteCourseList==null}">
						<tr>
							<td align="center" colspan="16">
								<div class="tips">
									<span>还没有推荐课程分类！</span>
								</div>
							</td>
						</tr>
					</c:if>
				</tbody>
			</table>
		</div>
		<!-- /commonWrap -->
	</div>
</body>
</html>