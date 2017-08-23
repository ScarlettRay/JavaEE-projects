<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>图片类型管理</title>
<script type="text/javascript">

	//修改图片类型名
	function updateType(typeId,em){
		var td = $(em).parent("td").parent("tr").children('td')[1];
		var typeName = $(td).text();
		$(td).html('<input style="width: 110px;" type="text" value="'+typeName+'"/>');
		var input = $(td).children('input')[0];
		$(input).focus();
		$(input).blur(function(){
			var newName = $(this).val();
			if(newName==null || $.trim(newName)==''){
				newName=typeName;
			}
			$.ajax({
				url:'${ctx}/admin/imagetype/updateType',
				type:'post',
				dataType:'json',
				data:{'type.typeName':newName,'type.typeId':typeId},
				success:function(result){
					if(result.success==false){
						alert(result.message);
						$(td).text(typeName);
					}else{
						$(td).text(newName);
					}
				},
				error:function(error){
					alert('系统繁忙，请稍后再操作！');
				}
			});
		});
	}
	
	//删除图片类型
	function deleteType(typeId){
		if(confirm('确认要删除该图片类型？')){
			document.location='${ctx}/admin/imagetype/deletetype/'+typeId;
		}
	}
</script>
</head>
<body>
	<div class="">
		<a title="创建类型" class="button tooltip" href="${ctx}/admin/imagetype/addtype">
			<span class="ui-icon ui-icon-newwin"></span>
			创建类型
		</a>
		<table cellspacing="0" cellpadding="0" border="0" class="fullwidth" width="100%">
			<thead>
				<tr>
					<td align="center" width="100">类型ID</td>
					<td align="center">类型名称</td>
					<td align="center" width="244">操作</td>
				</tr>
			</thead>

			<tbody>
				<c:choose>
					<c:when test="${typeList!=null && typeList.size()>0}">
						<c:forEach items="${typeList}" var="type" varStatus="index">
							<tr <c:if test="${index.count%2==1 }">class="odd"</c:if>>
								<td align="center">${type.typeId}</td>
								<td align="center">${type.typeName}</td>
								<td align="center">
									<%--<a href="javascript:void(0)" onclick="deleteType(${type.typeId})" class="button tooltip">删除</a>
									<a href="javascript:void(0)" onclick="updateType(${type.typeId},this)" class="button tooltip">修改名称</a>--%>

									<button onclick="window.location.href='${ctx}/admin/website/imagesPage?websiteImages.typeId=${type.typeId}'" class="ui-state-default ui-corner-all" type="button">查看广告图</button>
									<button onclick="updateType(${type.typeId},this)" class="ui-state-default ui-corner-all" type="button">修改名称</button>
									<button onclick="deleteType(${type.typeId})" class="ui-state-default ui-corner-all" type="button">删除</button>
								</td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr class="odd">
							<td align="center" colspan="6">
								<div class="tips">
									<span>还没有创建图片类型！</span>
								</div>
							</td>
						</tr>
					</c:otherwise>
				</c:choose>
			</tbody>
		</table>
	</div>
</body>
</html>