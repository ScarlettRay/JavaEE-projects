package com.inxedu.os.edu.dao.user;

import java.util.List;

import com.inxedu.os.common.entity.PageEntity;

import com.inxedu.os.edu.entity.user.UserLoginLog;

/**
 * @author www.inxedu.com
 *
 */
public interface UserLoginLogDao {
	/**
	 * 添加登录日志
	 * @param loginLog
	 * @return 日志ID
	 */
	public int createLoginLog(UserLoginLog loginLog);
	
	/**
	 * 查询用户登录日志
	 * @param userId 用户ID
	 * @param page 分页条件
	 * @return List<SysUserLoginLog>
	 */
	public List<UserLoginLog> queryUserLogPage(int userId,PageEntity page);

}
