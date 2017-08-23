package com.ukefu.webim.web.model;

public class AiUser implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id ;
	private String userid ;
	private long time ;
	
	public AiUser(String id , String userid, long time){
		this.id = id ;
		this.userid = userid ;
		this.time = time ;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
}
