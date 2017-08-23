<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>系统消息列表</title>
    <script type="text/javascript"  src="${ctximg}/static/common/jquery-ui-1.10.4/js/jquery-ui-1.10.4.custom.js?v=${v}"></script>
    <script type="text/javascript" src="${ctximg}/static/common/jquery-ui-1.10.4/js/jquery.ui.datepicker-zh-CN.js?v=${v}"></script>
    <script type="text/javascript" src="${ctximg}/static/common/jquery-ui-1.10.4/js/jquery-ui-timepicker-addon.js?v=${v}"></script>
    <script type="text/javascript" src="${ctximg}/static/common/jquery-ui-1.10.4/js/jquery-ui-timepicker-zh-CN.js?v=${v}"></script>

    <script type="text/javascript">
        $(function () {
            $("#startDate,#endDate").datetimepicker( {
                regional: "zh-CN",
                changeMonth: true,
                dateFormat: "yy-mm-dd ",
                timeFormat: "HH:mm:ss"
            });
            $("#status").val('${msgSystem.status}');
        });
        function submitSearch(){
            $("#searchForm").submit();
        }
        function clean(){
            $("input:text").val('');
            $("select").val('0');
        }
        /**
         * 全选
         * @param cb
         */
        function allselect(cb) {
            $("input[class='checkbox']").prop('checked', cb.checked);
        }
        /**
         * 批量删除
         */
        function deleteBatch(){
            var checked = false;
            var str = "";
            $(".checkbox").each(function(){
                if($(this).prop("checked")){
                    str+=this.value+",";
                    checked=true;
                }
            });
            if (!checked) {
                alert("请至少选择一条信息");
                return;
            }
            str=str.substring(0,str.length-1);
            if (confirm("确定删除所选消息吗？")) {
                $.ajax({
                    url:"${ctx}/admin/letter/delsystem",
                    data:{"ids":str},
                    type:"post",
                    dataType:"json",
                    success:function(result){
                        if(result.success){
                            alert(result.message);
                            window.location.href="/admin/letter/systemmsglist";
                        }else{
                            alert(result.message);
                        }
                    }
                });
            }
        }
    </script>
</head>
<body>

<%--<div class="page_head">
    <h4><em class="icon14 i_01"></em>&nbsp;<span>系统管理</span> &gt; <span>系统消息列表</span></h4>
</div>--%>
<!-- /tab1 begin-->
<div class="">
        <form action="${ctx}/admin/letter/systemmsglist" name="searchForm" id="searchForm" method="post">
            <input id="pageCurrentPage" type="hidden" name="page.currentPage" value="${page.currentPage}"/>
            内容：
            <input type="text" name="msgSystem.content" value="${msgSystem.content}"/>
            消息添加时间：
            <input id="startDate" type="text" name="msgSystem.addTime" value='<fmt:formatDate value="${msgSystem.addTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>'/>
            -
            <input id="endDate" type="text" name="msgSystem.endTime" value='<fmt:formatDate value="${msgSystem.endTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>'/>
            状态：
            <select id="status" name="msgSystem.status">
                <option value="0">正常</option>
                <option value="1">已删除</option>
                <option value="2">已过期</option>
            </select>

            <input type="button" class="button tooltip" value="查询" name="" onclick="submitSearch()"/>
            <input type="button" class="button tooltip" value="清空" name="" onclick="clean()"/>
            <input type="button" class="btn btn-danger" onclick="deleteBatch();" value="批量删除">
            <table cellspacing="0" cellpadding="0" border="0" class="fullwidth" width="100%">
                <thead>
                <tr>
                    <th width="20%"><span><input type="checkbox" onclick="allselect(this)"/>&nbsp;id</span></th>
                    <th width="40%"><span>消息内容</span></th>
                    <th width="20%"><span>注册时间</span></th>
                    <th width="20%"><span>状态</span></th>
                </tr>
                </thead>
                <tbody>
                <c:if test="${not empty nsgSystemList}">
                    <c:forEach items="${nsgSystemList}" var="list" varStatus="index">
                        <tr <c:if test="${index.count%2==1 }">class="odd"</c:if>>
                            <td align="center"><input type="checkbox" class="checkbox" value="${list.id}"/>&nbsp;${list.id}</td>
                            <td align="center"><div style="overflow-y: auto;height:auto;">${list.content }</div></td>
                            <td align="center"><fmt:formatDate value="${list.addTime}" type="both"/></td>
                            <td align="center">
                                <c:choose>
                                    <c:when test="${list.status==1}">已删除</c:when>
                                    <c:when test="${list.status==2}">已过期</c:when>
                                    <c:otherwise>正常</c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                </c:if>
                <c:if test="${empty nsgSystemList}">
                    <tr>
                        <td align="center" colspan="16">
                            <div class="tips">
                                <span>暂无相关信息！</span>
                            </div>
                        </td>
                    </tr>
                </c:if>
                </tbody>
            </table>
        </form>
        <!-- /pageBar begin -->
        <jsp:include page="/WEB-INF/view/common/admin_page.jsp" />
        <!-- /pageBar end -->
</div>
</body>
</html>
