package com.inxedu.os.app.controller.article;

import com.inxedu.os.common.controller.BaseController;
import com.inxedu.os.common.entity.PageEntity;
import com.inxedu.os.edu.entity.article.Article;
import com.inxedu.os.edu.entity.article.QueryArticle;
import com.inxedu.os.edu.service.article.ArticleService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author www.inxedu.com
 */
@Controller
@RequestMapping("/webapp")
public class AppArticleController extends BaseController {
	private static Logger logger = Logger.getLogger(AppArticleController.class);

	@Autowired
	private ArticleService articleService;

	/**
	 * 咨询列表
	 */
	@RequestMapping("/showlist")
	@ResponseBody
	public Map<String, Object> showArticleList(HttpServletRequest request, @ModelAttribute("page") PageEntity page) {
		Map<String, Object> json = new HashMap<String, Object>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			String currentPage = request.getParameter("currentPage");// 当前页
			if (currentPage == null || currentPage.trim().equals("")) {
				json = this.setJson(false, "页码不能为空", null);
				return json;
			}
			page.setCurrentPage(Integer.parseInt(currentPage));// 当前页

			page.setPageSize(10);// 每页多少条数据
			String pageSize = request.getParameter("pageSize");
			if (pageSize != null) {
				page.setPageSize(Integer.parseInt(pageSize));
			}

			String beginTime = request.getParameter("beginCreateTime");
			String endTime = request.getParameter("endCreateTime");
			Date beginCreateTime = null;
			Date endCreateTime = null;
			if (beginTime != null && !beginTime.equals("")) {
				beginCreateTime = sdf.parse(beginTime);// 开始添加时间
			}
			if (endTime != null && !endTime.equals("")) {
				endCreateTime = sdf.parse(endTime);// 结束添加时间
			}
			String queryKey = request.getParameter("queryKey");// 标题或文章来源

			/* ===================查询条件================== */
			QueryArticle queryArticle = new QueryArticle();
			queryArticle.setType(2);// 文章类型 2文章
			queryArticle.setBeginCreateTime(beginCreateTime);// 开始添加时间
			queryArticle.setEndCreateTime(endCreateTime);// 结束添加时间
			queryArticle.setQueryKey(queryKey);// 标题或文章来源
			List<Article> articleList = articleService.queryArticlePage(queryArticle, page);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("articleList", articleList);
			map.put("page", page);
			json = this.setJson(true, "成功", map);
		} catch (Exception e) {
			json = this.setJson(false, "异常", null);
			logger.error("toSubjectList()--error", e);
		}
		return json;
	}

	/**
	 * 咨询详情
	 */
	@RequestMapping("/articleinfo")
	@ResponseBody
	public Map<String, Object> articleInfo(HttpServletRequest request) {
		Map<String, Object> json = new HashMap<String, Object>();
		try {
			String id = request.getParameter("id");// 文章Id
			if (id == null || id.trim().equals("")) {
				json = this.setJson(false, "id不能为空", null);
				return json;
			}
			Article article = articleService.queryArticleById(Integer.parseInt(id));// 文章信息
			String content = articleService.queryArticleContentByArticleId(Integer.parseInt(id));// 文章内容
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("article", article);
			map.put("content", content);
			json = this.setJson(true, "成功", map);
		} catch (Exception e) {
			json = this.setJson(false, "异常", null);
			logger.error("articleInfo()--error", e);
		}
		return json;
	}
}
