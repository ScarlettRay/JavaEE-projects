package com.ukefu.webim.web.handler.apps;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ukefu.core.UKDataContext;
import com.ukefu.util.Menu;
import com.ukefu.util.UKTools;
import com.ukefu.webim.service.acd.ServiceQuene;
import com.ukefu.webim.service.repository.ContactsRepository;
import com.ukefu.webim.service.repository.InviteRecordRepository;
import com.ukefu.webim.service.repository.OnlineUserRepository;
import com.ukefu.webim.service.repository.UserEventRepository;
import com.ukefu.webim.service.repository.UserRepository;
import com.ukefu.webim.util.OnlineUserUtils;
import com.ukefu.webim.web.handler.Handler;
import com.ukefu.webim.web.model.Contacts;
import com.ukefu.webim.web.model.InviteRecord;
import com.ukefu.webim.web.model.OnlineUser;
import com.ukefu.webim.web.model.User;

@Controller
public class AppsController extends Handler{
	
	@Autowired
	private UserRepository userRes;
	
	@Autowired
	private OnlineUserRepository onlineUserRes;
	
	@Autowired
	private UserEventRepository userEventRes ;
	
	@Autowired
	private InviteRecordRepository inviteRecordRes ;
	
	@Autowired
	private ContactsRepository contactsRes ;

	@RequestMapping({"/apps/content"})
	@Menu(type="apps", subtype="content")
	public ModelAndView content(ModelMap map , HttpServletRequest request){
		
		Page<OnlineUser> onlineUserList = this.onlineUserRes.findByOrgiAndStatus(super.getOrgi(request), UKDataContext.OnlineUserOperatorStatus.ONLINE.toString(), new PageRequest(super.getP(request), super.getPs(request), Sort.Direction.DESC, new String[] { "createtime" })) ;
		List<String> ids = new ArrayList<String>();
		for(OnlineUser onlineUser : onlineUserList.getContent()){
			onlineUser.setBetweentime((int) (System.currentTimeMillis() - onlineUser.getLogintime().getTime()));
			if(!StringUtils.isBlank(onlineUser.getContactsid())){
				ids.add(onlineUser.getContactsid()) ;
			}
		}
		if(ids.size() > 0){
			Iterable<Contacts> contactsList = contactsRes.findAll(ids) ;
			for(OnlineUser onlineUser : onlineUserList.getContent()){
				if(!StringUtils.isBlank(onlineUser.getContactsid())){
					for(Contacts contacts : contactsList){
						if(onlineUser.getContactsid().equals(contacts.getId())){
							onlineUser.setContacts(contacts);
						}
					}
				}
			}
		}
		map.put("onlineUserList", onlineUserList);
		aggValues(map, request);
		
		return request(super.createAppsTempletResponse("/apps/desktop/index"));
	}
	
	private void aggValues(ModelMap map , HttpServletRequest request){
		map.put("agentReport", ServiceQuene.getAgentReport(super.getOrgi(request))) ;
		map.put("webIMReport", UKTools.getWebIMReport(userEventRes.findByOrgiAndCreatetimeRange(super.getOrgi(request), UKTools.getStartTime() , UKTools.getEndTime()))) ;
		map.put("agents", userRes.countByOrgiAndAgent(super.getOrgi(request), true)) ;

		map.put("webIMInvite", UKTools.getWebIMInviteStatus(onlineUserRes.findByOrgiAndStatus(super.getOrgi(request), UKDataContext.OnlineUserOperatorStatus.ONLINE.toString()))) ;
		
		map.put("inviteResult", UKTools.getWebIMInviteResult(onlineUserRes.findByOrgiAndAgentnoAndCreatetimeRange(super.getOrgi(request), super.getUser(request).getId() , UKTools.getStartTime() , UKTools.getEndTime()))) ;
		
		map.put("agentUserCount", onlineUserRes.countByAgentForAgentUser(super.getOrgi(request), UKDataContext.AgentUserStatusEnum.INSERVICE.toString(),super.getUser(request).getId() , UKTools.getStartTime() , UKTools.getEndTime())) ;
		
		map.put("agentServicesCount", onlineUserRes.countByAgentForAgentUser(super.getOrgi(request), UKDataContext.AgentUserStatusEnum.END.toString(),super.getUser(request).getId() , UKTools.getStartTime() , UKTools.getEndTime())) ;
		
		map.put("agentServicesAvg", onlineUserRes.countByAgentForAvagTime(super.getOrgi(request), UKDataContext.AgentUserStatusEnum.END.toString(),super.getUser(request).getId() , UKTools.getStartTime() , UKTools.getEndTime())) ;
		
	}
	
	@RequestMapping({"/apps/onlineuser"})
	@Menu(type="apps", subtype="onlineuser")
	public ModelAndView onlineuser(ModelMap map , HttpServletRequest request){
		Page<OnlineUser> onlineUserList = this.onlineUserRes.findByOrgiAndStatus(super.getOrgi(request), UKDataContext.OnlineUserOperatorStatus.ONLINE.toString(), new PageRequest(super.getP(request), super.getPs(request), Sort.Direction.DESC, new String[] { "createtime" })) ;
		List<String> ids = new ArrayList<String>();
		for(OnlineUser onlineUser : onlineUserList.getContent()){
			onlineUser.setBetweentime((int) (System.currentTimeMillis() - onlineUser.getLogintime().getTime()));
			if(!StringUtils.isBlank(onlineUser.getContactsid())){
				ids.add(onlineUser.getContactsid()) ;
			}
		}
		if(ids.size() > 0){
			Iterable<Contacts> contactsList = contactsRes.findAll(ids) ;
			for(OnlineUser onlineUser : onlineUserList.getContent()){
				if(!StringUtils.isBlank(onlineUser.getContactsid())){
					for(Contacts contacts : contactsList){
						if(onlineUser.getContactsid().equals(contacts.getId())){
							onlineUser.setContacts(contacts);
						}
					}
				}
			}
		}
		map.put("onlineUserList", onlineUserList);
		aggValues(map, request);
		
		return request(super.createAppsTempletResponse("/apps/desktop/onlineuser"));
	}
	
	@RequestMapping({"/apps/invite"})
	@Menu(type="apps", subtype="invite")
	public ModelAndView invite(ModelMap map , HttpServletRequest request , @Valid String id) throws Exception{
		OnlineUser onlineUser = onlineUserRes.findOne(id) ;
		if(onlineUser!=null){
			onlineUser.setInvitestatus(UKDataContext.OnlineUserInviteStatus.INVITE.toString());
			onlineUser.setInvitetimes(onlineUser.getInvitetimes()+1);
			onlineUserRes.save(onlineUser) ;
			OnlineUserUtils.sendWebIMClients(onlineUser.getUserid() , "invite");
			InviteRecord record = new InviteRecord() ;
			record.setAgentno(super.getUser(request).getId());
			record.setUserid(onlineUser.getUserid());
			record.setAppid(onlineUser.getAppid());
			record.setOrgi(super.getOrgi(request));
			inviteRecordRes.save(record) ;
		}
		
		return request(super.createRequestPageTempletResponse("redirect:/apps/content.html"));
	}
	
	@RequestMapping({"/apps/profile"})
	@Menu(type="apps", subtype="content")
	public ModelAndView profile(ModelMap map , HttpServletRequest request){
		map.addAttribute("userData",super.getUser(request)) ;
		return request(super.createRequestPageTempletResponse("/apps/desktop/profile"));
	}
	
	@RequestMapping({"/apps/profile/save"})
	@Menu(type="apps", subtype="content")
	public ModelAndView profile(ModelMap map , HttpServletRequest request , @Valid User user){
		User tempUser = userRes.getOne(user.getId()) ;
    	User exist = userRes.findByUsernameAndOrgi(user.getUsername(), super.getOrgi(request)) ;
    	if(exist==null || exist.equals(user.getId())){
	    	if(tempUser != null){
	    		tempUser.setUname(user.getUname());
	    		tempUser.setEmail(user.getEmail());
	    		tempUser.setMobile(user.getMobile());
	    		tempUser.setAgent(user.isAgent());
	    		tempUser.setOrgi(super.getOrgi(request));
	    		if(!StringUtils.isBlank(user.getPassword())){
	    			tempUser.setPassword(UKTools.md5(user.getPassword()));
	    		}
	    		if(tempUser.getCreatetime() == null){
	    			tempUser.setCreatetime(new Date());
	    		}
	    		tempUser.setUpdatetime(new Date());
	    		userRes.save(tempUser) ;
	    		User sessionUser = super.getUser(request) ;
	    		tempUser.setRoleList(sessionUser.getRoleList()) ;
	    		super.setUser(request, tempUser);
	    	}
    	}
		return request(super.createRequestPageTempletResponse("redirect:/apps/content.html"));
	}
	
}
