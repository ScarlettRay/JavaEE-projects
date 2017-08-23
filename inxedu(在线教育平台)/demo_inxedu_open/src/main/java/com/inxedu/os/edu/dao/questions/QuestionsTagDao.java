package com.inxedu.os.edu.dao.questions;

import java.util.List;
import java.util.Map;

import com.inxedu.os.edu.entity.questions.QuestionsTag;

/**
 * 问答标签dao层接口
 * @author www.inxedu.com
 */
public interface QuestionsTagDao {
	/**
	 * 创建问答标签
	 * @param questionsTag
	 * @return 返回问答标签ID
	 */
	public int createQuestionsTag(QuestionsTag questionsTag);
	
	/**
	 * 查询问答标签列表
	 * @return List<QuestionsTag>
	 */
	public List<QuestionsTag> getQuestionsTagList(QuestionsTag query);
	
	/**
	 * 修改问答标签父ID
	 * @param map
	 */
	public void updateQuestionsTagParentId(Map<String,Object> map);
	
	/**
	 * 修改问答标签
	 * @param questionsTag
	 */
	public void updateQuestionsTag(QuestionsTag questionsTag);
	
	/**
	 * 删除问答标签 
	 * @param questionsTagId 要删除的问答标签ID
	 */
	public void deleteQuestionsTag(int questionsTagId);
}