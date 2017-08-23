package com.inxedu.os.app.controller.course;

import com.inxedu.os.common.controller.BaseController;
import com.inxedu.os.common.entity.PageEntity;
import com.inxedu.os.edu.entity.course.Course;
import com.inxedu.os.edu.entity.course.CourseDto;
import com.inxedu.os.edu.entity.course.QueryCourse;
import com.inxedu.os.edu.service.course.CourseService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author www.inxedu.com
 */
@Controller
@RequestMapping("/webapp")
public class AppCourseController extends BaseController{
	private static Logger logger=Logger.getLogger(AppCourseController.class);
	
	@Autowired
	private CourseService courseService;
	
	/**
	 * 课程列表
	 */
	@RequestMapping("/cou/list")
	@ResponseBody
	public Map<String, Object> showCourseList(HttpServletRequest request,@ModelAttribute("page") PageEntity page){
		Map<String, Object> json=new HashMap<String, Object>();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try{
			String currentPage=request.getParameter("currentPage");//当前页
			if(currentPage==null||currentPage.trim().equals("")){
				json=this.setJson(false, "页码不能为空", null);
				return json;
			}
			page.setCurrentPage(Integer.parseInt(currentPage));//当前页
			
			page.setPageSize(10);//每页多少条数据
			String pageSize = request.getParameter("pageSize");
			if(pageSize!=null ){
				page.setPageSize(Integer.parseInt(pageSize));
			}
			
			String teacher=request.getParameter("teacherId");//讲师Id
			int teacherId=0;
			if(teacher!=null&&!teacher.trim().equals("")){
				teacherId=Integer.parseInt(teacher);//讲师Id
			}
			
			String courseName=request.getParameter("courseName");//课程名称
			String subject=request.getParameter("subjectId");//专业Id
			int subjectId=0;
			if(subject!=null&&!subject.trim().equals("")){
				subjectId=Integer.parseInt(subject);//专业Id
			}
			
			String beginTime=request.getParameter("beginCreateTime");//开始添加时间
			String endTime=request.getParameter("endCreateTime");//结束添加时间
			Date beginCreateTime=null;
			Date endCreateTime=null;
			if(beginTime!=null&&!beginTime.trim().equals("")){
				beginCreateTime=sdf.parse(beginTime);//开始添加时间
			}
			if(endTime!=null&&!endTime.trim().equals("")){
				endCreateTime=sdf.parse(endTime);//结束添加时间
			}
			
			/*================查询条件=================*/
			QueryCourse queryCourse=new QueryCourse();
			queryCourse.setIsavaliable(1);//1.正常课程2.删除课程
			queryCourse.setTeacherId(teacherId);//讲师Id
			queryCourse.setCourseName(courseName);//课程名称
			queryCourse.setSubjectId(subjectId);//专业Id
			queryCourse.setBeginCreateTime(beginCreateTime);//开始添加事件
			queryCourse.setEndCreateTime(endCreateTime);//结束添加时间
			List<CourseDto> courseList = courseService.queryCourseListPage(queryCourse, page);
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("courseList", courseList);
			map.put("page", page);
			json=this.setJson(true, "成功", map);
		}catch(Exception e){
			json=this.setJson(false, "异常", null);
			logger.error("showCourseList()--error",e);
		}
		return json;
	}
	
	/**
	 * 课程详情
	 */
	@RequestMapping("/front/couinfo")
	@ResponseBody
	public Map<String, Object> couinfo(HttpServletRequest request){
		Map<String, Object> json=new HashMap<String, Object>();
		try{
			String courseId=request.getParameter("courseId");
			if(courseId==null||courseId.trim().equals("")){
				json=this.setJson(true, "课程Id不能为空", null);
				return json;
			}
			// 查询课程详情
            Course course = courseService.queryCourseById(Integer.parseInt(courseId));
            json=this.setJson(true, "成功", course);
		}catch(Exception e){
			json=this.setJson(false, "异常", null);
			logger.error("couinfo()--error",e);
		}
		return json;
	}
	
}
