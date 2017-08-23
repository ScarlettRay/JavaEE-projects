package com.inxedu.os.edu.service.impl.system;

import com.inxedu.os.edu.dao.system.SysRoleDao;
import com.inxedu.os.edu.entity.system.SysRole;
import com.inxedu.os.edu.service.system.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 后台角色权限
 * @author www.inxedu.com
 *
 */
@Service("sysRoleService")
public class SysRoleServiceImpl implements SysRoleService{

	@Autowired
	private SysRoleDao sysRoleDao;
	
	public int createRoel(SysRole sysRole) {
		return sysRoleDao.createRoel(sysRole);
	}

	
	public void updateRole(SysRole sysRole) {
		sysRoleDao.updateRole(sysRole);
	}

	
	public List<SysRole> queryAllRoleList() {
		return sysRoleDao.queryAllRoleList();
	}

	
	public void deleteRoleByIds(String ids) {
		if(ids!=null && ids.trim().length()>0){
			if(ids.trim().endsWith(",")){
				ids = ids.trim().substring(0,ids.trim().length()-1);
			}
			sysRoleDao.deleteRoleByIds(ids);
		}
	}

	
	public void deleteRoleFunctionByRoleId(int roleId) {
		sysRoleDao.deleteRoleFunctionByRoleId(roleId);
	}

	
	public void createRoleFunction(String value) {
		if(value!=null && value.trim().length()>0){
			if(value.endsWith(",")){
				value = value.trim().substring(0,value.trim().length()-1);
			}
			sysRoleDao.createRoleFunction(value);
		}
	}

	
	public List<Integer> queryRoleFunctionIdByRoleId(int roleId) {
		return sysRoleDao.queryRoleFunctionIdByRoleId(roleId);
	}

}
