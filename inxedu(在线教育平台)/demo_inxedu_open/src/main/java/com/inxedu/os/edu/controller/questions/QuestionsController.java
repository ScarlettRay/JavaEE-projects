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

import com.inxedu.os.common.cache.EHCacheUtil;
import com.inxedu.os.common.constants.CacheConstans;
import com.inxedu.os.common.constants.CommonConstants;
import com.inxedu.os.common.controller.BaseController;
import com.inxedu.os.common.entity.PageEntity;
import com.inxedu.os.common.util.ObjectUtils;
import com.inxedu.os.common.util.SingletonLoginUtils;
import com.inxedu.os.common.util.StringUtils;
import com.inxedu.os.edu.entity.questions.Questions;
import com.inxedu.os.edu.entity.questions.QuestionsComment;
import com.inxedu.os.edu.entity.questions.QuestionsTag;
import com.inxedu.os.edu.entity.questions.QuestionsTagRelation;
import com.inxedu.os.edu.entity.user.User;
import com.inxedu.os.edu.service.questions.QuestionsCommentService;
import com.inxedu.os.edu.service.questions.QuestionsService;
import com.inxedu.os.edu.service.questions.QuestionsTagRelationService;
import com.inxedu.os.edu.service.questions.QuestionsTagService;
import com.inxedu.os.edu.service.user.UserService;

/**
 * 问答 Controller
 * @author www.inxedu.com
 */
@Controller
public class QuestionsController extends BaseController {
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(QuestionsController.class);

	@Autowired
	private QuestionsService questionsService;
	@Autowired
	private UserService userService;
	@Autowired
	private QuestionsCommentService questionsCommentService;
	@Autowired
	private QuestionsTagRelationService questionsTagRelationService;
	@Autowired
	private QuestionsTagService questionsTagService;

	// 问答列表
	private static final String questionslist = getViewPath("/web/questions/questions-list");
	// 添加问答页面
	private static final String questionsadd = getViewPath("/web/questions/questions-add");
	// 问答详情页面
	private static final String questionsinfo = getViewPath("/web/questions/questions-info");
	// 我的问答列表
	private static final String myquestionslist = getViewPath("/web/ucenter/questions-mylist");
	// 问答 我的回答列表
	private static final String myrepquestionslist = getViewPath("/web/ucenter/questions-myreplist");

	@InitBinder({ "questions" })
	public void initBinderQuestions(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("questions.");
	}

	/**
	 * 问答列表
	 * 
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping("/questions/list")
	public ModelAndView getQuestionsList(HttpServletRequest request, @ModelAttribute("questions") Questions questions, @ModelAttribute("page") PageEntity page) {
		ModelAndView model = new ModelAndView(questionslist);
		try {
			page.setPageSize(8);
			List<Questions> questionsList = questionsService.getQuestionsList(questions, page);
			// 查询该问答的标签
			QuestionsTagRelation questionsTagRelation = new QuestionsTagRelation();
			if (questionsList != null && questionsList.size() > 0) {
				QuestionsComment questionsComment = new QuestionsComment();
				for (Questions questionsTemp : questionsList) {
					questionsTagRelation.setQuestionsId(questionsTemp.getId());
					questionsTemp.setQuestionsTagRelationList(questionsTagRelationService.queryQuestionsTagRelation(questionsTagRelation));

					if (questionsTemp.getReplyCount() > 0) {// 问答评论不为空
						questionsComment.setQuestionId(questionsTemp.getId());
						questionsComment.setLimitSize(1);// 一条
						if (questionsTemp.getStatus() == 1) {// 已采纳最佳答案
							// 查找最佳回答回复
							questionsComment.setIsBest(1);// 已采纳
							questionsTemp.setQuestionsCommentList(questionsCommentService.getQuestionsCommentList(questionsComment));
						} else {
							// 查找最新回答回复
							questionsComment.setIsBest(-1);
							questionsComment.setOrderFlag("new");// 最新
							questionsTemp.setQuestionsCommentList(questionsCommentService.getQuestionsCommentList(questionsComment));
						}
					}
				}
			}
			model.addObject("questionsList", questionsList);
			model.addObject("page", page);

			// 查询所有的问答标签
			QuestionsTag questionsTag = new QuestionsTag();
			List<QuestionsTag> questionsTagList = questionsTagService.getQuestionsTagList(questionsTag);
			model.addObject("questionsTagList", questionsTagList);
		} catch (Exception e) {
			model.setViewName(this.setExceptionRequest(request, e));
			logger.error("QuestionsController.getQuestionsList()---error", e);
		}
		return model;
	}

	/**
	 * 到添加问答页面
	 */
	@RequestMapping("/questions/toadd")
	public ModelAndView toQuestionsAdd(HttpServletRequest request) {
		ModelAndView model = new ModelAndView(questionsadd);
		try {
			// 查询所有问答标签
			QuestionsTag questionsTag = new QuestionsTag();
			List<QuestionsTag> questionsTagList = questionsTagService.getQuestionsTagList(questionsTag);
			model.addObject("questionsTagList", questionsTagList);
		} catch (Exception e) {
			model.setViewName(this.setExceptionRequest(request, e));
			logger.error("QuestionsController.toQuestionsAdd()---error", e);
		}
		return model;
	}

	/**
	 * 添加问答
	 */
	@RequestMapping("/questions/ajax/add")
	@ResponseBody
	public Object addQuestions(HttpServletRequest request, @ModelAttribute("questions") Questions questions) {
		Map<String,Object> json = new HashMap<String,Object>();
		try {
			String randomCode = request.getParameter("randomCode");
			if (StringUtils.isEmpty(randomCode) || request.getSession().getAttribute(CommonConstants.RAND_CODE) == null || !randomCode.equalsIgnoreCase(request.getSession().getAttribute(CommonConstants.RAND_CODE).toString())) {
				json = this.setJson(false, "验证码错误", null);
				return json;
			}
			int userId = SingletonLoginUtils.getLoginUserId(request);
			if (userId == 0) {
				json = this.setJson(false, "请先登录", "");
				return json;
			}
			questions.setCusId(Long.valueOf(userId));
			questions.setAddTime(new Date());
			questionsService.addQuestions(questions);

			// 保存该问答的 问答标签(多个)
			String questionsTag = request.getParameter("questionsTag");
			if (ObjectUtils.isNotNull(questionsTag)) {
				questionsTag = questionsTag.substring(1);
				String questionsTagArr[] = questionsTag.split(",");
				for (int i = 0; i < questionsTagArr.length; i++) {
					QuestionsTagRelation questionsTagRelation = new QuestionsTagRelation();
					questionsTagRelation.setQuestionsId(questions.getId());
					questionsTagRelation.setQuestionsTagId(Long.valueOf(questionsTagArr[i]));
					questionsTagRelationService.addQuestionsTagRelation(questionsTagRelation);
				}
			}
			json = this.setJson(true, "", questions.getId());
		} catch (Exception e) {
			logger.error("QuestionsController.addQuestions()---error", e);
			json = this.setJson(false, "系统错误,请稍后重试", "");
		}
		return json;
	}

	/**
	 * 到问答详情
	 */
	@RequestMapping("/questions/info/{id}")
	public ModelAndView toQuestionsInfo(HttpServletRequest request, @PathVariable("id") Long id) {
		ModelAndView model = new ModelAndView(questionsinfo);
		try {
			Questions questions = questionsService.getQuestionsById(id);
			// 更新问答的浏览数 +1
			questions.setBrowseCount(questions.getBrowseCount() + 1);
			questionsService.updateQuestions(questions);

			// 查询该问答的标签
			QuestionsTagRelation questionsTagRelation = new QuestionsTagRelation();
			questionsTagRelation.setQuestionsId(questions.getId());
			questions.setQuestionsTagRelationList(questionsTagRelationService.queryQuestionsTagRelation(questionsTagRelation));
			model.addObject("questions", questions);

			int userId = SingletonLoginUtils.getLoginUserId(request);
			User user = userService.queryUserById(userId);
			model.addObject("user", user);

			// 热门问答
			List<Questions> hotQuestionsList = questionsService.queryQuestionsOrder(8);
			model.addObject("hotQuestionsList", hotQuestionsList);

			// 查询所有的问答标签
			QuestionsTag questionsTag = new QuestionsTag();
			List<QuestionsTag> questionsTagList = questionsTagService.getQuestionsTagList(questionsTag);
			model.addObject("questionsTagList", questionsTagList);
		} catch (Exception e) {
			model.setViewName(this.setExceptionRequest(request, e));
			logger.error("QuestionsController.toQuestionsInfo()---error", e);
		}
		return model;
	}

	/**
	 * 我的问答列表
	 * 
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping("/uc/myquestions/list")
	public ModelAndView getMyQuestionsList(HttpServletRequest request, @ModelAttribute("questions") Questions questions, @ModelAttribute("page") PageEntity page) {
		ModelAndView model = new ModelAndView(myquestionslist);
		try {
			page.setPageSize(4);
			int userId = SingletonLoginUtils.getLoginUserId(request);
			questions.setCusId(Long.valueOf(userId));
			List<Questions> questionsList = questionsService.getQuestionsList(questions, page);
			// 查询该问答的问答标签
			QuestionsTagRelation questionsTagRelation = new QuestionsTagRelation();
			if (questionsList != null && questionsList.size() > 0) {
				QuestionsComment questionsComment = new QuestionsComment();
				for (Questions questionsTemp : questionsList) {
					questionsTagRelation.setQuestionsId(questionsTemp.getId());
					questionsTemp.setQuestionsTagRelationList(questionsTagRelationService.queryQuestionsTagRelation(questionsTagRelation));

					if (questionsTemp.getReplyCount() > 0) {// 问答评论不为空
						questionsComment.setQuestionId(questionsTemp.getId());
						questionsComment.setLimitSize(1);// 一条
						if (questionsTemp.getStatus() == 1) {// 已采纳最佳答案
							// 查找最佳回答回复
							questionsComment.setIsBest(1);// 已采纳
							questionsTemp.setQuestionsCommentList(questionsCommentService.getQuestionsCommentList(questionsComment));
						} else {
							// 查找最新回答回复
							questionsComment.setIsBest(-1);
							questionsComment.setOrderFlag("new");// 最新
							questionsTemp.setQuestionsCommentList(questionsCommentService.getQuestionsCommentList(questionsComment));
						}
					}
				}
			}
			model.addObject("questionsList", questionsList);
			model.addObject("page", page);
		} catch (Exception e) {
			model.setViewName(this.setExceptionRequest(request, e));
			logger.error("QuestionsController.getMyQuestionsList()---error", e);
		}
		return model;
	}

	/**
	 * 问答 我的回答列表
	 * 
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping("/uc/myrepquestions/list")
	public ModelAndView getMyRepQuestionsList(HttpServletRequest request, @ModelAttribute("questions") Questions questions, @ModelAttribute("page") PageEntity page) {
		ModelAndView model = new ModelAndView(myrepquestionslist);
		try {
			page.setPageSize(4);
			int userId = SingletonLoginUtils.getLoginUserId(request);
			questions.setCommentUserId(Long.valueOf(userId));
			List<Questions> questionsList = questionsService.getQuestionsList(questions, page);
			// 查询该问答的标签
			QuestionsTagRelation questionsTagRelation = new QuestionsTagRelation();
			if (questionsList != null && questionsList.size() > 0) {
				QuestionsComment questionsComment = new QuestionsComment();
				for (Questions questionsTemp : questionsList) {
					questionsTagRelation.setQuestionsId(questionsTemp.getId());
					questionsTemp.setQuestionsTagRelationList(questionsTagRelationService.queryQuestionsTagRelation(questionsTagRelation));

					if (questionsTemp.getReplyCount() > 0) {// 问答评论不为空
						questionsComment.setQuestionId(questionsTemp.getId());
						questionsComment.setLimitSize(1);// 一条
						if (questionsTemp.getStatus() == 1) {// 已采纳最佳答案
							// 查找最佳回答回复
							questionsComment.setIsBest(1);// 已采纳
							questionsTemp.setQuestionsCommentList(questionsCommentService.getQuestionsCommentList(questionsComment));
						} else {
							// 查找最新回答回复
							questionsComment.setIsBest(-1);
							questionsComment.setOrderFlag("new");// 最新
							questionsTemp.setQuestionsCommentList(questionsCommentService.getQuestionsCommentList(questionsComment));
						}
					}
				}
			}
			model.addObject("questionsList", questionsList);
			model.addObject("page", page);
		} catch (Exception e) {
			model.setViewName(this.setExceptionRequest(request, e));
			logger.error("QuestionsController.getMyRepQuestionsList()---error", e);
		}
		return model;
	}
	
	/**
	 * 热门问答 推荐
	 */
	@RequestMapping("/questions/ajax/hotRecommend")
	@ResponseBody
	public Object hotQuestionsRecommend(HttpServletRequest request, @ModelAttribute("questions") Questions questions) {
		Map<String,Object> json = new HashMap<String,Object>();
		try {
			List<Questions> hotQuestionsList=(List<Questions>)EHCacheUtil.get(CacheConstans.QUESTIONS_HOT_RECOMMEND);
			if(hotQuestionsList==null||hotQuestionsList.size()==0){
				hotQuestionsList = questionsService.queryQuestionsOrder(8);
				EHCacheUtil.set(CacheConstans.QUESTIONS_HOT_RECOMMEND, hotQuestionsList);
			}
			json = this.setJson(true, "", hotQuestionsList);
		} catch (Exception e) {
			logger.error("QuestionsController.hotQuestionsRecommend()---error", e);
			json = this.setJson(false, "系统错误,请稍后重试", "");
		}
		return json;
	}
}
