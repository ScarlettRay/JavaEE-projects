package com.inxedu.os.edu.dao.impl.statistics;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.inxedu.os.common.dao.GenericDaoImpl;
import com.inxedu.os.edu.dao.statistics.StatisticsDayDao;
import com.inxedu.os.edu.entity.statistics.StatisticsDay;

/**
 *
 * StatisticsDay
 * @author www.inxedu.com
 */
 @Repository("statisticsDayDao")
public class StatisticsDayDaoImpl extends GenericDaoImpl implements StatisticsDayDao{

	/**
     * 添加StatisticsDay web
     * @param date
     * @return
     */
	public void addStatisticsDay(Date date) {
         this.insert("StatisticsDayMapper.createStatisticsDay",date);
    }
	 /**
     * 日期批量添加StatisticsDay web
     * @param date
     * @return
     */
    public void addStatisticsDayBatch(List<Date> dates){
    	for(Date date:dates){
    		this.insert("StatisticsDayMapper.createStatisticsDay",date);
    	}
    }
    
    /**
     * 更新StatisticsDay sns
     * @param date
     * @return
     */
	public void updateStatisticsDay(StatisticsDay statisticsDay) {
         this.update("StatisticsDayMapper.updateStatisticsDay",statisticsDay);
    }
	 /**
     * 日期批量更新StatisticsDay sns
     * @param date
     * @return
     */
    public void updateStatisticsDayBatch(List<StatisticsDay> statisticsDays){
    	for(StatisticsDay statisticsDay:statisticsDays){
    		this.update("StatisticsDayMapper.updateStatisticsDay",statisticsDay);
    	}
    }
    
	/**
	 * 按年查询网站统计
	 * 
	 * @param queryUser
	 * @return
	 */
	public List<StatisticsDay> getStatisticsByYear(String year){
		return this.selectList("StatisticsDayMapper.getStatisticsByYear", year);
	}
	/**
	 * 按月查询网站统计
	 * 
	 * @param queryUser
	 * @return
	 */
	public List<StatisticsDay> getStatisticsByMonth(String month,String year){
		if(Integer.parseInt(month)<10){
			month="0"+month;
		}
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("nowMonth",year + "-" + month + "-01");//年月日
		map.put("nowYear",year + "-" + month);//年月
		return this.selectList("StatisticsDayMapper.getStatisticsByMonth", map);
	}
	/**
	 * 网站统计 （总记录）
	 * 
	 * @return
	 * @throws Exception
	 */
	public StatisticsDay getStatisticsSumMsg(){
		return this.selectOne("StatisticsDayMapper.getStatisticsSumMsg", 0);
	}
	/**
	 * 查询最近30条的统计数据
	 * @param date
	 */
	public List<StatisticsDay> getStatisticThirty(int days){
		return this.selectList("StatisticsDayMapper.getStatisticThirty",days);
	}
	/**
	 * 查询指定时间段的统计数据
	 * @param date
	 */
	public List<StatisticsDay> getStatisticsByDate(String startTime,String endTime){
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		return this.selectList("StatisticsDayMapper.getStatisticsByDate", map);
	}
	/**
	 * 删除指定时间段的统计数据
	 * @param date
	 */
	public void delStatisticsByDate(String startTime,String endTime){
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		this.delete("StatisticsDayMapper.delStatisticsByDate", map);
	}
	/**
	 * 删除指定日期统计
	 */
	public void delStatisticsDay(Date date) {
		this.delete("StatisticsDayMapper.delStatisticsDay", date);
	}


	/**
	 * 获取日期的登录人数
	 * @param date
	 * @return
	 */
	public int getTodayLoginNum(Date date){
		return this.selectOne("StatisticsDayMapper.statistics_loginNumToday",date);
	}
	/**
	 * 获取日期的注册人数
	 */
	public int getTodayRegisteredNum(Date date) {
		return this.selectOne("StatisticsDayMapper.statistics_registeredNumToday", date);
	}
	/**
	 * 获取日期的订单数
	 */
	public Map<String, Object> getTodayOrderNum(Date date) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("orderNum", this.selectOne("StatisticsDayMapper.statistics_orderNumToday", date));
		map.put("orderSuccessNum", this.selectOne("StatisticsDayMapper.statistics_orderSuccessNumToday", date));
		map.put("orderInitNum", this.selectOne("StatisticsDayMapper.statistics_orderInitNumToday", date));
		map.put("orderClosedNum", this.selectOne("StatisticsDayMapper.statistics_orderClosedNumToday", date));
		return map;
	}

	/**
	 * 按时间段查询统计
	 */
	@Override
	public List<StatisticsDay> getStatisticsDayList(Date startDate, Date endDate) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		return selectList("StatisticsDayMapper.getStatisticsDayList", map);
	}

	/**
	 * 网校总用户数
	 */
	@Override
	public int getEudUserCount() {
		return selectOne("StatisticsDayMapper.getEudUserCount", null);
	}

	/**
	 * 网校课程数
	 */
	@Override
	public int getEudCouresCount() {
		return selectOne("StatisticsDayMapper.getEudCouresCount", null);
	}
 }
