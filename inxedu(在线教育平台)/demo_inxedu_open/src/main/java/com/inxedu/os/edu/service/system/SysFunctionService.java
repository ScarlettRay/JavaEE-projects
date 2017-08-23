package com.inxedu.os.edu.service.system;

import com.inxedu.os.edu.entity.system.SysFunction;

import java.util.List;

/**
 * 后台系统功能权限
 * @author www.inxedu.com
 */
public interface SysFunctionService {

	/**
	 * 查询所有的权限列表
	 * @return List<SysFunction>
	 */
	public List<SysFunction> queryAllSysFunction();
	
	/**
	 * 创建权限
	 * @param sysFunction 权限对象
	 * @return 权限ID
	 */
	public int cresateSysFunction(SysFunction sysFunction);
	
	/**
	 * 修改权限
	 * @param sysFunction 权限实体
	 */
	public void updateFunction(SysFunction sysFunction);
	
	/**
	 * 修改权限父ID
	 * @param parentId 父ID ，functionId权限ID
	 */
	public void updateFunctionParentId(int parentId,int functionId);
	
	/**
	 * 删除权限
	 * @param ids 权限ID串(12,13,14)
	 */
	public void deleteFunctionByIds(String ids);
	
	/***
	 * 获取用户权限
	 * @param userId 用户ID
	 * @return
	 */
	public List<SysFunction> querySysUserFunction(int userId);
	
}
