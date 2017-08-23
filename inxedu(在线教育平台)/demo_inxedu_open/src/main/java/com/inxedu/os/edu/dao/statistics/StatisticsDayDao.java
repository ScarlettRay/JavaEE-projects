package com.inxedu.os.edu.dao.statistics;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.inxedu.os.edu.entity.statistics.StatisticsDay;




/**
 * StatisticsDay管理接口
 * @author www.inxedu.com
 */
public interface StatisticsDayDao {

	 /**
     * 添加StatisticsDay
     * @param date
     * @return
     */
    public void addStatisticsDay(Date date);
    /**
     * 日期批量添加StatisticsDay
     * @param date
     * @return
     */
    public void addStatisticsDayBatch(List<Date> dates);
    
    /**
     * 更新StatisticsDay sns
     * @param date
     * @return
     */
	public void updateStatisticsDay(StatisticsDay statisticsDay);
	 /**
     * 日期批量更新StatisticsDay sns
     * @param date
     * @return
     */
    public void updateStatisticsDayBatch(List<StatisticsDay> statisticsDays);
    
    /**
	 * 按年查询网站统计
	 * 
	 * @param queryUser
	 * @return
	 */
	public List<StatisticsDay> getStatisticsByYear(String year);
	/**
	 * 按月查询网站统计
	 * 
	 * @param queryUser
	 * @return
	 */
	public List<StatisticsDay> getStatisticsByMonth(String month,String year);
	
	/**
	 * 网站统计 （总记录）
	 * 
	 * @return
	 * @throws Exception
	 */
	public StatisticsDay getStatisticsSumMsg();
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
	 * 查询最近30条的统计数据
	 * @param date
	 */
	public List<StatisticsDay> getStatisticThirty(int days);
	/**
	 * 删除指定日期统计
	 * @param date
	 */
	public void delStatisticsDay(Date date);

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