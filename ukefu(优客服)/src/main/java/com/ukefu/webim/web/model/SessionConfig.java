package com.ukefu.webim.web.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "uk_sessionconfig")
@org.hibernate.annotations.Proxy(lazy = false)
public class SessionConfig implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 565678041210332017L;
	private String id ;
	private String orgi ;
	private Date createtime = new Date() ;
	private String creater ;
	private String username ;
	private String name ;
	
	private int maxuser = 10 ;	//每个坐席 接入最多访客数量
	
	private int initmaxuser = 10 ;	//坐席进入就绪状态的时候，会首次分配用户，initmaxuser控制 分配的用户数量，如果不设置，则会 直接 分配到最大用户数或将当前 等待队列分配完成
	
	private String sessionmsg ;	//欢迎消息
	private String distribution ;	//坐席分配策略
	private boolean lastagent;			//启用历史服务坐席优先分配
	private boolean sessiontimeout;	//启用超时提醒功能
	private int timeout = 120;				//超时时长 , 默认2分钟
	private String timeoutmsg ;			//超时提醒消息
	private boolean resessiontimeout;	//启用再次超时断开
	private int retimeout = 120;				//再次超时时长 ， 默认2分钟
	private String retimeoutmsg ;		//再次超时断开
	private boolean satisfaction ;	//启用满意度调查
	
	private String noagentmsg ;		//无客服在线提示信息
	private String agentbusymsg ;	//坐席忙提示信息
	
	private String successmsg ;		//坐席分配成功提示消息
	private String finessmsg ;		//服务结束提示消息
	
	private boolean agentreplaytimeout ;	//启用坐席回复超时
	private int agenttimeout;
	private String agenttimeoutmsg ;
	
	private boolean hourcheck ;		//启用工作时间段检查
	private String workinghours ;	//工作时间段，格式   9:00-12:00,13:30-15:30
	private String notinwhmsg ;		//非工作时间段 访客咨询的提示消息
	
	@Id
	@Column(length = 32)
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOrgi() {
		return orgi;
	}
	public void setOrgi(String orgi) {
		this.orgi = orgi;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public String getCreater() {
		return creater;
	}
	public void setCreater(String creater) {
		this.creater = creater;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSessionmsg() {
		return sessionmsg;
	}
	public void setSessionmsg(String sessionmsg) {
		this.sessionmsg = sessionmsg;
	}
	public String getDistribution() {
		return distribution;
	}
	public void setDistribution(String distribution) {
		this.distribution = distribution;
	}
	public boolean isLastagent() {
		return lastagent;
	}
	public void setLastagent(boolean lastagent) {
		this.lastagent = lastagent;
	}
	public boolean isSessiontimeout() {
		return sessiontimeout;
	}
	public void setSessiontimeout(boolean sessiontimeout) {
		this.sessiontimeout = sessiontimeout;
	}
	public int getTimeout() {
		return timeout;
	}
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
	public String getTimeoutmsg() {
		return timeoutmsg;
	}
	public void setTimeoutmsg(String timeoutmsg) {
		this.timeoutmsg = timeoutmsg;
	}
	public boolean isResessiontimeout() {
		return resessiontimeout;
	}
	public void setResessiontimeout(boolean resessiontimeout) {
		this.resessiontimeout = resessiontimeout;
	}
	public int getRetimeout() {
		return retimeout;
	}
	public void setRetimeout(int retimeout) {
		this.retimeout = retimeout;
	}
	public String getRetimeoutmsg() {
		return retimeoutmsg;
	}
	public void setRetimeoutmsg(String retimeoutmsg) {
		this.retimeoutmsg = retimeoutmsg;
	}
	public boolean isSatisfaction() {
		return satisfaction;
	}
	public void setSatisfaction(boolean satisfaction) {
		this.satisfaction = satisfaction;
	}
	public boolean isAgentreplaytimeout() {
		return agentreplaytimeout;
	}
	public void setAgentreplaytimeout(boolean agentreplaytimeout) {
		this.agentreplaytimeout = agentreplaytimeout;
	}
	public int getAgenttimeout() {
		return agenttimeout;
	}
	public void setAgenttimeout(int agenttimeout) {
		this.agenttimeout = agenttimeout;
	}
	public String getAgenttimeoutmsg() {
		return agenttimeoutmsg;
	}
	public void setAgenttimeoutmsg(String agenttimeoutmsg) {
		this.agenttimeoutmsg = agenttimeoutmsg;
	}
	public int getMaxuser() {
		return maxuser;
	}
	public void setMaxuser(int maxuser) {
		this.maxuser = maxuser;
	}
	public int getInitmaxuser() {
		return initmaxuser;
	}
	public void setInitmaxuser(int initmaxuser) {
		this.initmaxuser = initmaxuser;
	}
	public String getWorkinghours() {
		return workinghours;
	}
	public void setWorkinghours(String workinghours) {
		this.workinghours = workinghours;
	}
	public String getNotinwhmsg() {
		return notinwhmsg;
	}
	public void setNotinwhmsg(String notinwhmsg) {
		this.notinwhmsg = notinwhmsg;
	}
	public boolean isHourcheck() {
		return hourcheck;
	}
	public void setHourcheck(boolean hourcheck) {
		this.hourcheck = hourcheck;
	}
	public String getNoagentmsg() {
		return noagentmsg;
	}
	public void setNoagentmsg(String noagentmsg) {
		this.noagentmsg = noagentmsg;
	}
	public String getAgentbusymsg() {
		return agentbusymsg;
	}
	public void setAgentbusymsg(String agentbusymsg) {
		this.agentbusymsg = agentbusymsg;
	}
	public String getSuccessmsg() {
		return successmsg;
	}
	public void setSuccessmsg(String successmsg) {
		this.successmsg = successmsg;
	}
	public String getFinessmsg() {
		return finessmsg;
	}
	public void setFinessmsg(String finessmsg) {
		this.finessmsg = finessmsg;
	}
}
