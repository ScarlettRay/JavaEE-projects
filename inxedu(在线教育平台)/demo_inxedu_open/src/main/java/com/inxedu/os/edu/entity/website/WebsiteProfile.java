package com.inxedu.os.edu.entity.website;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 网站配置实体
 * @author www.inxedu.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class WebsiteProfile implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6689726203603217717L;
	private int id;
	private String type;//类型
	private String desciption;//描述内容JSON格式
	private String explain;//说明
}
