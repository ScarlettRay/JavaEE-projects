package com.inxedu.os.edu.service.impl.user;

import com.inxedu.os.common.entity.PageEntity;
import com.inxedu.os.edu.dao.user.UserLoginLogDao;
import com.inxedu.os.edu.entity.user.UserLoginLog;
import com.inxedu.os.edu.service.user.UserLoginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 学员登录日志
 * @author www.inxedu.com
 *
 */
@Service("userLoginLogService")
public class UserLoginLogServiceImpl implements UserLoginLogService{

	@Autowired
	private UserLoginLogDao userLoginLogDao;
	
	public int createLoginLog(UserLoginLog loginLog) {
		return userLoginLogDao.createLoginLog(loginLog);
	}
	
	public List<UserLoginLog> queryUserLogPage(int userId, PageEntity page) {
		return userLoginLogDao.queryUserLogPage(userId, page);
	}

}
