<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/base.jsp"%>
<c:if test="${not empty questions.questionsCommentList}">
	<!-- /最佳答案 开始-->
	<div class="good-anwer-box">
		<h4 class="g-a-title">
			<span class="bg-green vam"><em class="icon24 mr5">&nbsp;</em>最佳答案</span>
		</h4>
		<section class="good-answer mt10">
			<section class="question-list lh-bj-list pr">
				<ul class="pr10">
					<c:forEach items="${questions.questionsCommentList}" var="questionsComment">
						<li>
							<aside class="noter-pic">
								<c:choose>
									<c:when test="${not empty questionsComment.picImg}">
										<img src="<%=staticImage %>${questionsComment.picImg}" alt="" width="50" height="50">
									</c:when>
									<c:otherwise>
										<img src="${ctx }/static/inxweb/img/avatar-boy.gif" alt="" width="50" height="50">
									</c:otherwise>
								</c:choose>
							</aside>
							<div class="of hLh20">
								<span class="fl"> <font class="fsize12 c-blue"> <c:if test="${not empty questionsComment.showName}">${questionsComment.showName}</c:if> <c:if test="${empty questionsComment.showName }">${questionsComment.email }</c:if>
								</font> <font class="fsize12 c-999 ml5">回复：</font></span>
							</div>
							<div class="noter-txt mt5">
								<p><c:out value="${questionsComment.content }"></c:out></p>
							</div>
							<div class="of mt5">
								<span class="fr"><font class="fsize12 c-999 ml5"><fmt:formatDate type="both" value="${questionsComment.addTime }" pattern="yyyy-MM-dd HH:mm" /></font></span> <span class="fl"> <a href="javascript: void(0)" title="回答" class="noter-dy vam"
										onclick="getCommentById(this,${questionsComment.id })">
										<em class="icon18">&nbsp;</em>全部评论(<span>${questionsComment.replyCount }</span>)
									</a> <tt class="noter-zan vam ml10 f-fM" title="赞一下" onclick="addPraise(${questionsComment.id },2,this)">
										<em class="icon18">&nbsp;</em>点赞(<span>${questionsComment.praiseCount }</span>)
									</tt>
								</span>
							</div>
							<div class="n-reply">
								<section class="n-reply-wrap mt10">
									<fieldset>
										<textarea name="" placeholder="输入您要评论的文字" id="commentContent"></textarea>
									</fieldset>
									<p class="of mt5 tar pl10 pr10">
										<span class="fl "><tt class="c-red commentContentmeg" style="display: none;"></tt></span>
										<a href="javascript:addComment();" title="回复" class="lh-reply-btn">回复</a>
									</p>
								</section>
								<input type="hidden" id="" name="" value="${questionsComment.id}" />
								<div class="mt10 pl10 pr10">
									<dl class="n-reply-list">
										<!-- 最佳答案的评论在这里 -->
									</dl>
								</div>
							</div> <!-- /回复盒子 -->
						</li>
					</c:forEach>
				</ul>
			</section>
		</section>
	</div>
	<!-- /最佳答案 结束-->
</c:if>
<!-- /回答列表 开始 -->
<div class="q-i-noter-box">
	<section class="q-i-reply">
		<h6 class="c-c-content c-infor-title">
			<span>问答列表</span>
		</h6>
		<c:if test="${questions.status==0 }">
			<section class="lh-bj-list pr mt20">
				<ul>
					<li class="unBr">
						<aside class="noter-pic">
							<c:choose>
								<c:when test="${not empty user.picImg }">
									<img src="<%=staticImage %>${user.picImg }" alt="" width="50" height="50">
								</c:when>
								<c:otherwise>
									<img src="${ctx }/static/inxweb/img/avatar-boy.gif" alt="" width="50" height="50">
								</c:otherwise>
							</c:choose>
						</aside>
						<div class="of">
							<section class="n-reply-wrap">
								<fieldset>
									<textarea placeholder="输入您要回复的文字" name="questionsComment.content" onkeyup="$(this).parent().next().find('tt').html('');"></textarea>
								</fieldset>
								<p class="of mt5 tar pl10 pr10">
									<span class="fl"><tt class="c-red"></tt></span> <u class="hand mr10 qxBtn c-999" style="display: none;">取消</u>
									<a class="lh-reply-btn" title="回复" href="javascript: void(0)" onclick="addComment(this)">回复</a>
								</p>
							</section>
						</div>
					</li>
				</ul>
			</section>
		</c:if>
	</section>
	<c:if test="${empty questionsCommentList  }">
		<!-- /无数据提示 开始-->
		<section class="no-data-wrap">
			<em class="icon30 no-data-ico">&nbsp;</em> <span class="c-666 fsize14 ml10 vam">还没有人回答，提问者喊你去回答...</span>
		</section>
		<!-- /无数据提示 结束-->
	</c:if>
	<c:if test="${not empty questionsCommentList  }">
		<div class="q-n-r-box">
			<section class="question-list lh-bj-list pr">
				<ul class="pr10">
					<c:forEach items="${questionsCommentList}" var="questionsComment" varStatus="index">
						<li>
							<aside class="noter-pic">
								<c:choose>
									<c:when test="${not empty questionsComment.picImg }">
										<img src="<%=staticImage %>${questionsComment.picImg }" alt="" width="50" height="50">
									</c:when>
									<c:otherwise>
										<img src="${ctx }/static/inxweb/img/avatar-boy.gif" alt="" width="50" height="50">
									</c:otherwise>
								</c:choose>
							</aside>
							<div class="of hLh20">
								<c:if test="${questions.status==0 and  questions.cusId==user.userId and questionsComment.cusId != questions.cusId}">
									<span class="fr"><a href="javascript:void(0)" onclick="acceptComment(${questionsComment.id})" title="" class="comm-btn c-btn-6">采纳为最佳答案</a></span>
								</c:if>
								<span class="fl"> <font class="fsize12 c-blue"> <c:if test="${not empty questionsComment.showName}">${questionsComment.showName}</c:if> <c:if test="${empty questionsComment.showName}">${questionsComment.email}</c:if>
								</font> <font class="fsize12 c-999 ml5">回复：</font></span>
							</div>
							<div class="noter-txt mt5">
								<p><c:out value="${questionsComment.content }"></c:out></p>
							</div>
							<div class="of mt5">
								<span class="fr"><font class="fsize12 c-999 ml5"> <!-- <a href="" class="c-blue mr10">删除</a> --> <fmt:formatDate type="both" value="${questionsComment.addTime}" pattern="yyyy-MM-dd HH:mm" />
								</font></span> <span class="fl"> <a href="javascript: void(0)" title="回答" class="noter-dy vam" onclick="getCommentById(this,${questionsComment.id})">
										<em class="icon18">&nbsp;</em>(<span>${questionsComment.replyCount }</span>)
									</a> <tt class="noter-zan vam ml10" title="赞一下" onclick="addPraise(${questionsComment.id },2,this)">
										<em class="icon18">&nbsp;</em>(<span>${questionsComment.praiseCount }</span>)
									</tt>
								</span>
							</div>

							<div class="n-reply" style="display: none;padding-left: 0;">
								<div>
									<section class="n-reply-wrap mt10">
										<fieldset>
											<textarea name="" onkeyup="$(this).parent().next().find('tt').html('');"></textarea>
										</fieldset>
										<p class="of mt5 tar pl10 pr10">
											<span class="fl"><tt class="c-red"></tt></span><u class="hand mr10 qxBtn c-999" >取消</u>
											<a href="javascript: void(0)" title="回复" class="lh-reply-btn" onclick="addReply(this)">回复</a>
										</p>
									</section>
									<input type="hidden" id="" name="" value="${questionsComment.id}" />
								</div>
								<div class="mt10 pl10 pr10">
									<dl class="n-reply-list">
										<!-- 当前问答回复的所有评论在这里 -->
									</dl>
								</div>
							</div> <!-- /回复盒子 -->
						</li>
					</c:forEach>
				</ul>
			</section>
		</div>
		<!-- 公共分页 开始 -->
		<jsp:include page="/WEB-INF/view/common/ajaxpage.jsp"></jsp:include>
		<!-- 公共分页 结束 -->
</div>
<!-- /回答列表 结束 -->
</c:if>
