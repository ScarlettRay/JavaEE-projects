package com.inxedu.os.edu.service.impl.subject;

import com.inxedu.os.edu.dao.subject.SubjectDao;
import com.inxedu.os.edu.entity.subject.QuerySubject;
import com.inxedu.os.edu.entity.subject.Subject;
import com.inxedu.os.edu.service.subject.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 专业service实现
 * @author www.inxedu.com
 */
@Service("subjectService")
public class SubjectServiceImpl implements SubjectService {

	@Autowired
	private SubjectDao subjectDao;

	public int createSubject(Subject subject) {
		return subjectDao.createSubject(subject);
	}

	public List<Subject> getSubjectList(QuerySubject query) {
		return subjectDao.getSubjectList(query);
	}

	public void updateSubjectParentId(int subjectId, int parentId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("subjectId", subjectId);
		map.put("parentId", parentId);
		subjectDao.updateSubjectParentId(map);
	}

	public void updateSubject(Subject subject) {
		subjectDao.updateSubject(subject);
	}
	/**
	 * 修改排序
	 */
	public void updateSubjectSort(Subject subject){
		subjectDao.updateSubjectSort(subject);
	}

	public void deleteSubject(int subjectId) {
		subjectDao.deleteSubject(subjectId);
	}

	@Override
	public Subject getSubjectBySubject(Subject subject) {
		return subjectDao.getSubjectBySubject(subject);
	}

	@Override
	public List<Subject> getSubjectListByOne(Long subjectId) {
		return subjectDao.getSubjectListByOne(subjectId);
	}
}
