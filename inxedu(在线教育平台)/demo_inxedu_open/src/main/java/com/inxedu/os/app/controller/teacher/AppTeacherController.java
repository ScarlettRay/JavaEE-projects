package com.inxedu.os.app.controller.teacher;

import com.inxedu.os.common.controller.BaseController;
import com.inxedu.os.common.entity.PageEntity;
import com.inxedu.os.edu.entity.teacher.QueryTeacher;
import com.inxedu.os.edu.entity.teacher.Teacher;
import com.inxedu.os.edu.service.teacher.TeacherService;
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
public class AppTeacherController extends BaseController{
	private static Logger logger=Logger.getLogger(AppTeacherController.class);

	 @Autowired
	 private TeacherService teacherService;
	 
	 /**
	  * 讲师列表
	  */
	 @RequestMapping("/teacher/list")
	 @ResponseBody
	 public Map<String, Object> teacherList(HttpServletRequest request,@ModelAttribute("page") PageEntity page){
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
			 String pageSize=request.getParameter("pageSize");
			 if(pageSize!=null){
				 page.setPageSize(Integer.parseInt(pageSize));
			 }
			 
			 String str=request.getParameter("isStar");//头衔 1高级讲师2首席讲师
			 int isStar=0;
			 if(str!=null&&!str.equals("")){
				 isStar=Integer.parseInt("isStar");//头衔 1高级讲师2首席讲师
			 }
			 
			 String name=request.getParameter("name");//讲师名称
			 
			 String subject=request.getParameter("subjectId");//专业Id
			 int subjectId=0;
			 if(subject!=null&&!subject.equals("")){
				 subjectId=Integer.parseInt(subject);//专业Id
			 }
			 
			 String beginTime=request.getParameter("beginCreateTime");//开始添加时间
			 String endTime=request.getParameter("endCreateTime");//结束添加时间
			 Date beginCreateTime=null;
			 Date endCreateTime=null;
			 if(beginTime!=null&&!beginTime.equals("")){
			 	 beginCreateTime=sdf.parse(beginTime);//开始添加时间
			 }
			 if(endTime!=null&&!endTime.equals("")){
				 endCreateTime=sdf.parse(endTime);//结束添加时间
			 }
			 /*=============查询条件=======================*/
			 QueryTeacher queryTeacher=new QueryTeacher();
			 queryTeacher.setIsStar(isStar);//头衔 1高级讲师2首席讲师
			 queryTeacher.setName(name);//讲师名称
			 queryTeacher.setSubjectId(subjectId);//专业Id
			 queryTeacher.setBeginCreateTime(beginCreateTime);//开始添加时间
			 queryTeacher.setEndCreateTime(endCreateTime);//结束添加时间
			 List<Teacher> teacherList =  teacherService.queryTeacherListPage(queryTeacher, page);
			 Map<String, Object> map=new HashMap<String, Object>();
			 map.put("teacherList", teacherList);
			 map.put("page", page);
			 json=this.setJson(true, "成功", map);
		 }catch(Exception e){
			 json=this.setJson(false, "异常", null);
			 logger.error("teacherList()--error",e);
		 }
		 return json;
	 }
	 
	 /**
	  * 讲师详情
	  */
	 @RequestMapping("/front/teacher")
	 @ResponseBody
	 public Map<String, Object> teacherInfo(HttpServletRequest request){
		 Map<String, Object> json=new HashMap<String, Object>();
		 try{
			 String teacherId=request.getParameter("teacherId");//讲师Id
			 if(teacherId==null||teacherId.trim().equals("")){
				 json=this.setJson(false, "讲师Id不能为空", null);
				 return json;
			 }
			 Teacher teacher = teacherService.getTeacherById(Integer.parseInt(teacherId));
			 json=this.setJson(true, "成功", teacher);
		 }catch(Exception e){
			 json=this.setJson(false, "异常", null);
			 logger.error("teacherInfo()--error",e);
		 }
		 return json;
	 }
}
