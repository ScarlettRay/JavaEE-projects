<%@page language="java" pageEncoding="UTF-8"%>
<%@page import="com.inxedu.os.common.constants.CommonConstants"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator"%>
<%!
/**图片、CSS、js静态资源文件地址*/
	static String staticServer = CommonConstants.staticServer;
	/**上传服务用服务器地址，访问时用staticImage，数据库中不存储域名*/
	static String uploadServerUrl=CommonConstants.uploadImageServer;
	 /**页面显示图片的前缀路径*/
    static String staticImage=CommonConstants.staticImage;

	//内容编辑器上传图片路径
	static String keuploadSimpleUrl = uploadServerUrl+"/image/keupload?";
	//图片上传路径 
	static String uploadSimpleUrl = uploadServerUrl+"/image/gok4?";
	%>

<c:set var="ctx" value="<%=CommonConstants.contextPath%>"></c:set>
<c:set var="ctximg" value="<%=CommonConstants.staticServer%>"></c:set>

