package com.ukefu.util.client;

import java.util.List;

import com.corundumstudio.socketio.SocketIOClient;

public interface NettyClient {
	
	public List<SocketIOClient> getClients(String key) ;
	
	public void putClient(String key , SocketIOClient client) ;
	
	public void removeClient(String key , String id) ;
}
