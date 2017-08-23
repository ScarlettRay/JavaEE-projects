package com.ukefu.core;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.ConvertUtils;
import org.springframework.context.ApplicationContext;

import com.ukefu.util.DateConverter;

public class UKDataContext {

	public static final String USER_SESSION_NAME = "user";
	public static final String GUEST_USER = "guest";
	public static final String IM_USER_SESSION_NAME = "im_user";
	public static final String UKEFU_SYSTEM_DIC = "com.dic.system.template";
	public static final String UKEFU_SYSTEM_AUTH_DIC = "com.dic.auth.resource";
	public static final String UKEFU_SYSTEM_CALLCENTER = "callcenter";
	public static final String UKEFU_SYSTEM_INFOACQ = "infoacq";		//数据采集模式
	public static final String GUEST_USER_ID_CODE = "R3GUESTUSEKEY" ;
	public static final String WORKORDERS_CLOSED_STATUS = "uckefu_workorders_closed" ;
	public static final String SERVICE_QUENE_NULL_STR = "service_quene_null" ;
	public static final String DEFAULT_TYPE = "default"	;		//默认分类代码
	public static final String START = "start";					//流程默认的开始节点
	public static final String CACHE_SKILL = "cache_skill";					//技能组的缓存
	public static final String CACHE_AGENT = "cache_agent";					//坐席列表的缓存
	
	public static final int MAX_IMAGE_WIDTH = 460 ;		
	
	private static boolean imServerRunning = false ;			//IM服务状态
	
	public static final int AGENT_STATUS_MAX_USER = 10 ; 		//每个坐席 最大接待的 咨询数量
	
	public static final String SYSTEM_CACHE_SESSION_CONFIG = "session_config";
	
	public static final String SYSTEM_CACHE_AI_CONFIG = "ai_config";
	
	public static String SYSTEM_ORGI = "ukewo" ;
	public static Map<String , Boolean> model = new HashMap<String,Boolean>();
	
	private static int WebIMPort = 8081 ;
	
	private static ApplicationContext applicationContext ;
	
	static{
		ConvertUtils.register(new DateConverter(), java.util.Date.class); 
	}
	
	public enum AskSectionType{
		DEFAULT ;
		public String toString(){
			return super.toString().toLowerCase() ;
		}
	}
	
	public enum ProcessType{
		WORKORDER ;
		public String toString(){
			return super.toString().toLowerCase() ;
		}
	}
	
	public enum QuickTypeEnum{
		PUB,
		PRI;
		public String toString(){
			return super.toString().toLowerCase() ;
		}
	}
	
	
	
	
	public enum SalesNamesStatus{
		DIST,			//已分配
		NOTDIST;		//未分配
		public String toString(){
			return super.toString().toLowerCase() ;
		}
	}
	
	public enum CallCenterCallTypeEnum{
		INSIDELINE,
		ORGCALLOUT,
		ORGCALLIN,
		INSIDEQUENE,
		INSIDETRANS,			//已分配
		OUTSIDELINE,
		OUTSIDEQUENE,
		OUTSIDETRANS;		//未分配
		public String toString(){
			return super.toString().toLowerCase() ;
		}
	}
	
	
	public enum CallServiceStatus{
		INQUENE,
		RING,			//振铃
		INCALL,			//应答
		BRIDGE,			//桥接
		HOLD,			//已挂起
		HANGUP;			//已挂机
		public String toString(){
			return super.toString().toLowerCase() ;
		}
	}
	
	public enum CallChannelStatus{
		EARLY,
		DOWN;
		public String toString(){
			return super.toString().toLowerCase() ;
		}
	}
	
	public enum WxMpFileType{
		JPG,
		PNG;
		public String toString(){
			return super.toString().toLowerCase() ;
		}
	}
	
	public enum AgentInterType{
		SKILL , 
		AGENT ;
		public String toString(){
			return super.toString().toLowerCase() ;
		}
	}
	
	public enum ExtentionType{
		LINE, 
		IVR,
		SKILL,
		CONFERENCE,
		QUENE;
		public String toString(){
			return super.toString().toLowerCase() ;
		}
	}
	
	
	public enum AiItemType{
		USERINPUT , 
		AIREPLY;
		public String toString(){
			return super.toString().toLowerCase() ;
		}
	}
	
	public enum MultiUpdateType{
		SAVE , 
		UPDATE,
		DELETE;
		public String toString(){
			return super.toString().toLowerCase() ;
		}
	}
	
	public enum WorkOrdersEventType{
		ACCEPTUSER,		//审批人变更
		OTHER;			//其他变更
		public String toString(){
			return super.toString().toLowerCase() ;
		}
	}
	
	public enum BpmType{
		WORKORDERS;
		public String toString(){
			return super.toString().toLowerCase() ;
		}
	}
	
	public enum AskOperatorType{
		VIEWS ,
		COMMENTS,
		UPS;
		public String toString(){
			return super.toString().toLowerCase() ;
		}
	}
	
	public enum ModelType{
		USER ,
		WORKORDERS ,
		KBS, 
		SUMMARY,
		CCSUMMARY,WEBIM;
		public String toString(){
			return super.toString().toLowerCase() ;
		}
	}
	

	public enum AdapterType{
		TEXT,
		MEDIA,
		AGENT,
		XIAOE,
		INTER;
		public String toString(){
			return super.toString().toLowerCase() ;
		}
	}
	
	public enum MetadataTableType{
		UK_WORKORDERS;
		public String toString(){
			return super.toString().toLowerCase() ;
		}
	}
	
	public enum OnlineUserInviteStatus{
		DEFAULT,
		INVITE,
		REFUSE,
		INSERV,
		ACCEPT;
		public String toString(){
			return super.toString().toLowerCase() ;
		}
	}
	
	public enum CustomerTypeEnum{
		ENTERPRISE ,
		PERSONAL ; 
		
		public String toString(){
			return super.toString().toLowerCase() ;
		}
	}
	
	public enum WeiXinEventTypeEnum{
		SUB,
		UNSUB; 
		
		public String toString(){
			return super.toString().toLowerCase() ;
		}
	}
	
	public enum ChannelTypeEnum{
		WEIXIN ,
		WEIBO,
		WEBIM,
		PHONE,
		EMAIL;

		public String toString(){
			return super.toString().toLowerCase() ;
		}
	}
	
	public enum EventTypeEnum{
		SUB ,
		UNSUB,
		MENU;

		public String toString(){
			return super.toString().toLowerCase() ;
		}
	}
	
	public enum AgentStatusEnum{
		READY,
		NOTREADY,
		BUSY,
		IDLE,
		LEAVE,
		SERVICES;
		public String toString(){
			return super.toString().toLowerCase() ;
		}
	}
	
	public enum TaskStatusType{
		NORMAL("0"),
		READ("1"),
		RUNNING("2"),
		END("3");
		
		private String type ;
		
		TaskStatusType(String type){
			this.type = type ;
		} 
		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String toString(){
			return super.toString().toLowerCase() ;
		}
	}
	
	
	public enum NameSpaceEnum{
		
		IM("/im/user") ,
		AGENT("/im/agent"), 
		ENTIM("/im/ent") ,
		AIIM("/im/ai") ,
		CALLCENTER("/callcenter/event");
		
		private String namespace ;
		
		public String getNamespace() {
			return namespace;
		}

		public void setNamespace(String namespace) {
			this.namespace = namespace;
		}

		NameSpaceEnum(String namespace){
			this.namespace = namespace ;
		}
		
		public String toString(){
			return super.toString().toLowerCase() ;
		}
	}
	
	public enum MessageTypeEnum{
		NEW,
		MESSAGE, 
		END,
		TRANS, STATUS , AGENTSTATUS , SERVICE, WRITING;
		
		public String toString(){
			return super.toString().toLowerCase() ;
		}
	}
	
	public enum CallCenterResultStatusEnum{
		OK;
		
		public String toString(){
			return super.toString().toLowerCase() ;
		}
	}
	
	public enum MediaTypeEnum{
		TEXT,
		EVENT,
		IMAGE, 
		VIDIO,
		VOICE,LOCATION, FILE;
		
		public String toString(){
			return super.toString().toLowerCase() ;
		}
	}
	
	public enum CallTypeEnum{
		IN ,
		OUT, 
		SYSTEM;
		
		public String toString(){
			return super.toString().toLowerCase() ;
		}
	}
	
	public enum OnlineUserOperatorStatus{
		ONLINE,
		OFFLINE,
		REONLINE,
		CHAT,
		RECHAT,
		BYE,
		SEARCH,
		ACCESS;
		
		public String toString(){
			return super.toString().toLowerCase() ;
		}
	}
	
	public enum OnlineUserTypeStatus{
		USER,
		WEBIM,
		WEIXIN,
		APP,
		OTHER,
		WEIBO;
		
		public String toString(){
			return super.toString().toLowerCase() ;
		}
	}
	
	public enum AgentUserStatusEnum{
		INSERVICE,
		INQUENE, END;
		
		public String toString(){
			return super.toString().toLowerCase() ;
		}
	}
	
	public static void setApplicationContext(ApplicationContext context){
		applicationContext = context ;
	}
	
	public static ApplicationContext getContext(){
		return applicationContext ;
	}

	public static int getWebIMPort() {
		return WebIMPort;
	}

	public static void setWebIMPort(int webIMPort) {
		WebIMPort = webIMPort;
	}
	/**
	 * 系统级的加密密码 ， 从CA获取
	 * @return
	 */
	public static String getSystemSecrityPassword(){
		return "UCKeFu" ;
	}
	
	public static void setIMServerStatus(boolean running){
		imServerRunning = running ;
	}
	public static boolean getIMServerStatus(){
		return imServerRunning;
	}
}

