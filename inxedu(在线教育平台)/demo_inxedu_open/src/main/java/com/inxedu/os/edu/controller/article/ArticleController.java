package com.inxedu.os.edu.controller.article;

import com.inxedu.os.common.cache.EHCacheUtil;
import com.inxedu.os.common.constants.CacheConstans;
import com.inxedu.os.common.controller.BaseController;
import com.inxedu.os.common.entity.PageEntity;
import com.inxedu.os.edu.entity.article.Article;
import com.inxedu.os.edu.entity.article.QueryArticle;
import com.inxedu.os.edu.entity.website.WebsiteImages;
import com.inxedu.os.edu.service.article.ArticleService;
import com.inxedu.os.edu.service.website.WebsiteImagesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 前台文章资讯
 * @author www.inxedu.com
 */
@Controller
@RequestMapping("/front")

public class ArticleController extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(ArticleController.class);
	// 文章列表
	private static String listPage = getViewPath("/web/article/article-list");
	// 好文推荐
	private static String queryArticleRecommend = getViewPath("/web/article/article-recommend");
	// 文章详情
	private static String articleInfo = getViewPath("/web/article/article-info");
	
	@Autowired
	private ArticleService articleService;
	@Autowired
	private WebsiteImagesService websiteImagesService;

	/**
	 * 分页查询文章列表
	 */
	@RequestMapping("/articlelist")
	public ModelAndView showArticleList(HttpServletRequest request, @ModelAttribute("queryArticle") QueryArticle queryArticle, @ModelAttribute("page") PageEntity page) {
		ModelAndView model = new ModelAndView();
		try {
			// 查询已经发布的文章资讯
			queryArticle.setType(2);
			page.setPageSize(10);
			List<Article> articleList = articleService.queryArticlePage(queryArticle, page);
			model.addObject("articleList", articleList);
			model.addObject("page", page);
			model.setViewName(listPage);

		} catch (Exception e) {
			model.setViewName(this.setExceptionRequest(request, e));
			logger.error("showArticleList()--error", e);
		}
		return model;
	}

	/**
	 * 修改文章点击数量
	 */
	@RequestMapping("/updateArticleClickNum/{articleId}")
	@ResponseBody
	public Map<String, Object> updateArticleClickNum(HttpServletRequest request, @PathVariable("articleId") int articleId) {
		Map<String,Object> json = new HashMap<String,Object>();
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("num", "+1");
			map.put("type", "clickNum");
			map.put("articleId", articleId + "");
			articleService.updateArticleNum(map);
			Article article = articleService.queryArticleById(articleId);
			json = this.setJson(true, null, article);
		} catch (Exception e) {
			this.setAjaxException(json);
			logger.error("updateArticleClickNum()--error", e);
		}
		return json;
	}
	

	/**
	 * 查询好文推荐（文章按浏览数排行）
	 */
	@RequestMapping("/ajax/recommend")
	public String queryArticleRecommend(HttpServletRequest request) {
		try {
			// 查询排行文章
			List<Article> articleList=(List<Article>)EHCacheUtil.get(CacheConstans.ARTICLE_GOOD_RECOMMEND);
			if (articleList==null||articleList.size()==0) {
				QueryArticle query = new QueryArticle();
				query.setType(2);
				query.setCount(8);
				query.setOrderby(1);
				articleList = articleService.queryArticleList(query);
				EHCacheUtil.set(CacheConstans.ARTICLE_GOOD_RECOMMEND, articleList,CacheConstans.RECOMMEND_COURSE_TIME);//缓存一小时
			}
			request.setAttribute("articleList", articleList);
			
			// 获得广告图
			Map<String, List<WebsiteImages>> websiteImages = websiteImagesService.queryImagesByType();
			request.setAttribute("websiteImages", websiteImages);
		} catch (Exception e) {
			logger.error("queryArticleRecommend()--error", e);
		}
		return queryArticleRecommend;
	}
	/**
	 * 文章详情
	 */
	@RequestMapping("/articleinfo/{id}.html")
	public String articleInfo(HttpServletRequest request, @PathVariable("id") int id) {
		try {
			// 查询文章详情
			Article article = articleService.queryArticleById(id);
			String content = articleService.queryArticleContentByArticleId(id);
			request.setAttribute("content", content);
			request.setAttribute("article", article);
		} catch (Exception e) {
			logger.error("articleInfo()--error", e);
			return this.setExceptionRequest(request, e);
		}
		return articleInfo;
	}

}
