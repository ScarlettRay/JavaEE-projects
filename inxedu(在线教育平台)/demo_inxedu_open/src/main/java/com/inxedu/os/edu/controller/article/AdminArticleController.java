package com.inxedu.os.edu.controller.article;

import com.inxedu.os.common.cache.EHCacheUtil;
import com.inxedu.os.common.constants.CacheConstans;
import com.inxedu.os.common.controller.BaseController;
import com.inxedu.os.common.entity.PageEntity;
import com.inxedu.os.common.util.WebUtils;
import com.inxedu.os.edu.entity.article.Article;
import com.inxedu.os.edu.entity.article.ArticleContent;
import com.inxedu.os.edu.entity.article.QueryArticle;
import com.inxedu.os.edu.service.article.ArticleService;
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
import java.util.Date;
import java.util.List;

/**
 * 文章后台管理
 * @author www.inxedu.com
 */
@Controller
@RequestMapping("/admin/article")
public class AdminArticleController extends BaseController {

	private static Logger logger = LoggerFactory.getLogger(AdminArticleController.class);
	// 文章添加页面
	private static String addArticlePage = getViewPath("/admin/article/add-article");
	// 文章列表
	private static String articleListPage = getViewPath("/admin/article/article-list");
	// 更新文章
	private static String updatePage = getViewPath("/admin/article/update-article");

	@Autowired
	private ArticleService articleService;

	@InitBinder({ "articleContent" })
	public void initArticleContentBinder(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("articleContent.");
	}

	@InitBinder({ "article" })
	public void initArticleBinder(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("article.");
	}

	@InitBinder({ "queryArticle" })
	public void initQueryArticleBinder(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("queryArticle.");
	}

	/**
	 * 执行添加文章
	 */
	@RequestMapping("/addarticle")
	public ModelAndView addArticle(HttpServletRequest request, @ModelAttribute("article") Article article, @ModelAttribute("articleContent") ArticleContent articleContent) {
		ModelAndView model = new ModelAndView();
		try {
			// 重定向到咨询列表
			model.setViewName("redirect:/admin/article/initcreate");
			// 添加时间
			article.setCreateTime(new Date());
			// 添加文章
			articleService.createArticle(article);
			// 添加文章内容
			articleContent.setArticleId(article.getArticleId());
			articleService.addArticleContent(articleContent);
		} catch (Exception e) {
			model.setViewName(this.setExceptionRequest(request, e));
			logger.error("AdminArticleController.addArticle()---error", e);
		}
		return model;
	}

	/**
	 * 修改文章
	 */
	@RequestMapping("/updatearticle")
	public ModelAndView updateArticle(HttpServletRequest request, @ModelAttribute("article") Article article, @ModelAttribute("articleContent") ArticleContent articleContent) {
		ModelAndView model = new ModelAndView();
		try {
			articleService.updateArticle(article);
			articleContent.setArticleId(article.getArticleId());
			articleService.updateArticleContent(articleContent);
			// 修改成功返回原列表页面
			model.setViewName(this.getRequestUri(request));
		} catch (Exception e) {
			model.setViewName(this.setExceptionRequest(request, e));
			logger.error("AdminArticleController.updateArticle()--error", e);
		}
		return model;
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	public ModelAndView delete(HttpServletRequest request) {
		ModelAndView model = new ModelAndView();
		try {
			model.setViewName(this.getRequestUri(request));
			String[] aridArr = request.getParameterValues("articelId");
			if (aridArr != null && aridArr.length > 0) {
				this.deleteArticle(aridArr);
			}
		} catch (Exception e) {
			model.setViewName(this.setExceptionRequest(request, e));
			logger.error("AdminArticleController.delete()--error", e);
		}
		return model;
	}

	/**
	 * 执行删除文章
	 */
	private void deleteArticle(String[] artidArr) {
		// 删除数据中记录
		articleService.deleteArticleByIds(artidArr);
		EHCacheUtil.remove(CacheConstans.ARTICLE_GOOD_RECOMMEND);

	}

	/**
	 * 初始化修改页面
	 */
	@RequestMapping("/initupdate/{articleId}")
	public ModelAndView initUpdateArticle(HttpServletRequest request, @PathVariable("articleId") int articleId) {
		ModelAndView model = new ModelAndView();
		try {
			model.setViewName(updatePage);
			// 查询文章信息
			Article article = articleService.queryArticleById(articleId);
			model.addObject("article", article);
			// 查询文章内容
			String content = articleService.queryArticleContentByArticleId(articleId);
			model.addObject("content", content);
		} catch (Exception e) {
			model.setViewName(this.setExceptionRequest(request, e));
			logger.error("AdminArticleController.initUpdateArticle()--erro", e);
		}
		return model;
	}

	/**
	 * 分页查询文章列表
	 */
	@RequestMapping("/showlist")
	public ModelAndView showArticleList(HttpServletRequest request, @ModelAttribute("queryArticle") QueryArticle queryArticle, @ModelAttribute("page") PageEntity page) {
		ModelAndView model = new ModelAndView();
		try {
			model.setViewName(articleListPage);
			page.setPageSize(12);
			List<Article> articleList = articleService.queryArticlePage(queryArticle, page);
			model.addObject("articleList", articleList);
			model.addObject("page", page);
			String uri = WebUtils.getServletRequestUriParms(request);
			request.getSession().setAttribute("requestUri", uri);
		} catch (Exception e) {
			model.setViewName(this.setExceptionRequest(request, e));
			logger.error("AdminArticleController.showArticleList()--error", e);
		}
		return model;
	}

	/**
	 * 初始化文章添加页面
	 */
	@RequestMapping("/initcreate")
	public ModelAndView initAddArticle(HttpServletRequest request) {
		ModelAndView model = new ModelAndView();
		model.setViewName(addArticlePage);
		return model;
	}

	/**
	 * 获取操作完成返回原有页面
	 */
	private String getRequestUri(HttpServletRequest request) {
		Object uri = request.getSession().getAttribute("requestUri");
		if (uri == null) {
			return "redirect:/admin/article/showlist";
		} else {
			return "redirect:" + uri.toString();
		}
	}

}
