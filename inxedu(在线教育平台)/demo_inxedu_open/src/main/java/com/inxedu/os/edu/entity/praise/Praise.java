package com.inxedu.os.edu.entity.praise;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 点赞表
 *@author www.inxedu.com
 */
@Data
public class Praise  implements Serializable{
	private static final long serialVersionUID = 7687324559966427231L;
	private Long id;//id
	private Long userId;//用户id
	private Long targetId;//点赞的目标id
	private int type;//点赞类型 1问答点赞 2问答评论点赞
	private Date addTime;//点赞和点踩的时间

}
