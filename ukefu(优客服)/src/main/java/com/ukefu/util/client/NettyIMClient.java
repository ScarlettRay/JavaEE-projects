package com.ukefu.util.client;

import java.util.List;

import com.corundumstudio.socketio.SocketIOClient;
import com.google.common.collect.ArrayListMultimap;

public class NettyIMClient implements NettyClient{
	
	private ArrayListMultimap<String, SocketIOClient> imClientsMap = ArrayListMultimap.create();
	
	public int size(){
		return imClientsMap.size() ;
	}
	
	public List<SocketIOClient> getClients(String key){
		return imClientsMap.get(key) ;
	}
	
	public void putClient(String key , SocketIOClient client){
		imClientsMap.put(key, client) ;
	}
	
	public void removeClient(String key , String id){
		List<SocketIOClient> keyClients = this.getClients(key) ;
		for(SocketIOClient client : keyClients){
			if(client.getSessionId().toString().equals(id)){
				keyClients.remove(client) ;
				break ;
			}
		}
		if(keyClients.size() == 0){
			imClientsMap.removeAll(key) ;
		}
	}
}
