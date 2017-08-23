package com.inxedu.os.edu.entity.help;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;



/**
 * 帮助菜单
 * @author http://www.inxedu.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class HelpMenu implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	/**
	 * 默认0一级菜单，非0二级菜单
	 */
	private Long parentId;
	/**
	 * 菜单名称
	 */
	private String name;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 菜单内容
	 */
	private String content;
	/**
	 * 排序
	 */
	private String sort;
	private String linkBuilding; // 外链
}

