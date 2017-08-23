package com.inxedu.os.edu.entity.system;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 角色
 * author www.inxedu.com
 */
@Data
public class SysRole implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private int roleId;
	private String roleName;
	private Date createTime;
}
