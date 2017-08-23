package com.inxedu.os.edu.service.statistics;


import java.util.Date;
import java.util.List;
import java.util.Map;

import com.inxedu.os.edu.entity.statistics.StatisticsDay;

/**
 * StatisticsDay管理接口
 * @author www.inxedu.com
 */
public interface StatisticsDayService {

	 /**
     * 定时添加StatisticsDay
     * @param date
     * @return
     */
    public void addStatisticsDayAuto();
   

    /**
	 * 网站统计 （按年、月）
	 * 
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getStatisticsMsg(String month, String year);
	/**
	 * 查询最近30条的统计数据
	 * @param date
	 */
	public List<StatisticsDay> getStatisticThirty(int days);
	/**
	 * 查询指定时间段的统计数据
	 * @param date
	 */
	public List<StatisticsDay> getStatisticsByDate(String startTime,String endTime);
	/**
	 * 删除指定时间段的统计数据
	 * @param date
	 */
	public void delStatisticsByDate(String startTime,String endTime);
	/**
	 * 生成指定时间段的统计数据
	 * @param date
	 */
	public void createStatisticsByDate(Date startTime,Date endTime);

	/**
	 * 获取日期的登录人数
	 * @param date
	 * @return
	 */
	public int getTodayLoginNum(Date date);
	/**
	 * 获取日期的注册人数
	 * @param date
	 * @return
	 */
	public int getTodayRegisteredNum(Date date);
	/**
	 * 获取日期的订单数
	 * @param date
	 * @return
	 */
	public Map<String, Object> getTodayOrderNum(Date date);

	/**
	 * 按时间段查询统计
	 */
	public List<StatisticsDay> getStatisticsDayList(Date startDate,Date endDate);

	/**
	 * 网校总用户数
	 */
	public int getEudUserCount();

	/**
	 * 网校课程数
	 */
	public int getEudCouresCount();
}