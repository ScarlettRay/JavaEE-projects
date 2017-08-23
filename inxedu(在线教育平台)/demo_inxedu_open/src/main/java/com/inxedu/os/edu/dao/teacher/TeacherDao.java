package com.inxedu.os.edu.dao.teacher;


import java.util.List;
import java.util.Map;

import com.inxedu.os.common.entity.PageEntity;

import com.inxedu.os.edu.entity.teacher.QueryTeacher;
import com.inxedu.os.edu.entity.teacher.Teacher;

/**
 * 教师dao层接口
 * @author www.inxedu.com
 */
public interface TeacherDao {

    /**
     * 添加Teacher
     */
    public int addTeacher(Teacher teacher);
    
    /**
     * 删除讲师
     * @param tcId 讲师ID
     */
    public void deleteTeacherById(int tcId);
    
    /**
     * 修改讲师信息
     * @param teacher 讲师数据
     */
    public void updateTeacher(Teacher teacher);
    
    /**
     * 通过讲师ID，查询讲师数据
     * @param tcId
     * @return
     */
    public Teacher getTeacherById(int tcId);
    
    /**
     * 分页查询讲师列表
     * @param query 查询条件
     * @param page 分页条件
     * @return List<Teacher>
     */
    public List<Teacher> queryTeacherListPage(QueryTeacher query,PageEntity page);
    
    /**
     * 查询课程讲师列表
     * @param courseId 课程ID
     * @return List<Map<String,Object>> [{id,name},{id,name}]
     */
    public List<Map<String,Object>> queryCourseTeacerList(int courseId);
    
    /**
     * 条件查询老师列表
     * @param queryTeacher 查询条件
     * @return List<Teacher>
     */
    public List<Teacher> queryTeacherList(QueryTeacher queryTeacher);
    

}