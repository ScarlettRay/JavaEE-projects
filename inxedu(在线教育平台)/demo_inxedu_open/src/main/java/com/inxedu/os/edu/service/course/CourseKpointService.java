package com.inxedu.os.edu.service.course;


import com.inxedu.os.edu.entity.kpoint.CourseKpoint;
import com.inxedu.os.edu.entity.kpoint.CourseKpointDto;

import java.util.List;

/**
 * CourseKpoint 课程章节 管理接口
 * @author www.inxedu.com
 */
public interface CourseKpointService {

    /**
     * 添加CourseKpoint
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
     * @param kpointId 视频ID
     * @param parentId 父ID
     */
    public void updateKpointParentId(int kpointId,int parentId);
    
    /**
     * 获取课程的 二级视频节点总数(只支持二级)
     */
    public int getSecondLevelKpointCount(Long courseId);
}