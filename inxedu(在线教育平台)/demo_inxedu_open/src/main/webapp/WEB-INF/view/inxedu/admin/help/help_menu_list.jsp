<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<title></title>
<script type="text/javascript">

function showMenu(id)
{
	var title=$("#"+id).attr("title");
	if(title=="展开")
	{
		$("#"+id).attr("title","收起");
		$("#"+id).html("-&nbsp;&nbsp;");
		$('tr[name="'+id+'s"]').show();
	}
	else
	{
		$("#"+id).attr("title","展开");
		$("#"+id).html("+&nbsp;&nbsp;");
		$('tr[name="'+id+'s"]').hide();
	}
	
}
function longMenu(id)
{
	
	$("#"+id).attr("onclick","shortMenu('"+id+"')");
	$("#"+id).html("-&nbsp;&nbsp;");
	
}
function doDel(id)
{
	if(confirm("确认删除？")){
		$.ajax({
			url:"${ctx}/admin/helpMenu/del/"+id,  
			type: "post",
			dataType:"json",
			success:function(data){
				if(data.message=='true'){
					alert("删除菜单成功");
					window.location.reload();
				}
			}
		});
	}
		
}
</script>
</head>
<body>
<div class="">
	<!-- /tab1 begin-->
	<div class="mt20">
		<div class="commonWrap">
			<form action="${ctx}/admin/website/navigates" name="searchForm" id="searchForm" method="post">
			<table cellspacing="0" cellpadding="0" border="0" class="fullwidth" width="100%">
				<caption>
					<div class="capHead">
						<div class="clearfix">
							<div class="optionList">
								<font color="red">
						1.一级菜单下有子菜单时，该菜单不可删除。<br/>
					    2.在编辑中，二级菜单可以移动至其它一级菜单下。 </frot>
							</div>
						</div>
					</div>
				</caption>
				<thead>
					<tr>
						<th><span>名称</span></th>
                        <th><span>排序</span></th>
                        <th><span>操作</span></th>
					</tr>
				</thead>
				<tbody id="tabS_02" align="center">
					<c:if test="${helpMenus.size()>0}">
						<c:forEach items="${helpMenus}" var="Menus" varStatus="status">
							<c:forEach items="${Menus}" var="Menus1" varStatus="status1">
								<c:choose>
									<c:when test="${Menus1.parentId==0}">
										<tr>
											<td style="background-color:#f3f3f3" width="30%" align="left">
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												<span id="${status.index}id" style="cursor: pointer;" onclick="showMenu('${status.index}id')" title="收起">
													-&nbsp;&nbsp;
												</span>
												${Menus1.name}<font style="color: #999">（${fn:length(Menus)-1}个子菜单）</font>
											</td>
											<td style="background-color:#f3f3f3;color:#6e6e6e">${Menus1.sort}</td>
											<td  class="c_666 czBtn" align="center" width="30%" style="background-color:#f3f3f3;">
												<a class="button tooltip" href="${ctx}/admin/helpMenu/doupdate/${Menus1.id}">编辑</a>
												<c:choose>
													<c:when test="${fn:length(Menus)==1}">
														<a class="button tooltip" href="javascript:doDel(${Menus1.id})">删除</a>
       												</c:when>
													<c:otherwise>
														<a class="button tooltip" href="javascript:void(0)" style='background: #ffffff url("../assets/input.png") repeat-x scroll 0 0;'>删除</a>
													</c:otherwise>
												</c:choose>
											</td>
										</tr>
									</c:when>
									<c:otherwise>
										<tr name="${status.index}ids">
											<td align="left" style="padding-left: 40px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${Menus1.name}</td>
											<td>${Menus1.sort}</td>
											<td>
												<a class="button tooltip" href="${ctx}/admin/helpMenu/doupdate/${Menus1.id}">编辑</a>
												<a class="button tooltip" href="javascript:doDel(${Menus1.id})">删除</a>
											</td>
										</tr>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</c:forEach>
					</c:if>
					<c:if test="${helpMenus.size()==0||helpMenus==null}">
					<tr>
						<td align="center" colspan="16">
							<div class="tips">
							<span>还没有菜单！</span>
							</div>
						</td>
					</tr>
					</c:if>
				</tbody>
			</table>
			</form>
		</div><!-- /commonWrap -->
	</div>
</div>
</body>
</html>