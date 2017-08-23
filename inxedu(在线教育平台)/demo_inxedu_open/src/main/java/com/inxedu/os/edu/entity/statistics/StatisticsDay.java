package com.inxedu.os.edu.entity.statistics;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author www.inxedu.com
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class StatisticsDay implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 主键id
	 */
	private Long id;
	/**
	 * 统计日期
	 */
    private java.util.Date statisticsTime;
    /**
     * 登录人数（活跃人数 ）
     */
    private Long loginNum;
    /**
     *  生成时间
     */
    private java.util.Date createTime;
    /**
     * 注册人数
     */
    private Long registeredNum;
    /**
     * 每日播放视频数
     */
    private Long videoViewingNum;
    /**
     * 每日用户数
     */
    private Long dailyUserNumber;
    /**
     * 每日课程数
     */
    private Long dailyCourseNumber;
}
