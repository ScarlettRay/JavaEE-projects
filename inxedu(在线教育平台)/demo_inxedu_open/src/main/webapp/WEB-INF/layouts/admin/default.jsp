<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="sitemesh"%>
<%@ include file="/base.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=9, IE=8">
<title><sitemesh:title></sitemesh:title></title>
<link type="text/css" href="${ctx}/static/admin/css/layout.css" rel="stylesheet" />
<link rel="shortcut icon" href="${ctx}/favicon.ico" type="image/x-icon">
	<script type="text/javascript" src="${ctx}/static/common/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${ctximg}/static/common/admin/js/admin-inxedu.js"></script>
<script type="text/javascript" src="${ctximg}/static/common/webutils.js"></script>
<script type="text/javascript">
    baselocation='${ctx}';
    keuploadSimpleUrl='<%=keuploadSimpleUrl%>';
    uploadSimpleUrl='<%=uploadSimpleUrl%>';
    imagesPath='<%=staticImage%>';
    </script>
    
<sitemesh:head></sitemesh:head>
</head>
<body>
	<sitemesh:body></sitemesh:body>
	<script>
		$(function(){
			$("section").hide();
		});
	</script>
</body>
</html>
