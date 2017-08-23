package com.ukefu.util.client;

import java.util.List;

import com.corundumstudio.socketio.SocketIOClient;
import com.google.common.collect.ArrayListMultimap;

public class NettyAgentClient implements NettyClient{
	
	private ArrayListMultimap<String, SocketIOClient> agentClientsMap = ArrayListMultimap.create();
	
	public List<SocketIOClient> getClients(String key){
		return agentClientsMap.get(key) ;
	}
	
	public void putClient(String key , SocketIOClient client){
		agentClientsMap.put(key, client) ;
	}
	
	public void removeClient(String key , String id){
		List<SocketIOClient> keyClients = this.getClients(key) ;
		for(SocketIOClient client : keyClients){
			if(client.getSessionId().equals(id)){
				keyClients.remove(client) ;
				break ;
			}
		}
		if(keyClients.size() == 0){
			agentClientsMap.removeAll(key) ;
		}
	}
}
