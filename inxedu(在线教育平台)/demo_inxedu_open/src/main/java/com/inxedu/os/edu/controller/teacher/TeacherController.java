package com.inxedu.os.edu.controller.teacher;

import com.inxedu.os.common.controller.BaseController;
import com.inxedu.os.common.entity.PageEntity;
import com.inxedu.os.edu.entity.course.CourseDto;
import com.inxedu.os.edu.entity.course.QueryCourse;
import com.inxedu.os.edu.entity.subject.QuerySubject;
import com.inxedu.os.edu.entity.subject.Subject;
import com.inxedu.os.edu.entity.teacher.QueryTeacher;
import com.inxedu.os.edu.entity.teacher.Teacher;
import com.inxedu.os.edu.service.course.CourseService;
import com.inxedu.os.edu.service.subject.SubjectService;
import com.inxedu.os.edu.service.teacher.TeacherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 前台讲师
 * @author www.inxedu.com
 */
@Controller
public class TeacherController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(TeacherController.class);

	@Autowired
	private TeacherService teacherService;
	@Autowired
	private CourseService courseService;
	@Autowired
	private SubjectService subjectService;

	// 绑定属性 封装参数
	@InitBinder("queryTeacher")
	public void initQueryTeacher(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("queryTeacher.");
	}

	/**
	 * 教师列表展示
	 */
	@RequestMapping("/front/teacherlist")
	public ModelAndView teacherlist(HttpServletRequest request, @ModelAttribute("queryTeacher") QueryTeacher queryTeacher, @ModelAttribute("page") PageEntity page) {
		//教师列表
		ModelAndView model = new ModelAndView(getViewPath("/web/teacher/teacher_list"));
		try {
			// 教师分类
			QuerySubject querySubject = new QuerySubject();
			querySubject.setParentId(0);
			List<Subject> subjectList = subjectService.getSubjectList(querySubject);
			model.addObject("subjectList", subjectList);
			// 页面传来的数据放到page中
			page.setPageSize(8);
			// 查询老师
			List<Teacher> teacherList = teacherService.queryTeacherListPage(queryTeacher, page);
			model.addObject("teacherList", teacherList);
			model.addObject("page", page);
			model.addObject("subjectId", queryTeacher.getSubjectId());// 老师的专业Id
		} catch (Exception e) {
			model.setViewName(this.setExceptionRequest(request, e));
			logger.error("teacherlist()--error", e);
		}
		return model;
	}

	/**
	 * 教师详情
	 */
	@RequestMapping("/front/teacher/{teacherId}")
	public ModelAndView teacherInfo(HttpServletRequest request, @PathVariable int teacherId, @ModelAttribute("page") PageEntity page) {
		ModelAndView model = new ModelAndView();
		try {
			//教师详情
			model.setViewName(getViewPath("/web/teacher/teacher_info"));
			// 查询老师
			Teacher teacher = teacherService.getTeacherById(teacherId);
			model.addObject("teacher", teacher);
			// 讲师所讲的课程
			page.setPageSize(8);
			QueryCourse queryCourse = new QueryCourse();
			// 只查询状态正常的
			queryCourse.setIsavaliable(1);
			queryCourse.setTeacherId(teacherId);
			List<CourseDto> courseList = courseService.queryCourseListPage(queryCourse, page);
			model.addObject("courseList", courseList);
			model.addObject("page", page);
		} catch (Exception e) {
			model.setViewName(this.setExceptionRequest(request, e));
			logger.error("teacherInfo()--error", e);
		}
		return model;
	}
}