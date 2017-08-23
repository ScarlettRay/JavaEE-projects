package com.inxedu.os.edu.controller.subject;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.inxedu.os.common.controller.BaseController;
import com.inxedu.os.edu.entity.subject.QuerySubject;
import com.inxedu.os.edu.entity.subject.Subject;
import com.inxedu.os.edu.service.subject.SubjectService;

/**
 * 专业 Controller
 * @author www.inxedu.com
 */
@Controller
@RequestMapping("/admin")
public class AdminSubjectController extends BaseController {
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(AdminSubjectController.class);
	@Autowired
	private SubjectService subjectService;

	// 返回路径
	private String toSubjectList = getViewPath("/admin/subject/subject_list");// 专业列表

	// 绑定变量名字和属性，参数封装进类
	@InitBinder("subject")
	public void initBinderSubject(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("subject.");
	}

	/**
	 * 删除专业
	 */
	@RequestMapping("/subj/deleteSubject/{subjectId}")
	@ResponseBody
	public Map<String, Object> deleteSubject(@PathVariable("subjectId") int subjectId) {
		Map<String,Object> json = new HashMap<String,Object>();
		try {
			subjectService.deleteSubject(subjectId);
			json = this.setJson(true, null, null);
		} catch (Exception e) {
			this.setAjaxException(json);
			logger.error("deleteSubject()--error", e);
		}
		return json;
	}

	/**
	 * 修改文件名
	 */
	@RequestMapping("/subj/updatesubjectName")
	@ResponseBody
	public Map<String, Object> updateSubjectName(@ModelAttribute("subject") Subject subject) {
		Map<String,Object> json = new HashMap<String,Object>();
		try {
			subjectService.updateSubject(subject);
			json = this.setJson(true, null, null);
		} catch (Exception e) {
			this.setAjaxException(json);
			logger.error("updateSubjectName()--error", e);
		}
		return json;
	}
	/**
	 * 修改排序
	 */
	@RequestMapping("/subj/updatesort")
	@ResponseBody
	public Map<String, Object> updateSort(@ModelAttribute("subject") Subject subject) {
		Map<String,Object> json = new HashMap<String,Object>();
		try {
			subjectService.updateSubjectSort(subject);
			json = this.setJson(true, null, null);
		} catch (Exception e) {
			this.setAjaxException(json);
			logger.error("updateSort()--error", e);
		}
		return json;
	}
	
	/**
	 * 修改专业父ID
	 */
	@RequestMapping("/subj/updateparentid/{parentId}/{subjectId}")
	@ResponseBody
	public Map<String, Object> updateParentId(@PathVariable("parentId") int parentId, @PathVariable("subjectId") int subjectId) {
		Map<String,Object> json = new HashMap<String,Object>();
		try {
			subjectService.updateSubjectParentId(subjectId, parentId);
			json = this.setJson(true, null, null);
		} catch (Exception e) {
			this.setAjaxException(json);
			logger.error("updateParentId()---error", e);
		}
		return json;
	}

	/**
	 * 创建专业
	 */
	@RequestMapping("/subj/createSubject")
	@ResponseBody
	public Map<String, Object> createSubject(@ModelAttribute("subject") Subject subject) {
		Map<String,Object> json = new HashMap<String,Object>();
		try {
			subject.setCreateTime(new Date());
			subjectService.createSubject(subject);
			json = this.setJson(true, null, subject);
		} catch (Exception e) {
			this.setAjaxException(json);
			logger.error("createSubject()---error", e);
		}
		return json;
	}

	/**
	 * 到专业列表页面
	 */
	@RequestMapping("/subj/toSubjectList")
	public ModelAndView toSubjectList(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView model = new ModelAndView();
		try {
			model.setViewName(toSubjectList);
			QuerySubject querySubject = new QuerySubject();
			List<Subject> subjectList = subjectService.getSubjectList(querySubject);
			model.addObject("subjectList", gson.toJson(subjectList).toString());
		} catch (Exception e) {
			model.setViewName(this.setExceptionRequest(request, e));
			logger.error("toSubjectList()---error", e);
		}
		return model;
	}

}
