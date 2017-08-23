package com.inxedu.os.edu.service.impl.course;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inxedu.os.edu.dao.course.CourseTeacherDao;
import com.inxedu.os.edu.service.course.CourseTeacherService;

/**
 * CourseTeacher管理接口
 * @author www.inxedu.com
 */
@Service("courseTeacherService")
public class CourseTeacherServiceImpl implements CourseTeacherService {

 	@Autowired
    private CourseTeacherDao courseTeacherDao;

	
	public void addCourseTeacher(String value) {
		courseTeacherDao.addCourseTeacher(value);
	}

	
	public void deleteCourseTeacher(int courseId) {
		courseTeacherDao.deleteCourseTeacher(courseId);
	}
}