package com.inxedu.os.edu.entity.teacher;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 讲师 查询辅助类
 * @author www.inxedu.com
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class QueryTeacher implements Serializable{
    private static final long serialVersionUID = -2260935476109762530L;
    private String name;	//教师名称
    private int isStar;	//
    private int subjectId;//老师专业
    private int count;//数量
    private int teacherId;
    
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date beginCreateTime;//查询 开始时间
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date endCreateTime;//查询 结束时间
}
