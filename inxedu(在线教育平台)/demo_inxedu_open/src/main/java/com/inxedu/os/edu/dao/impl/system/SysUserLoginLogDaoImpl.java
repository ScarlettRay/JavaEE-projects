package com.inxedu.os.edu.dao.impl.system;

import com.inxedu.os.common.dao.GenericDaoImpl;
import com.inxedu.os.common.entity.PageEntity;
import com.inxedu.os.edu.dao.system.SysUserLoginLogDao;
import com.inxedu.os.edu.entity.system.SysUserLoginLog;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 后台用户登录日志
 * @author www.inxedu.com
 *
 */
@Repository("sysUserLoginLogDao")
public class SysUserLoginLogDaoImpl extends GenericDaoImpl implements SysUserLoginLogDao {

	
	public int createLoginLog(SysUserLoginLog loginLog) {
		this.insert("SysUserLoginLogMapper.createLoginLog", loginLog);
		return loginLog.getLogId();
	}

	
	public List<SysUserLoginLog> queryUserLogPage(int userId, PageEntity page) {
		return this.queryForListPage("SysUserLoginLogMapper.queryUserLogPage", userId, page);
	}

}
