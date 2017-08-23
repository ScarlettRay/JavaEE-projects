<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/base.jsp"%>
<samp class="accordion" id="menu-${userFunctionList[0].parentId}">
<c:forEach items="${userFunctionList}" var="uf" varStatus="index">
	<c:choose>
		<c:when test="${index.index==0}">
			<div>
				<h3 class="ui-accordion-header ui-helper-reset ui-state-active ui-corner-top">
					<span class="ui-icon ui-icon-triangle-1-s"></span>
					<c:choose>
						<c:when test="${uf.functionUrl==null || uf.functionUrl==''}">
							<a href="javascript:void(0)" title="${uf.functionName}" class="tooltip">${uf.functionName}</a>
						</c:when>
						<c:otherwise>

							<a href="${uf.functionUrl}" data-href="${uf.functionUrl}"  title="${uf.functionName}" class="tooltip">${uf.functionName}</a>
						</c:otherwise>
					</c:choose>
				</h3>
				<div class="ui-accordion-content ui-helper-reset ui-widget-content ui-corner-bottom" style="display: block;">
					<c:if test="${uf.childList!=null && uf.childList.size()>0}">
						<c:forEach items="${uf.childList}" var="cuf">
							<dl class="acd-sub-dl">
								<c:choose>
									<c:when test="${cuf.functionUrl==null || cuf.functionUrl==''}">
										<dt>
											<a href="javascript:void(0)" title="${cuf.functionName}">${cuf.functionName}</a>
										</dt>
									</c:when>
									<c:otherwise>
										<dt>
											<a  href="${cuf.functionUrl}" title="${cuf.functionName}" data-href="${cuf.functionUrl}" title="${cuf.functionName}">${cuf.functionName}</a>
										</dt>
									</c:otherwise>
								</c:choose>
								<c:if test="${cuf.childList!=null && cuf.childList.size()>0}">
									<c:forEach items="${cuf.childList}" var="ccuf">
										<c:choose>
											<c:when test="${ccuf.functionUrl==null || ccuf.functionUrl==''}">
												<dd>
													<a href="javascript:void(0)" title="${ccuf.functionName}">${ccuf.functionName}</a>
												</dd>
											</c:when>
											<c:otherwise>
												<dd>
													<a href="${ccuf.functionUrl}" data-href="${ccuf.functionUrl}" title="${ccuf.functionName}">${ccuf.functionName}</a>
												</dd>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</c:if>
							</dl>
						</c:forEach>
					</c:if>
				</div>
			</div>
		</c:when>
		<c:otherwise>
			<div>
				<h3 class="ui-accordion-header ui-helper-reset ui-state-default ui-corner-all">
					<span class="ui-icon ui-icon-triangle-1-e"></span>
					<c:choose>
						<c:when test="${uf.functionUrl==null || uf.functionUrl==''}">
							<a href="javascript:void(0)" title="${uf.functionName}" class="tooltip">${uf.functionName}</a>
						</c:when>
						<c:otherwise>
							<a href="${uf.functionUrl}"  data-href="${uf.functionUrl}"  title="${uf.functionName}" class="tooltip">${uf.functionName}</a>
						</c:otherwise>
					</c:choose>
				</h3>
				<div class="ui-accordion-content ui-helper-reset ui-widget-content ui-corner-bottom">
					<c:if test="${uf.childList!=null && uf.childList.size()>0}">
						<c:forEach items="${uf.childList}" var="cuf">
							<dl class="acd-sub-dl">
								<c:choose>
									<c:when test="${cuf.functionUrl==null && cuf.functionUrl==''}">
										<dt>
											<a href="javascript:void(0)" title="${cuf.functionName}">${cuf.functionName}</a>
										</dt>
									</c:when>
									<c:otherwise>
										<dt>
											<a href="${cuf.functionUrl}" data-href="${cuf.functionUrl}"  title="${cuf.functionName}">${cuf.functionName}</a>
										</dt>
									</c:otherwise>
								</c:choose>
								<c:if test="${cuf.childList!=null && cuf.childList.size()>0}">
									<c:forEach items="${cuf.childList}" var="ccuf">
										<c:choose>
											<c:when test="${ccuf.functionUrl==null || ccuf.functionUrl==''}">
												<dd>
													<a href="javascript:void(0)" title="${ccuf.functionName}">${ccuf.functionName}</a>
												</dd>
											</c:when>
											<c:otherwise>
												<dd>
													<a  href="${ccuf.functionUrl}" data-href="${ccuf.functionUrl}" title="${ccuf.functionName}">${ccuf.functionName}</a>
												</dd>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</c:if>
							</dl>
						</c:forEach>
					</c:if>
				</div>
			</div>
		</c:otherwise>
	</c:choose>
</c:forEach>
</samp>
<script>
	$("#ui-sMenu div").each(function() {
		var _this = $(this),
				_oT = _this.find("h3"),
				_oTi = _oT.find(".ui-icon"),
				_oC = _oT.next("div");
		_oT.unbind('click');//为div绑定RecommandProduct 函数
		_oT.click(function() {
			if(_oC.is(":hidden")) {
				$("#ui-sMenu>div h3").removeClass("ui-state-active ui-corner-top").addClass("ui-state-default ui-corner-all");
				$("#ui-sMenu>div .ui-icon").removeClass("ui-icon-triangle-1-s").addClass("ui-icon-triangle-1-e");
				$("#ui-sMenu>div div").slideUp("fast");
				_oT.removeClass("ui-state-default ui-corner-all").addClass("ui-state-active ui-corner-top");
				_oTi.removeClass("ui-icon-triangle-1-e").addClass("ui-icon-triangle-1-s");
				_oC.slideDown("fast");
			} else {
				_oT.removeClass("ui-state-active ui-corner-top").addClass("ui-state-default ui-corner-all");
				_oTi.removeClass("ui-icon-triangle-1-s").addClass("ui-icon-triangle-1-e");
				_oC.slideUp("fast");
			}
		});
	});
</script>