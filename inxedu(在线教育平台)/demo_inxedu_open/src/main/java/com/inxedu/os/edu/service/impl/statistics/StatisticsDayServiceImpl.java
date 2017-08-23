package com.inxedu.os.edu.service.impl.statistics;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inxedu.os.common.cache.EHCacheUtil;
import com.inxedu.os.common.constants.CacheConstans;
import com.inxedu.os.edu.dao.statistics.StatisticsDayDao;
import com.inxedu.os.edu.entity.statistics.StatisticsDay;
import com.inxedu.os.edu.service.statistics.StatisticsDayService;

import lombok.Getter;
import lombok.Setter;

/**
 * StatisticsDayDay管理接口
 * @author www.inxedu.com
 */
@Service("statisticsDayService")
public class StatisticsDayServiceImpl implements StatisticsDayService{

	@Autowired
    private StatisticsDayDao statisticsDayDao;
 	
 	
 	@Getter@Setter
	private Map<String,Object> userMsg= new HashMap<String,Object>();
    /**
     * 定时添加StatisticsDayDay
     * @param date
     * @return
     */
    public void addStatisticsDayAuto(){
    	Calendar c = Calendar.getInstance();		
    	c.setTime(new Date());		
    	//每天定时统计前一天的数据，天数减1
    	c.set(Calendar.DATE, c.get(Calendar.DATE) - 1);		
    	Date date = c.getTime();
    	//先删除再生成，防止数据重复
    	statisticsDayDao.delStatisticsDay(date);
    	//添加网校统计
    	statisticsDayDao.addStatisticsDay(date);

    	EHCacheUtil.remove(CacheConstans.WEB_STATISTICS);
    	EHCacheUtil.remove(CacheConstans.WEB_STATISTICS_THIRTY+7);
    	EHCacheUtil.remove(CacheConstans.WEB_STATISTICS_THIRTY+15);
    	EHCacheUtil.remove(CacheConstans.WEB_STATISTICS_THIRTY+30);
    }
    
    /**
     * 查询网站统计
     */
	public Map<String,Object> getStatisticsMsg(String month, String year){
		
		if (month!=null &&month!= "") {
			getStatisticsDayByMonth(month, year);// 按月查
		} else if (year != null) {
			getStatisticsDayByYear(year);//按年查
		}
		return userMsg;
	}
	
	/**
	 * 按月查询网站统计
	 * 
	 * @param month
	 * @return
	 */
	public void getStatisticsDayByMonth(String month, String year) {
		String showDate = "";
		List<StatisticsDay> statisticsDayList = statisticsDayDao.getStatisticsByMonth(month, year);
		for (int i=0;i<statisticsDayList.size();i++ ) {
			showDate += (i+1) + ",";
		}
		showDate = showDate.substring(0, showDate.length() - 1);
		userMsg.put("showDate", showDate);
		userMsg.put("statisticsDayList", statisticsDayList);
	}

	/**
	 * 按年查询网站统计
	 * @param year
	 * @return
	 */
	public void getStatisticsDayByYear(String year) {
		List<StatisticsDay> statisticsList = statisticsDayDao.getStatisticsByYear(year);
		String showDate = "01,02,03,04,05,06,07,08,09,10,11,12";
		// 数据拼凑
		userMsg.put("showDate", showDate);
		userMsg.put("statisticsDayList", statisticsList);
	}
	
	/**
	 * 网站统计 （总记录）
	 * 
	 * @return
	 * @throws Exception
	 */
	public StatisticsDay getStatisticsSumMsg(){

		StatisticsDay statisticsDay=  (StatisticsDay) EHCacheUtil.get(CacheConstans.WEB_STATISTICS);
        if (statisticsDay!=null) {
            return statisticsDay;
        }
        statisticsDay=statisticsDayDao.getStatisticsSumMsg();
        if (statisticsDay!=null) {
        	EHCacheUtil.set(CacheConstans.WEB_STATISTICS, statisticsDay);
        }
		return statisticsDay;
	}
	/**
	 * 查询指定时间段的统计数据
	 * @param date
	 */
	public List<StatisticsDay> getStatisticsByDate(String startTime,String endTime){
		return statisticsDayDao.getStatisticsByDate(startTime,endTime);
	}
	/**
	 * 动态查询活跃度
	 * @param date
	 */
	@SuppressWarnings("unchecked")
	public List<StatisticsDay> getStatisticThirty(int days){

		List<StatisticsDay> statistics= (List<StatisticsDay>) EHCacheUtil.get(CacheConstans.WEB_STATISTICS_THIRTY+days);
        if (statistics!=null&&statistics.size()>0) {
            return statistics;
        }
        statistics=statisticsDayDao.getStatisticThirty(days);
        if (statistics!=null) {
        	EHCacheUtil.set(CacheConstans.WEB_STATISTICS_THIRTY+days, statistics, CacheConstans.WEB_STATISTICS_TIME);
        }
		return statistics;
	}
	/**
	 * 删除指定时间段的统计数据
	 * @param date
	 */
	public void delStatisticsByDate(String startTime,String endTime){
		statisticsDayDao.delStatisticsByDate(startTime,endTime);
		EHCacheUtil.remove(CacheConstans.WEB_STATISTICS);
		EHCacheUtil.remove(CacheConstans.WEB_STATISTICS_THIRTY+7);
        EHCacheUtil.remove(CacheConstans.WEB_STATISTICS_THIRTY+15);
        EHCacheUtil.remove(CacheConstans.WEB_STATISTICS_THIRTY+30);
	}
	/**
	 * 生成指定时间段的统计数据
	 * @param date
	 */
	public void createStatisticsByDate(Date startDate,Date endDate){ 
		Calendar cal = Calendar.getInstance(); 
		List<Date> dates=new ArrayList<Date>();
		dates.add(startDate);
        // 使用给定的 Date 设置此 Calendar 的时间   
        cal.setTime(startDate);   
        while (true) {  
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量   
            cal.add(Calendar.DAY_OF_MONTH, 1);  
            // 测试此日期是否在指定日期之后   
            if (endDate.after(cal.getTime())) {  
            	dates.add(cal.getTime());
            } else {  
                break;  
            }  
        }   
        if(dates.get(0).getTime()!=endDate.getTime()){//首尾日起相同，只添加一条
        	dates.add(endDate);
        }
        //批量添加网校统计
        statisticsDayDao.addStatisticsDayBatch(dates);
        
        EHCacheUtil.remove(CacheConstans.WEB_STATISTICS);
        EHCacheUtil.remove(CacheConstans.WEB_STATISTICS_THIRTY+7);
        EHCacheUtil.remove(CacheConstans.WEB_STATISTICS_THIRTY+15);
        EHCacheUtil.remove(CacheConstans.WEB_STATISTICS_THIRTY+30);
	}

	/**
	 * 获取日期的登录人数
	 * @param date
	 * @return
	 */
	public int getTodayLoginNum(Date date){
		return statisticsDayDao.getTodayLoginNum(date);
	}
	
	/**
	 * 获取日期的注册人数
	 */
	public int getTodayRegisteredNum(Date date) {
		return statisticsDayDao.getTodayRegisteredNum(date);
	}
	/**
	 * 获取日期的订单数
	 */
	public Map<String, Object> getTodayOrderNum(Date date) {
		return statisticsDayDao.getTodayOrderNum(date);
	}

	/**
	 * 按时间段查询统计
	 */
	@Override
	public List<StatisticsDay> getStatisticsDayList(Date startDate, Date endDate) {
		return statisticsDayDao.getStatisticsDayList(startDate, endDate);
	}

	/**
	 * 网校总用户数
	 */
	@Override
	public int getEudUserCount() {
		return statisticsDayDao.getEudUserCount();
	}

	/**
	 * 网校课程数
	 */
	@Override
	public int getEudCouresCount() {
		return statisticsDayDao.getEudCouresCount();
	}

}