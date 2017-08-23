package com.ukefu.webim.util.server.handler;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.ukefu.core.UKDataContext;
import com.ukefu.util.UKTools;
import com.ukefu.util.client.NettyClients;
import com.ukefu.webim.service.cache.CacheHelper;
import com.ukefu.webim.util.MessageUtils;
import com.ukefu.webim.util.router.OutMessageRouter;
import com.ukefu.webim.util.server.message.AgentStatusMessage;
import com.ukefu.webim.util.server.message.ChatMessage;
import com.ukefu.webim.util.server.message.NewRequestMessage;
import com.ukefu.webim.web.model.AiUser;
import com.ukefu.webim.web.model.MessageOutContent;

public class AiIMEventHandler     
{  
	protected SocketIOServer server;
	
    @Autowired  
    public AiIMEventHandler(SocketIOServer server)   
    {  
        this.server = server ;
    }  
    
    @OnConnect  
    public void onConnect(SocketIOClient client)  
    {  
    	try {
			String user = client.getHandshakeData().getSingleUrlParam("userid") ;
//			String orgi = client.getHandshakeData().getSingleUrlParam("orgi") ;
//			String session = client.getHandshakeData().getSingleUrlParam("session") ;
//			String appid = client.getHandshakeData().getSingleUrlParam("appid") ;
//			String agent = client.getHandshakeData().getSingleUrlParam("agent") ;
//			String skill = client.getHandshakeData().getSingleUrlParam("skill") ;
			
			if(!StringUtils.isBlank(user)){
//				/**
//				 * 加入到 缓存列表
//				 */
				NettyClients.getInstance().putIMEventClient(user, client);
//				
				MessageOutContent outMessage = new MessageOutContent() ;
		    	outMessage.setMessage("欢迎使用优客服小E，我来帮您解答问题");
		    	outMessage.setMessageType(UKDataContext.MessageTypeEnum.MESSAGE.toString());
		    	outMessage.setCalltype(UKDataContext.CallTypeEnum.IN.toString());
		    	outMessage.setNickName("AI");
				outMessage.setCreatetime(UKTools.dateFormate.format(new Date()));
				
				client.sendEvent(UKDataContext.MessageTypeEnum.STATUS.toString(), outMessage);
				CacheHelper.getOnlineUserCacheBean().put(client.getSessionId().toString(), new AiUser(client.getSessionId().toString(), user, System.currentTimeMillis()), UKDataContext.SYSTEM_ORGI);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }  
      
    
    //添加@OnDisconnect事件，客户端断开连接时调用，刷新客户端信息  
    @OnDisconnect  
    public void onDisconnect(SocketIOClient client)  
    {  
    	String user = client.getHandshakeData().getSingleUrlParam("userid") ;
    	NettyClients.getInstance().removeIMEventClient(user , client.getSessionId().toString());
    	CacheHelper.getOnlineUserCacheBean().delete(client.getSessionId().toString(),UKDataContext.SYSTEM_ORGI) ;
    }  
      
    //消息接收入口，网站有新用户接入对话  
    @OnEvent(value = "new")  
    public void onEvent(SocketIOClient client, AckRequest request, NewRequestMessage data)   
    {
    	
    }  
    
  //消息接收入口，坐席状态更新  
    @OnEvent(value = "agentstatus")  
    public void onEvent(SocketIOClient client, AckRequest request, AgentStatusMessage data)   
    {
    	System.out.println(data.getMessage());
    } 
    
    //消息接收入口，收发消息，用户向坐席发送消息和 坐席向用户发送消息  
    @OnEvent(value = "message")  
    public void onEvent(SocketIOClient client, AckRequest request, ChatMessage data)   
    {
    	if(data.getType() == null){
    		data.setType("message");
    	}
    	if(!StringUtils.isBlank(data.getMessage()) && data.getMessage().length() > 300){
    		data.setMessage(data.getMessage().substring(0 , 300));
    	}
    	/**
		 * 处理表情
		 */
    	data.setMessage(UKTools.processEmoti(data.getMessage()));
    	MessageOutContent outMessage = MessageUtils.createAiMessage(data , data.getAppid() , data.getChannel() , UKDataContext.CallTypeEnum.IN.toString() , UKDataContext.AiItemType.USERINPUT.toString() , UKDataContext.MediaTypeEnum.TEXT.toString(), data.getUserid()) ;
    	if(!StringUtils.isBlank(data.getUserid()) && UKDataContext.MessageTypeEnum.MESSAGE.toString().equals(data.getType())){
    		if(!StringUtils.isBlank(data.getTouser())){
	    		OutMessageRouter router = null ; 
	    		router  = (OutMessageRouter) UKDataContext.getContext().getBean(data.getChannel()) ;
	    		if(router!=null){
	    			router.handler(data.getTouser(), UKDataContext.MessageTypeEnum.MESSAGE.toString(), data.getAppid(), outMessage);
	    		}
	    	}
    		Object cacheData = (AiUser) CacheHelper.getOnlineUserCacheBean().getCacheObject(client.getSessionId().toString(),UKDataContext.SYSTEM_ORGI) ;
    		if(cacheData!=null && cacheData instanceof AiUser){
    			AiUser aiUser = (AiUser)cacheData ;
    			aiUser.setTime(System.currentTimeMillis());
    			CacheHelper.getOnlineUserCacheBean().put(client.getSessionId().toString(), aiUser, UKDataContext.SYSTEM_ORGI);
    		}
    	}
    	UKTools.ai(data);
    } 
}  