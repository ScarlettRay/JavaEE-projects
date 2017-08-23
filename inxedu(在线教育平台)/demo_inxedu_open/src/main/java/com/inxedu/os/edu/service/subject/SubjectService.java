package com.inxedu.os.edu.service.subject;

import com.inxedu.os.edu.entity.subject.QuerySubject;
import com.inxedu.os.edu.entity.subject.Subject;

import java.util.List;



/**
 * 专业接口
 * @author www.inxedu.com
 */
public interface SubjectService {
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
	 * @param subjectId 专业ID
	 * @param parentId 专业父ID
	 */
	public void updateSubjectParentId(int subjectId,int parentId);
	/**
	 * 修改专业
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
