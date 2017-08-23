package com.inxedu.os.edu.entity.user;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 学员
 * @author www.inxedu.com
 */
@Data
public class User implements Serializable{
	private static final long serialVersionUID = 1L;
	/**学员ID*/
	private int userId;
	/**手机号*/
	private String mobile;
	/**邮箱*/
	private String email;
	/**密码*/
	private String password;
	/**学员名*/
	private String userName;
	/**显示名（昵称）*/
	private String showName;
	/**性别 1男	2女*/
	private int sex;
	/**年龄*/
	private int age;
	/**注册时间*/
	private Date createTime;
	/**状态 1正常	2冻结*/
	private int isavalible;
	/**用户头像*/
	private String picImg;
	/**个人中心个性图片URL*/
	private String bannerUrl;
	/** 站内信未读消息数*/
	private int msgNum;
	/**系统自动消息未读消息数*/
	private int sysMsgNum;
    /**上传统计系统消息时间*/
    private Date lastSystemTime;

	private long loginTimeStamp;//登录时的当前时间戳
}
