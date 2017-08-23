package com.inxedu.os.edu.entity.course;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author www.inxedu.com
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CourseNote implements Serializable{
	private static final long serialVersionUID = 1L;
    private Long id;//主键
    private Long kpointId;//节点ID
    private Long courseId;//课程id
    private Long userId;//用户ID
    private String content;//笔记内容
    private java.util.Date updateTime;//添加修改时间
    private int status;//0公开1隐藏
}
