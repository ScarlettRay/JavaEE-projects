package com.ukefu.webim.web.model;

public class AgentReport implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5931219598388385394L;
	
	private int agents ;		//坐席数量
	private int users ;			//服务中的用户
	private int inquene ;		//队列中的用户
	private String type = "status";	//坐席状态
	
	public int getAgents() {
		return agents;
	}
	public void setAgents(int agents) {
		this.agents = agents;
	}
	public int getUsers() {
		return users;
	}
	public void setUsers(int users) {
		this.users = users;
	}
	public int getInquene() {
		return inquene;
	}
	public void setInquene(int inquene) {
		this.inquene = inquene;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
