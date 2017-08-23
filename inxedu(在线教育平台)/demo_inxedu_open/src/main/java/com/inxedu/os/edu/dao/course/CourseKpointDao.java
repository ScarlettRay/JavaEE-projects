package com.inxedu.os.edu.dao.course;


import java.util.List;
import java.util.Map;

import com.inxedu.os.edu.entity.kpoint.CourseKpoint;
import com.inxedu.os.edu.entity.kpoint.CourseKpointDto;

/**
 * CourseKpoint管理接口
 * @author www.inxedu.com
 */
public interface CourseKpointDao {
    /**
     * 添加视频节点
     */
    public int addCourseKpoint(CourseKpoint courseKpoint);
    
    /**
     * 通过课程ID，查询课程所属视频
     * @param courseId 课程ID
     * @return List<CourseKpoint>
     */
    public List<CourseKpoint> queryCourseKpointByCourseId(int courseId);
    
    /**
     * 通过ID，查询视频详情
     * @param kpointId 视频ID
     * @return CourseKpointDto
     */
    public CourseKpointDto queryCourseKpointById(int kpointId);
    
    /**
     * 修改视频节点
     * @param kpoint
     */
    public void updateKpoint(CourseKpoint kpoint);
    
    /**
     * 删除视频节点
     * @param ids ID串
     */
    public void deleteKpointByIds(String ids);
    
    /**
     * 修改视频节点父ID
     * @param map
     */
    public void updateKpointParentId(Map<String,Integer> map);
    
    /**
     * 获取课程的 二级视频节点总数(只支持二级)
     */
    public int getSecondLevelKpointCount(Long courseId);

}