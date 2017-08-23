package com.inxedu.os.edu.controller.questions;

import java.util.Date;
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
import com.inxedu.os.common.util.SingletonLoginUtils;
import com.inxedu.os.edu.entity.questions.Questions;
import com.inxedu.os.edu.entity.questions.QuestionsComment;
import com.inxedu.os.edu.entity.user.User;
import com.inxedu.os.edu.service.questions.QuestionsCommentService;
import com.inxedu.os.edu.service.questions.QuestionsService;
import com.inxedu.os.edu.service.user.UserService;

/**
 * 问答评论 Controller
 * @author www.inxedu.com
 */
@Controller
public class QuestionsCommentController extends BaseController {
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(QuestionsCommentController.class);
	
	@Autowired
	private QuestionsService questionsService;
	@Autowired
	private QuestionsCommentService questionsCommentService;
	@Autowired
	private UserService userService;
	
	//问答详情 评论列表页面
	private static final String questionscommentlist = getViewPath("/web/questionscomment/questionscomment-ajax-list");
	//子评论
	private static final String questionscommentreplist=getViewPath("/web/questionscomment/questionscomment-ajax-listreply");
	//所有子评论
	private static final String questionscommentreplistall=getViewPath("/web/questionscomment/questionscomment-ajax-listreply_all");
	
	@InitBinder({"questionsComment"})
	public void initBinderQuestionsComment(WebDataBinder binder){
		binder.setFieldDefaultPrefix("questionsComment.");
	}
	
	/**
	 * 问答详情 问答回复列表
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping("/questionscomment/ajax/list")
	public ModelAndView getQuestionsCommentList(HttpServletRequest request,@ModelAttribute("questionsComment")QuestionsComment questionsComment,@ModelAttribute("page") PageEntity page){
		ModelAndView model = new ModelAndView(questionscommentlist);
		try{
			Questions questions=questionsService.getQuestionsById(questionsComment.getQuestionId());
			model.addObject("questions", questions);
			
			page.setPageSize(3);
			List<QuestionsComment> questionsCommentList = questionsCommentService.queryQuestionsCommentListByQuestionsId(questionsComment, page);
			model.addObject("questionsCommentList", questionsCommentList);
			model.addObject("page", page);
			
			int userId = SingletonLoginUtils.getLoginUserId(request);
			User user=userService.queryUserById(userId);
			model.addObject("user", user);
			
			//查询最佳答案
			if(questions.getStatus()==1){//已采纳最佳答案
				questionsComment=new QuestionsComment();
				//查找最佳回答回复
				questionsComment.setIsBest(1);//已采纳
				questionsComment.setQuestionId(questions.getId());
				questionsComment.setLimitSize(1);
				questions.setQuestionsCommentList(questionsCommentService.getQuestionsCommentList(questionsComment));
			}
		}catch (Exception e) {
			model.setViewName(this.setExceptionRequest(request, e));
			logger.error("getQuestionsCommentList()---error",e);
		}
		return model;
	}
	
	/**
	 * 添加回答回复
	 */
	@RequestMapping("/questionscomment/ajax/add")
	@ResponseBody
	public Object addQuestionsComment(HttpServletRequest request,@ModelAttribute("questionsComment")QuestionsComment questionsComment){
		Map<String,Object> json = new HashMap<String,Object>();
		try{
			int userId = SingletonLoginUtils.getLoginUserId(request);
			if (userId==0) {
				json = this.setJson(false, "请先登录", "");
				return json;
			}
			questionsComment.setCusId(Long.valueOf(userId));
			questionsComment.setAddTime(new Date());
			questionsComment.setIsBest(0);
			questionsComment.setReplyCount(0);
			questionsComment.setPraiseCount(0);
			questionsComment.setCommentId(0L);
			questionsCommentService.addQuestionsComment(questionsComment);
			
			//修改问答的评论数
			Questions questions=questionsService.getQuestionsById(questionsComment.getQuestionId());
			questions.setReplyCount(questions.getReplyCount()+1);
			questionsService.updateQuestions(questions);
			json = this.setJson(true, "", "");
		}catch (Exception e) {
			json = this.setJson(false, "系统错误,请稍后重试", "");
			logger.error("addQuestionsComment()---error",e);
		}
		return json;
	}
	
	/**
	 * 添加回答 回复 评论
	 */
	@RequestMapping("/questionscomment/ajax/addReply")
	@ResponseBody
	public Object addQuestionsCommentReply(HttpServletRequest request,@ModelAttribute("questionsComment")QuestionsComment questionsComment){
		Map<String,Object> json = new HashMap<String,Object>();
		try{
			int userId = SingletonLoginUtils.getLoginUserId(request);
			if (userId==0) {
				json = this.setJson(false, "请先登录", "");
				return json;
			}
			questionsComment.setCusId(Long.valueOf(userId));
			questionsComment.setAddTime(new Date());
			questionsComment.setIsBest(0);
			questionsComment.setReplyCount(0);
			questionsComment.setPraiseCount(0);
			questionsComment.setQuestionId(0L);
			questionsCommentService.addQuestionsComment(questionsComment);
			
			//修改问答回复的评论数
			questionsComment=questionsCommentService.getQuestionsCommentById(questionsComment.getCommentId());
			questionsComment.setReplyCount(questionsComment.getReplyCount()+1);
			questionsCommentService.updateQuestionsComment(questionsComment);
			json = this.setJson(true, "", "");
		}catch (Exception e) {
			json = this.setJson(false, "系统错误,请稍后重试", "");
			logger.error("addQuestionsCommentReply()---error",e);
		}
		return json;
	}
	
	/**
	 * 采纳问答回复 为最佳答案
	 */
	@RequestMapping("/questionscomment/ajax/acceptComment")
	@ResponseBody
	public Object acceptComment(HttpServletRequest request,@ModelAttribute("questionsComment")QuestionsComment questionsComment){
		Map<String,Object> json = new HashMap<String,Object>();
		try{
			int userId = SingletonLoginUtils.getLoginUserId(request);
			if (userId==0) {
				json = this.setJson(false, "请先登录", "");
				return json;
			}
			//查询问答
			Questions questions=questionsService.getQuestionsById(questionsComment.getQuestionId());
			if (questions.getStatus()==1) {
				json = this.setJson(false, "该问答已采纳最佳答案", "");
				return json;
			}
			if(userId!=questions.getCusId().intValue()){
				json = this.setJson(false, "您不是该问答的创建者,无权操作!", "");
				return json;
			}
			//查询问答回复评论
			questionsComment=questionsCommentService.getQuestionsCommentById(questionsComment.getCommentId());
			if(questionsComment.getCusId()==questions.getCusId()){
				json = this.setJson(false, "您自己发表的问答回复,不能采纳!", "");
				return json;
			}
			//修改问答回复为最佳答案状态
			questionsComment.setIsBest(1);
			questionsCommentService.updateQuestionsComment(questionsComment);
			//修改问答的状态未 已采纳最佳答案
			questions.setStatus(1);
			questionsService.updateQuestions(questions);
			json = this.setJson(true, "", "");
		}catch (Exception e) {
			json = this.setJson(false, "系统错误,请稍后重试", "");
			logger.error("acceptComment()---error",e);
		}
		return json;
	}
	
	/**
	 * 根据问答回复id  获取子评论 
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping("/questionscomment/ajax/getCommentById/{commentId}")
	public ModelAndView getQuestionsCommentById(HttpServletRequest request,@PathVariable("commentId")Long commentId){
		ModelAndView model = new ModelAndView(questionscommentreplist);
		try{
			QuestionsComment questionsComment=new QuestionsComment();
			questionsComment.setOrderFlag("new");
			//查找该评论下的子评论
			questionsComment.setCommentId(commentId);
			questionsComment.setLimitSize(9);
			List<QuestionsComment> questionsCommentRepList=questionsCommentService.getQuestionsCommentList(questionsComment);
			model.addObject("questionsCommentRepList", questionsCommentRepList);
		}catch (Exception e) {
			model.setViewName(this.setExceptionRequest(request, e));
			logger.error("getQuestionsCommentById()---error",e);
		}
		return model;
	}
	
	/**
	 * 根据问答回复id  获取所有子评论 
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping("/questionscommentall/ajax/getCommentById/{commentId}")
	public ModelAndView getAllQuestionsCommentById(HttpServletRequest request,@PathVariable("commentId")Long commentId){
		ModelAndView model = new ModelAndView(questionscommentreplistall);
		try{
			QuestionsComment questionsComment=new QuestionsComment();
			questionsComment.setOrderFlag("new");
			//查找该评论下的子评论
			questionsComment.setCommentId(commentId);
			List<QuestionsComment> questionsCommentRepList=questionsCommentService.getQuestionsCommentList(questionsComment);
			model.addObject("questionsCommentRepList", questionsCommentRepList);
			
			questionsComment=questionsCommentService.getQuestionsCommentById(commentId);
			model.addObject("questionsComment",questionsComment);
		}catch (Exception e) {
			model.setViewName(this.setExceptionRequest(request, e));
			logger.error("getAllQuestionsCommentById()---error",e);
		}
		return model;
	}
}
