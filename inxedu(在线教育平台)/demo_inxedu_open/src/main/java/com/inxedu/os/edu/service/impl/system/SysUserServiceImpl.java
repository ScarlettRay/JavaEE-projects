package com.inxedu.os.edu.service.impl.system;

import com.inxedu.os.common.entity.PageEntity;
import com.inxedu.os.edu.dao.system.SysUserDao;
import com.inxedu.os.edu.entity.system.QuerySysUser;
import com.inxedu.os.edu.entity.system.SysUser;
import com.inxedu.os.edu.service.system.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 后台用户
 * @author www.inxedu.com
 *
 */
@Service("sysUserService")
public class SysUserServiceImpl implements SysUserService{
	
	@Autowired
	private SysUserDao sysUserDao;
	
	
	public int createSysUser(SysUser sysuser) {
		return sysUserDao.createSysUser(sysuser);
	}

	
	public void updateSysUser(SysUser sysuser) {
		sysUserDao.updateSysUser(sysuser);
	}

	
	public SysUser querySysUserByUserId(int userId) {
		return sysUserDao.querySysUserByUserId(userId);
	}

	
	public List<SysUser> querySysUserPage(QuerySysUser querySysUser,
			PageEntity page) {
		return sysUserDao.querySysUserPage(querySysUser, page);
	}

	
	public boolean validateLoginName(String userLoginName) {
		int count = sysUserDao.validateLoginName(userLoginName);
		if(count<=0){
			return true;
		}
		return false;
	}

	
	public SysUser queryLoginUser(SysUser sysUser) {
		return sysUserDao.queryLoginUser(sysUser);
	}

	
	public void updateUserPwd(SysUser sysUser) {
		sysUserDao.updateUserPwd(sysUser);
	}

	
	public void updateDisableOrstartUser(int userId, int type) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("type", type);
		sysUserDao.updateDisableOrstartUser(map);
	}

	
	public void updateUserLoginLog(int userId, Date time, String ip) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("time", time);
		map.put("ip", ip);
		sysUserDao.updateUserLoginLog(map);
	}
	
}
