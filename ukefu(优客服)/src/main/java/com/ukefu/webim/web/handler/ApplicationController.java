package com.ukefu.webim.web.handler;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ukefu.webim.service.acd.ServiceQuene;
import com.ukefu.webim.service.cache.CacheHelper;
import com.ukefu.webim.service.repository.UserRepository;
import com.ukefu.webim.web.model.User;

@Controller
public class ApplicationController extends Handler{
	
	@Autowired
	private UserRepository userRepository;

	@RequestMapping("/")
    public ModelAndView admin(HttpServletRequest request) {
		ModelAndView view = request(super.createRequestPageTempletResponse("/apps/index"));
		User user = super.getUser(request) ;
        view.addObject("agentStatusReport",ServiceQuene.getAgentReport(user.getOrgi())) ;
		view.addObject("agentStatus",CacheHelper.getAgentStatusCacheBean().getCacheObject(user.getId(), user.getOrgi())) ;
        return view;
    }
}