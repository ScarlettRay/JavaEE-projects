package com.inxedu.os.edu.service.course;



/**
 * CourseTeacher 课程讲师 管理接口
 * @author www.inxedu.com
 */
public interface CourseTeacherService {

	 /**
     * 添加课程与讲师的关联数
     */
    public void addCourseTeacher(String value);
    
    /**
     * 删除课程与讲师的关联数据
     * @param courseId 课程ID
     */
    public void deleteCourseTeacher(int courseId);
}