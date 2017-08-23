package com.inxedu.os.edu.entity.website;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 导航
 * @author www.inxedu.com
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class WebsiteNavigate implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	/**
	 * 导航的名称
	 */
	private String name;
	/**
	 * 跳转链接
	 */
	private String url;
	/**
	 * 是否在新页面打开0是1否
	 */
	private int newPage;
	/**
	 *  INDEX首页、USER个人中心、FRIENDLINK尾部友链、TAB尾部标签
	 */
	private String type;
	/**
	 * 显示排序
	 */
	private int orderNum;
	/**
	 * 0正常1冻结
	 */
	private int status ;
}
