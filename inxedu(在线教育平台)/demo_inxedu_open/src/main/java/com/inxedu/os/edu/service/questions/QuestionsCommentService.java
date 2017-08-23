package com.inxedu.os.edu.service.questions;

import com.inxedu.os.common.entity.PageEntity;
import com.inxedu.os.edu.entity.questions.QuestionsComment;

import java.util.List;

/**
 * QuestionsComment管理接口
 * @author www.inxedu.com
 */
public interface QuestionsCommentService {

    /**
     * 添加QuestionsComment
     * @param questionsComment 要添加的QuestionsComment
     * @return id
     */
    public java.lang.Long addQuestionsComment(QuestionsComment questionsComment);

    /**
     * 根据id删除一个QuestionsComment
     *
     * @param id 要删除的id
     */
    public Long deleteQuestionsCommentById(Long id);
    
    /**
     * 根据id查询QuestionsComment
     *
     * @param id 要查询的id
     */
    public QuestionsComment getQuestionsCommentById(Long id);

    /**
     * 修改QuestionsComment
     *
     * @param questionsComment 要修改的QuestionsComment
     */
    public void updateQuestionsComment(QuestionsComment questionsComment);

    /**
     * 根据条件获取QuestionsComment列表
     *
     * @param questionsComment 查询条件
     * @return List<QuestionsComment>
     */
    public List<QuestionsComment> getQuestionsCommentList(QuestionsComment questionsComment);

    /**
     * 通过问答id 查询该问答下的回复
     * @return List<QuestionsComment>
     */
    public List<QuestionsComment> queryQuestionsCommentListByQuestionsId(QuestionsComment questionsComment, PageEntity page);

    /**
     * 根据问答id删除QuestionsComment
     *
     * @param id 要删除的id
     */
    public Long delQuestionsCommentByQuestionId(Long id);
    
    /**
     * 查询所有的问答
     */
    public List<QuestionsComment> queryQuestionsCommentList(QuestionsComment questionsComment, PageEntity page);
    
    /**
     * 根据问答回复id删除QuestionsComment
     */
    public Long delQuestionsCommentByCommentId(Long commentId);
}