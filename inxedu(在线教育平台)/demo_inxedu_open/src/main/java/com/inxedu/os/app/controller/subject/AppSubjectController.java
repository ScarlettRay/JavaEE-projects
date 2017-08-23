package com.inxedu.os.app.controller.subject;

import com.inxedu.os.common.controller.BaseController;
import com.inxedu.os.edu.entity.subject.QuerySubject;
import com.inxedu.os.edu.entity.subject.Subject;
import com.inxedu.os.edu.service.subject.SubjectService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author www.inxedu.com
 */
@Controller
@RequestMapping("/webapp")
public class AppSubjectController extends BaseController{
	private static Logger logger=Logger.getLogger(AppSubjectController.class);
	
	@Autowired
	private SubjectService subjectService;
	
	/**
	 * 返回专业
	 */
	@RequestMapping("/subj/toSubjectList")
	@ResponseBody
	public Map<String, Object> toSubjectList(HttpServletRequest request){
		Map<String,Object> json = new HashMap<String,Object>();
		try{
			QuerySubject querySubject = new QuerySubject();
			List<Subject> subjectList = subjectService.getSubjectList(querySubject);
			json=this.setJson(true, "成功", subjectList);
		}catch(Exception e){
			json=this.setJson(false, "异常", null);
			logger.error("toSubjectList()--error",e);
		}
		return json;
	}
}
