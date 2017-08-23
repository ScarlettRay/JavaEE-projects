package com.inxedu.os.edu.entity.website;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 推荐分类
 * @author www.inxedu.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class WebsiteCourse implements Serializable {
	private static final long serialVersionUID = 1383373953853661904L;

	private int id;// 分类id
	private String name;//分类名称
	private String link;//更多链接
	private String description;//详细描述
	private int courseNum;//数量限制
}
