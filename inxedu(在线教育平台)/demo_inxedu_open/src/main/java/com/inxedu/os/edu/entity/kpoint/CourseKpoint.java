package com.inxedu.os.edu.entity.kpoint;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author www.inxedu.com
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CourseKpoint implements Serializable{
    private static final long serialVersionUID = -2252970709827434582L;
    /**视频节点ID*/
    private int kpointId;
    /**课程ID*/
    private int courseId;
    /**视频名*/
    private String name;
    /**视频父节点*/
    private int parentId;
    /**创建时间*/
    private Date addTime;
    /**排序*/
    private int sort;
    /**播放次数*/
    private int playCount;
    /**是否是免费 1免费 2收费*/
    private int free;
    /**视频路径*/
    private String videoUrl;
    /**教师ID*/
    private int teacherId;
    /**播放时间*/
    private String playTime;
    /**节点类型 0文件目录 1视频*/
    private int kpointType;
    /** 视频类型 */
    private String videoType;
    /**节点list*/
    private List<CourseKpoint> kpointList=new ArrayList<CourseKpoint>();
    private String fileType;//节点文件格式
    private String content;//文本内容

}
