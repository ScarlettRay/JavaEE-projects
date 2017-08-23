package com.inxedu.os.edu.dao.impl.questions;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.inxedu.os.common.dao.GenericDaoImpl;
import com.inxedu.os.edu.dao.questions.QuestionsTagRelationDao;
import com.inxedu.os.edu.entity.questions.QuestionsTagRelation;

/**
 * 问答和 问答标签的 关联表 实现类
 * @author www.inxedu.com
 */
@Repository("questionsTagRelationDao")
public class QuestionsTagRelationDaoImpl extends GenericDaoImpl implements QuestionsTagRelationDao {

	@Override
	public Long addQuestionsTagRelation(QuestionsTagRelation questionsTagRelation) {
		return this.insert("QuestionsTagRelationMapper.createQuestionsTagRelation", questionsTagRelation);
	}

	@Override
	public List<QuestionsTagRelation> queryQuestionsTagRelation(QuestionsTagRelation questionsTagRelation) {
		return this.selectList("QuestionsTagRelationMapper.queryQuestionsTagRelation", questionsTagRelation);
	}

}
