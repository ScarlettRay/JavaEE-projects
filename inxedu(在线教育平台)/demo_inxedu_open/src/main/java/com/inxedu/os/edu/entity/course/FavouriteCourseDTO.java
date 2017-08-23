package com.inxedu.os.edu.entity.course;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author www.inxedu.com
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class FavouriteCourseDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String courseName; // 课程名字
	private int courseId; // 课程id
	private String logo;// 课程图片
	private int favouriteId; // 收藏课程id
	private Date addTime;//收藏时间
	private List<Map<String,Object>> teacherList;//该课程 下的老师list
}
