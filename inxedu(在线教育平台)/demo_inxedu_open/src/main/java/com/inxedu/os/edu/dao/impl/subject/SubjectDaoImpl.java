package com.inxedu.os.edu.dao.impl.subject;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.inxedu.os.common.dao.GenericDaoImpl;
import com.inxedu.os.edu.dao.subject.SubjectDao;
import com.inxedu.os.edu.entity.subject.QuerySubject;
import com.inxedu.os.edu.entity.subject.Subject;

/**
 * Subject对象操作实现类
 * @author www.inxedu.com
 */
@Repository("subjectDao")
public class SubjectDaoImpl extends GenericDaoImpl implements SubjectDao {

	public int createSubject(Subject subject) {
		this.insert("SubjectMapper.createSubject", subject);
		return subject.getSubjectId();
	}

	public List<Subject> getSubjectList(QuerySubject query) {
		return this.selectList("SubjectMapper.getSubjectList", query);
	}

	public void updateSubjectParentId(Map<String, Object> map) {
		this.update("SubjectMapper.updateSubjectParentId", map);
	}

	public void updateSubject(Subject subject) {
		this.update("SubjectMapper.updateSubject", subject);
	}
	/**
	 * 修改排序
	 */
	public void updateSubjectSort(Subject subject){
		this.update("SubjectMapper.updateSubjectSort", subject);
	}
	public void deleteSubject(int subjectId) {
		this.update("SubjectMapper.deleteSubject", subjectId);
	}

	@Override
	public Subject getSubjectBySubject(Subject subject) {
		return this.selectOne("SubjectMapper.getSubjectBySubject", subject);
	}

	@Override
	public List<Subject> getSubjectListByOne(Long subjectId) {
		return this.selectList("SubjectMapper.getSubjectListByOne", subjectId);
	}

}
