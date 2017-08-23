package com.ukefu.webim.web.handler.apps.agent;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ukefu.core.UKDataContext;
import com.ukefu.util.Menu;
import com.ukefu.util.UKTools;
import com.ukefu.util.client.NettyClients;
import com.ukefu.util.extra.DataExchangeInterface;
import com.ukefu.webim.service.acd.ServiceQuene;
import com.ukefu.webim.service.cache.CacheHelper;
import com.ukefu.webim.service.repository.AgentServiceRepository;
import com.ukefu.webim.service.repository.AgentStatusRepository;
import com.ukefu.webim.service.repository.AgentUserContactsRepository;
import com.ukefu.webim.service.repository.AgentUserRepository;
import com.ukefu.webim.service.repository.AgentUserTaskRepository;
import com.ukefu.webim.service.repository.AttachmentRepository;
import com.ukefu.webim.service.repository.BlackListRepository;
import com.ukefu.webim.service.repository.ChatMessageRepository;
import com.ukefu.webim.service.repository.OnlineUserRepository;
import com.ukefu.webim.service.repository.OrganRepository;
import com.ukefu.webim.service.repository.QuickReplyRepository;
import com.ukefu.webim.service.repository.QuickTypeRepository;
import com.ukefu.webim.service.repository.SNSAccountRepository;
import com.ukefu.webim.service.repository.ServiceSummaryRepository;
import com.ukefu.webim.service.repository.TagRelationRepository;
import com.ukefu.webim.service.repository.TagRepository;
import com.ukefu.webim.service.repository.UserRepository;
import com.ukefu.webim.service.repository.WeiXinUserRepository;
import com.ukefu.webim.util.router.OutMessageRouter;
import com.ukefu.webim.util.server.message.ChatMessage;
import com.ukefu.webim.web.handler.Handler;
import com.ukefu.webim.web.model.AgentService;
import com.ukefu.webim.web.model.AgentServiceSummary;
import com.ukefu.webim.web.model.AgentStatus;
import com.ukefu.webim.web.model.AgentUser;
import com.ukefu.webim.web.model.AgentUserContacts;
import com.ukefu.webim.web.model.AgentUserTask;
import com.ukefu.webim.web.model.AttachmentFile;
import com.ukefu.webim.web.model.BlackEntity;
import com.ukefu.webim.web.model.MessageOutContent;
import com.ukefu.webim.web.model.OnlineUser;
import com.ukefu.webim.web.model.Organ;
import com.ukefu.webim.web.model.SessionConfig;
import com.ukefu.webim.web.model.TagRelation;
import com.ukefu.webim.web.model.UploadStatus;
import com.ukefu.webim.web.model.User;
import com.ukefu.webim.web.model.WeiXinUser;

@Controller
@RequestMapping("/agent")
public class AgentController extends Handler {
	
	@Autowired
	private AgentUserRepository agentUserRepository ;
	
	@Autowired
	private AgentStatusRepository agentStatusRepository ;
	
	@Autowired
	private AgentServiceRepository agentServiceRepository;
	
	@Autowired
	private OnlineUserRepository onlineUserRes;
	
	@Autowired
	private WeiXinUserRepository weiXinUserRes;
	
	@Autowired
	private ServiceSummaryRepository serviceSummaryRes ;
	
	@Autowired
	private ChatMessageRepository chatMessageRepository ;
	
	@Autowired
	private AttachmentRepository attachementRes;
	
	@Autowired 
	private BlackListRepository blackListRes ;
	
	@Autowired
	private TagRepository tagRes ;
	
	@Autowired
	private OrganRepository organRes ;
	
	@Autowired
	private TagRelationRepository tagRelationRes ;
	
	@Autowired
	private QuickReplyRepository quickReplyRes ;
	
	@Autowired
	private QuickTypeRepository quickTypeRes ;
	
	@Autowired
	private AgentUserTaskRepository agentUserTaskRes ;
	
	@Autowired
	private SNSAccountRepository snsAccountRes ;
	
	@Autowired
	private UserRepository userRes ;
	
	@Autowired
	private AgentUserContactsRepository agentUserContactsRes; 
	
	@Value("${web.upload-path}")
	private String path;	
	
	@RequestMapping("/index")
	@Menu(type = "apps", subtype = "agent")
	public ModelAndView index(ModelMap map , HttpServletRequest request ,HttpServletResponse response , @Valid String sort) {
		ModelAndView view = request(super.createAppsTempletResponse("/apps/agent/index")) ; 
		User user = super.getUser(request) ;
		Sort defaultSort = null ;
		if(StringUtils.isBlank(sort)){
			Cookie[] cookies = request.getCookies();//这样便可以获取一个cookie数组
			if(cookies!=null){
				for(Cookie cookie : cookies){
					if(cookie.getName().equals("sort")){
						sort = cookie.getValue() ;break ; 
					}
				}
			}
		}
		if(!StringUtils.isBlank(sort)){
			List<Order> list = new ArrayList<Order>();
			if(sort.equals("lastmessage")){
				list.add(new Order(Direction.DESC,"status")) ;
				list.add(new Order(Direction.DESC,"lastmessage")) ;
			}else if(sort.equals("logintime")){
				list.add(new Order(Direction.DESC,"status")) ;
				list.add(new Order(Direction.DESC,"createtime")) ;
			}else if(sort.equals("default")){
				defaultSort = new Sort(Direction.DESC,"status") ;
				Cookie name = new Cookie("sort",null);
				name.setMaxAge(0);
				response.addCookie(name);
			}
			if(list.size() > 0){
				defaultSort = new Sort(list) ;
				Cookie name = new Cookie("sort",sort);
				name.setMaxAge(60*60*24*365);
				response.addCookie(name);
				map.addAttribute("sort", sort) ;
			}
		}else{
			defaultSort = new Sort(Direction.DESC,"status") ;
		}
		List<AgentUser> agentUserList = agentUserRepository.findByAgentnoAndOrgi(user.getId() , super.getOrgi(request) , defaultSort);
		view.addObject("agentUserList", agentUserList) ;
		
		if(agentUserList.size() > 0){
			AgentUser agentUser = agentUserList.get(0) ;
			agentUser = (AgentUser) agentUserList.get(0);
			view.addObject("curagentuser", agentUser);
			
			if(!StringUtils.isBlank(agentUser.getAgentserviceid())){
				AgentServiceSummary summary = this.serviceSummaryRes.findByAgentserviceidAndOrgi(agentUser.getAgentserviceid(), super.getOrgi(request)) ;
				if(summary!=null){
					view.addObject("summary", summary) ;
				}
			}

			view.addObject("agentUserMessageList", this.chatMessageRepository.findByUsessionAndOrgi(agentUser.getUserid() , super.getOrgi(request), new PageRequest(0, 20, Direction.DESC , "updatetime")));
			
			if(UKDataContext.ChannelTypeEnum.WEIXIN.toString().equals(agentUser.getChannel())){
				List<WeiXinUser> weiXinUserList = weiXinUserRes.findByOpenidAndOrgi(agentUser.getUserid(), super.getOrgi(request)) ;
				if(weiXinUserList.size() > 0){
					WeiXinUser weiXinUser = weiXinUserList.get(0) ;
					view.addObject("weiXinUser",weiXinUser);
				}
			}else if(UKDataContext.ChannelTypeEnum.WEBIM.toString().equals(agentUser.getChannel())){
				List<OnlineUser> onlineUserList = this.onlineUserRes.findByUseridAndOrgi(agentUser.getUserid(), super.getOrgi(request)) ;
				if(onlineUserList.size()  > 0){
					OnlineUser onlineUser = onlineUserList.get(0) ;
					if(UKDataContext.OnlineUserOperatorStatus.OFFLINE.toString().equals(onlineUser.getStatus())){
						onlineUser.setBetweentime((int) (onlineUser.getUpdatetime().getTime() - onlineUser.getLogintime().getTime()));
					}else{
						onlineUser.setBetweentime((int) (System.currentTimeMillis() - onlineUser.getLogintime().getTime()));
					}
					view.addObject("onlineUser",onlineUser);
				}
			}
			if(!StringUtils.isBlank(agentUser.getAgentserviceid())){
				AgentService agentService = this.agentServiceRepository.findOne(agentUser.getAgentserviceid()) ;
				view.addObject("curAgentService", agentService) ;
				
				if(agentService!=null){
					/**
					 * 获取关联数据
					 */
					processRelaData(request, agentService, map);
				}
			}
			view.addObject("serviceCount", Integer
					.valueOf(this.agentServiceRepository
							.countByUseridAndOrgiAndStatus(agentUser
									.getUserid(), super.getOrgi(request),
									UKDataContext.AgentUserStatusEnum.END.toString())));
			
			view.addObject("tags", tagRes.findByOrgiAndTagtype(super.getOrgi(request) , UKDataContext.ModelType.USER.toString())) ;
			view.addObject("tagRelationList", tagRelationRes.findByUserid(agentUser.getUserid())) ;
			view.addObject("quickReplyList", quickReplyRes.findByOrgiAndCreater(super.getOrgi(request) , super.getUser(request).getId() , null)) ;
			
			view.addObject("pubQuickTypeList", quickTypeRes.findByOrgiAndQuicktype(super.getOrgi(request), UKDataContext.QuickTypeEnum.PUB.toString())) ;
		}
		return view ;
	}
	
	@RequestMapping("/agentusers")
	@Menu(type = "apps", subtype = "agent")
	public ModelAndView agentusers(HttpServletRequest request , String userid) {
		ModelAndView view = request(super.createRequestPageTempletResponse("/apps/agent/agentusers")) ;
		User user = super.getUser(request) ;
		view.addObject("agentUserList", agentUserRepository.findByAgentnoAndOrgi(user.getId() , super.getOrgi(request) , new Sort(Direction.ASC,"status"))) ;
		List<AgentUser> agentUserList = agentUserRepository.findByUseridAndOrgi(userid, super.getOrgi(request)) ; 
		view.addObject("curagentuser", agentUserList!=null && agentUserList.size() > 0 ? agentUserList.get(0) : null) ;
		return view ;
	}
	
	private void processRelaData(HttpServletRequest request , AgentService agentService , ModelMap map){
		map.addAttribute("agentServiceList", agentServiceRepository.findByUseridAndOrgiAndStatus(agentService.getUserid() , super.getOrgi(request), UKDataContext.AgentUserStatusEnum.END.toString())) ;
		if(!StringUtils.isBlank(agentService.getAppid())){
			map.addAttribute("snsAccount", snsAccountRes.findBySnsidAndOrgi(agentService.getAppid(), super.getOrgi(request))  ); 
		}
		List<AgentUserContacts> relaList = agentUserContactsRes.findByUseridAndOrgi(agentService.getUserid(), agentService.getOrgi()) ;
		if(relaList.size() > 0){
			AgentUserContacts agentUserContacts = relaList.get(0) ;
			if(UKDataContext.model.get("contacts")!=null && !StringUtils.isBlank(agentUserContacts.getContactsid())){
				DataExchangeInterface dataExchange = (DataExchangeInterface) UKDataContext.getContext().getBean("contacts") ;
				if(dataExchange!=null){
					map.addAttribute("contacts", dataExchange.getDataByIdAndOrgi(agentUserContacts.getContactsid(), super.getOrgi(request))) ;
				}
			}
			if(UKDataContext.model.get("workorders")!=null && !StringUtils.isBlank(agentUserContacts.getContactsid())){
				DataExchangeInterface dataExchange = (DataExchangeInterface) UKDataContext.getContext().getBean("workorders") ;
				if(dataExchange!=null){
					map.addAttribute("workOrdersList", dataExchange.getListDataByIdAndOrgi(agentUserContacts.getContactsid(), super.getUser(request).getId(),  super.getOrgi(request))) ;
				}
				map.addAttribute("contactsid", agentUserContacts.getContactsid()) ;
			}
		}
	}
	
	@RequestMapping("/agentuser")
	@Menu(type = "apps", subtype = "agent")
	public ModelAndView agentuser(ModelMap map , HttpServletRequest request , String id) {
		ModelAndView view = request(super.createRequestPageTempletResponse("/apps/agent/mainagentuser")) ;
		AgentUser agentUser = agentUserRepository.findByIdAndOrgi(id, super.getOrgi(request));
		if(agentUser!=null){
			view.addObject("curagentuser", agentUser) ;
			
			List<AgentUserTask> agentUserTaskList = agentUserTaskRes.findByIdAndOrgi(id, super.getOrgi(request)) ;
			if(agentUserTaskList.size() > 0){
				AgentUserTask agentUserTask = agentUserTaskList.get(0) ;
				agentUserTask.setTokenum(0);
				agentUserTaskRes.save(agentUserTask) ;
			}
			
			if(!StringUtils.isBlank(agentUser.getAgentserviceid())){
				AgentServiceSummary summary = this.serviceSummaryRes.findByAgentserviceidAndOrgi(agentUser.getAgentserviceid(), super.getOrgi(request)) ;
				if(summary!=null){
					view.addObject("summary", summary) ;
				}
			}
			
			view.addObject("agentUserMessageList", this.chatMessageRepository.findByUsessionAndOrgi(agentUser.getUserid() , super.getOrgi(request), new PageRequest(0, 20, Direction.DESC , "updatetime")));
			
			if(UKDataContext.ChannelTypeEnum.WEIXIN.toString().equals(agentUser.getChannel())){
				List<WeiXinUser> weiXinUserList = weiXinUserRes.findByOpenidAndOrgi(agentUser.getUserid(), super.getOrgi(request)) ;
				if(weiXinUserList.size() > 0){
					WeiXinUser weiXinUser = weiXinUserList.get(0) ;
					view.addObject("weiXinUser",weiXinUser);
				}
			}else if(UKDataContext.ChannelTypeEnum.WEBIM.toString().equals(agentUser.getChannel())){
				List<OnlineUser> onlineUserList = this.onlineUserRes.findByUseridAndOrgi(agentUser.getUserid(), super.getOrgi(request)) ;
				if(onlineUserList.size()  > 0){
					OnlineUser onlineUser = onlineUserList.get(0) ;
					if(UKDataContext.OnlineUserOperatorStatus.OFFLINE.toString().equals(onlineUser.getStatus())){
						onlineUser.setBetweentime((int) (onlineUser.getUpdatetime().getTime() - onlineUser.getLogintime().getTime()));
					}else{
						onlineUser.setBetweentime((int) (System.currentTimeMillis() - onlineUser.getLogintime().getTime()));
					}
					view.addObject("onlineUser",onlineUser);
				}
			}
			
			if(!StringUtils.isBlank(agentUser.getAgentserviceid())){
				AgentService agentService = this.agentServiceRepository.findOne(agentUser.getAgentserviceid()) ;
				
				if(agentService!=null){
					/**
					 * 获取关联数据
					 */
					processRelaData(request, agentService, map);
				}
			}
	
			view.addObject("serviceCount", Integer
					.valueOf(this.agentServiceRepository
							.countByUseridAndOrgiAndStatus(agentUser
									.getUserid(), super.getOrgi(request),
									UKDataContext.AgentUserStatusEnum.END
											.toString())));
			view.addObject("tagRelationList", tagRelationRes.findByUserid(agentUser.getUserid())) ;
		}
		
		view.addObject("tags", tagRes.findByOrgiAndTagtype(super.getOrgi(request) , UKDataContext.ModelType.USER.toString())) ;
		view.addObject("quickReplyList", quickReplyRes.findByOrgiAndCreater(super.getOrgi(request) , super.getUser(request).getId() , null)) ;

		return view ;
	}
	
	@RequestMapping("/workorders/list")
	@Menu(type = "apps", subtype = "workorderslist")
	public ModelAndView workorderslist(HttpServletRequest request , String contactsid , ModelMap map) {
		if(UKDataContext.model.get("workorders")!=null && !StringUtils.isBlank(contactsid)){
			DataExchangeInterface dataExchange = (DataExchangeInterface) UKDataContext.getContext().getBean("workorders") ;
			if(dataExchange!=null){
				map.addAttribute("workOrdersList", dataExchange.getListDataByIdAndOrgi(contactsid , super.getUser(request).getId(), super.getOrgi(request))) ;
			}
			map.addAttribute("contactsid", contactsid) ;
		}
		return request(super.createRequestPageTempletResponse("/apps/agent/workorders")) ;
	}
	
	
	@RequestMapping(value="/ready")  
	@Menu(type = "apps", subtype = "agent")
    public ModelAndView ready(HttpServletRequest request){ 
		User user = super.getUser(request) ;
    	AgentStatus agentStatus = agentStatusRepository.findByAgentnoAndOrgi(user.getId() , super.getOrgi(request));
    	if(agentStatus==null){
    		agentStatus = new AgentStatus() ;
	    	agentStatus.setUserid(user.getId());
	    	agentStatus.setUsername(user.getUname());
	    	agentStatus.setAgentno(user.getId());
	    	agentStatus.setLogindate(new Date());
	    	
	    	if(!StringUtils.isBlank(user.getOrgan())){
	    		Organ organ = organRes.findByIdAndOrgi(user.getOrgan(), super.getOrgi(request)) ;
	    		if(organ!=null && organ.isSkill()){
	    			agentStatus.setSkill(organ.getId());
	    			agentStatus.setSkillname(organ.getName());
	    		}
	    	}
	    	
	    	SessionConfig sessionConfig = ServiceQuene.initSessionConfig(super.getOrgi(request)) ;
	    	
	    	agentStatus.setUsers(agentUserRepository.countByAgentnoAndStatusAndOrgi(user.getId(), UKDataContext.AgentUserStatusEnum.INSERVICE.toString(), super.getOrgi(request)));
	    	
	    	agentStatus.setOrgi(user.getOrgi());
	    	agentStatus.setMaxusers(sessionConfig.getMaxuser());
	    	agentStatusRepository.save(agentStatus) ;
    	}
    	
    	agentStatus.setStatus(UKDataContext.AgentStatusEnum.READY.toString());
    	CacheHelper.getAgentStatusCacheBean().put(agentStatus.getAgentno(), agentStatus, user.getOrgi());
    	
    	ServiceQuene.allotAgent(agentStatus.getAgentno(), user.getOrgi());
    	
    	return request(super.createAppsTempletResponse("/public/success")) ; 
    }
	
	@RequestMapping(value="/notready") 
	@Menu(type = "apps", subtype = "agent")
    public ModelAndView notready(HttpServletRequest request){ 
		User user = super.getUser(request) ;
		AgentStatus agentStatus = agentStatusRepository.findByAgentnoAndOrgi(user.getId() , super.getOrgi(request));
		if(agentStatus!=null){
			agentStatusRepository.delete(agentStatus);
		}
    	CacheHelper.getAgentStatusCacheBean().delete(super.getUser(request).getId(), user.getOrgi());;
    	ServiceQuene.publishMessage(user.getOrgi());
    	
    	return request(super.createAppsTempletResponse("/public/success")) ; 
    }
	
	@RequestMapping(value="/clean") 
	@Menu(type = "apps", subtype = "clean" , access= false)
    public ModelAndView clean(HttpServletRequest request) throws Exception{ 
		List<AgentUser> agentUserList = agentUserRepository.findByAgentnoAndStatusAndOrgi(super.getUser(request).getId(), UKDataContext.AgentUserStatusEnum.END.toString(), super.getOrgi(request));
		List<AgentService> agentServiceList = new ArrayList<AgentService>();
		for(AgentUser agentUser : agentUserList){
			if(agentUser!=null && super.getUser(request).getId().equals(agentUser.getAgentno())){
				ServiceQuene.deleteAgentUser(agentUser, super.getOrgi(request));
				AgentService agentService = agentServiceRepository.findByIdAndOrgi(agentUser.getAgentserviceid(), super.getOrgi(request)) ;
				agentService.setStatus(UKDataContext.AgentUserStatusEnum.END.toString());
				agentServiceList.add(agentService) ;
			}
		}
		agentServiceRepository.save(agentServiceList) ;
		return request(super
				.createRequestPageTempletResponse("redirect:/agent/index.html"));
    }
	
	
	@RequestMapping({ "/end" })
	@Menu(type = "apps", subtype = "agent")
	public ModelAndView end(HttpServletRequest request, @Valid String userid)
			throws Exception {
		User user = super.getUser(request);
		AgentUser agentUser = agentUserRepository.findByIdAndOrgi(userid, super.getOrgi(request));
		if(agentUser!=null && super.getUser(request).getId().equals(agentUser.getAgentno())){
			ServiceQuene.deleteAgentUser(agentUser, user.getOrgi());
			if(!StringUtils.isBlank(agentUser.getAgentserviceid())){
				AgentService agentService = agentServiceRepository.findByIdAndOrgi(agentUser.getAgentserviceid(), super.getOrgi(request)) ;
				agentService.setStatus(UKDataContext.AgentUserStatusEnum.END.toString());
				agentServiceRepository.save(agentService) ;
			}
		}
		return request(super
				.createRequestPageTempletResponse("redirect:/agent/index.html"));
	}
	
	@RequestMapping({ "/readmsg" })
	@Menu(type = "apps", subtype = "agent")
	public ModelAndView readmsg(HttpServletRequest request, @Valid String userid)
			throws Exception {
		List<AgentUserTask> agentUserTaskList = agentUserTaskRes.findByIdAndOrgi(userid, super.getOrgi(request)) ;
		if(agentUserTaskList.size() > 0){
			AgentUserTask agentUserTask = agentUserTaskList.get(0) ;
			agentUserTask.setTokenum(0);
			agentUserTaskRes.save(agentUserTask);
		}
		return request(super.createRequestPageTempletResponse("/public/success"));
	}
	
	@RequestMapping({ "/blacklist/add" })
	@Menu(type = "apps", subtype = "blacklist")
	public ModelAndView blacklistadd(ModelMap map , HttpServletRequest request, @Valid String agentuserid , @Valid String agentserviceid ,  @Valid String userid)
			throws Exception {
		map.addAttribute("agentuserid", agentuserid);
		map.addAttribute("agentserviceid", agentserviceid);
		map.addAttribute("userid", userid);
		map.addAttribute("agentUser", agentUserRepository.findByIdAndOrgi(userid, super.getOrgi(request)));
		return request(super.createRequestPageTempletResponse("/apps/agent/blacklistadd")) ;
	}
	
	@RequestMapping({ "/blacklist/save" })
	@Menu(type = "apps", subtype = "blacklist")
	public ModelAndView blacklist(HttpServletRequest request, @Valid String agentuserid , @Valid String agentserviceid ,  @Valid String userid , @Valid BlackEntity blackEntity)
			throws Exception {
		User user = super.getUser(request);
		List<OnlineUser> onlineUserList = this.onlineUserRes.findByUseridAndOrgi(userid, super.getOrgi(request)) ;
		if(onlineUserList.size()  > 0){
			OnlineUser onlineUser = onlineUserList.get(0) ;
			BlackEntity tempBlackEntiry = blackListRes.findByUseridAndOrgi(onlineUser.getUserid(), super.getOrgi(request)) ;
			if(tempBlackEntiry == null){
				blackEntity.setUserid(userid);
				blackEntity.setCreater(user.getId());
				blackEntity.setOrgi(super.getOrgi(request));
				if(blackEntity.getControltime() > 0){
					blackEntity.setEndtime(new Date(System.currentTimeMillis() + blackEntity.getControltime()*3600*1000L));
				}
				blackEntity.setAgentid(user.getId());
				blackEntity.setAgentuser(onlineUser.getUsername());
				blackEntity.setSessionid(onlineUser.getSessionid());
				blackEntity.setAgentserviceid(agentserviceid);
				blackEntity.setChannel(onlineUser.getChannel());
				blackListRes.save(blackEntity) ;
			}else{
				if(blackEntity.getControltime() > 0){
					tempBlackEntiry.setEndtime(new Date(System.currentTimeMillis() + blackEntity.getControltime()*3600*1000L));
				}
				tempBlackEntiry.setDescription(tempBlackEntiry.getDescription());
				tempBlackEntiry.setControltime(blackEntity.getControltime());
				tempBlackEntiry.setAgentuser(onlineUser.getUsername());
				blackListRes.save(tempBlackEntiry) ;
				blackEntity = tempBlackEntiry ;
			}
			if(!StringUtils.isBlank(userid)){
				CacheHelper.getSystemCacheBean().put(userid, blackEntity, super.getOrgi(request));
			}
		}
		return end(request , agentuserid);
	}
	
	@RequestMapping("/tagrelation")
	@Menu(type = "apps", subtype = "tagrelation")
    public ModelAndView tagrelation(ModelMap map , HttpServletRequest request , @Valid String userid , @Valid String tagid,@Valid String dataid) {
		TagRelation tagRelation = tagRelationRes.findByUseridAndTagid(userid, tagid) ;
		if(tagRelation==null){
			tagRelation = new TagRelation();
			tagRelation.setUserid(userid);
			tagRelation.setTagid(tagid);
			tagRelation.setDataid(dataid);
			tagRelationRes.save(tagRelation) ;
		}else{
			tagRelationRes.delete(tagRelation);
		}
		return request(super
				.createRequestPageTempletResponse("/public/success"));
    }
	
	@RequestMapping("/image/upload")
    @Menu(type = "im" , subtype = "image" , access = false)
    public ModelAndView upload(ModelMap map,HttpServletRequest request , @RequestParam(value = "imgFile", required = false) MultipartFile imgFile , @Valid String id) throws IOException {
    	ModelAndView view = request(super.createRequestPageTempletResponse("/apps/agent/upload")) ; 
    	UploadStatus upload = null ;
    	String fileName = null ;
    	if(imgFile!=null && imgFile.getOriginalFilename().lastIndexOf(".") > 0){
    		File uploadDir = new File(path , "upload");
    		if(!uploadDir.exists()){
    			uploadDir.mkdirs() ;
    		}
    		String fileid = UKTools.md5(imgFile.getBytes()) , fileURL = null , targetFile = null;
    		ChatMessage data = new ChatMessage() ;
    		if(imgFile.getContentType()!=null && imgFile.getContentType().indexOf("image") >= 0){
	    		fileName = "upload/"+fileid+"_original"  ;
	    		File imageFile = new File(path , fileName) ;
	    		FileCopyUtils.copy(imgFile.getBytes(), imageFile);
	    		targetFile  = "upload/"+fileid ; 
	    		UKTools.processImage(new File(path , targetFile), imageFile) ;
	    		
	    		
	    		fileURL =  request.getScheme()+"://"+request.getServerName()+"/res/image.html?id="+targetFile ;
	    		if(request.getServerPort() == 80){
	    			fileURL = request.getScheme()+"://"+request.getServerName()+"/res/image.html?id="+targetFile;
				}else{
					fileURL = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/res/image.html?id="+targetFile;
				}
	    		upload = new UploadStatus("0" , fileURL); //图片直接发送给 客户，不用返回
	    		
    		}else{
    			String attachid = processAttachmentFile(imgFile, request) ;
    			
    			upload = new UploadStatus("0" , "/res/file.html?id="+attachid);
    			fileURL =  request.getScheme()+"://"+request.getServerName()+"/res/file.html?id="+attachid ;
	    		if(request.getServerPort() == 80){
	    			fileURL = request.getScheme()+"://"+request.getServerName()+"/res/file.html?id="+attachid;
				}else{
					fileURL = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/res/file.html?id="+attachid;
				}
    		}
    		
    		data.setFilename(imgFile.getOriginalFilename());
    		data.setFilesize((int) imgFile.getSize());
    		
    		OutMessageRouter router = null ; 
    		AgentUser agentUser = agentUserRepository.findByIdAndOrgi(id, super.getOrgi(request)) ;
			
			if(agentUser!=null){
	    		router  = (OutMessageRouter) UKDataContext.getContext().getBean(agentUser.getChannel()) ;
	    		MessageOutContent outMessage = new MessageOutContent() ;
	    		if(router!=null){
	    			outMessage.setMessage(fileURL);
	    			outMessage.setFilename(imgFile.getOriginalFilename());
	    			outMessage.setFilesize((int) imgFile.getSize());
	    			if(imgFile.getContentType()!=null && imgFile.getContentType().indexOf("image") >= 0){
	    				outMessage.setMessageType(UKDataContext.MediaTypeEnum.IMAGE.toString());
	    				data.setMsgtype(UKDataContext.MediaTypeEnum.IMAGE.toString());
	    			}else{
	    				outMessage.setMessageType(UKDataContext.MediaTypeEnum.FILE.toString());
	    				data.setMsgtype(UKDataContext.MediaTypeEnum.FILE.toString());
	    			}
					outMessage.setCalltype(UKDataContext.CallTypeEnum.OUT.toString());
					outMessage.setCreatetime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
					outMessage.setNickName(super.getUser(request).getUsername());
					
	    			router.handler(agentUser.getUserid(), UKDataContext.MessageTypeEnum.MESSAGE.toString(), agentUser.getAppid(), outMessage);
	    		}
	    		//同时发送消息给 坐席
	    		data.setMessage(fileURL);
	    		data.setId(UKTools.getUUID());
	    		data.setContextid(agentUser.getContextid());
	    		
	    		data.setAgentserviceid(agentUser.getAgentserviceid());
	    		
	    		data.setCalltype(UKDataContext.CallTypeEnum.OUT.toString());
	    		if(!StringUtils.isBlank(agentUser.getAgentno())){
	    			data.setTouser(agentUser.getUserid());
	    		}
	    		data.setChannel(agentUser.getChannel());
	    		
	    		data.setUsession(agentUser.getUserid());
	    		data.setAppid(agentUser.getAppid());
	    		data.setUserid(super.getUser(request).getId());
	    		
	    		data.setOrgi(super.getUser(request).getOrgi());
	    		
	    		data.setCreater(super.getUser(request).getId());
	    		data.setUsername(super.getUser(request).getUsername());
	    		
	    		chatMessageRepository.save(data) ;
	    		
	    		NettyClients.getInstance().sendAgentEventMessage(agentUser.getAgentno(), UKDataContext.MessageTypeEnum.MESSAGE.toString(), data);
			}
    		
    	}else{
    		upload = new UploadStatus("请选择图片文件");
    	}
    	map.addAttribute("upload", upload) ;
        return view ; 
    }
	
	private String processAttachmentFile(MultipartFile file , HttpServletRequest request) throws IOException{
    	String id = null ;
    	if(file.getSize() > 0){			//文件尺寸 限制 ？在 启动 配置中 设置 的最大值，其他地方不做限制
			String fileid = UKTools.md5(file.getBytes()) ;	//使用 文件的 MD5作为 ID，避免重复上传大文件
			if(!StringUtils.isBlank(fileid)){
    			AttachmentFile attachmentFile = new AttachmentFile() ;
    			attachmentFile.setCreater(super.getUser(request).getId());
    			attachmentFile.setOrgi(super.getOrgi(request));
    			attachmentFile.setOrgan(super.getUser(request).getOrgan());
    			attachmentFile.setModel(UKDataContext.ModelType.WEBIM.toString());
    			attachmentFile.setFilelength((int) file.getSize());
    			if(file.getContentType()!=null && file.getContentType().length() > 255){
    				attachmentFile.setFiletype(file.getContentType().substring(0 , 255));
    			}else{
    				attachmentFile.setFiletype(file.getContentType());
    			}
    			if(file.getOriginalFilename()!=null && file.getOriginalFilename().length() > 255){
    				attachmentFile.setTitle(file.getOriginalFilename().substring(0 , 255));
    			}else{
    				attachmentFile.setTitle(file.getOriginalFilename());
    			}
    			if(!StringUtils.isBlank(attachmentFile.getFiletype()) && attachmentFile.getFiletype().indexOf("image") >= 0){
    				attachmentFile.setImage(true);
    			}
    			attachmentFile.setFileid(fileid);
    			attachementRes.save(attachmentFile) ;
    			FileUtils.writeByteArrayToFile(new File(path , "app/webim/"+fileid), file.getBytes());
    			id = attachmentFile.getId();
			}
		}
    	return id  ;
    }
	
	
	@RequestMapping(value="/contacts")  
	@Menu(type = "apps", subtype = "contacts")
    public ModelAndView contacts(ModelMap map , HttpServletRequest request , @Valid String contactsid , @Valid String userid , @Valid String agentserviceid, @Valid String agentuserid){ 
		if(!StringUtils.isBlank(userid) && !StringUtils.isBlank(contactsid)){
			List<OnlineUser> onlineUserList = this.onlineUserRes.findByUseridAndOrgi(userid, super.getOrgi(request)) ;
			if(onlineUserList.size()  > 0){
				OnlineUser onlineUser = onlineUserList.get(0) ;
				onlineUser.setContactsid(contactsid);
				this.onlineUserRes.save(onlineUser) ;
			}
			AgentService agentService = this.agentServiceRepository.findOne(agentserviceid) ;
			if(agentService!=null){
				agentService.setContactsid(contactsid);
				this.agentServiceRepository.save(agentService) ;
			
				List<AgentUserContacts> agentUserContactsList = agentUserContactsRes.findByUseridAndOrgi(userid, super.getOrgi(request)) ;
				if(agentUserContactsList.size() == 0){
					AgentUserContacts agentUserContacts = new AgentUserContacts() ;
					agentUserContacts.setAppid(agentService.getAppid());
					agentUserContacts.setChannel(agentService.getChannel());
					agentUserContacts.setContactsid(contactsid);
					agentUserContacts.setUserid(userid);
					agentUserContacts.setCreater(super.getUser(request).getId());
					agentUserContacts.setOrgi(super.getOrgi(request));
					agentUserContacts.setCreatetime(new Date());
					agentUserContactsRes.save(agentUserContacts) ;
				}else{
					AgentUserContacts agentUserContacts = agentUserContactsList.get(0) ;
					agentUserContacts.setContactsid(contactsid);
					agentUserContactsRes.save(agentUserContacts) ;
				}
			}
			DataExchangeInterface dataExchange = (DataExchangeInterface) UKDataContext.getContext().getBean("contacts") ;
			if(dataExchange!=null){
				map.addAttribute("contacts", dataExchange.getDataByIdAndOrgi(contactsid, super.getOrgi(request))) ;
			}
		}
		
    	return request(super.createRequestPageTempletResponse("/apps/agent/contacts")) ; 
    }
	
	@RequestMapping(value="/summary")  
	@Menu(type = "apps", subtype = "summary")
    public ModelAndView summary(ModelMap map , HttpServletRequest request , @Valid String userid , @Valid String agentserviceid, @Valid String agentuserid){ 
		if(!StringUtils.isBlank(userid) && !StringUtils.isBlank(agentuserid)){
			AgentUser agentUser = this.agentUserRepository.findByIdAndOrgi(agentuserid, super.getOrgi(request)) ;
			if(agentUser!=null && !StringUtils.isBlank(agentUser.getAgentserviceid())){
				AgentServiceSummary summary = this.serviceSummaryRes.findByAgentserviceidAndOrgi(agentUser.getAgentserviceid(), super.getOrgi(request)) ;
				if(summary!=null){
					map.addAttribute("summary", summary) ;
				}
			}
			map.addAttribute("tags", tagRes.findByOrgiAndTagtype(super.getOrgi(request) , UKDataContext.ModelType.SUMMARY.toString())) ;
			map.addAttribute("userid", userid) ;
			map.addAttribute("agentserviceid", agentserviceid) ;
			map.addAttribute("agentuserid", agentuserid) ;
			
		}
		
    	return request(super.createRequestPageTempletResponse("/apps/agent/summary")) ; 
    }
	
	@RequestMapping(value="/summary/save")  
	@Menu(type = "apps", subtype = "summarysave")
    public ModelAndView summarysave(ModelMap map , HttpServletRequest request , @Valid AgentServiceSummary summary , @Valid String contactsid , @Valid String userid , @Valid String agentserviceid, @Valid String agentuserid){ 
		if(!StringUtils.isBlank(userid) && !StringUtils.isBlank(agentuserid)){
			summary.setOrgi(super.getOrgi(request));
			summary.setCreater(super.getUser(request).getId());
			
			summary.setCreatetime(new Date());
			
			AgentService service = agentServiceRepository.findByIdAndOrgi(agentserviceid, super.getOrgi(request)) ;
			summary.setAgent(service.getAgentno());
			summary.setAgentno(service.getAgentno());
			summary.setUsername(service.getUsername());
			summary.setAgentusername(service.getAgentusername());
			summary.setChannel(service.getChannel());
			summary.setLogindate(service.getLogindate());
			summary.setContactsid(service.getContactsid());
			summary.setEmail(service.getEmail());
			summary.setPhonenumber(service.getPhone());
			serviceSummaryRes.save(summary) ;
		}
		
    	return request(super.createRequestPageTempletResponse("redirect:/agent/agentuser.html?id="+agentuserid)) ; 
    }
	
	@RequestMapping(value="/transfer")  
	@Menu(type = "apps", subtype = "transfer")
    public ModelAndView transfer(ModelMap map , HttpServletRequest request , @Valid String userid , @Valid String agentserviceid, @Valid String agentuserid){ 
		if(!StringUtils.isBlank(userid) && !StringUtils.isBlank(agentuserid)){
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
			map.addAttribute("userid", userid) ;
			map.addAttribute("agentserviceid", agentserviceid) ;
			map.addAttribute("agentuserid", agentuserid) ;
			map.addAttribute("agentservice", this.agentServiceRepository.findByIdAndOrgi(agentserviceid, super.getOrgi(request))) ;
			
		}
		
    	return request(super.createRequestPageTempletResponse("/apps/agent/transfer")) ; 
    }
	
	@RequestMapping(value="/transfer/save")  
	@Menu(type = "apps", subtype = "transfersave")
    public ModelAndView transfersave(ModelMap map , HttpServletRequest request , @Valid String userid , @Valid String agentserviceid, @Valid String agentuserid, @Valid String agentno , @Valid String memo){ 
		if(!StringUtils.isBlank(userid) && !StringUtils.isBlank(agentuserid) && !StringUtils.isBlank(agentno)){
			AgentUser agentUser = (AgentUser) CacheHelper.getAgentUserCacheBean().getCacheObject(userid, super.getOrgi(request)) ;
			AgentService agentService = this.agentServiceRepository.findByIdAndOrgi(agentserviceid, super.getOrgi(request)) ;
			if(agentUser != null){
				
				agentUser.setAgentno(agentno);
				CacheHelper.getAgentUserCacheBean().put(userid , agentUser , super.getOrgi(request)) ;
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
				agentUser = agentUserRepository.findByIdAndOrgi(agentuserid, super.getOrgi(request));
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
				agentServiceRepository.save(agentService) ;
			}
		}
		
    	return request(super.createRequestPageTempletResponse("redirect:/agent/index.html")) ; 
    }
}