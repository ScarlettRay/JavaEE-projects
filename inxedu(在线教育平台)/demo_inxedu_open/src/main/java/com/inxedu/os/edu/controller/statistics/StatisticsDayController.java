package com.inxedu.os.edu.controller.statistics;

import com.inxedu.os.common.controller.BaseController;
import com.inxedu.os.edu.entity.statistics.StatisticsDay;
import com.inxedu.os.edu.service.statistics.StatisticsDayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * StatisticsDay 统计 管理接口
 * @author www.inxedu.com
 */
@Controller
@RequestMapping("/admin")
public class StatisticsDayController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(StatisticsDayController.class);

	@Autowired
	private StatisticsDayService statisticsDayService;

	private static final String GenerationStatisticsChart = getViewPath("/admin/statistics/generation_statistics");// 生成统计图
	private static final String statisticalChart = getViewPath("/admin/statistics/statistical_chart");// 统计图容器
	private static final String webChart = getViewPath("/admin/statistics/web_statistics");//网站统计

	/**
	 * 最近统计查询
	 * 
	 * @param request
	 * @param days
	 * @param type
	 * @return
	 */
	@RequestMapping("/statistics/web/detailajax")
	@ResponseBody
	public Map<String, Object> getWebStatisticsAjax(HttpServletRequest request,
			@RequestParam int days, @RequestParam String type) {
		Map<String,Object> json = null;
		try {
			if (days > 0) {// 历史的
				List<StatisticsDay> loginStatistics = statisticsDayService.getStatisticThirty(days);
				String statisticalNum = "";// 登录数--统计数（包括三中）
				Calendar c = Calendar.getInstance();
				Object[] showDate = new Object[days];// 时间轴
				for (int i = loginStatistics.size() - 1; i >= 0; i--) {
					c.setTime(loginStatistics.get(i).getStatisticsTime());
					if (days > 15) {
						showDate[loginStatistics.size() - 1 - i] = c
								.get(Calendar.DAY_OF_MONTH);
					} else {
						showDate[loginStatistics.size() - 1 - i] = (c
								.get(Calendar.MONTH) + 1)
								+ "月"
								+ c.get(Calendar.DAY_OF_MONTH);
					}
					if (type.equals("LOGIN_NUM")) {
						statisticalNum += loginStatistics.get(i).getLoginNum()
								+ ",";
					}
					if(type.equals("REGISTERED_NUM")){
						statisticalNum += loginStatistics.get(i).getRegisteredNum()
								+ ",";
					}
				}
				if(type.equals("LOGIN_NUM")||type.equals("REGISTERED_NUM")){
					if (statisticalNum.length() > 0) {
						statisticalNum = statisticalNum.substring(0,
								statisticalNum.length() - 1);
					}
					json = this.setJson(true, gson.toJson(showDate)
							.toString(), statisticalNum);
				}
			} else {// 今天的

			}

		} catch (Exception e) {
			logger.error("StatisticsDayController.getWebStatisticsAjax" + e);
			json = this.setJson(false, "", null);
		}
		return json;
	}

	/**
	 * 生成指定时间段的统计
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/statistics/create/batch")
	@ResponseBody
	public Map<String, Object> createStatisticsByDate(HttpServletRequest request) {
		Map<String,Object> json = new HashMap<String,Object>();
		try {
			String startTime = request.getParameter("startTime");
			String endTime = request.getParameter("endTime");
			List<StatisticsDay> statistics = statisticsDayService
					.getStatisticsByDate(startTime, endTime);
			if (statistics.size() > 0) {// 时间段内已有数据
				json = this.setJson(true, "exists", null);
				return json;
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date dBegin = sdf.parse(startTime);
			Date dEnd = sdf.parse(endTime);	
			statisticsDayService.createStatisticsByDate(dBegin, dEnd);
			json = this.setJson(true, "true", null);
		} catch (Exception e) {
			logger.error("StatisticsDayController.createStatisticsByDate" + e);
			json = this.setJson(false, "error", null);
		}
		return json;
	}

	/**
	 * 删除指定时间段的统计
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/statistics/del/batch")
	@ResponseBody
	public Map<String, Object> delStatisticsByDate(HttpServletRequest request) {
		Map<String,Object> json = new HashMap<String,Object>();
		try {
			String startTime = request.getParameter("startTime");
			String endTime = request.getParameter("endTime");
			statisticsDayService.delStatisticsByDate(startTime, endTime);
			json = this.setJson(true, "true", null);
		} catch (Exception e) {
			logger.error("StatisticsDayController.createStatisticsByDate" + e);
			json = this.setJson(false, "error", null);
		}
		return json;
	}

	/**
	 * 每日注册人数(--------------------------------------------------------------------------------------------------------------------)
	 */
	@RequestMapping("/todayRegisteredNum")
	@ResponseBody
	public Map<String, Object> todayRegisteredNum(){
		Map<String, Object> json = null;
		try{
			int registeredNum=statisticsDayService.getTodayRegisteredNum(new Date());
			json = this.setJson(true, "true", registeredNum);
		}catch(Exception e){
			logger.error("StatisticsDayController.todayRegisteredNum" + e);
			json = this.setJson(false, "error", null);
		}
		return json;
	}
	/**
	 * 每日订单数
	 */
	@RequestMapping("/todayOrderNum")
	@ResponseBody
	public Map<String, Object> todayOrderNum(){
		Map<String, Object> json = null;
		try{
			Map<String, Object> map=statisticsDayService.getTodayOrderNum(new Date());
			json = this.setJson(true, "true", map);
		}catch(Exception e){
			logger.error("StatisticsDayController.todayOrderNum" + e);
			json = this.setJson(false, "error", null);
		}
		return json;
	}


	/**
	 * 统计信息
	 * @param request
	 * @return
	 */
	@RequestMapping("/ajax/statistics/{type}")
	public String getStatistics(HttpServletRequest request,@PathVariable("type") String type) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String startDate = request.getParameter("startDate");//开始时间
			String endDate = request.getParameter("endDate");//结束时间

			List<StatisticsDay> statistics=statisticsDayService.getStatisticsDayList(sdf.parse(startDate), sdf.parse(endDate));

			String showDate = "";// 统计js的X轴的日期显示
			String data = "";// 统计数据
			for (int i = 0; i < statistics.size() - 1; i++) {
				if(type.equals("registered")){//注册统计信息
					data+= statistics.get(i).getRegisteredNum() + ",";
				}else if(type.equals("dailyUserNumber")){//每日用户数统计
					data+=statistics.get(i).getDailyUserNumber()+",";
				}else if(type.equals("dailyCourseNumber")){//每日课程数统计
					data+=statistics.get(i).getDailyCourseNumber()+",";
				}else if(type.equals("videoViewingNum")){//视频播放数
					data+=statistics.get(i).getVideoViewingNum()+",";
				}else{
					data+= statistics.get(i).getLoginNum() + ",";//登录统计（活跃人数
				}
				showDate += "\"";
				showDate += sdf.format(statistics.get(i).getStatisticsTime());
				showDate += "\"";
				showDate += ",";
			}
			if (data.length() > 0) {
				data = data.substring(0, data.length() - 1);
			}
			if(showDate.length()>0){
				showDate = showDate.substring(0, showDate.length() - 1);
			}
			// 绘图数据
			request.setAttribute("showDate", "["+showDate+"]");//统计图x轴数据
			request.setAttribute("data", "["+data+"]");//统计数据
			//开始时间
			request.setAttribute("startDate", startDate);
			//结束时间
			request.setAttribute("endDate", endDate);
			//统计图类型
			request.setAttribute("type", type);
		} catch (Exception e) {
			logger.error("StatisticsDayController.getLoginStatistics" + e);
		}
		return webChart;
	}

	/**
	 * 跳转到统计数据生成页
	 * @return
	 */
	@RequestMapping("/jumpGenerationStatisticsPage")
	public String jumpLoginStatisticsPage(){
		return GenerationStatisticsChart;
	}

	/**
	 * 跳转统计图页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/statisticsPage/{type}")
	public String statisticsPage(HttpServletRequest request,@PathVariable String type){
		try{
			request.setAttribute("type", type);
		}catch(Exception e){
			logger.error("StatisticsDayController.getEveryDayIncome" + e);
		}
		return statisticalChart;
	}

}