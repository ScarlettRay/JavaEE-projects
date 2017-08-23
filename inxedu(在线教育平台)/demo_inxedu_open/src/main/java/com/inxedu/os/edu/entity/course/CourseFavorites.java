package com.inxedu.os.edu.entity.course;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author www.inxedu.com
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CourseFavorites implements Serializable{
	private static final long serialVersionUID = 5055812991457774890L;
	private int id;
    private int courseId;
    private int userId;
    private Date addTime;
}
