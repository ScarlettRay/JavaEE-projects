package com.inxedu.os.edu.entity.website;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * banner广告图管理
 * @author www.inxedu.com
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class WebsiteImages implements Serializable{
	private static final long serialVersionUID = 1L;
	private int imageId;
    private String imagesUrl;//图地址
    private String linkAddress;//图连接地址
    private String title;//图标题
    private int typeId;//图片类型ID
    private int seriesNumber;//序列号
    private String previewUrl;//略缩图片地址
    private String color;//背景色
    private String describe;//描述
    
	
}
