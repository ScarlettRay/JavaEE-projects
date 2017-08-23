package com.ukefu.webim.web.handler.apps.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ukefu.core.UKDataContext;
import com.ukefu.util.Menu;
import com.ukefu.util.client.NettyClients;
import com.ukefu.webim.service.acd.ServiceQuene;
import com.ukefu.webim.service.cache.CacheHelper;
import com.ukefu.webim.service.repository.AgentServiceRepository;
import com.ukefu.webim.service.repository.AgentStatusRepository;
import com.ukefu.webim.service.repository.AgentUserRepository;
import com.ukefu.webim.service.repository.LeaveMsgRepository;
import com.ukefu.webim.service.repository.OrganRepository;
import com.ukefu.webim.service.repository.UserRepository;
import com.ukefu.webim.web.handler.Handler;
import com.ukefu.webim.web.model.AgentService;
import com.ukefu.webim.web.model.AgentStatus;
import com.ukefu.webim.web.model.AgentUser;
import com.ukefu.webim.web.model.LeaveMsg;
import com.ukefu.webim.web.model.Organ;
import com.ukefu.webim.web.model.User;

@Controller
@RequestMapping("/service")
public class ChatServiceController extends Handler{
	
	@Autowired
	private AgentServiceRepository agentServiceRes ;
	
	@Autowired
	private AgentUserRepository agentUserRes ;
	
	@Autowired
	private AgentStatusRepository agentStatusRepository ;
	
	@Autowired
	private AgentUserRepository agentUserRepository ;
	
	@Autowired
	private LeaveMsgRepository leaveMsgRes ;
	
	@Autowired
	private OrganRepository organRes ;
	
	@Autowired
	private UserRepository userRes ;
	
	@RequestMapping("/history/index")
    @Menu(type = "service" , subtype = "history" , admin= true)
    public ModelAndView index(ModelMap map , HttpServletRequest request) {
		map.put("agentServiceList", agentServiceRes.findByOrgiAndStatus(super.getOrgi(request), UKDataContext.AgentUserStatusEnum.END.toString() ,new PageRequest(super.getP(request), super.getPs(request), Direction.DESC , "createtime"))) ;
        return request(super.createAppsTempletResponse("/apps/service/history/index"));
    }
	
	@RequestMapping("/current/index")
    @Menu(type = "service" , subtype = "current" , admin= true)
    public ModelAndView current(ModelMap map , HttpServletRequest request) {
		map.put("agentServiceList", agentServiceRes.findByOrgiAndStatus(super.getOrgi(request), UKDataContext.AgentUserStatusEnum.INSERVICE.toString() ,new PageRequest(super.getP(request), super.getPs(request), Direction.DESC , "createtime"))) ;
        return request(super.createAppsTempletResponse("/apps/service/current/index"));
    }
	
	@RequestMapping("/current/trans")
    @Menu(type = "service" , subtype = "current" , admin= true)
    public ModelAndView trans(ModelMap map , HttpServletRequest request , @Valid String id) {
		if(!StringUtils.isBlank(id)){
			AgentService agentService = agentServiceRes.findByIdAndOrgi(id, super.getOrgi(request)) ;
			map.addAttribute("organList", organRes.findByOrgi(super.getOrgi(request))) ;
			
			List<String> usersids = new ArrayList<String>();
			
			Collection<?> users =  CacheHelper.getAgentStatusCacheBean().getAllCacheObject(super.getOrgi(request)) ;
			Iterator<?> iterator = users.iterator() ;
			while(iterator.hasNext()){
				String agentno = (String) iterator.next() ;
				if(agentno!=null && !agentno.equals(super.getUser(request).getId())){
					usersids.add(agentno) ;
				}
			}
			List<User> userList = userRes.findAll(usersids) ;
			for(User user : userList){
				user.setAgentStatus((AgentStatus) CacheHelper.getAgentStatusCacheBean().getCacheObject(user.getId(), super.getOrgi(request)));
			}
			map.addAttribute("userList", userList) ;
			map.addAttribute("userid", agentService.getUserid()) ;
			map.addAttribute("agentserviceid", agentService.getId()) ;
			map.addAttribute("agentuserid", agentService.getAgentuserid()) ;
			map.addAttribute("agentservice", agentService) ;
		}
		
		return request(super.createRequestPageTempletResponse("/apps/service/current/transfer"));
    }
	
	@RequestMapping(value="/transfer/save")  
	@Menu(type = "apps", subtype = "transfersave")
    public ModelAndView transfersave(ModelMap map , HttpServletRequest request , @Valid String id , @Valid String agentno , @Valid String memo){ 
		if(!StringUtils.isBlank(id)){
			AgentService agentService = agentServiceRes.findByIdAndOrgi(id, super.getOrgi(request)) ;
			AgentUser agentUser = (AgentUser) CacheHelper.getAgentUserCacheBean().getCacheObject(agentService.getUserid(), super.getOrgi(request)) ;
			if(agentUser != null){
				agentUser.setAgentno(agentno);
				CacheHelper.getAgentUserCacheBean().put(agentService.getUserid() , agentUser , super.getOrgi(request)) ;
				agentUserRepository.save(agentUser) ;
				if(UKDataContext.AgentUserStatusEnum.INSERVICE.toString().equals(agentUser.getStatus())){		//转接 ， 发送消息给 目标坐席
					AgentStatus agentStatus = (AgentStatus) CacheHelper.getAgentStatusCacheBean().getCacheObject(super.getUser(request).getId(), super.getOrgi(request)) ;
					
					if(agentStatus!=null){
						ServiceQuene.updateAgentStatus(agentStatus, agentUser, super.getOrgi(request), false);
					}
					
					AgentStatus transAgentStatus = (AgentStatus) CacheHelper.getAgentStatusCacheBean().getCacheObject(agentno, super.getOrgi(request)) ;
					if(transAgentStatus!=null){
						ServiceQuene.updateAgentStatus(transAgentStatus, agentUser, super.getOrgi(request), true);
						agentService.setAgentno(agentno);
						agentService.setAgentusername(transAgentStatus.getUsername());
					}
					NettyClients.getInstance().sendAgentEventMessage(agentno, UKDataContext.MessageTypeEnum.NEW.toString(), agentUser);
				}
			}else{
				agentUser = agentUserRepository.findByIdAndOrgi(agentService.getAgentuserid(), super.getOrgi(request));
				if(agentUser!=null){
					agentUser.setAgentno(agentno);	
					agentUserRepository.save(agentUser) ;
				}
			}
			
			if(agentService!=null){
				agentService.setAgentno(agentno);
				if(!StringUtils.isBlank(memo)){
					agentService.setTransmemo(memo);
				}
				agentService.setTrans(true);
				agentService.setTranstime(new Date());
				agentServiceRes.save(agentService) ;
			}
		}
		
    	return request(super.createRequestPageTempletResponse("redirect:/service/current/index.html")) ; 
    }
	
	@RequestMapping("/current/end")
    @Menu(type = "service" , subtype = "current" , admin= true)
    public ModelAndView end(ModelMap map , HttpServletRequest request , @Valid String id) throws Exception {
		if(!StringUtils.isBlank(id)){
			AgentService agentService = agentServiceRes.findByIdAndOrgi(id, super.getOrgi(request)) ;
			if(agentService!=null){
				User user = super.getUser(request);
				AgentUser agentUser = agentUserRepository.findByIdAndOrgi(agentService.getAgentuserid(), super.getOrgi(request));
				if(agentUser!=null){
					ServiceQuene.deleteAgentUser(agentUser, user.getOrgi());
				}
				agentService.setStatus(UKDataContext.AgentUserStatusEnum.END.toString());
				agentServiceRes.save(agentService) ;
			}
		}
        return request(super.createRequestPageTempletResponse("redirect:/service/current/index.html"));
    }
	
	@RequestMapping("/quene/index")
    @Menu(type = "service" , subtype = "quene" , admin= true)
    public ModelAndView quene(ModelMap map , HttpServletRequest request) {
		Page<AgentUser> agentUserList = agentUserRes.findByOrgiAndStatus(super.getOrgi(request), UKDataContext.AgentUserStatusEnum.INQUENE.toString() ,new PageRequest(super.getP(request), super.getPs(request), Direction.DESC , "createtime")) ;
		List<String> skillList = new ArrayList<String>();
		for(AgentUser agentUser : agentUserList.getContent()){
			agentUser.setWaittingtime((int) (System.currentTimeMillis() - agentUser.getCreatetime().getTime()));
			if(!StringUtils.isBlank(agentUser.getSkill())){
				skillList.add(agentUser.getSkill()) ;
			}
		}
		if(skillList.size() > 0){
			List<Organ> organList = organRes.findAll(skillList) ;
			for(AgentUser agentUser : agentUserList.getContent()){
				if(!StringUtils.isBlank(agentUser.getSkill())){
					for(Organ organ : organList){
						if(agentUser.getSkill().equals(organ.getId())){
							agentUser.setSkillname(organ.getName());
							break ;
						}
					}
				}
			}
		}
		map.put("agentUserList", agentUserList) ;
        return request(super.createAppsTempletResponse("/apps/service/quene/index"));
    }
	
	@RequestMapping("/quene/clean")
    @Menu(type = "service" , subtype = "queneclean" , admin= true)
    public ModelAndView clean(ModelMap map , HttpServletRequest request ,@Valid String id) {
		AgentUser agentUser = agentUserRes.findByIdAndOrgi(id, super.getOrgi(request)) ;
		if(agentUser!=null && agentUser.getStatus().equals(UKDataContext.AgentUserStatusEnum.INQUENE.toString())){
			agentUser.setAgent(null);
			agentUser.setSkill(null);
			agentUserRes.save(agentUser) ;
			CacheHelper.getAgentUserCacheBean().put(agentUser.getUserid(), agentUser, super.getOrgi(request));
			ServiceQuene.allotAgent(agentUser, super.getOrgi(request)) ;
		}
        return request(super.createRequestPageTempletResponse("redirect:/service/quene/index.html"));
    }
	
	@RequestMapping("/quene/invite")
    @Menu(type = "service" , subtype = "invite" , admin= true)
    public ModelAndView invite(ModelMap map , HttpServletRequest request ,@Valid String id) throws Exception {
		AgentUser agentUser = agentUserRes.findByIdAndOrgi(id, super.getOrgi(request)) ;
		if(agentUser!=null && agentUser.getStatus().equals(UKDataContext.AgentUserStatusEnum.INQUENE.toString())){
			ServiceQuene.allotAgentForInvite(super.getUser(request).getId() , agentUser, super.getOrgi(request)) ;
		}
        return request(super.createRequestPageTempletResponse("redirect:/service/quene/index.html"));
    }
	
	@RequestMapping("/agent/index")
    @Menu(type = "service" , subtype = "onlineagent" , admin= true)
    public ModelAndView agent(ModelMap map , HttpServletRequest request) {
		List<AgentStatus> agentStatusList = agentStatusRepository.findByOrgi(super.getOrgi(request)) ;
		for(int i=0 ; i<agentStatusList.size() ; ){
			AgentStatus agentStatus = agentStatusList.get(i) ;
			if(CacheHelper.getAgentStatusCacheBean().getCacheObject(agentStatus.getAgentno(), super.getOrgi(request))==null) {
				agentStatusRepository.delete(agentStatus); 
				agentStatusList.remove(i) ;
				continue ;
			}else{
				agentStatusList.set(i, (AgentStatus) CacheHelper.getAgentStatusCacheBean().getCacheObject(agentStatus.getAgentno(), super.getOrgi(request))) ;	
			}
			i++ ;
		}
		List<String> skillList = new ArrayList<String>();
		for(AgentStatus agentStatus : agentStatusList){
			if(!StringUtils.isBlank(agentStatus.getSkill())){
				skillList.add(agentStatus.getSkill()) ;
			}
		}
		if(skillList.size() > 0){
			List<Organ> organList = organRes.findAll(skillList) ;
			for(AgentStatus agentStatus : agentStatusList){
				if(!StringUtils.isBlank(agentStatus.getSkill())){
					for(Organ organ : organList){
						if(agentStatus.getSkill().equals(organ.getId())){
							agentStatus.setSkillname(organ.getName());
							break ;
						}
					}
				}
			}
		}
		map.put("agentStatusList", agentStatusList) ;
        return request(super.createAppsTempletResponse("/apps/service/agent/index"));
    }
	
	@RequestMapping("/agent/offline")
    @Menu(type = "service" , subtype = "offline" , admin= true)
    public ModelAndView offline(ModelMap map , HttpServletRequest request , @Valid String id) {
		
		AgentStatus agentStatus = agentStatusRepository.findByIdAndOrgi(id, super.getOrgi(request));
		if(agentStatus!=null){
			agentStatusRepository.delete(agentStatus);
		}
    	CacheHelper.getAgentStatusCacheBean().delete(agentStatus.getAgentno(), super.getOrgi(request));;
    	ServiceQuene.publishMessage(super.getOrgi(request));
    	
		
        return request(super.createRequestPageTempletResponse("redirect:/service/agent/index.html"));
    }
	
	@RequestMapping("/user/index")
    @Menu(type = "service" , subtype = "userlist" , admin= true)
    public ModelAndView user(ModelMap map , HttpServletRequest request) {
		Page<User> userList = userRes.findByOrgiAndAgent(super.getOrgi(request), true,  new PageRequest(super.getP(request), super.getPs(request), Direction.DESC , "createtime")) ;
		for(User user : userList.getContent()){
			if(CacheHelper.getAgentStatusCacheBean().getCacheObject(user.getId(), super.getOrgi(request))!=null){
				user.setOnline(true);
			}
		}
		map.put("userList", userList) ;
        return request(super.createAppsTempletResponse("/apps/service/user/index"));
    }
	
	@RequestMapping("/leavemsg/index")
    @Menu(type = "service" , subtype = "leavemsg" , admin= true)
    public ModelAndView leavemsg(ModelMap map , HttpServletRequest request) {
		Page<LeaveMsg> leaveMsgList = leaveMsgRes.findByOrgi(super.getOrgi(request),new PageRequest(super.getP(request), super.getPs(request), Direction.DESC , "createtime")) ;
		map.put("leaveMsgList", leaveMsgList) ;
        return request(super.createAppsTempletResponse("/apps/service/leavemsg/index"));
    }
	
	@RequestMapping("/leavemsg/delete")
    @Menu(type = "service" , subtype = "leavemsg" , admin= true)
    public ModelAndView leavemsg(ModelMap map , HttpServletRequest request , @Valid String id) {
		if(!StringUtils.isBlank(id)){
			leaveMsgRes.delete(id);
		}
		return request(super.createRequestPageTempletResponse("redirect:/service/leavemsg/index.html"));
    }
}
