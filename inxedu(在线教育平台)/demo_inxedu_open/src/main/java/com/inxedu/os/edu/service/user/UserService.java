package com.inxedu.os.edu.service.user;

import com.inxedu.os.common.entity.PageEntity;
import com.inxedu.os.edu.entity.user.QueryUser;
import com.inxedu.os.edu.entity.user.User;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 前台用户
 * @author www.inxedu.com
 *
 */
public interface UserService {
	/**
	 * 创建用户
	 * @param user
	 * @return 返回用户ID
	 */
	public int createUser(User user);
	
	/**
	 * 通过用户ID查询用户
	 * @param userId 用户不D
	 * @return User
	 */
	public User queryUserById(int userId);
	
	/**
	 * 检测手机是否存在 
	 * @param mobile 手机号
	 * @return true存在 false不存在 
	 */
	public boolean checkMobile(String mobile);
	
	/**
	 * 检测邮箱号是否存在 
	 * @param email 邮箱号
	 * @return  true存在 false不存在 
	 */
	public boolean checkEmail(String email);
	
	/**
	 * 获取登录用户
	 * @param account 帐号（邮箱或手机号）
	 * @param password 密码
	 * @return User
	 */
	public User getLoginUser(String account,String password);
	
	/**
	 * 修改用户密码
	 * @param user
	 */
	public void updateUserPwd(User user);
	
	/**
	 * 分页查询用户
	 * @param query 查询条件
	 * @param page 分页条件
	 * @return List<User>
	 */
	public List<User> queryUserListPage(QueryUser query,PageEntity page);
	
	/**
	 * 冻结或解冻用户
	 * @param user
	 */
	public void updateUserStates(User user);
	
	/**
	 * 修改用户信息
	 * @param user
	 */
	public void updateUser(User user);
	
	/**
	 * 修改用户头像
	 * @param user
	 */
	public void updateImg(User user);

	/**
	 * 查询所有学员记录数
	 * @return 返回所有的记录数
	 */
	public int queryAllUserCount();
	
	/**
	 * 通过手机号或邮箱号查询用户信息
	 * @param emailOrMobile 手机或邮箱号
	 * @return User
	 */
	public User queryUserByEmailOrMobile(String emailOrMobile);
	
	/**
	 * 根据用户uid获取用户信息
	 * 
	 * @param uids
	 * @return
	 */
	public Map<String, User> getUserExpandByUids(String uids) throws Exception;
	
	/**
	 * 通过集合cusId 查询customer 返回map
	 * 
	 * @param cusIds
	 * @return
	 * @throws Exception
	 */
	public Map<String, User> queryCustomerInCusIds(List<Long> cusIds) throws Exception;
	
	/**
	 * 通过标识更新未读数加一
	 */
	public void updateUnReadMsgNumAddOne(String falg, Long cusId);

	/**
	 * 通过标识更新未读数清零
	 */
	public void updateUnReadMsgNumReset(String falg, Long cusId);
	
	/**
	 * 更新用户的上传统计系统消息时间
	 */
	public void updateCusForLST(Long cusId, Date date);
	
	/**
	 * excel批量开通用户 shanXinYing
	 * 
	 * @param myFile
	 * @return
	 * @throws Exception
	 */
	public String updateImportExcel(HttpServletRequest request,MultipartFile myFile,Integer mark) throws Exception;

	/**
	 * 缓存用户信息
	 */
	public void setLoginInfo(HttpServletRequest request, int userId,String autoThirty);

	/**
	 * 根据条件获取User列表  带课程名称
	 * @param user  用户
	 * @param page   分页参数
	 * @return
	 */
	public List<User> getUserListPage(User user, PageEntity page);
}
