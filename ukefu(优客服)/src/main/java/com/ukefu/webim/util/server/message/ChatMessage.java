package com.ukefu.webim.util.server.message;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.ukefu.util.UKTools;
import com.ukefu.util.event.UserEvent;

@Entity
@Table(name = "uk_chat_message")
@org.hibernate.annotations.Proxy(lazy = false)
public class ChatMessage implements java.io.Serializable ,UserEvent{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3520656734252136303L;
	
	private String id = UKTools.getUUID();
	private String appid ;
	private String userid ;
	private String username ;
	
	
	private String touser ;
	private String tousername ;
	
	private String msgtype ;
	private String creater; 
	private String usession ;
	private String agentserviceid ;
	private String message ;
	private String orgi ;
	private String channel ;
	private String model ;			//消息所属模块， WebIM/EntIM
	private String chatype ;		//对话类型，是私聊还是群聊 或者是智能机器人对话
	private Date lastagentmsgtime ;	//前一条的坐席发送消息时间
	private Date lastmsgtime ;		//前一条的访客发送消息时间
	private int agentreplytime ;	//坐席回复消息时长		单位：秒
	private int agentreplyinterval ;//坐席回复消息时间间隔 ， 单位：秒
	
	private String headimgurl ;		//用户头像 ，临时用
	
	private String filename ;		//文件名
	private int filesize ;			//文件尺寸
	private String attachmentid ;	//附件ID
	
	private String mediaid ;
	private String locx ;	//location x
	private String locy ;	//location y
	
	private long updatetime = System.currentTimeMillis();
	
	private int duration ;	//音频时长
	
	private String scale ;		//地图级别
	
	private int tokenum ; 	//当前未读消息数量
	private String agentuser ;	
	
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUsession() {
		return usession;
	}
	public void setUsession(String usession) {
		this.usession = usession;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public String getOrgi() {
		return orgi;
	}

	public void setOrgi(String orgi) {
		this.orgi = orgi;
	}

	public String getTouser() {
		return touser;
	}

	public void setTouser(String touser) {
		this.touser = touser;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTousername() {
		return tousername;
	}

	public void setTousername(String tousername) {
		this.tousername = tousername;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}
	private String type ;		// 类型有两种 ， 一种 message ， 一种 writing
	private String contextid ;
	private String calltype ;
	private String createtime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getChatype() {
		return chatype;
	}
	public void setChatype(String chatype) {
		this.chatype = chatype;
	}
	public String getAgentserviceid() {
		return agentserviceid;
	}
	public void setAgentserviceid(String agentserviceid) {
		this.agentserviceid = agentserviceid;
	}
	@Transient
	public int getTokenum() {
		return tokenum;
	}
	public void setTokenum(int tokenum) {
		this.tokenum = tokenum;
	}
	@Transient
	public String getAgentuser() {
		return agentuser;
	}
	public void setAgentuser(String agentuser) {
		this.agentuser = agentuser;
	}
	@Transient
	public String getHeadimgurl() {
		return headimgurl;
	}
	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}
	public String getMsgtype() {
		return msgtype;
	}
	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}
	public String getCreater() {
		return creater;
	}
	public void setCreater(String creater) {
		this.creater = creater;
	}
	public String getMediaid() {
		return mediaid;
	}
	public void setMediaid(String mediaid) {
		this.mediaid = mediaid;
	}
	public String getLocx() {
		return locx;
	}
	public void setLocx(String locx) {
		this.locx = locx;
	}
	public String getLocy() {
		return locy;
	}
	public void setLocy(String locy) {
		this.locy = locy;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public String getScale() {
		return scale;
	}
	public void setScale(String scale) {
		this.scale = scale;
	}
	public long getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(long updatetime) {
		this.updatetime = updatetime;
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
	public String getAttachmentid() {
		return attachmentid;
	}
	public void setAttachmentid(String attachmentid) {
		this.attachmentid = attachmentid;
	}
	public Date getLastagentmsgtime() {
		return lastagentmsgtime;
	}
	public void setLastagentmsgtime(Date lastagentmsgtime) {
		this.lastagentmsgtime = lastagentmsgtime;
	}
	public int getAgentreplytime() {
		return agentreplytime;
	}
	public void setAgentreplytime(int agentreplytime) {
		this.agentreplytime = agentreplytime;
	}
	public Date getLastmsgtime() {
		return lastmsgtime;
	}
	public void setLastmsgtime(Date lastmsgtime) {
		this.lastmsgtime = lastmsgtime;
	}
	public int getAgentreplyinterval() {
		return agentreplyinterval;
	}
	public void setAgentreplyinterval(int agentreplyinterval) {
		this.agentreplyinterval = agentreplyinterval;
	}
}
