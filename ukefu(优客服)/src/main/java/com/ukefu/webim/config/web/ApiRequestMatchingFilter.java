package com.ukefu.webim.config.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.elasticsearch.common.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.ukefu.core.UKDataContext;
import com.ukefu.webim.service.cache.CacheHelper;

public class ApiRequestMatchingFilter implements Filter {
    private RequestMatcher[] ignoredRequests;

    public ApiRequestMatchingFilter(RequestMatcher... matcher) {
        this.ignoredRequests = matcher;
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
         HttpServletRequest request = (HttpServletRequest) req;
         HttpServletResponse response = (HttpServletResponse) resp;
         
         String method = request.getMethod() ;
         
         if(!StringUtils.isBlank(method) && method.equalsIgnoreCase("options")){
	         response.setHeader("Access-Control-Allow-Origin", "*");
	         response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
	         response.setHeader("Access-Control-Max-Age", "3600");
	         response.setHeader("Access-Control-Allow-Headers", "x-requested-with,accept,authorization,content-type");
	         response.setHeader("X-Frame-Options", "SAMEORIGIN");
	         response.setStatus(HttpStatus.ACCEPTED.value());
         }else{
	         boolean matchAnyRoles = false ;
	         for(RequestMatcher anyRequest : ignoredRequests ){
	        	 if(anyRequest.matches(request)){
	        		 matchAnyRoles = true ;
	        	 }
	         }
	         if(matchAnyRoles){
	        	 String authorization = request.getHeader("authorization") ;
	        	 if(StringUtils.isBlank(authorization)){
	        		 authorization = request.getParameter("authorization") ;
	        	 }
	        	 if(!StringUtils.isBlank(authorization) && CacheHelper.getApiUserCacheBean().getCacheObject(authorization, UKDataContext.SYSTEM_ORGI) != null){
	        		 chain.doFilter(req,resp);
	        	 }else{
		        	 response.sendRedirect("/tokens/error");
	        	 }
	         }else{
	        	 chain.doFilter(req,resp);
	         }
         }
    }

	@Override
	public void destroy() {
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}
}