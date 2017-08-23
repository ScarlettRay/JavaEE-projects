package com.inxedu.os.edu.service.impl.article;

import com.inxedu.os.common.entity.PageEntity;
import com.inxedu.os.edu.dao.article.ArticleDao;
import com.inxedu.os.edu.entity.article.Article;
import com.inxedu.os.edu.entity.article.ArticleContent;
import com.inxedu.os.edu.entity.article.QueryArticle;
import com.inxedu.os.edu.service.article.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author www.inxedu.com
 *
 */
@Service("articleService")
public class ArticleServiceImpl implements ArticleService {

	@Autowired
	private ArticleDao articleDao;

	public int createArticle(Article article) {
		return articleDao.createArticle(article);
	}

	public void addArticleContent(ArticleContent content) {
		articleDao.addArticleContent(content);
	}

	public void updateArticle(Article article) {
		articleDao.updateArticle(article);
	}

	public void updateArticleContent(ArticleContent content) {
		articleDao.updateArticleContent(content);
	}

	public void deleteArticleByIds(String[] articleIds) {
		if (articleIds != null && articleIds.length > 0) {
			String ids = "";
			for (int i = 0; i < articleIds.length; i++) {
				if (i < articleIds.length - 1) {
					ids += articleIds[i] + ",";
				} else {
					ids += articleIds[i];
				}
			}
			articleDao.deleteArticleByIds(ids);
			articleDao.deleteArticleContentByArticleIds(ids);
		}
	}

	public Article queryArticleById(int articleId) {
		return articleDao.queryArticleById(articleId);
	}

	public String queryArticleContentByArticleId(int articleId) {
		return articleDao.queryArticleContentByArticleId(articleId);
	}

	/**
	 * 分页查询文章列表
	 */
	public List<Article> queryArticlePage(QueryArticle query, PageEntity page) {
		return articleDao.queryArticlePage(query, page);
	}

	public void updateArticleNum(Map<String, String> map) {
		articleDao.updateArticleNum(map);
	}

	/**
	 * 公共多条件查询文章资讯列表,用于前台
	 */
	public List<Article> queryArticleList(QueryArticle queryArticle) {
		return articleDao.queryArticleList(queryArticle);
	}

	public int queryAllArticleCount() {
		return articleDao.queryAllArticleCount();
	}

}
