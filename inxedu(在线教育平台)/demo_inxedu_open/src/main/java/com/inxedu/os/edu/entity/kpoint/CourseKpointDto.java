package com.inxedu.os.edu.entity.kpoint;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author www.inxedu.com
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CourseKpointDto extends CourseKpoint{
	private static final long serialVersionUID = -5911245722257969805L;
	private String teacherName;
}
