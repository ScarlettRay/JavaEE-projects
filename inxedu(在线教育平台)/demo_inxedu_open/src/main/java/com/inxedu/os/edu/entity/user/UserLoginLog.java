package com.inxedu.os.edu.entity.user;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户登录日志
 * @author www.inxedu.com
 */
@Data
public class UserLoginLog implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int logId;//ID
	private Date loginTime;//登录时间
	private String ip;//登录IP
	private int userId;//用户ID
	private String osName;//操作系统
	private String userAgent;//浏览器

}
