package com.inxedu.os.edu.service.course;


import com.inxedu.os.common.entity.PageEntity;
import com.inxedu.os.edu.entity.course.Course;
import com.inxedu.os.edu.entity.course.CourseDto;
import com.inxedu.os.edu.entity.course.QueryCourse;

import java.util.List;
import java.util.Map;

/**
 * Course 课程 管理接口
 * @author www.inxedu.com
 */
public interface CourseService {
	 /**
     * 添加Course
     */
    public int addCourse(Course course);
    
    /**
     * 分页查询课程列表
     * @param query 条件条件
     * @param page 分页条件
     * @return List<CourseDto>
     */
    public List<CourseDto> queryCourseListPage(QueryCourse query,PageEntity page);
    
    /**
     * 通过ID，查询课程数据
     * @param courseId 课程ID
     * @return Course
     */
    public Course queryCourseById(int courseId);

    /**
     * 更新课程数据
     * @param course 课程
     */
    public void updateCourse(Course course);

    /**
     * 上架或下架课程
     * @param courseId 课程ID
     * @param type 1上架 2下架
     */
	public void updateAvaliableCourse(int courseId, int type);
	
	/**
	 * 查询所有的推荐课程
	 * @return Map<String,List<CourseDto>>
	 */
	public Map<String,List<CourseDto>> queryRecommenCourseList();
	
	/**
	 * 根据不同条件查询课程列表
	 * @param query 查询条件
	 * @return List<CourseDto>
	 */
	public List<CourseDto> queryCourseList(QueryCourse query);
	
	/**
	 * 前台查询课程列表
	 * @param queryCourse 查询条件
	 * @param page 分页条件
	 * @return List<CourseDto>
	 */
	public List<CourseDto> queryWebCourseListPage(QueryCourse queryCourse,PageEntity page);
	
	/**
	 * 查询相关课程
	 * @param subjectId 专业ID
	 * @param count 查询数量
	 * @return List<Course>
	 */
	public List<CourseDto> queryInterfixCourseLis(int subjectId,int count,int courseId);
	
	/**
	 * 分页查询我的课程列表
	 * @param userId 用户ID
	 * @param page 分页条件
	 * @return List<CourseDto>
	 */
	public List<CourseDto> queryMyCourseList(int userId,PageEntity page);
	
	/**
	 * 查询所有的课程个数
	 * @return 返回课程个数
	 */
	public int queryAllCourseCount();
	
	/**
	 *  查询一部分已购买的课程，用户前台头部
	 * @param userId 用户ID
	 * @param count 查询条数
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String,Object>> queryMyCourseListByMap(int userId,int count);
	
	/**
	 * 根据RecommendId		查询推荐课程
	 * 
	 * @param recommendId		推荐类型Id
	 * @param count	查询几条
	 * @return
	 */
	public List<CourseDto>queryRecommenCourseListByRecommendId(Long recommendId,Long count);
	
	/**
	 * 查询精品课程、最新课程、全部课程
	 * @param queryCourse
	 * @return
	 */
	public List<CourseDto> queryCourse(QueryCourse queryCourse);

	/**
	 * 更新课程数据（浏览数，购买数）
	 * @param type pageViewcount浏览数 pageBuycount购买数
	 * @param courseId 课程id
	 */
	public void updateCourseCount(String type,int courseId);


}