package com.inxedu.os.common.intercepter;



import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.inxedu.os.edu.constants.enums.WebSiteProfileType;
import com.inxedu.os.edu.entity.website.WebsiteImages;
import com.inxedu.os.edu.service.website.WebsiteImagesService;
import com.inxedu.os.edu.service.website.WebsiteNavigateService;
import com.inxedu.os.edu.service.website.WebsiteProfileService;

/**
 * 网站配置管理拦截器
 * @author www.inxedu.com
 */
public class LimitIntercepterForWebsite extends HandlerInterceptorAdapter{
	 //logger
	 Logger logger = LoggerFactory.getLogger(LimitIntercepterForWebsite.class);
     @Autowired
     private WebsiteProfileService websiteProfileService;
 	 @Autowired
 	 private WebsiteNavigateService websiteNavigateService;
 	 @Autowired
 	 private WebsiteImagesService websiteImagesService;
 	
     public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
         super.afterCompletion(request, response, handler, ex);
     }

     
     public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
         super.postHandle(request, response, handler, modelAndView);
     }
     
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
         try{
        		// 获得banner图
 			Map<String, List<WebsiteImages>> websiteImages = websiteImagesService.queryImagesByType();
 			request.setAttribute("websiteImages", websiteImages);
        	//获得网站配置
          	Map<String,Object> websitemap=websiteProfileService.getWebsiteProfileByType(WebSiteProfileType.web.toString());
          	request.setAttribute("websitemap",websitemap);
            //获得LOGO配置
          	Map<String,Object> logomap=websiteProfileService.getWebsiteProfileByType(WebSiteProfileType.logo.toString());
          	request.setAttribute("logomap",logomap);
          	//网站统计代码
            Map<String,Object> tongjiemap=websiteProfileService.getWebsiteProfileByType(WebSiteProfileType.censusCode.toString());
            request.setAttribute("tongjiemap",tongjiemap);
            //咨询链接
            Map<String,Object> onlinemap = websiteProfileService.getWebsiteProfileByType(WebSiteProfileType.online.toString());
            request.setAttribute("onlinemap", onlinemap);
            
            //网站导航配置
          	Map<String,Object> navigatemap=websiteNavigateService.getWebNavigate();
      		request.setAttribute("navigatemap",navigatemap);
         }catch(Exception e){
        	 logger.error("LimitIntercepterForWebsite.preHandle 网站配置出错",e);
         }
		
    	return super.preHandle(request, response, handler);
    } 
}
