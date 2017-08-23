package com.inxedu.os.edu.service.praise;

import com.inxedu.os.edu.entity.praise.Praise;

/**
 * 点赞服务接口
 *@author www.inxedu.com
 */
public interface PraiseService {
	/**
	 * 添加点赞记录
	 */
	public Long addPraise(Praise praise);
	
	/**
	 * 根据条件查询点赞数
	 */
	public int queryPraiseCount(Praise praise);
}
