package com.ukefu.webim.web.model;

import com.ukefu.core.UKDataContext;


public class MessageInContent implements MessageDataBean{
	
	public String id ;
	private String nickName;
	private String orgi ;
	private String message ;
	private String filename ;
	private int filesize ;
	private String messageType; 
	private String fromUser;
	private String calltype = UKDataContext.CallTypeEnum.IN.toString() ;
	private String toUser;
	private SNSAccount snsAccount ;
	private AgentUser agentUser ;
	private Object channelMessage ;
	private String agentserviceid ;
	private Object user ;
	private String contextid ;
	private String createtime ;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getOrgi() {
		return orgi;
	}
	public void setOrgi(String orgi) {
		this.orgi = orgi;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	public String getFromUser() {
		return fromUser;
	}
	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}
	public String getToUser() {
		return toUser;
	}
	public void setToUser(String toUser) {
		this.toUser = toUser;
	}
	public SNSAccount getSnsAccount() {
		return snsAccount;
	}
	public void setSnsAccount(SNSAccount snsAccount) {
		this.snsAccount = snsAccount;
	}
	public AgentUser getAgentUser() {
		return agentUser;
	}
	public void setAgentUser(AgentUser agentUser) {
		this.agentUser = agentUser;
	}
	public Object getChannelMessage() {
		return channelMessage;
	}
	public void setChannelMessage(Object channelMessage) {
		this.channelMessage = channelMessage;
	}
	public Object getUser() {
		return user;
	}
	public void setUser(Object user) {
		this.user = user;
	}
	public String getContextid() {
		return contextid;
	}
	public void setContextid(String contextid) {
		this.contextid = contextid;
	}
	public String getCalltype() {
		return calltype;
	}
	public void setCalltype(String calltype) {
		this.calltype = calltype;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public int getFilesize() {
		return filesize;
	}
	public void setFilesize(int filesize) {
		this.filesize = filesize;
	}
	public String getAgentserviceid() {
		return agentserviceid;
	}
	public void setAgentserviceid(String agentserviceid) {
		this.agentserviceid = agentserviceid;
	}
}
