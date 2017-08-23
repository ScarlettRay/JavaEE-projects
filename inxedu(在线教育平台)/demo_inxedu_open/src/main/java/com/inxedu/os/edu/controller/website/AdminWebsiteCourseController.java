package com.inxedu.os.edu.controller.website;



import com.inxedu.os.common.controller.BaseController;
import com.inxedu.os.common.util.ObjectUtils;
import com.inxedu.os.edu.entity.website.WebsiteCourse;
import com.inxedu.os.edu.service.website.WebsiteCourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 推荐模块课程分类
 * @author www.inxedu.com
 */
@Controller
@RequestMapping("/admin")
public class AdminWebsiteCourseController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(AdminWebsiteCourseController.class);

	@Autowired
	private WebsiteCourseService websiteCourseService;

	// 创建群 绑定变量名字和属性，把参数封装到类
	@InitBinder("websiteCourse")
	public void initBinderWebsiteCourse(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("websiteCourse.");
	}
	
	/**
	 * 添加课程课程分类
	 */
	@RequestMapping("/website/addCourse")
	public ModelAndView addWebsiteCourse(HttpServletRequest request,WebsiteCourse websiteCourse) {
		ModelAndView model = new ModelAndView();
		try {
			model.setViewName("redirect:/admin/website/websiteCoursePage");
			if (ObjectUtils.isNotNull(websiteCourse)) {
				websiteCourseService.addWebsiteCourse(websiteCourse);
			}
		} catch (Exception e) {
			model.setViewName(setExceptionRequest(request, e));
			logger.error("addWebsiteCourse()--error", e);
		}
		return model;
	}

	/** 查询课程分类 */
	@RequestMapping("/website/websiteCoursePage")
	public ModelAndView getWebsiteCourseList(HttpServletRequest request) {
		//推荐课程分类列表
		ModelAndView model = new ModelAndView(getViewPath("/admin/website/course/websiteCourse_list"));
		try {
			// 查询模块课程分类
			List<WebsiteCourse> websiteCourseList = websiteCourseService.queryWebsiteCourseList();
			// 把websiteCourseList放到modelAndView
			model.addObject("websiteCourseList", websiteCourseList);
		} catch (Exception e) {
			model.setViewName(this.setExceptionRequest(request, e));
			logger.error("getWebsiteCourseList()--error", e);
		}
		return model;
	}

	/**
	 * 删除课程分类
	 */
	@RequestMapping("/website/delWebsiteCourseById/{id}")
	public ModelAndView delWebsiteCourseById(HttpServletRequest request, @PathVariable int id) {
		ModelAndView model =new ModelAndView();
		try {
			model.setViewName("redirect:/admin/website/websiteCoursePage");
			if (id>0) {
				// 删除课程分类
				websiteCourseService.deleteWebsiteCourseDetailById(id);
			}
		} catch (Exception e) {
			model.setViewName(this.setExceptionRequest(request, e));
			logger.error("delWebsiteCourseById()--error", e);
		}
		return model;
	}

	/**
	 * 添加课程分类跳转
	 */
	@RequestMapping("/website/doAddWebsiteCourse")
	public ModelAndView getWebsiteCourse() {
		ModelAndView model = new ModelAndView();
		model.setViewName(getViewPath("/admin/website/course/websiteCourse_add"));//推荐课程分类添加页面
		return model;
	}
	/**
	 * 更新课程分类跳转
	 */
	@RequestMapping("/website/doUpdateWebsiteCourse/{id}")
	public ModelAndView doUpdateWebsiteCourse(HttpServletRequest request, @PathVariable("id") int id) {
		ModelAndView model = new ModelAndView();
		try {
			//推荐课程分类修改页面
			model.setViewName(getViewPath("/admin/website/course/websiteCourse_update"));
			// 获得websiteCourse
			WebsiteCourse websiteCourse = websiteCourseService.queryWebsiteCourseById(id);
			model.addObject("websiteCourse", websiteCourse);
		} catch (Exception e) {
			model.setViewName(this.setExceptionRequest(request, e));
			logger.error("doUpdateWebsiteCourse()--error", e);
		}
		return model;
	}
	/**
	 * 修改课程分类
	 */
	@RequestMapping("/website/updateWebsiteCourse")
	public String updateWebsiteCourse(HttpServletRequest request, WebsiteCourse websiteCourse) {
		try {
			if (ObjectUtils.isNotNull(websiteCourse)) {
				websiteCourseService.updateWebsiteCourseById(websiteCourse);
			}
		} catch (Exception e) {
			logger.error("AdminWebsiteCourseController.updateWebsiteCourse--修改课程分类分类出错", e);
			return setExceptionRequest(request, e);
		}
		return "redirect:/admin/website/websiteCoursePage";
	}
}
