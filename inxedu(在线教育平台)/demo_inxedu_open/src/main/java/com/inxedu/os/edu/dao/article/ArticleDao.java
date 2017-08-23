package com.inxedu.os.edu.dao.article;

import com.inxedu.os.common.entity.PageEntity;
import com.inxedu.os.edu.entity.article.Article;
import com.inxedu.os.edu.entity.article.ArticleContent;
import com.inxedu.os.edu.entity.article.QueryArticle;

import java.util.List;
import java.util.Map;

/**
 * 文章DAO层接口
 * @author www.inxedu.com
 */
public interface ArticleDao {
	
	/**
	 * 创建文章
	 * @param article 文章实体
	 * @return 返回文章ID
	 */
	public int createArticle(Article article);
	
	/**
	 * 添加文章内容
	 * @param content 文章内容实体
	 */
	public void addArticleContent(ArticleContent content);
	
	/**
	 * 修改文章信息
	 * @param article 文章实体
	 */
	public void updateArticle(Article article);
	
	/**
	 * 修改文章内容 
	 * @param content
	 */
	public void updateArticleContent(ArticleContent content);
	
	/**
	 * 删除文章
	 * @param articleIds 文章ID串 如：(1,2,3,4)
	 */
	public void deleteArticleByIds(String articleIds);
	
	/**
	 * 删除文章内容
	 * @param articleIds 文章ID串 如：(1,2,3,4)
	 */
	public void deleteArticleContentByArticleIds(String articleIds);
	
	/**
	 * 通过文章ID查询文章信息
	 * @param articleId 文章ID
	 * @return Article文章实体信息
	 */
	public Article queryArticleById(int articleId);
	
	/**
	 * 通过文章ID查询文章内容
	 * @param articleId 文章内容
	 * @return String类型文章内容
	 */
	public String queryArticleContentByArticleId(int articleId);
	
	/**
	 * 分页查询文章列表
	 * @param query 查询条件
	 * @param page 分页条件
	 * @return List<Article>文章列表
	 */
	public List<Article> queryArticlePage(QueryArticle query,PageEntity page);
	
	/**
	 * 修改累加文章点击量
	 */
	public void updateArticleNum(Map<String,String> map);
	
	/**
	 * 公共多条件查询文章资讯列表,用于前台
	 */
	public List<Article> queryArticleList(QueryArticle queryArticle);
	
	/**
	 * 获取所有文章总记录数
	 * @return 总记录数
	 */
	public int queryAllArticleCount();

}
