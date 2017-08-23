package com.inxedu.os.edu.controller.questions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.inxedu.os.common.entity.PageEntity;
import com.inxedu.os.edu.entity.questions.Questions;
import com.inxedu.os.edu.entity.questions.QuestionsComment;
import com.inxedu.os.edu.service.questions.QuestionsCommentService;
import com.inxedu.os.edu.service.questions.QuestionsService;

/**
 * 问答评论后台 Controller
 * @author www.inxedu.com
 */
@Controller
@RequestMapping("/admin")
public class AdminQuestionsCommentController extends BaseController {
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(AdminQuestionsCommentController.class);
	
	@Autowired
	private QuestionsService questionsService;
	@Autowired
	private QuestionsCommentService questionsCommentService;
	
	// 所有问答回复列表
	private static final String questionscommentlist = getViewPath("/admin/questions/questionscomment_list");
	//某个问答回复下的所有评论
	private static final String questionscommentlistone=getViewPath("/admin/questions/questionscomment_list_one");
	
	@InitBinder({"questionsComment"})
	public void initBinderQuestionsComment(WebDataBinder binder){
		binder.setFieldDefaultPrefix("questionsComment.");
	}
	
	/**
	 * 问答回复列表
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping("/questionscomment/list")
	public ModelAndView getQuestionsCommentList(HttpServletRequest request,@ModelAttribute("questionsComment")QuestionsComment questionsComment,@ModelAttribute("page") PageEntity page){
		ModelAndView model = new ModelAndView(questionscommentlist);
		try{
			page.setPageSize(20);
			List<QuestionsComment> questionsCommentList = questionsCommentService.queryQuestionsCommentList(questionsComment, page);
			model.addObject("questionsCommentList", questionsCommentList);
			model.addObject("page", page);
			
		}catch (Exception e) {
			model.setViewName(this.setExceptionRequest(request, e));
			logger.error("getQuestionsCommentList()---error",e);
		}
		return model;
	}
	
	/**
	 * 删除问答回复
	 */
	@RequestMapping("/questionscomment/del/{questionsCommentId}")
	@ResponseBody
	public Object delQuestionsComment(HttpServletRequest request,@PathVariable("questionsCommentId")Long questionsCommentId){
		Map<String,Object> json = new HashMap<String,Object>();
		try{
			QuestionsComment questionsComment=questionsCommentService.getQuestionsCommentById(questionsCommentId);
			
			//修改问答回复的评论数
			Questions questions=questionsService.getQuestionsById(questionsComment.getQuestionId());
			if (questionsComment.getIsBest()==1) {//如果是最佳答案,修改 问答的状态为 未采纳最佳答案
				questions.setStatus(0);
			}
			questions.setReplyCount(questions.getReplyCount()-1);
			questionsService.updateQuestions(questions);
			
			//删除
			questionsCommentService.deleteQuestionsCommentById(questionsCommentId);
			//删除该问答回复下的所有评论
			questionsCommentService.delQuestionsCommentByCommentId(questionsCommentId);
			json = this.setJson(true, "", "");
		}catch (Exception e) {
			logger.error("delQuestionsComment()---error",e);
			json = this.setJson(false, "系统错误,请稍后重试", "");
		}
		return json;
	}
	
	/**
	 * 查看问答回复下的所有评论
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping("/questionscomment/querybycommentid/{questionsCommentId}")
	public ModelAndView queryByCommentId(HttpServletRequest request,@PathVariable("questionsCommentId")Long questionsCommentId){
		ModelAndView model = new ModelAndView(questionscommentlistone);
		try{
			QuestionsComment questionsComment=new QuestionsComment();
			questionsComment.setOrderFlag("new");
			questionsComment.setCommentId(questionsCommentId);
			List<QuestionsComment> questionsCommentList=questionsCommentService.getQuestionsCommentList(questionsComment);
			model.addObject("questionsCommentList", questionsCommentList);
		}catch (Exception e) {
			model.setViewName(this.setExceptionRequest(request, e));
			logger.error("queryByCommentId()---error",e);
		}
		return model;
	}
	
	/**
	 * 删除问答回复评论
	 */
	@RequestMapping("/questionscomment/delComment/{questionsCommentId}")
	@ResponseBody
	public Object delComment(HttpServletRequest request,@PathVariable("questionsCommentId")Long questionsCommentId){
		Map<String,Object> json = new HashMap<String,Object>();
		try{
			//修改问答回复的回复数
			QuestionsComment questionsComment=questionsCommentService.getQuestionsCommentById(questionsCommentId);
			questionsComment=questionsCommentService.getQuestionsCommentById(questionsComment.getCommentId());
			questionsComment.setReplyCount(questionsComment.getReplyCount()-1);
			questionsCommentService.updateQuestionsComment(questionsComment);
			//删除
			questionsCommentService.deleteQuestionsCommentById(questionsCommentId);
			json = this.setJson(true, "", "");
		}catch (Exception e) {
			logger.error("delComment()---error",e);
			json = this.setJson(false, "系统错误,请稍后重试", "");
		}
		return json;
	}
	
	/**
	 * 后台管理员  采纳问答回复 为最佳答案
	 */
	@RequestMapping("/questionscomment/ajax/acceptComment/{id}")
	@ResponseBody
	public Object acceptComment(HttpServletRequest request,@PathVariable("id")Long id){
		Map<String,Object> json = new HashMap<String,Object>();
		try{
			//查询问答回复
			QuestionsComment questionsComment=questionsCommentService.getQuestionsCommentById(id);
			//查询问答
			Questions questions=questionsService.getQuestionsById(questionsComment.getQuestionId());
			if (questions.getStatus()==1) {
				json = this.setJson(false, "该问答已采纳最佳答案", "");
				return json;
			}
			if(questionsComment.getCusId().intValue()==questions.getCusId().intValue()){
				json = this.setJson(false, "问答最佳答案不能采用他本人发表的回复!", "");
				return json;
			}
			//修改问答回复为最佳答案状态
			questionsComment.setIsBest(1);
			questionsCommentService.updateQuestionsComment(questionsComment);
			//修改问答的状态为 已采纳最佳答案
			questions.setStatus(1);
			questionsService.updateQuestions(questions);
			json = this.setJson(true, "", "");
		}catch (Exception e) {
			json = this.setJson(false, "系统错误,请稍后重试", "");
			logger.error("AdminQuestionsCommentController.acceptComment()---error",e);
		}
		return json;
	}
	
	/**
	 * 获得问答回复
	 */
	@RequestMapping("/questionscomment/getCommentById/{questionsCommentId}")
	@ResponseBody
	public Object getCommentById(HttpServletRequest request,@PathVariable("questionsCommentId")Long questionsCommentId){
		Map<String,Object> json = new HashMap<String,Object>();
		try{
			//问答回复
			QuestionsComment questionsComment=questionsCommentService.getQuestionsCommentById(questionsCommentId);
			json = this.setJson(true, "", questionsComment);
		}catch (Exception e) {
			logger.error("delComment()---error",e);
			json = this.setJson(false, "系统错误,请稍后重试", "");
		}
		return json;
	}
	
	/**
	 * 修改问答回复
	 */
	@RequestMapping("/questionscomment/updQuestionComment")
	@ResponseBody
	public Object updQuestionComment(HttpServletRequest request,@ModelAttribute("questionsComment")QuestionsComment questionsComment){
		Map<String,Object> json = new HashMap<String,Object>();
		try{
			//问答回复
			QuestionsComment questionsCommentUpd=questionsCommentService.getQuestionsCommentById(questionsComment.getId());
			questionsCommentUpd.setPraiseCount(questionsComment.getPraiseCount());
			questionsCommentUpd.setContent(questionsComment.getContent());
			questionsCommentService.updateQuestionsComment(questionsCommentUpd);
			json = this.setJson(true, "", "");
		}catch (Exception e) {
			logger.error("updQuestionComment()---error",e);
			json = this.setJson(false, "系统错误,请稍后重试", "");
		}
		return json;
	}
}
