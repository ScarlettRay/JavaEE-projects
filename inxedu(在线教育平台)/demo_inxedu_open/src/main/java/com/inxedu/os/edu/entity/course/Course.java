package com.inxedu.os.edu.entity.course;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * @author www.inxedu.com
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Course implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int courseId;
    private String courseName;//课程名称
    private int isavaliable;//1 正常　２　下架   3删除
    private int subjectId;//课程专业ID
    private String subjectLink;//课程专业链
    private Date addTime;//课程添加时间
    private java.math.BigDecimal sourcePrice;//课程原价格（只显示）
    private java.math.BigDecimal currentPrice;//课程销售价格（实际支付价格）设置为0则可免费观看
    private String title;//课程简介
    private String context;//课程详情
    private int lessionNum;//课时
    private String logo;//课程图片
    private Date updateTime;
    private int pageBuycount;//销售数量
    private int pageViewcount;//浏览数量
    private Date endTime;//有效结束时间
    private int loseType;//有效期类型，0：到期时间，1：按天数
    private String loseTime;//有效期:商品订单过期时间点
    
    private String studyPercent;//课程学习进度百分比
}
