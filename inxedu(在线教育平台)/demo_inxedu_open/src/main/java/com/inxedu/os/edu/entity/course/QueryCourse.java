package com.inxedu.os.edu.entity.course;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author www.inxedu.com
 * 课程查询条件
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class QueryCourse implements Serializable{
    private static final long serialVersionUID = 4550896941810655734L;
    private int subjectId;
    private String courseName;
    private int isavaliable;
    private int teacherId;
    private int count;
    private String order;
    private String isFree;//查询免费课程
    
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date beginCreateTime;//查询 开始添加时间
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date endCreateTime;//查询 结束添加时间
    private int queryLimit;//查询条数
}
