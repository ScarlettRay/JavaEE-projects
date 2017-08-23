<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>图片列表</title>
<script type="text/javascript">
	$(function(){
		$("#selectAll").click(function(){
			$("input[name='imageId']").attr('checked',$(this).attr('checked'));
		});
		$("input[name='imageId']").click(function(){
			var allarr = $("input[name='imageId']");
			var selectarr = $("input[name='imageId']:checked");
			if(selectarr!=null && allarr!=null && allarr.length==selectarr.length){
				$("#selectAll").attr('checked',true);
			}else{
				$("#selectAll").attr('checked',false);
			}
		});
	});
	
	//批量删除删除图片
	function deleteImage(){
		var selectarr = $("input[name='imageId']:checked");
		if(selectarr==null || selectarr.length==0){
			alert("选择要删除的图片");
			return false;
		}
		if(confirm('确认要删除选择中的图片？')){
			$("#deleteForm").submit();
		}
	}
	
	//单条记录删除
	function deleteImgThis(em){
		 $("input[name='imageId']").attr('checked',false);
		var input = $($(em).parent("td").parent("tr").children('td')[0]).children('input')[0];
		$(input).attr('checked',true);
		if(confirm('确认要删除该图片！')){
			$("#deleteForm").submit();
		}
	}
</script>
</head>
<body>
	<div class="">
		<form action="${ctx}/admin/website/imagesPage" method="post" id="searchForm">
			<input type="hidden" id="pageCurrentPage" name="page.currentPage" value="1" />
			<input placeholder="图片标题" type="text" name="websiteImages.title" value="${websiteImages.title}" />
			<select name="websiteImages.typeId">
				<option value="0">请选类型</option>
				<c:forEach items="${typeList}" var="type">
					<option <c:if test="${websiteImages.typeId==type.typeId}">selected</c:if> value="${type.typeId}">${type.typeName}</option>
				</c:forEach>
			</select>
			<a title="查找" onclick="javascript:$('#searchForm').submit();" class="button tooltip" href="javascript:void(0)">
				<span class="ui-icon ui-icon-search"></span>
				查找
			</a>
			<a title="清空" onclick="javascript:$('#searchForm input:text').val('');$('#searchForm select').val(0);" class="button tooltip"
				href="javascript:void(0)">
				<span class="ui-icon ui-icon-cancel"></span>
				清空
			</a>

			<a title="批量删除" onclick="deleteImage()" class="button tooltip" href="javascript:void(0)">
				<span></span>
				批量删除
			</a>
			<a title="新建图片" class="button tooltip" href="${ctx}/admin/website/doAddImages">
				<span class="ui-icon ui-icon-newwin"></span>
				新建图片
			</a>
		</form>
		<form action="${ctx}/admin/website/delImages" id="deleteForm" method="post">
			<table cellspacing="0" cellpadding="0" border="0" class="fullwidth" width="100%">
				<thead>
					<tr>
						<td align="center">
							<input name="allck" type="checkbox" id="selectAll" />
						</td>
						<td align="center">标题</td>
						<td align="center">图片URL</td>
						<td align="center">链接URL</td>
						<td align="center">类型</td>
						<td align="center">序列号</td>
						<td align="center" width="232">操作</td>
					</tr>
				</thead>

				<tbody>
					<c:forEach items="${websiteImagesList}" var="image" varStatus="index">
						<tr <c:if test="${index.count%2==1 }">class="odd"</c:if>>
							<td align="center">
								<input type="checkbox" name="imageId" value="${image.imageId}" />
							</td>
							<td align="center">${image.title}</td>
							<td align="center">${image.imagesUrl}</td>
							<td align="center">${image.linkAddress}</td>
							<td align="center">${image.typeName}</td>
							<td align="center">${image.seriesNumber}</td>
							<td align="center">
								<button onclick="window.open('<%=staticImage%>${image.imagesUrl}')" target="_blank" class="ui-state-default ui-corner-all" type="button">预览</button>
								<button onclick="deleteImgThis(this)" class="ui-state-default ui-corner-all" type="button">删除</button>
								<button onclick="window.location.href='${ctx}/admin/website/doUpdateImages/${image.imageId}'" class="ui-state-default ui-corner-all" type="button">修改</button>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</form>
		<jsp:include page="/WEB-INF/view/common/admin_page.jsp" />
	</div>
</body>
</html>