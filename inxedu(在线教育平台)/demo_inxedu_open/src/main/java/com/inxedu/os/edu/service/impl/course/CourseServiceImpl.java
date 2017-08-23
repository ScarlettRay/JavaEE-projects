package com.inxedu.os.edu.service.impl.course;

import com.inxedu.os.common.cache.EHCacheUtil;
import com.inxedu.os.common.constants.CacheConstans;
import com.inxedu.os.common.entity.PageEntity;
import com.inxedu.os.edu.dao.course.CourseDao;
import com.inxedu.os.edu.entity.course.Course;
import com.inxedu.os.edu.entity.course.CourseDto;
import com.inxedu.os.edu.entity.course.QueryCourse;
import com.inxedu.os.edu.service.course.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Course 课程service接口实现
 * @author www.inxedu.com
 */
@Service("courseService")
public class CourseServiceImpl implements CourseService {

	@Autowired
	private CourseDao courseDao;

	public int addCourse(Course course) {
		return courseDao.addCourse(course);
	}

	public List<CourseDto> queryCourseListPage(QueryCourse query, PageEntity page) {
		return courseDao.queryCourseListPage(query, page);
	}

	public Course queryCourseById(int courseId) {
		return courseDao.queryCourseById(courseId);
	}

	public void updateCourse(Course course) {
		courseDao.updateCourse(course);
	}

	public void updateAvaliableCourse(int courseId, int type) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("courseId", courseId);
		map.put("type", type);
		courseDao.updateAvaliableCourse(map);

	}

	public Map<String, List<CourseDto>> queryRecommenCourseList() {
		@SuppressWarnings("unchecked")
		Map<String, List<CourseDto>> recMap = (Map<String, List<CourseDto>>) EHCacheUtil.get(CacheConstans.RECOMMEND_COURSE);
		if (recMap == null) {
			List<CourseDto> courseList = courseDao.queryRecommenCourseList();
			if (courseList != null && courseList.size() > 0) {
				recMap = new HashMap<String, List<CourseDto>>();
				List<CourseDto> _list = new ArrayList<CourseDto>();
				int recommendId = courseList.get(0).getRecommendId();
				for (CourseDto _cd : courseList) {
					if (recommendId == _cd.getRecommendId()) {
						_list.add(_cd);
					} else {
						recMap.put("recommen_" + recommendId, _list);
						_list = new ArrayList<CourseDto>();
						_list.add(_cd);
					}
					recommendId = _cd.getRecommendId();
				}
				recMap.put("recommen_" + recommendId, _list);
				EHCacheUtil.set(CacheConstans.RECOMMEND_COURSE, recMap, CacheConstans.RECOMMEND_COURSE_TIME);
			}
		}
		return recMap;
	}

	public List<CourseDto> queryCourseList(QueryCourse query) {
		return courseDao.queryCourseList(query);
	}

	public List<CourseDto> queryWebCourseListPage(QueryCourse queryCourse, PageEntity page) {
		return courseDao.queryWebCourseListPage(queryCourse, page);
	}

	public List<CourseDto> queryInterfixCourseLis(int subjectId, int count, int courseId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("count", count);
		map.put("subjectId", subjectId);
		map.put("courseId", courseId);
		return courseDao.queryInterfixCourseList(map);
	}

	public List<CourseDto> queryMyCourseList(int userId, PageEntity page) {
		return courseDao.queryMyCourseList(userId, page);
	}

	public int queryAllCourseCount() {
		return courseDao.queryAllCourseCount();
	}

	public List<Map<String, Object>> queryMyCourseListByMap(int userId, int count) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("count", count);
		return courseDao.queryMyCourseListByMap(map);
	}

	public List<CourseDto> queryRecommenCourseListByRecommendId(Long recommendId, Long count) {
		return courseDao.queryRecommenCourseListByRecommendId(recommendId, count);
	}

	public List<CourseDto> queryCourse(QueryCourse queryCourse) {
		return courseDao.queryCourse(queryCourse);
	}

	/**
	 * 更新课程数据（浏览数，购买数）
	 * @param type pageViewcount浏览数 pageBuycount购买数
	 * @param courseId 课程id
	 */
	public void updateCourseCount(String type,int courseId){
		this.courseDao.updateCourseCount(type,courseId);
	}
}