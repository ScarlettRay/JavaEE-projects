package com.inxedu.os.edu.entity.websiteehcache;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 缓存管理
 * @author www.inxedu.com
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class WebsiteEhcache implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4611745081384979974L;
	
	private Long id;//主键自增
	private String ehcacheKey;//ehcache key
	private String ehcacheDesc;//ehcache 描述

}
