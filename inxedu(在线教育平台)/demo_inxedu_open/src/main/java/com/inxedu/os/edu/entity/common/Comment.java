package com.inxedu.os.edu.entity.common;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

/**
 * @author www.inxedu.com
 *
 */
@Data
public class Comment {
	private int commentId;//评论Id
	private int userId;//用户id
	private int pCommentId;//父级评论id // 为0 则是一级评论 不为0则是评论的回复
	private String content;//评论内容
	private Date addTime;//发送时间
	private int otherId;//相关Id
	private int praiseCount;//点赞数
	private int replyCount;//回复数
	private int type;//类型 1 文章 2 课程

	private String email;//用户Email
	private String userName;//用户昵称
	private String courseName;//课程名
	private int courseId;//课程id
	private String picImg;//用户头像
	private int commentNumber;//回复数
	private int limitNumber;//查询数
	private String order ="commentId";// commentId 降序  praiseCount 降序
	
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date beginCreateTime;//查询 开始时间
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date endCreateTime;//查询 结束时间
}
