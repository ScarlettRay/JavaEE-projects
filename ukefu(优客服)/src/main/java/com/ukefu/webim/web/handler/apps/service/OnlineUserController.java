package com.ukefu.webim.web.handler.apps.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ukefu.core.UKDataContext;
import com.ukefu.util.Menu;
import com.ukefu.util.UKTools;
import com.ukefu.webim.service.impl.AgentUserService;
import com.ukefu.webim.service.repository.AgentServiceRepository;
import com.ukefu.webim.service.repository.AgentStatusRepository;
import com.ukefu.webim.service.repository.AgentUserContactsRepository;
import com.ukefu.webim.service.repository.ChatMessageRepository;
import com.ukefu.webim.service.repository.ContactsRepository;
import com.ukefu.webim.service.repository.OnlineUserHisRepository;
import com.ukefu.webim.service.repository.OnlineUserRepository;
import com.ukefu.webim.service.repository.ServiceSummaryRepository;
import com.ukefu.webim.service.repository.TagRelationRepository;
import com.ukefu.webim.service.repository.TagRepository;
import com.ukefu.webim.service.repository.UserRepository;
import com.ukefu.webim.service.repository.WeiXinUserRepository;
import com.ukefu.webim.web.handler.Handler;
import com.ukefu.webim.web.model.AgentService;
import com.ukefu.webim.web.model.AgentServiceSummary;
import com.ukefu.webim.web.model.AgentUser;
import com.ukefu.webim.web.model.AgentUserContacts;
import com.ukefu.webim.web.model.OnlineUser;
import com.ukefu.webim.web.model.WeiXinUser;

@Controller
@RequestMapping("/service")
public class OnlineUserController extends Handler{
	@Autowired
	private AgentServiceRepository agentServiceRes ;
	
	@Autowired
	private AgentUserService agentUserRes ;
	
	@Autowired
	private OnlineUserRepository onlineUserRes; 
	
	@Autowired
	private ServiceSummaryRepository serviceSummaryRes; 
	
	@Autowired
	private AgentStatusRepository agentStatusRepository ;
	
	@Autowired
	private OnlineUserHisRepository onlineUserHisRes;
	
	@Autowired
	private UserRepository userRes ;
	
	@Autowired
	private WeiXinUserRepository weiXinUserRes;
	
	@Autowired
	private TagRepository tagRes ;
	
	@Autowired
	private TagRelationRepository tagRelationRes ;
	
	@Autowired
	private ChatMessageRepository chatMessageRepository ;
	
	@Autowired
	private ContactsRepository contactsRes ;
	
	@Autowired
	private AgentUserContactsRepository agentUserContactsRes ;
	
	@RequestMapping("/online/index")
    @Menu(type = "service" , subtype = "online" , admin= true)
    public ModelAndView index(ModelMap map , HttpServletRequest request , String userid , String agentservice , @Valid String channel) {
		if(!StringUtils.isBlank(userid)){
			map.put("inviteResult", UKTools.getWebIMInviteResult(onlineUserRes.findByOrgiAndUserid(super.getOrgi(request), userid))) ;
			map.put("tagRelationList", tagRelationRes.findByUserid(userid)) ;
			map.put("onlineUserHistList", onlineUserHisRes.findByUseridAndOrgi(userid, super.getOrgi(request))) ;
			map.put("agentServicesAvg", onlineUserRes.countByUserForAvagTime(super.getOrgi(request), UKDataContext.AgentUserStatusEnum.END.toString(),userid)) ;
			
			List<AgentService> agentServiceList = agentServiceRes.findByUseridAndOrgi(userid, super.getOrgi(request)) ; 
			
			map.put("agentServiceList", agentServiceList) ;
			if(agentServiceList.size()>0){
				map.put("serviceCount", Integer
						.valueOf(this.agentServiceRes
								.countByUseridAndOrgiAndStatus(userid, super.getOrgi(request),
										UKDataContext.AgentUserStatusEnum.END.toString())));
				
				AgentService agentService = agentServiceList.get(0) ;
				if(!StringUtils.isBlank(agentservice)){
					for(AgentService as : agentServiceList){
						if(as.getId().equals(agentservice)){
							agentService = as ; break ;
						}
					}
				}
				
				if(agentService!=null){
					AgentServiceSummary summary = serviceSummaryRes.findByAgentserviceidAndOrgi(agentService.getId(), super.getOrgi(request)) ;
					map.put("summary" , summary) ;
				}
				
				List<AgentUserContacts> agentUserContactsList = agentUserContactsRes.findByUseridAndOrgi(userid, super.getOrgi(request)) ;
				if(agentUserContactsList.size() > 0){
					AgentUserContacts agentUserContacts = agentUserContactsList.get(0) ;
					map.put("contacts", contactsRes.findOne(agentUserContacts.getContactsid())) ;
				}
				
				map.put("tags", tagRes.findByOrgiAndTagtype(super.getOrgi(request) , UKDataContext.ModelType.USER.toString())) ;
				map.put("summaryTags", tagRes.findByOrgiAndTagtype(super.getOrgi(request) , UKDataContext.ModelType.SUMMARY.toString())) ;
				map.put("curAgentService", agentService) ;
				
				
				map.put("agentUserMessageList", chatMessageRepository.findByAgentserviceidAndOrgi(agentService.getId() , super.getOrgi(request), new PageRequest(0, 50, Direction.DESC , "updatetime")));
			}
			
			if(UKDataContext.ChannelTypeEnum.WEIXIN.toString().equals(channel)){
				List<WeiXinUser> weiXinUserList = weiXinUserRes.findByOpenidAndOrgi(userid, super.getOrgi(request)) ;
				if(weiXinUserList.size() > 0){
					WeiXinUser weiXinUser = weiXinUserList.get(0) ;
					map.put("weiXinUser",weiXinUser);
				}
			}else if(UKDataContext.ChannelTypeEnum.WEBIM.toString().equals(channel)){
				List<OnlineUser> onlineUserList = onlineUserRes.findByUseridAndOrgi(userid, super.getOrgi(request)) ;
				if(onlineUserList.size() >0){
					map.put("onlineUser", onlineUserList.get(0)) ;
				}
			}
			map.put("agentUser", agentUserRes.findByUseridAndOrgi(userid, super.getOrgi(request))) ;
			map.put("curragentuser", agentUserRes.findByUseridAndOrgi(userid, super.getOrgi(request))) ;
		}
        return request(super.createAppsTempletResponse("/apps/service/online/index"));
    }
	
	@RequestMapping("/online/chatmsg")
    @Menu(type = "service" , subtype = "chatmsg" , admin= true)
    public ModelAndView onlinechat(ModelMap map , HttpServletRequest request , String id , String title) {
		AgentService agentService = agentServiceRes.getOne(id) ; 
		AgentUser curragentuser = agentUserRes.findByUseridAndOrgi(agentService.getUserid(), super.getOrgi(request)) ;
		
		map.put("curAgentService", agentService) ;
		map.put("curragentuser", curragentuser) ;
		if(!StringUtils.isBlank(title)){
			map.put("title", title) ;
		}
		
		map.put("summaryTags", tagRes.findByOrgiAndTagtype(super.getOrgi(request) , UKDataContext.ModelType.SUMMARY.toString())) ;
		
		if(agentService!=null){
			AgentServiceSummary summary = serviceSummaryRes.findByAgentserviceidAndOrgi(agentService.getId(), super.getOrgi(request)) ;
			map.put("summary" , summary) ;
		}
		
		map.put("agentUserMessageList", chatMessageRepository.findByAgentserviceidAndOrgi(agentService.getId() , super.getOrgi(request), new PageRequest(0, 50, Direction.DESC , "updatetime")));
		
        return request(super.createRequestPageTempletResponse("/apps/service/online/chatmsg"));
    }
	
	
}
