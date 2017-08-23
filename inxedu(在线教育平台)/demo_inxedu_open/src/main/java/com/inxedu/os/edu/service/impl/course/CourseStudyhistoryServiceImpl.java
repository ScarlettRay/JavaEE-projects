package com.inxedu.os.edu.service.impl.course;

import com.inxedu.os.common.util.ObjectUtils;
import com.inxedu.os.edu.dao.course.CourseStudyhistoryDao;
import com.inxedu.os.edu.entity.course.Course;
import com.inxedu.os.edu.entity.course.CourseStudyhistory;
import com.inxedu.os.edu.entity.kpoint.CourseKpoint;
import com.inxedu.os.edu.service.course.CourseKpointService;
import com.inxedu.os.edu.service.course.CourseService;
import com.inxedu.os.edu.service.course.CourseStudyhistoryService;
import org.apache.commons.httpclient.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * CourseStudyhistory 用户课程学下记录 service接口实现
 * @author www.inxedu.com
 */
@Service("courseStudyhistoryService")
public class CourseStudyhistoryServiceImpl implements CourseStudyhistoryService {

	@Autowired
	private CourseStudyhistoryDao courseStudyhistoryDao;
	@Autowired
	private CourseService courseService;
	@Autowired
	private CourseKpointService courseKpointService;

	/**
	 * 添加CourseStudyhistory
	 * 
	 * @param courseStudyhistory
	 *            要添加的CourseStudyhistory
	 * @return id
	 */
	public java.lang.Long addCourseStudyhistory(CourseStudyhistory courseStudyhistory) {
		return courseStudyhistoryDao.addCourseStudyhistory(courseStudyhistory);
	}

	/**
	 * 添加播放记录和播放次数
	 */
	public void playertimes(CourseStudyhistory courseStudyhistory) {
		Course course = courseService.queryCourseById(courseStudyhistory.getCourseId().intValue());
		// 判断课程不为空
		if (ObjectUtils.isNull(course)) {
			return;
		}
		// 判断节点不为空
		CourseKpoint courseKpoint = courseKpointService.queryCourseKpointById(courseStudyhistory.getKpointId().intValue());
		if (ObjectUtils.isNull(courseKpoint)) {
			return;
		}
		
		CourseStudyhistory tempHistory =  new CourseStudyhistory();
		tempHistory.setUserId(courseStudyhistory.getUserId());
		tempHistory.setCourseId(courseStudyhistory.getCourseId());

		// 查询是否添加过记录
		List<CourseStudyhistory> courseStudyhistoryList = getCourseStudyhistoryList(courseStudyhistory);
		if (ObjectUtils.isNull(courseStudyhistoryList)) {
			// 如果没有添加过则添加记录
			// 填充数据
			courseStudyhistory.setKpointName(courseKpoint.getName());
			courseStudyhistory.setCourseName(course.getCourseName());
			courseStudyhistory.setUpdateTime(new Date());
			courseStudyhistory.setDataback(DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss") + ",");
			courseStudyhistory.setPlayercount(1L);
			addCourseStudyhistory(courseStudyhistory);
		} else {
			// 如果添加过则更新记录
			CourseStudyhistory courseStudy = courseStudyhistoryList.get(0);
			courseStudy.setKpointName(courseKpoint.getName());
			courseStudy.setCourseName(course.getCourseName());
			courseStudy.setUpdateTime(new Date());
			// 更新时间记录存字段
			courseStudy.setDataback(DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss") + "," + courseStudy.getDataback());
			// 当字符串大于500时截取，留前面最新的
			if (courseStudy.getDataback().length() > 500) {
				courseStudy.setDataback(courseStudy.getDataback().substring(0, 500));
			}
			courseStudy.setPlayercount(courseStudy.getPlayercount() + 1);
			updateCourseStudyhistory(courseStudy);
		}
		
	}

	/**
	 * 根据id删除一个CourseStudyhistory
	 * 
	 * @param id
	 *            要删除的id
	 */
	public void deleteCourseStudyhistoryById(Long id) {
		courseStudyhistoryDao.deleteCourseStudyhistoryById(id);
	}

	/**
	 * 修改CourseStudyhistory
	 * 
	 * @param courseStudyhistory
	 *            要修改的CourseStudyhistory
	 */
	public void updateCourseStudyhistory(CourseStudyhistory courseStudyhistory) {
		courseStudyhistoryDao.updateCourseStudyhistory(courseStudyhistory);
	}

	/**
	 * 根据条件获取CourseStudyhistory列表
	 * 
	 * @param courseStudyhistory
	 *            查询条件
	 * @return List<CourseStudyhistory>
	 */
	public List<CourseStudyhistory> getCourseStudyhistoryList(CourseStudyhistory courseStudyhistory) {
		return courseStudyhistoryDao.getCourseStudyhistoryList(courseStudyhistory);
	}

	public List<CourseStudyhistory> getCourseStudyhistoryListByCouId(Long courseId) {
		return courseStudyhistoryDao.getCourseStudyhistoryListByCouId(courseId);
	}

	public int getCourseStudyhistoryCountByCouId(Long courseId) {
		return courseStudyhistoryDao.getCourseStudyhistoryCountByCouId(courseId);
	}
	
}