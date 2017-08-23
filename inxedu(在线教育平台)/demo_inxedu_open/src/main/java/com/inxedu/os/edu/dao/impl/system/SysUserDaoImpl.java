package com.inxedu.os.edu.dao.impl.system;

import com.inxedu.os.common.dao.GenericDaoImpl;
import com.inxedu.os.common.entity.PageEntity;
import com.inxedu.os.edu.dao.system.SysUserDao;
import com.inxedu.os.edu.entity.system.QuerySysUser;
import com.inxedu.os.edu.entity.system.SysUser;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 后台用户
 * @author www.inxedu.com
 */
@Repository("sysUserDao")
public class SysUserDaoImpl extends GenericDaoImpl implements SysUserDao{
	
	
	public int createSysUser(SysUser sysuser) {
		this.insert("SysUserMapper.createSysUser", sysuser);
		return sysuser.getUserId();
	}

	
	public void updateSysUser(SysUser sysuser) {
		this.update("SysUserMapper.updateSysUser", sysuser);
	}

	
	public SysUser querySysUserByUserId(int userId) {
		return this.selectOne("SysUserMapper.querySysUserByUserId", userId);
	}

	
	public List<SysUser> querySysUserPage(QuerySysUser querySysUser,
			PageEntity page) {
		return this.queryForListPage("SysUserMapper.querySysUserPage", querySysUser, page);
	}

	
	public int validateLoginName(String userLoginName) {
		return this.selectOne("SysUserMapper.validateLoginName", userLoginName);
	}

	
	public SysUser queryLoginUser(SysUser sysUser) {
		return this.selectOne("SysUserMapper.queryLoginUser", sysUser);
	}

	
	public void updateUserPwd(SysUser sysUser) {
		this.update("SysUserMapper.updateUserPwd", sysUser);
	}

	
	public void updateDisableOrstartUser(Map<String, Object> map) {
		this.update("SysUserMapper.updateDisableOrstartUser", map);
	}

	
	public void updateUserLoginLog(Map<String, Object> map) {
		this.update("SysUserMapper.updateUserLoginLog", map);
	}

}
