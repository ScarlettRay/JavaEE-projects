package com.inxedu.os.edu.dao.subject;

import java.util.List;
import java.util.Map;

import com.inxedu.os.edu.entity.subject.QuerySubject;
import com.inxedu.os.edu.entity.subject.Subject;

/**
 * 专业dao层接口
 * @author www.inxedu.com
 */
public interface SubjectDao {
	/**
	 * 创建专业
	 * @param subject
	 * @return 返回专业ID
	 */
	public int createSubject(Subject subject);
	
	/**
	 * 查询专业列表
	 * @return List<Subject>
	 */
	public List<Subject> getSubjectList(QuerySubject query);
	
	/**
	 * 修改专业父ID
	 * @param map
	 */
	public void updateSubjectParentId(Map<String,Object> map);
	
	/**
	 * 修改专业
	 * @param subject
	 */
	public void updateSubject(Subject subject);
	/**
	 * 修改排序
	 */
	public void updateSubjectSort(Subject subject);
	/**
	 * 删除专业 
	 * @param subjectId 要删除的专业ID
	 */
	public void deleteSubject(int subjectId);
	
	/**
     * 查询项目
     */
    public Subject getSubjectBySubject(Subject subject);
    
    /**
     * 根据父级ID查找子项目集合
     */
    public List<Subject> getSubjectListByOne(Long subjectId);
}