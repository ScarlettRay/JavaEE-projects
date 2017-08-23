<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>导航管理</title>
<script type="text/javascript">
	function delNavigate(id){
		if(confirm("真的要删除吗？")==true){
			$.ajax({
				url:"${ctx}/admin/website/delNavigate/"+id,
				type:"post",
				dataType:"json",
				success:function(result){
					if(result.message=="true"){
						alert("删除成功");
						window.location.reload();
					}
				}
			});
		}
	} 
	//冻结或解冻导航
	function freezeNavigate(id,status){
		$.ajax({
			url:"${ctx}/admin/website/freezeNavigate",
			type:"post",
			data:{"websiteNavigate.id":id,"websiteNavigate.status":status},
			dataType:"json",
			success:function(result){
				if(result.message=="true"){
					if(status==0){
						alert("解冻成功");
					}else{
						alert("冻结成功");
					}
					window.location.reload();
				}
			}
		});
	}
</script>
</head>
<body>
	<!-- /tab1 begin-->
	<div class="mt20">
		<div class="commonWrap">
			<form action="${ctx}/admin/website/navigates" name="searchForm" id="searchForm" method="post">
				<table cellspacing="0" cellpadding="0" border="0" width="100%" class="fullwidth" width="100%">
					<caption>
						<div class="mt10 clearfix">
							<p class="fl c_666">
								导航类型： <select name="websiteNavigate.type" id="type">
									<option value="">请选择</option>
									<option value="INDEX" <c:if test="${websiteNavigate.type=='INDEX'}">selected="selected"</c:if>>首页</option>
									<option value="FRIENDLINK" <c:if test="${websiteNavigate.type=='FRINEDLINK'}">selected="selected"</c:if>>尾部友链</option>
									<option value="TAB" <c:if test="${websiteNavigate.type=='TAB'}">selected="selected"</c:if>>尾部标签</option>
								</select>
								<input type="submit" class="button" value="查询" />
								<input type="button" class="button" value="清空" onclick="$('#type').val('');" />
								<input type="button" class="button" value="添加" onclick="window.location.href='${ctx}/admin/website/doAddNavigates'" />
							</p>
						</div>
					</caption>
					<thead  align="center">
						<tr>
							<td>
								<span>名称</span>
							</td>
							<td>
								<span>跳转地址</span>
							</td>
							<td>
								<span>在新页面打开</span>
							</td>
							<td>
								<span>类型</span>
							</td>
							<td>
								<span>排序（由大到小显示）</span>
							</td>
							<td>
								<span>状态</span>
							</td>
							<td>
								<span>操作</span>
							</td>
						</tr>
					</thead>
					<tbody id="tabS_02" align="center">
						<c:if test="${websiteNavigates.size()>0}">
							<c:forEach items="${websiteNavigates}" var="navigate" varStatus="index">
								<tr <c:if test="${index.count%2==1 }">class="odd"</c:if>>
									<td>${navigate.name }</td>
									<td>${navigate.url }</td>
									<td>
										<c:if test="${navigate.newPage==0 }">是</c:if>
										<c:if test="${navigate.newPage==1 }">否</c:if>
									</td>
									<td>
										<c:if test="${navigate.type=='INDEX' }">首页</c:if>
										<c:if test="${navigate.type=='USER' }">个人中心</c:if>
										<c:if test="${navigate.type=='SNS' }">社区</c:if>
										<c:if test="${navigate.type=='FRIENDLINK' }">尾部友链</c:if>
										<c:if test="${navigate.type=='TAB' }">尾部标签</c:if>
									</td>
									<td>${navigate.orderNum }</td>
									<td>
										<c:if test="${navigate.status==0 }">正常</c:if>
										<c:if test="${navigate.status==1 }">冻结</c:if>
									</td>
									<td class="c_666 czBtn" align="center">
										<button type="button" class="ui-state-default ui-corner-all"
											onclick="window.location.href='${ctx}/admin/website/doUpdateNavigate/${navigate.id}'">修改</button>
										<button type="button" class="ui-state-default ui-corner-all" onclick="delNavigate(${navigate.id})">删除</button>
										<c:if test="${navigate.status==0}">
											<button type="button" class="ui-state-default ui-corner-all" onclick="freezeNavigate(${navigate.id},1)">冻结</button>
										</c:if>
										<c:if test="${navigate.status==1}">
											<button type="button" class="ui-state-default ui-corner-all" onclick="freezeNavigate(${navigate.id},0)">解冻</button>
										</c:if>
									</td>
								</tr>
							</c:forEach>
						</c:if>
						<c:if test="${websiteNavigates.size()==0||websiteNavigates==null}">
							<tr>
								<td align="center" colspan="16">
									<div class="tips">
										<span>还没有导航！</span>
									</div>
								</td>
							</tr>
						</c:if>
					</tbody>
				</table>
			</form>
		</div>
	</div>
</body>
</html>