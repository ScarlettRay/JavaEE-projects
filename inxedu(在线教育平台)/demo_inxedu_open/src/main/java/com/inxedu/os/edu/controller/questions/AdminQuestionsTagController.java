package com.inxedu.os.edu.controller.questions;

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
import com.inxedu.os.edu.entity.questions.QuestionsTag;
import com.inxedu.os.edu.service.questions.QuestionsTagService;

/**
 * 后台问答标签 Controller
 * @author www.inxedu.com
 */
@Controller
@RequestMapping("/admin")
public class AdminQuestionsTagController extends BaseController {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(AdminQuestionsTagController.class);
    @Autowired
    private QuestionsTagService questionsTagService;
    
    // 返回路径
    private String toQuestionsTagList = getViewPath("/admin/questions/questionsTag_list");//问答标签列表

    // 绑定变量名字和属性，参数封装进类
    @InitBinder("questionsTag")
    public void initBinderQuestionsTag(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("questionsTag.");
    }
    
    /**
     * 删除问答标签
     * @param questionsTagId 要删除的问答标签ID
     * @return Map<String,Object>
     */
    @RequestMapping("/questions/deleteQuestionsTag/{questionsTagId}")
    @ResponseBody
    public Map<String,Object> deleteQuestionsTag(@PathVariable("questionsTagId") int questionsTagId){
    	Map<String,Object> json = new HashMap<String,Object>();
    	try{
    		questionsTagService.deleteQuestionsTag(questionsTagId);
    		json = this.setJson(true, null, null);
    	}catch (Exception e) {
    		this.setAjaxException(json);
			logger.error("deleteQuestionsTag()--error",e);
		}
    	return json;
    }
    
    /**
     * 修改文件名
     * @param questionsTag
     * @return Map<String,Object>
     */
    @RequestMapping("/questions/updatequestionsTagName")
    @ResponseBody
    public Map<String,Object> updateQuestionsTagName(@ModelAttribute("questionsTag") QuestionsTag questionsTag){
    	Map<String,Object> json = new HashMap<String,Object>();
    	try{
    		questionsTagService.updateQuestionsTag(questionsTag);
    		json = this.setJson(true, null, null);
    	}catch (Exception e) {
    		this.setAjaxException(json);
			logger.error("updateQuestionsTagName()--error",e);
		}
    	return json;
    }
    
    /**
     * 修改问答标签父ID
     * @param parentId 父ID
     * @param questionsTagId 问答标签ID
     * @return Map<String,Object>
     */
    @RequestMapping("/questions/updateparentid/{parentId}/{questionsTagId}")
    @ResponseBody
    public Map<String,Object> updateParentId(@PathVariable("parentId") int parentId,@PathVariable("questionsTagId") int questionsTagId){
    	Map<String,Object> json = new HashMap<String,Object>();
    	try{
    		questionsTagService.updateQuestionsTagParentId(questionsTagId, parentId);
    		json = this.setJson(true, null, null);
    	}catch (Exception e) {
    		this.setAjaxException(json);
			logger.error("updateParentId()---error",e);
		}
    	return json;
    }
    
    /**
     * 创建问答标签
     * @return Map<String,Object>
     */
    @RequestMapping("/questions/createQuestionsTag")
    @ResponseBody
    public Map<String,Object> createQuestionsTag(@ModelAttribute("questionsTag") QuestionsTag questionsTag){
    	Map<String,Object> json = new HashMap<String,Object>();
    	try{
    		questionsTag.setCreateTime(new Date());
    		questionsTagService.createQuestionsTag(questionsTag);
    		json = this.setJson(true, null, questionsTag);
    	}catch (Exception e) {
			this.setAjaxException(json);
			logger.error("createQuestionsTag()---error",e);
		}
    	return json;
    }

    /**
     * 到问答标签列表页面
     * @param request
     * @param response
     * @return ModelAndView
     */
    @RequestMapping("/questions/toQuestionsTagList")
    public ModelAndView toQuestionsTagList(HttpServletRequest request, HttpServletResponse response) {
    	ModelAndView model = new ModelAndView();
    	try {
    		model.setViewName(toQuestionsTagList);
            QuestionsTag queryQuestionsTag = new QuestionsTag();
            List<QuestionsTag> questionsTagList = questionsTagService.getQuestionsTagList(queryQuestionsTag);
            model.addObject("questionsTagList", gson.toJson(questionsTagList).toString());
        } catch (Exception e) {
        	model.setViewName(this.setExceptionRequest(request, e));
            logger.error("toQuestionsTagList()---error", e);
        }
        return model;
    }

}
