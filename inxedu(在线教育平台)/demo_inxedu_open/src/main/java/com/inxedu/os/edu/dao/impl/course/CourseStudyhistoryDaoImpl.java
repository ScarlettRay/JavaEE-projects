package com.inxedu.os.edu.dao.impl.course;


import com.inxedu.os.common.dao.GenericDaoImpl;
import com.inxedu.os.edu.dao.course.CourseStudyhistoryDao;
import com.inxedu.os.edu.entity.course.CourseStudyhistory;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * CourseStudyhistory 管理接口实现
 * @author www.inxedu.com
 */
 @Repository("courseStudyhistoryDao")
public class CourseStudyhistoryDaoImpl extends GenericDaoImpl implements CourseStudyhistoryDao{

    public java.lang.Long addCourseStudyhistory(CourseStudyhistory courseStudyhistory) {
        return this.insert("CourseStudyhistoryMapper.createCourseStudyhistory",courseStudyhistory);
    }

    public void deleteCourseStudyhistoryById(Long id){
        this.delete("CourseStudyhistoryMapper.deleteCourseStudyhistoryById",id);
    }

    public void updateCourseStudyhistory(CourseStudyhistory courseStudyhistory) {
        this.update("CourseStudyhistoryMapper.updateCourseStudyhistory",courseStudyhistory);
    }

    public List<CourseStudyhistory> getCourseStudyhistoryList(CourseStudyhistory courseStudyhistory) {
        return this.selectList("CourseStudyhistoryMapper.getCourseStudyhistoryList",courseStudyhistory);
    }

	public List<CourseStudyhistory> getCourseStudyhistoryListByCouId(Long courseId) {
		return this.selectList("CourseStudyhistoryMapper.getCourseStudyhistoryListByCouId", courseId);
	}

	public int getCourseStudyhistoryCountByCouId(Long courseId) {
		return (Integer)this.selectOne("CourseStudyhistoryMapper.getCourseStudyhistoryCountByCouId", courseId);
	}
    
}
