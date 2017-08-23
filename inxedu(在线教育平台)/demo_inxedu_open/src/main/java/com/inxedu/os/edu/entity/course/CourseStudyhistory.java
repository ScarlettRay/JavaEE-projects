package com.inxedu.os.edu.entity.course;

import com.inxedu.os.common.util.StringUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @ClassName com.inxedu.os.inxedu.entity.course.CourseStudyhistory
 * @description 记录播放记录
 * @author www.inxedu.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CourseStudyhistory implements Serializable {
	/**
     * 
     */
    private static final long serialVersionUID = 5434482371608343070L;
    private Long id;
    private Long userId;//播放次数
    private Long courseId;//播放次数
    private Long kpointId;//播放次数
    private Long playercount;//播放次数
    private String courseName;//课程名称
    private String kpointName;//节点名称
    private String databack;//playercount小于20时记录,备注观看的时间，叠加
    private java.util.Date updateTime;//更新时间
    private String logo;	//图片
    private String teacherName;	//教师名称
    
    private String userShowName;//用户名
    private String userEmail;//用户邮箱
    private String userImg;//用户头像

    //辅助字段
    private int queryLimit;//查询 的个数
    private String picImg;//用户头像
    private String showName;//用户昵称
    private String updateTimeFormat;//时间 格式化显示

    public void setUpdateTime(Date date){
        this.updateTime=date;
        this.updateTimeFormat= StringUtils.getModelDate(this.getUpdateTime());
    }
}
