package com.ukefu.util.client;

import java.util.List;

import com.corundumstudio.socketio.SocketIOClient;


public class NettyClients {
	
	private static NettyClients clients = new NettyClients();
	
	private NettyIMClient imClients = new NettyIMClient();
	private NettyAgentClient agentClients = new NettyAgentClient();
	private NettyIMClient entIMClients = new NettyIMClient();
	private NettyCallCenterClient callCenterClients = new NettyCallCenterClient();
	
	public int size(){
		return imClients.size();
	}
	
	public static NettyClients getInstance(){
		return clients ;
	}
	
	public NettyCallCenterClient getCallCenterClients(){
		return this.callCenterClients ;
	}

	public void setImClients(NettyIMClient imClients) {
		this.imClients = imClients;
	}
	public void putIMEventClient(String id , SocketIOClient userClient){
		imClients.putClient(id, userClient);
	}
	
	public void closeIMEventClient(String id , String sessionid, String orgi){
		List<SocketIOClient> userClients = imClients.getClients(id) ;
		for(SocketIOClient userClient : userClients){
			if(userClient.getSessionId().toString().equals(sessionid)){
				userClient.disconnect();
			}
		}
	}
	public void removeIMEventClient(String id , String sessionid){
		imClients.removeClient(id, sessionid);
	}
	public void sendIMEventMessage(String id , String event , Object data){
		List<SocketIOClient> userClients = imClients.getClients(id) ;
		for(SocketIOClient userClient : userClients){
			userClient.sendEvent(event, data);
		}
	}
	
	public void setAgentClients(NettyAgentClient agentClients) {
		this.agentClients = agentClients;
	}
	public void putAgentEventClient(String id , SocketIOClient agentClient){
		agentClients.putClient(id, agentClient);
	}
	public void removeAgentEventClient(String id , String sessionid){
		agentClients.removeClient(id, sessionid);
	}
	public void sendAgentEventMessage(String id , String event , Object data){
		List<SocketIOClient> agents = agentClients.getClients(id) ;
		for(SocketIOClient agentClient : agents){
			agentClient.sendEvent(event, data);
		}
	}
	
	public void setEntImClients(NettyIMClient entIMClients) {
		this.entIMClients = entIMClients;
	}
	public void putEntIMEventClient(String id , SocketIOClient userClient){
		entIMClients.putClient(id, userClient);
	}
	public void removeEntIMEventClient(String id , String sessionid){
		entIMClients.removeClient(id, sessionid);
	}
	public void sendEntIMEventMessage(String id , String event , Object data){
		List<SocketIOClient> entims = entIMClients.getClients(id) ;
		for(SocketIOClient userClient : entims){
			userClient.sendEvent(event, data);
		}
	}
	public int getEntIMClientsNum(String user){
		return entIMClients.getClients(user)!=null ? entIMClients.getClients(user).size() : 0;
	}
	
	public void sendCallCenterMessage(String id , String event , Object data){
		List<SocketIOClient> ccClients = callCenterClients.getClients(id) ;
		for(SocketIOClient ccClient : ccClients){
			ccClient.sendEvent(event, data);
		}
	}
}
