package com.ukefu.webim.web.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Proxy;

@Entity
@Table(name="uk_agentuser")
@Proxy(lazy=false)

public class AgentUserTask {

	private String id ;
	private String userid ;
	private String orgi ;
	
	private int tokenum ;
	
	private String warnings ;
	private Date warningtime ;
	
	private int agentreplyinterval;
	private int agentreplytime;
	private int avgreplyinterval;
	private int avgreplytime;
	
	private int agentreplys;
	private int userasks;
	
	private Date lastmessage = new Date();
	private Date lastgetmessage = new Date();
	private String lastmsg;
	
	private String status ;
	
	private Date waittingtimestart = new Date();
	
	private Date reptime ;	//坐席长时间未回复 ，由系统发送消息 ，该字段记录 最后一次发送消息的时间 
	private String reptimes ;	//坐席长时间未回复 ，由系统发送消息 ，该字段记录系统发送的次数
	
	@Id
	@Column(length=32)
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy="uuid")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getTokenum() {
		return tokenum;
	}

	public void setTokenum(int tokenum) {
		this.tokenum = tokenum;
	}

	public Date getLastmessage() {
		return lastmessage;
	}

	public void setLastmessage(Date lastmessage) {
		this.lastmessage = lastmessage;
	}

	public Date getWaittingtimestart() {
		return waittingtimestart;
	}

	public void setWaittingtimestart(Date waittingtimestart) {
		this.waittingtimestart = waittingtimestart;
	}

	public Date getLastgetmessage() {
		return lastgetmessage;
	}

	public void setLastgetmessage(Date lastgetmessage) {
		this.lastgetmessage = lastgetmessage;
	}

	public Date getWarningtime() {
		return warningtime;
	}

	public void setWarningtime(Date warningtime) {
		this.warningtime = warningtime;
	}

	public Date getReptime() {
		return reptime;
	}

	public void setReptime(Date reptime) {
		this.reptime = reptime;
	}

	
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getOrgi() {
		return orgi;
	}

	public void setOrgi(String orgi) {
		this.orgi = orgi;
	}

	public String getWarnings() {
		return warnings;
	}

	public void setWarnings(String warnings) {
		this.warnings = warnings;
	}

	public String getReptimes() {
		return reptimes;
	}

	public void setReptimes(String reptimes) {
		this.reptimes = reptimes;
	}

	public String getLastmsg() {
		return lastmsg;
	}

	public void setLastmsg(String lastmsg) {
		this.lastmsg = lastmsg;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getAgentreplyinterval() {
		return agentreplyinterval;
	}

	public void setAgentreplyinterval(int agentreplyinterval) {
		this.agentreplyinterval = agentreplyinterval;
	}

	public int getAgentreplytime() {
		return agentreplytime;
	}

	public void setAgentreplytime(int agentreplytime) {
		this.agentreplytime = agentreplytime;
	}

	public int getAvgreplyinterval() {
		return avgreplyinterval;
	}

	public void setAvgreplyinterval(int avgreplyinterval) {
		this.avgreplyinterval = avgreplyinterval;
	}

	public int getAvgreplytime() {
		return avgreplytime;
	}

	public void setAvgreplytime(int avgreplytime) {
		this.avgreplytime = avgreplytime;
	}

	public int getAgentreplys() {
		return agentreplys;
	}

	public void setAgentreplys(int agentreplys) {
		this.agentreplys = agentreplys;
	}

	public int getUserasks() {
		return userasks;
	}

	public void setUserasks(int userasks) {
		this.userasks = userasks;
	}
	
}
