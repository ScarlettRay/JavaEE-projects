package com.inxedu.os.edu.entity.system;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *	后台系统权限
 * @author www.inxedu.com
 */
@Data
public class SysFunction implements Serializable{
	private static final long serialVersionUID = 1L;
	/**权限ID*/
	private int functionId;
	/**权限父ID*/
	private int parentId;
	/**权限名*/
	private String functionName;
	/**权限路径*/
	private String functionUrl;
	/**权限类型 1菜单 2功能*/
	private int functionType;
	/**权限创建时间*/
	private Date createTime;
	/**排序*/
	private int sort;
	//图片路径
	private String imageUrl;

	/**子级权限List*/
	private List<SysFunction> childList;

}
