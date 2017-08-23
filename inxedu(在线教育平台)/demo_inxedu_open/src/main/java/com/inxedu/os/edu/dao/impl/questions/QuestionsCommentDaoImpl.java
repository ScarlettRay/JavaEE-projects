package com.inxedu.os.edu.dao.impl.questions;

import java.util.List;

import com.inxedu.os.common.entity.PageEntity;
import org.springframework.stereotype.Repository;

import com.inxedu.os.common.dao.GenericDaoImpl;
import com.inxedu.os.edu.dao.questions.QuestionsCommentDao;
import com.inxedu.os.edu.entity.questions.QuestionsComment;

/**
 * @author www.inxedu.com
 *
 */
@Repository("questionsCommentDao")
public class QuestionsCommentDaoImpl extends GenericDaoImpl implements QuestionsCommentDao {

	@Override
	public Long addQuestionsComment(QuestionsComment questionsComment) {
		return this.insert("QuestionsCommentMapper.createQuestionsComment", questionsComment);
	}

	@Override
	public Long deleteQuestionsCommentById(Long id) {
		return this.delete("QuestionsCommentMapper.deleteQuestionsCommentById", id);
	}

	@Override
	public QuestionsComment getQuestionsCommentById(Long id) {
		return this.selectOne("QuestionsCommentMapper.getQuestionsCommentById", id);
	}
	
	@Override
	public void updateQuestionsComment(QuestionsComment questionsComment) {
		this.update("QuestionsCommentMapper.updateQuestionsComment", questionsComment);
	}

	@Override
	public List<QuestionsComment> getQuestionsCommentList(QuestionsComment questionsComment) {
		return this.selectList("QuestionsCommentMapper.getQuestionsCommentList", questionsComment);
	}

	@Override
	public List<QuestionsComment> queryQuestionsCommentListByQuestionsId(QuestionsComment questionsComment,
			PageEntity page) {
		return this.queryForListPage("QuestionsCommentMapper.queryQuestionsCommentListByQuestionsId", questionsComment, page);
	}

	@Override
	public Long delQuestionsCommentByQuestionId(Long id) {
		return this.delete("QuestionsCommentMapper.delQuestionsCommentByQuestionId", id);
	}

	@Override
	public List<QuestionsComment> queryQuestionsCommentList(QuestionsComment questionsComment, PageEntity page) {
		return this.queryForListPageCount("QuestionsCommentMapper.queryQuestionsCommentList",questionsComment , page);
	}

	@Override
	public Long delQuestionsCommentByCommentId(Long commentId) {
		return this.delete("QuestionsCommentMapper.delQuestionsCommentByCommentId", commentId);
	}

}
