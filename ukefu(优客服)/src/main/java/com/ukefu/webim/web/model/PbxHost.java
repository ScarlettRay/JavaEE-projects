package com.ukefu.webim.web.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "uk_callcenter_pbxhost")
@org.hibernate.annotations.Proxy(lazy = false)
public class PbxHost implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3932323765657445180L;
	private String id;
	private String name;
	private String hostname ;	//host name
	private String ipaddr ;		//IP
	private int port ;
	private String password ;	//pbx host password
	
	private boolean connected ;
	
	private boolean callcenter ;
	
	private String recordpath ;	//录音文件存储路径
	private String ivrpath ;	//IVR文件路径
	private String fspath ;		//FreeSwitch安装路径
	private String device ;	//设备厂商
	
	private String orgi;
	
	private boolean autoanswer ;
	
	private boolean sipautoanswer ;
	
	private String abscodec = "PCMU";	//默认的 呼叫编码 
	
	private String callbacktype ;	//回呼送号 号码
	private String callbacknumber ;
	
	private String creater ;
	private Date createtime = new Date();
	private Date updatetime = new Date();
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOrgi() {
		return orgi;
	}
	public void setOrgi(String orgi) {
		this.orgi = orgi;
	}
	public String getCreater() {
		return creater;
	}
	public void setCreater(String creater) {
		this.creater = creater;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public Date getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	public String getHostname() {
		return hostname;
	}
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getIpaddr() {
		return ipaddr;
	}
	public void setIpaddr(String ipaddr) {
		this.ipaddr = ipaddr;
	}
	@Transient
	public boolean isConnected() {
		return connected;
	}
	public void setConnected(boolean connected) {
		this.connected = connected;
	}
	public boolean isAutoanswer() {
		return autoanswer;
	}
	public void setAutoanswer(boolean autoanswer) {
		this.autoanswer = autoanswer;
	}
	public String getCallbacknumber() {
		return callbacknumber;
	}
	public void setCallbacknumber(String callbacknumber) {
		this.callbacknumber = callbacknumber;
	}
	public boolean isCallcenter() {
		return callcenter;
	}
	public void setCallcenter(boolean callcenter) {
		this.callcenter = callcenter;
	}
	public String getRecordpath() {
		return recordpath;
	}
	public void setRecordpath(String recordpath) {
		this.recordpath = recordpath;
	}
	public String getIvrpath() {
		return ivrpath;
	}
	public void setIvrpath(String ivrpath) {
		this.ivrpath = ivrpath;
	}
	public String getFspath() {
		return fspath;
	}
	public void setFspath(String fspath) {
		this.fspath = fspath;
	}
	public String getDevice() {
		return device;
	}
	public void setDevice(String device) {
		this.device = device;
	}
	public String getCallbacktype() {
		return callbacktype;
	}
	public void setCallbacktype(String callbacktype) {
		this.callbacktype = callbacktype;
	}
	public boolean isSipautoanswer() {
		return sipautoanswer;
	}
	public void setSipautoanswer(boolean sipautoanswer) {
		this.sipautoanswer = sipautoanswer;
	}
	public String getAbscodec() {
		return abscodec;
	}
	public void setAbscodec(String abscodec) {
		this.abscodec = abscodec;
	}
}
