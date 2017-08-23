package com.inxedu.os.edu.entity.system;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 后台系统用户实体
 * @author www.inxedu.com
 */


@Data
public class SysUser implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/**用户ID*/
	private int userId;
	/**用户登录名*/
	private String loginName;
	/**密码*/
	private String loginPwd;
	/**用户真名*/
	private String userName;
	/**用户状态 0正常 1冻结 2删除*/
	private int status;
	/**最后登录时间*/
	private Date lastLoginTime;
	/**最后登录IP号*/
	private String lastLoginIp;
	/**用户创建时间*/
	private Date createTime;
	/**邮箱*/
	private String email;
	/**手机号*/
	private String tel;
	/**角色ID*/
	private int roleId;

}
