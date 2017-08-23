package com.inxedu.os.edu.entity.user;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 查询用户
 * @author www.inxedu.com
 *
 */
@Data
public class QueryUser {
	private int isavalible;
	private String keyWord;
	
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date beginCreateTime;//查询 开始注册时间
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date endCreateTime;//查询 结束注册时间
}
