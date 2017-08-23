package com.inxedu.os.edu.entity.questions;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;
/**
 * 问答评论
 *@author www.inxedu.com
 */
@Data
public class QuestionsComment implements Serializable {
	private static final long serialVersionUID = 7687324559966427231L;
    private Long id;// 主键自增
    private Long cusId;// 评论人id
    private Long questionId;// 问答id
    private String content;// 内容
    private int isBest;// 是否为最佳答案 0否1是
    private int replyCount;// 回复数量
    private int praiseCount;// 点赞数
    private Date addTime;// 添加时间
    private Long commentId;//父级评论id
    
    private int limitSize;//查询限制条数
    private String orderFlag;//排序值 new 最新
    private String showName;//用户名
    private String email;//用户邮箱
    private String picImg;//用户头像
    private List<QuestionsComment> questionsCommentList;//子评论列表
    private String questionsTitle;//问答标题
    private int questionsStatus;//问答状态  0可回复1不可回复（采纳最佳答案后改为1 ）
    private Date beginCreateTime;//查询 开始添加时间
    private Date endCreateTime;//查询 结束添加时间
}
