package com.inxedu.os.edu.dao.common;

import com.inxedu.os.common.entity.PageEntity;
import com.inxedu.os.edu.entity.common.Comment;

import java.util.List;
import java.util.Map;
/**
 * 评论dao层接口
 * @author www.inxedu.com
 */
public interface CommentDao {
	/**
	 * 分页查询评论
	 */
	public List<Comment> getCommentByPage(Comment comment,PageEntity page);
	/**
	 * 添加评论
	 * @param comment 评论实体
	 */
	public void addComment(Comment comment);
	/**
	 * 更新评论
	 */
	public void updateComment(Comment comment);
	/**
	 * 查询评论互动
	 */
	public List<Comment> queryCommentInteraction(Comment comment);
	/**
	 * 更新评论点赞数,回复数等
	 */
	public void updateCommentNum(Map<String,String> map);
	/**
	 * 查询评论
	 */
	public Comment queryComment(Comment comment);
	/**
	 * 删除评论
	 */
	public void delComment(int commentId);
	/**
	 * 查询评论 list
	 */
	public List<Comment> queryCommentList(Comment comment);
}
