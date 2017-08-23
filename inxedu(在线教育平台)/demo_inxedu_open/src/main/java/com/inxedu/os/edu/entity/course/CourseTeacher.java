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
public class CourseTeacher implements Serializable{
	private static final long serialVersionUID = 1L;
	private int id;
    private Integer courseId;
    private Integer teacherId;
}
