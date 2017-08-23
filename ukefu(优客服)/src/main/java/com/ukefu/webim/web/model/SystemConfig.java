package com.ukefu.webim.web.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "uk_systemconfig")
@org.hibernate.annotations.Proxy(lazy = false)
public class SystemConfig implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8675632756915176657L;
	private String id ;
	private String name ;
	private String title ;	
	
	private String theme = "01";	//默认绿色
	
	private String code ;
	private String orgi ;
	private String description;
	private String memo ;
	private String creater;
	private Date createtime;
	private Date updatetime;
	private String loglevel ;
	private boolean enablessl ;
	private String jksfile ;
	private String jkspassword ;
	private String mapkey ;
	private boolean workorders ;	//工单启用三栏布局
	
	private boolean callout ;		//允许在电话号码上点击呼出
	private boolean auth ;			//启用权限控制
	
	private boolean callcenter ;	//启用呼叫中心模板配置
	private String cc_extention ;
	private String cc_quene ;
	private String cc_router ;
	private String cc_ivr ;
	private String cc_acl ;
	private String cc_siptrunk ;
	private String cc_callcenter ;
	
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTheme() {
		return theme;
	}
	public void setTheme(String theme) {
		this.theme = theme;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getOrgi() {
		return orgi;
	}
	public void setOrgi(String orgi) {
		this.orgi = orgi;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
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
	public String getLoglevel() {
		return loglevel;
	}
	public void setLoglevel(String loglevel) {
		this.loglevel = loglevel;
	}
	
	public boolean isEnablessl() {
		return enablessl;
	}
	public void setEnablessl(boolean enablessl) {
		this.enablessl = enablessl;
	}
	public String getJksfile() {
		return jksfile;
	}
	public void setJksfile(String jksfile) {
		this.jksfile = jksfile;
	}
	public String getJkspassword() {
		return jkspassword;
	}
	public void setJkspassword(String jkspassword) {
		this.jkspassword = jkspassword;
	}
	
	public String getMapkey() {
		return mapkey;
	}
	public void setMapkey(String mapkey) {
		this.mapkey = mapkey;
	}
	
	public boolean isWorkorders() {
		return workorders;
	}
	public void setWorkorders(boolean workorders) {
		this.workorders = workorders;
	}
	public boolean isCallcenter() {
		return callcenter;
	}
	public void setCallcenter(boolean callcenter) {
		this.callcenter = callcenter;
	}
	public String getCc_extention() {
		return cc_extention;
	}
	public void setCc_extention(String cc_extention) {
		this.cc_extention = cc_extention;
	}
	public String getCc_quene() {
		return cc_quene;
	}
	public void setCc_quene(String cc_quene) {
		this.cc_quene = cc_quene;
	}
	public String getCc_router() {
		return cc_router;
	}
	public void setCc_router(String cc_router) {
		this.cc_router = cc_router;
	}
	public String getCc_ivr() {
		return cc_ivr;
	}
	public void setCc_ivr(String cc_ivr) {
		this.cc_ivr = cc_ivr;
	}
	public String getCc_acl() {
		return cc_acl;
	}
	public void setCc_acl(String cc_acl) {
		this.cc_acl = cc_acl;
	}
	public String getCc_siptrunk() {
		return cc_siptrunk;
	}
	public void setCc_siptrunk(String cc_siptrunk) {
		this.cc_siptrunk = cc_siptrunk;
	}
	public String getCc_callcenter() {
		return cc_callcenter;
	}
	public void setCc_callcenter(String cc_callcenter) {
		this.cc_callcenter = cc_callcenter;
	}
	
	public boolean isCallout() {
		return callout;
	}
	public void setCallout(boolean callout) {
		this.callout = callout;
	}
	public boolean isAuth() {
		return auth;
	}
	public void setAuth(boolean auth) {
		this.auth = auth;
	}
	@Transient
	public String getBackgroundColor(){
		String backgroundColor = "background-color:#32c24d !important;" ;
		if(!StringUtils.isBlank(this.theme) && this.theme.equals("01")){
			backgroundColor = "background-color:#32c24d !important;" ;
		}else if(!StringUtils.isBlank(this.theme) && this.theme.equals("02")){
			backgroundColor = "background-color:#373d41 !important;" ;
		}else if(!StringUtils.isBlank(this.theme) && this.theme.equals("03")){
			backgroundColor = "background-image: -webkit-linear-gradient(right,#00c89d 0,#1E90FF 100%) !important;" ;
		}
		return backgroundColor ;
	}
	@Transient
	public String getColor(){
		String color = "color:#32c24d;" ;
		if(!StringUtils.isBlank(this.theme) && this.theme.equals("01")){
			color = "color:#32c24d;" ;
		}else if(!StringUtils.isBlank(this.theme) && this.theme.equals("02")){
			color = "color:#32c24d;" ;
		}else if(!StringUtils.isBlank(this.theme) && this.theme.equals("03")){
			color = "color:#1E90FF;" ;
		}
		return color ;
	}
	
	@Transient
	public String getBgColor(){
		String color = "background-color:#32c24d;" ;
		if(!StringUtils.isBlank(this.theme) && this.theme.equals("01")){
			color = "background-color:#32c24d !important;" ;
		}else if(!StringUtils.isBlank(this.theme) && this.theme.equals("02")){
			color = "background-color:#32c24d !important;" ;
		}else if(!StringUtils.isBlank(this.theme) && this.theme.equals("03")){
			color = "background-color:#1E90FF !important;" ;
		}
		return color ;
	}
	
	@Transient
	public String getStyleColor(){
		String color = "#32c24d  !important;" ;
		if(!StringUtils.isBlank(this.theme) && this.theme.equals("01")){
			color = "#32c24d  !important;" ;
		}else if(!StringUtils.isBlank(this.theme) && this.theme.equals("02")){
			color = "#32c24d  !important;" ;
		}else if(!StringUtils.isBlank(this.theme) && this.theme.equals("03")){
			color = "#1E90FF  !important;" ;
		}
		return color ;
	}
}
