package com.ukefu.webim.service.acd;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.Lock;

import org.apache.commons.lang.StringUtils;

import com.corundumstudio.socketio.SocketIONamespace;
import com.hazelcast.core.IMap;
import com.hazelcast.mapreduce.aggregation.Aggregations;
import com.hazelcast.mapreduce.aggregation.Supplier;
import com.hazelcast.query.PagingPredicate;
import com.hazelcast.query.SqlPredicate;
import com.ukefu.core.UKDataContext;
import com.ukefu.util.UKTools;
import com.ukefu.util.WebIMReport;
import com.ukefu.util.client.NettyClients;
import com.ukefu.webim.service.cache.CacheHelper;
import com.ukefu.webim.service.quene.AgentStatusOrgiFilter;
import com.ukefu.webim.service.quene.AgentUserOrgiFilter;
import com.ukefu.webim.service.repository.AgentServiceRepository;
import com.ukefu.webim.service.repository.AgentUserRepository;
import com.ukefu.webim.service.repository.AgentUserTaskRepository;
import com.ukefu.webim.service.repository.OnlineUserRepository;
import com.ukefu.webim.service.repository.SessionConfigRepository;
import com.ukefu.webim.util.router.OutMessageRouter;
import com.ukefu.webim.web.model.AgentReport;
import com.ukefu.webim.web.model.AgentService;
import com.ukefu.webim.web.model.AgentStatus;
import com.ukefu.webim.web.model.AgentUser;
import com.ukefu.webim.web.model.AgentUserTask;
import com.ukefu.webim.web.model.MessageOutContent;
import com.ukefu.webim.web.model.OnlineUser;
import com.ukefu.webim.web.model.SessionConfig;

@SuppressWarnings("deprecation")
public class ServiceQuene {
	
	/**
	 * 载入坐席 ACD策略配置
	 * @param orgi
	 * @return
	 */
	public static SessionConfig initSessionConfig(String orgi){
		SessionConfig sessionConfig = null;
		if(UKDataContext.getContext() != null && (sessionConfig = (SessionConfig) CacheHelper.getSystemCacheBean().getCacheObject(UKDataContext.SYSTEM_CACHE_SESSION_CONFIG, orgi)) == null){
			SessionConfigRepository agentUserRepository = UKDataContext.getContext().getBean(SessionConfigRepository.class) ;
			sessionConfig = agentUserRepository.findByOrgi(orgi) ;
			if(sessionConfig == null){
				sessionConfig = new SessionConfig() ;
			}else{
				CacheHelper.getSystemCacheBean().put(UKDataContext.SYSTEM_CACHE_SESSION_CONFIG,sessionConfig, orgi) ;
			}
		}
		return sessionConfig ;
	}
	
	/**
	 * 获得 当前服务状态
	 * @param orgi
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static AgentReport getAgentReport(String orgi){
		/**
		 * 统计当前在线的坐席数量
		 */
		AgentReport report = new AgentReport() ;
		IMap agentStatusMap = (IMap<String, Object>) CacheHelper.getAgentStatusCacheBean().getCache() ;
		AgentStatusOrgiFilter filter = new AgentStatusOrgiFilter(orgi) ;
		Long agents = (Long) agentStatusMap.aggregate(Supplier.fromKeyPredicate(filter), Aggregations.count()) ;
		report.setAgents(agents.intValue());
		
		/**
		 * 统计当前服务中的用户数量
		 */
		IMap agentUserMap = (IMap<String, Object>) CacheHelper.getAgentUserCacheBean().getCache() ;
		Long users = (Long) agentUserMap.aggregate(Supplier.fromKeyPredicate(new AgentUserOrgiFilter(orgi , UKDataContext.AgentUserStatusEnum.INSERVICE.toString())), Aggregations.count()) ;
		report.setUsers(users.intValue());
		
		Long queneUsers = (Long) agentUserMap.aggregate(Supplier.fromKeyPredicate(new AgentUserOrgiFilter(orgi , UKDataContext.AgentUserStatusEnum.INQUENE.toString())), Aggregations.count()) ;
		report.setInquene(queneUsers.intValue());
		
		return report;
	}
	
	public static int getQueneIndex(String userid , String orgi , long ordertime){
		
//		IList<AgentUser> queneUserList = (IList<AgentUser>) CacheHelper.getQueneUserCacheBean().getCache() ;
		int queneUsers = 0 ;
//		for(AgentUser agentUser : queneUserList){
//			if(agentUser.getOrgi().equals(orgi) && agentUser.getUserid().equals(userid)){
//				queneUsers ++ ;
//			}
//		}
		
		return queneUsers;
	}
	/**
	 * 为坐席批量分配用户
	 * @param agentStatus
	 */
	@SuppressWarnings("unchecked")
	public static void allotAgent(String agentno , String orgi){
		AgentStatus agentStatus = (AgentStatus) CacheHelper.getAgentStatusCacheBean().getCacheObject(agentno, orgi) ;
		List<AgentUser> agentStatusList = new ArrayList<AgentUser>();
		PagingPredicate<String, AgentUser> pagingPredicate = null ;
		if(agentStatus!=null && !StringUtils.isBlank(agentStatus.getSkill())){
			pagingPredicate = new PagingPredicate<String, AgentUser>(  new SqlPredicate( "status = 'inquene' AND ((agent = null AND skill = null) OR (skill = '" + agentStatus.getSkill() + "' AND agent = null) OR agent = '"+agentno+"') AND orgi = '" + orgi +"'") , 10 );
		}else{
			pagingPredicate = new PagingPredicate<String, AgentUser>(  new SqlPredicate( "status = 'inquene' AND ((agent = null AND skill = null) OR agent = '"+agentno+"') AND orgi = '" + orgi +"'") , 10 );
		}
		agentStatusList.addAll(((IMap<String , AgentUser>) CacheHelper.getAgentUserCacheBean().getCache()).values(pagingPredicate)) ;
		for(AgentUser agentUser : agentStatusList){
			SessionConfig sessionConfig = ServiceQuene.initSessionConfig(orgi) ;
			long maxusers = sessionConfig!=null ? sessionConfig.getMaxuser() : UKDataContext.AGENT_STATUS_MAX_USER ;
			if(agentStatus != null && agentStatus.getUsers() < maxusers){		//坐席未达到最大咨询访客数量
				CacheHelper.getAgentUserCacheBean().delete(agentUser.getUserid(), orgi);		//从队列移除，进入正在处理的队列， 避免使用 分布式锁
				try{
					AgentService agentService = processAgentService(agentStatus, agentUser, orgi) ;

					MessageOutContent outMessage = new MessageOutContent() ;
					outMessage.setMessage(ServiceQuene.getSuccessMessage(agentService , agentUser.getChannel()));
					outMessage.setMessageType(UKDataContext.MediaTypeEnum.TEXT.toString());
					outMessage.setCalltype(UKDataContext.CallTypeEnum.IN.toString());
					outMessage.setNickName(agentStatus.getUsername());
					outMessage.setCreatetime(UKTools.dateFormate.format(new Date()));

					if(!StringUtils.isBlank(agentUser.getUserid())){
						OutMessageRouter router = null ; 
						router  = (OutMessageRouter) UKDataContext.getContext().getBean(agentUser.getChannel()) ;
						if(router!=null){
							router.handler(agentUser.getUserid(), UKDataContext.MessageTypeEnum.MESSAGE.toString(), agentUser.getAppid(), outMessage);
						}
					}

					NettyClients.getInstance().sendAgentEventMessage(agentService.getAgentno(), UKDataContext.MessageTypeEnum.NEW.toString(), agentUser);
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}else{
				break ;
			}
		}
		publishMessage(orgi);
	}
	
	/**
	 * 为坐席批量分配用户
	 * @param agentStatus
	 * @throws Exception 
	 */
	public static void serviceFinish(AgentUser agentUser , String orgi) throws Exception{
		if(agentUser!=null){
			AgentStatus agentStatus = null;
			if(UKDataContext.AgentUserStatusEnum.INSERVICE.toString().equals(agentUser.getStatus()) && agentUser.getAgentno()!=null){
				agentStatus = (AgentStatus) CacheHelper.getAgentStatusCacheBean().getCacheObject(agentUser.getAgentno(), orgi) ;
			}
			CacheHelper.getAgentUserCacheBean().delete(agentUser.getUserid(), orgi);
			
			AgentUserRepository agentUserRepository = UKDataContext.getContext().getBean(AgentUserRepository.class) ;
			
			AgentUser agentUseDataBean = agentUserRepository.findByIdAndOrgi(agentUser.getId() , agentUser.getOrgi()) ;
			
			if(agentUseDataBean!=null){
				agentUseDataBean.setStatus(UKDataContext.AgentUserStatusEnum.END.toString()) ;
				if(agentUser.getServicetime()!=null){
					agentUseDataBean.setSessiontimes(System.currentTimeMillis() - agentUser.getServicetime().getTime()) ;
				}
				
				agentUserRepository.save(agentUseDataBean) ;
				
				/**
				 * 更新OnlineUser对象，变更为服务中，不可邀请 , WebIM渠道专用
				 */
				if(UKDataContext.ChannelTypeEnum.WEBIM.toString().equals(agentUser.getChannel())){
					OnlineUserRepository onlineUserRes = UKDataContext.getContext().getBean(OnlineUserRepository.class) ;
					List<OnlineUser> onlineUserList = onlineUserRes.findByUseridAndOrgi(agentUser.getUserid() , agentUser.getOrgi());
					if (onlineUserList.size() > 0) {
						OnlineUser onlineUser = onlineUserList.get(0) ;
						onlineUser.setInvitestatus(UKDataContext.OnlineUserInviteStatus.DEFAULT.toString());
						onlineUserRes.save(onlineUser) ;
					}
				}
			}
			
			AgentServiceRepository agentServiceRes = UKDataContext.getContext().getBean(AgentServiceRepository.class) ;
			if(!StringUtils.isBlank(agentUser.getAgentserviceid())){
				AgentService service = 	agentServiceRes.findByIdAndOrgi(agentUser.getAgentserviceid() , agentUser.getOrgi()) ;
				if(service!=null){
					service.setStatus(UKDataContext.AgentUserStatusEnum.END.toString());
					service.setEndtime(new Date());
					if(service.getServicetime()!=null){
						service.setSessiontimes(System.currentTimeMillis() - service.getServicetime().getTime());
					}
					
					AgentUserTaskRepository agentUserTaskRes = UKDataContext.getContext().getBean(AgentUserTaskRepository.class) ;
		    		AgentUserTask agentUserTask = agentUserTaskRes.getOne(agentUser.getId()) ;
		    		if(agentUserTask!=null){
		    			service.setAgentreplyinterval(agentUserTask.getAgentreplyinterval());
		    			service.setAgentreplytime(agentUserTask.getAgentreplytime());
		    			service.setAvgreplyinterval(agentUserTask.getAvgreplyinterval());
		    			service.setAvgreplytime(agentUserTask.getAvgreplytime());
		    			
		    			service.setUserasks(agentUserTask.getUserasks());
		    			service.setAgentreplys(agentUserTask.getAgentreplys());
		    		}
					
					agentServiceRes.save(service) ;
				}
			}
			
			if(agentStatus!=null){
				NettyClients.getInstance().sendAgentEventMessage(agentUser.getAgentno(), UKDataContext.MessageTypeEnum.END.toString(), agentUser);
			}
			
			OutMessageRouter router = null ; 
    		router  = (OutMessageRouter) UKDataContext.getContext().getBean(agentUser.getChannel()) ;
    		if(router!=null){
    			MessageOutContent outMessage = new MessageOutContent() ;
				outMessage.setMessage(ServiceQuene.getServiceFinishMessage(agentUser.getChannel()));
				outMessage.setMessageType(UKDataContext.AgentUserStatusEnum.END.toString());
				outMessage.setCalltype(UKDataContext.CallTypeEnum.IN.toString());
				if(agentStatus!=null){
					outMessage.setNickName(agentStatus.getUsername());
				}else{
					outMessage.setNickName(agentUser.getUsername());
				}
				outMessage.setCreatetime(UKTools.dateFormate.format(new Date()));
				outMessage.setAgentserviceid(agentUser.getAgentserviceid());
				
    			router.handler(agentUser.getUserid(), UKDataContext.MessageTypeEnum.STATUS.toString(), agentUser.getAppid(), outMessage);
    		}
			
    		if(agentStatus!=null){
				updateAgentStatus(agentStatus  , agentUser , orgi , false) ;
				SessionConfig sessionConfig = ServiceQuene.initSessionConfig(agentStatus.getOrgi()) ;
				long maxusers = sessionConfig!=null ? sessionConfig.getMaxuser() : UKDataContext.AGENT_STATUS_MAX_USER ;
				if(agentStatus.getUsers() < maxusers){
					allotAgent(agentStatus.getAgentno(), orgi);
				}
			}
    		
			publishMessage(orgi);
		}
	}
	
	/**
	 * 更新坐席当前服务中的用户状态，需要分布式锁
	 * @param agentStatus
	 * @param agentUser
	 * @param orgi
	 */
	public synchronized static void updateAgentStatus(AgentStatus agentStatus , AgentUser agentUser , String orgi , boolean in){
		Lock lock = CacheHelper.getAgentStatusCacheBean().getLock("LOCK", orgi) ;
		lock.lock();
		try{
			if( in == false && agentStatus.getUsers()>0){
				agentStatus.setUsers(agentStatus.getUsers() - 1);
			}else if(in = true){
				agentStatus.setUsers(agentStatus.getUsers() + 1);
			}
			agentStatus.setUpdatetime(new Date());
			CacheHelper.getAgentStatusCacheBean().put(agentStatus.getAgentno(), agentStatus, orgi);
		}finally{
			lock.unlock();
		}
	}
	
	public static void publishMessage(String orgi){
		/**
		 * 坐席状态改变，通知监测服务
		 */
		UKDataContext.getContext().getBean("agentNamespace" , SocketIONamespace.class) .getBroadcastOperations().sendEvent("status", ServiceQuene.getAgentReport(orgi));
	}
	/**
	 * 为用户分配坐席
	 * @param agentUser
	 */
	@SuppressWarnings("unchecked")
	public static AgentService allotAgent(AgentUser agentUser , String orgi){
		/**
		 * 查询条件，当前在线的 坐席，并且 未达到最大 服务人数的坐席
		 */
		
		List<AgentStatus> agentStatusList = new ArrayList<AgentStatus>();
		PagingPredicate<String, AgentStatus> pagingPredicate = null ;
		/**
		 * 处理ACD 的 技能组请求和 坐席请求
		 */
		if(!StringUtils.isBlank(agentUser.getAgent())){
			pagingPredicate = new PagingPredicate<String, AgentStatus>(  new SqlPredicate( " agentno = '" + agentUser.getAgent() + "' AND users < " + initSessionConfig(orgi).getMaxuser() ) , 1 );
		}else if(!StringUtils.isBlank(agentUser.getSkill())){
			pagingPredicate = new PagingPredicate<String, AgentStatus>(  new SqlPredicate( " skill = '" + agentUser.getSkill() + "' AND users < " + initSessionConfig(orgi).getMaxuser() ) , 1 );
		}else{
			pagingPredicate = new PagingPredicate<String, AgentStatus>(  new SqlPredicate( "users < " + initSessionConfig(orgi).getMaxuser() ) , 1 );
		}
		
		agentStatusList.addAll(((IMap<String , AgentStatus>) CacheHelper.getAgentStatusCacheBean().getCache()).values(pagingPredicate)) ;
		AgentService agentService = null ;	//放入缓存的对象
		try {
			agentService = processAgentService(agentStatusList.size()>0 ? agentStatusList.get(0) : null, agentUser, orgi) ;
		}catch(Exception ex){
			ex.printStackTrace(); 
		}
		publishMessage(orgi);
		return agentService;
	}
	/**
	 * 邀请访客进入当前对话，如果当前操作的 坐席是已就绪状态，则直接加入到当前坐席的 对话列表中，如果未登录，则分配给其他坐席
	 * @param agentno
	 * @param agentUser
	 * @param orgi
	 * @return
	 * @throws Exception
	 */
	public static AgentService allotAgentForInvite(String agentno , AgentUser agentUser , String orgi) throws Exception{
		AgentStatus agentStatus = (AgentStatus) CacheHelper.getAgentStatusCacheBean().getCacheObject(agentno, orgi) ;
		AgentService agentService = null ;
		if(agentStatus!=null){
			agentService = processAgentService(agentStatus, agentUser, orgi) ;
			publishMessage(orgi);
			NettyClients.getInstance().sendAgentEventMessage(agentService.getAgentno(), UKDataContext.MessageTypeEnum.NEW.toString(), agentUser);
		}else{
			agentService = allotAgent(agentUser, orgi) ;
		}
		return agentService;
	}
	
	/**
	 * 为访客 分配坐席， ACD策略，此处 AgentStatus 是建议 的 坐席，  如果启用了  历史服务坐席 优先策略， 则会默认检查历史坐席是否空闲，如果空闲，则分配，如果不空闲，则 分配当前建议的坐席
	 * @param agentStatus
	 * @param agentUser
	 * @param orgi
	 * @return
	 * @throws Exception
	 */
	private static AgentService processAgentService(AgentStatus agentStatus , AgentUser agentUser , String orgi) throws Exception{
		AgentService agentService = new AgentService();	//放入缓存的对象
		agentService.setOrgi(orgi);

		OnlineUserRepository onlineUserRes = UKDataContext.getContext().getBean(OnlineUserRepository.class) ;
		agentUser.setLogindate(new Date());
		List<OnlineUser> onlineUserList = onlineUserRes.findByUseridAndOrgi(agentUser.getUserid() , agentUser.getOrgi());
		OnlineUser onlineUser = null ;
		if (onlineUserList.size() > 0) {
			onlineUser = onlineUserList.get(0) ;
		}
		
		if(agentStatus!=null){	
			SessionConfig sessionConfig = initSessionConfig(orgi) ;
			if(sessionConfig.isLastagent()){	//启用了历史坐席优先 ， 查找 历史服务坐席
				List<WebIMReport> webIMaggList = UKTools.getWebIMDataAgg(onlineUserRes.findByOrgiForDistinctAgent(orgi, agentUser.getUserid())) ;
				if(webIMaggList.size()>0){
					for(WebIMReport report : webIMaggList){
						if(report.getData().equals(agentStatus.getAgentno())){
							break ;
						}else{
							AgentStatus hisAgentStatus = (AgentStatus) CacheHelper.getAgentStatusCacheBean().getCacheObject(report.getData(), orgi) ;
							if(hisAgentStatus!=null && hisAgentStatus.getUsers() < hisAgentStatus.getMaxusers()){
								agentStatus = hisAgentStatus ;	//变更为 历史服务坐席
								break ;
							}
						}
						
					}
				}
			}
			
			UKTools.copyProperties(agentUser, agentService); //复制属性
//			agentService.setId(null);
			agentService.setAgentno(agentStatus.getUserid());
			agentService.setAgentusername(agentStatus.getUsername());	//agent
			
			agentService.setAgentuserid(agentUser.getId());
			
			updateAgentStatus(agentStatus  , agentUser , orgi , true) ;
			
			long waittingtime = 0  ;
			if(agentUser.getWaittingtimestart()!=null){
				waittingtime = System.currentTimeMillis() - agentUser.getWaittingtimestart().getTime() ;
			}else if(agentUser.getCreatetime()!=null){
				waittingtime = System.currentTimeMillis() - agentUser.getCreatetime().getTime() ;
			}
			agentUser.setWaittingtime((int)waittingtime);
			
			agentUser.setServicetime(new Date());
			
			agentUser.setStatus(UKDataContext.AgentUserStatusEnum.INSERVICE.toString());
			agentService.setStatus(UKDataContext.AgentUserStatusEnum.INSERVICE.toString());
			agentService.setTimes(0);
			agentUser.setAgentno(agentService.getAgentno());
			
			AgentServiceRepository agentServiceRes = UKDataContext.getContext().getBean(AgentServiceRepository.class) ;
			
			if(!StringUtils.isBlank(agentUser.getName())){
				agentService.setName(agentUser.getName());
			}
			if(!StringUtils.isBlank(agentUser.getPhone())){
				agentService.setPhone(agentUser.getPhone());
			}
			if(!StringUtils.isBlank(agentUser.getEmail())){
				agentService.setEmail(agentUser.getEmail());
			}
			if(!StringUtils.isBlank(agentUser.getResion())){
				agentService.setResion(agentUser.getResion());
			}
			
			agentService.setServicetime(new Date());
			if(agentUser.getCreatetime()!=null){
				agentService.setWaittingtime((int) (System.currentTimeMillis() - agentUser.getCreatetime().getTime()));
				agentUser.setWaittingtime(agentService.getWaittingtime());
			}
			if (onlineUser != null ) {
				agentService.setOsname(onlineUser.getOpersystem());
				agentService.setBrowser(onlineUser.getBrowser());
				agentService.setDataid(onlineUser.getId());		//记录  onlineuser 的id
			}
			agentService.setLogindate(agentUser.getCreatetime());
			agentServiceRes.save(agentService) ;
			agentUser.setAgentserviceid(agentService.getId());
			agentUser.setLastgetmessage(new Date());
			agentUser.setLastmessage(new Date());
		}else{
			agentUser.setStatus(UKDataContext.AgentUserStatusEnum.INQUENE.toString());
			agentService.setStatus(UKDataContext.AgentUserStatusEnum.INQUENE.toString());
		}
		agentService.setDataid(agentUser.getId());
		/**
		 * 分配成功以后， 将用户 和坐席的对应关系放入到 缓存
		 */
		/**
		 * 将 AgentUser 放入到 当前坐席的 服务队列
		 */
		AgentUserRepository agentUserRepository = UKDataContext.getContext().getBean(AgentUserRepository.class) ;
		
		/**
		 * 更新OnlineUser对象，变更为服务中，不可邀请
		 */
		
		if(onlineUser!=null){
			onlineUser.setInvitestatus(UKDataContext.OnlineUserInviteStatus.INSERV.toString());
			onlineUserRes.save(onlineUser) ;
		}
		
		if(CacheHelper.getAgentUserCacheBean().getCacheObject(agentUser.getUserid(), UKDataContext.SYSTEM_ORGI) == null){
			agentUserRepository.save(agentUser);
		}else{
			AgentUser agentUseDataBean = agentUserRepository.findByIdAndOrgi(agentUser.getId() , agentUser.getOrgi()) ;
			if(agentUseDataBean!=null){
				agentUseDataBean.setAgentno(agentService.getAgentno()) ;
				agentUseDataBean.setStatus(agentUser.getStatus()) ;
				
				agentUserRepository.save(agentUseDataBean) ;
			}
		}

		CacheHelper.getAgentUserCacheBean().put(agentUser.getUserid(), agentUser , UKDataContext.SYSTEM_ORGI) ;

		return agentService ;
	}
	
	public static AgentUser deleteAgentUser(AgentUser agentUser, String orgi)
			throws Exception {
		if (agentUser!=null) {
			if (!UKDataContext.AgentUserStatusEnum.END.toString().equals(
					agentUser.getStatus())) {
				serviceFinish(agentUser, orgi);
			}
			UKDataContext.getContext().getBean(AgentUserRepository.class).delete(agentUser);
		}
		return agentUser;
	}
	
	
	/**
	 * 
	 * @param agentStatus
	 * @return
	 */
	public static String getSuccessMessage(AgentService agentService ,String channel){
		String queneTip = "<span id='agentno'>"+agentService.getAgentusername()+"</span>" ;
		if(!UKDataContext.ChannelTypeEnum.WEBIM.toString().equals(channel)){
			queneTip = agentService.getAgentusername() ;
		}
		SessionConfig sessionConfig = initSessionConfig(UKDataContext.SYSTEM_ORGI) ;
		String successMsg = "坐席分配成功，"+queneTip+"为您服务。"  ;
		if(!StringUtils.isBlank(sessionConfig.getNoagentmsg())){
			successMsg = sessionConfig.getSuccessmsg().replaceAll("\\{agent\\}", queneTip) ;
		}
		return successMsg ;
	}
	
	/**
	 * 
	 * @param agentStatus
	 * @return
	 */
	public static String getServiceFinishMessage(String channel){
		SessionConfig sessionConfig = initSessionConfig(UKDataContext.SYSTEM_ORGI) ;
		String queneTip = "坐席已断开和您的对话" ;
		if(!StringUtils.isBlank(sessionConfig.getNoagentmsg())){
			queneTip = sessionConfig.getFinessmsg();
		}
		return queneTip ;
	}
	
	public static String getNoAgentMessage(int queneIndex , String channel){
		String queneTip = "<span id='queneindex'>"+queneIndex+"</span>" ;
		if(!UKDataContext.ChannelTypeEnum.WEBIM.toString().equals(channel)){
			queneTip = String.valueOf(queneIndex) ;
		}
		SessionConfig sessionConfig = initSessionConfig(UKDataContext.SYSTEM_ORGI) ;
		String noAgentTipMsg = "坐席全忙，已进入等待队列，在您之前，还有 "+queneTip+" 位等待用户。"  ;
		if(!StringUtils.isBlank(sessionConfig.getNoagentmsg())){
			noAgentTipMsg = sessionConfig.getNoagentmsg().replaceAll("\\{num\\}", queneTip) ;
		}
		return noAgentTipMsg;
	}
	public static String getQueneMessage(int queneIndex , String channel){
		
		String queneTip = "<span id='queneindex'>"+queneIndex+"</span>" ;
		if(!UKDataContext.ChannelTypeEnum.WEBIM.toString().equals(channel)){
			queneTip = String.valueOf(queneIndex) ;
		}
		SessionConfig sessionConfig = initSessionConfig(UKDataContext.SYSTEM_ORGI) ;
		String agentBusyTipMsg = "正在排队，请稍候,在您之前，还有  "+queneTip+" 位等待用户。"  ;
		if(!StringUtils.isBlank(sessionConfig.getAgentbusymsg())){
			agentBusyTipMsg = sessionConfig.getAgentbusymsg().replaceAll("\\{num\\}", queneTip) ;
		}
		return agentBusyTipMsg;
	}
}
