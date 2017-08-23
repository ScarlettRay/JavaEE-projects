package com.ukefu.util.client;

import java.util.List;

import com.corundumstudio.socketio.SocketIOClient;
import com.google.common.collect.ArrayListMultimap;

/**
 * 呼叫中心登录坐席
 * @author iceworld
 *
 */
public class NettyCallCenterClient implements NettyClient{
	
	private ArrayListMultimap<String, SocketIOClient> callCenterMap = ArrayListMultimap.create();
	
	public List<SocketIOClient> getClients(String key){
		return callCenterMap.get(key) ;
	}
	
	public void putClient(String key , SocketIOClient client){
		callCenterMap.put(key, client) ;
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
			callCenterMap.removeAll(key) ;
		}
	}
}
