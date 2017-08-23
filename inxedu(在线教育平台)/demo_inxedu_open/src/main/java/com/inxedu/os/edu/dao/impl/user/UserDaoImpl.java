package com.inxedu.os.edu.dao.impl.user;

import com.inxedu.os.common.dao.GenericDaoImpl;
import com.inxedu.os.common.entity.PageEntity;
import com.inxedu.os.edu.dao.user.UserDao;
import com.inxedu.os.edu.entity.user.QueryUser;
import com.inxedu.os.edu.entity.user.User;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author www.inxedu.com
 *
 */
@Repository("userDao")
public class UserDaoImpl extends GenericDaoImpl implements UserDao{

	
	public int createUser(User user) {
		this.insert("UserMapper.createUser", user);
		return user.getUserId();
	}

	
	public User queryUserById(int userId) {
		return this.selectOne("UserMapper.queryUserById", userId);
	}

	
	public int checkMobile(String mobile) {
		return this.selectOne("UserMapper.checkMobile", mobile);
	}

	
	public int checkEmail(String email) {
		return this.selectOne("UserMapper.checkEmail", email);
	}

	
	public User getLoginUser(Map<String, Object> map) {
		return this.selectOne("UserMapper.getLoginUser", map);
	}

	
	public void updateUserPwd(User user) {
		this.update("UserMapper.updateUserPwd", user);
	}

	
	public List<User> queryUserListPage(QueryUser query, PageEntity page) {
		return this.queryForListPage("UserMapper.queryUserListPage", query, page);
	}

	
	public void updateUserStates(User user) {
		this.update("UserMapper.updateUserStates", user);
	}

	
	public void updateUser(User user) {
		this.update("UserMapper.updateUser", user);
	}

	
	public void updateImg(User user) {
		this.update("UserMapper.updateImg", user);
	}

	
	public int queryAllUserCount() {
		return this.selectOne("UserMapper.queryAllUserCount", null);
	}

	
	public User queryUserByEmailOrMobile(String emailOrMobile) {
		return this.selectOne("UserMapper.queryUserByEmailOrMobile", emailOrMobile);
	}


	@Override
	public List<User> queryUsersByIds(List<Long> cusIds) throws Exception {
		 return this.selectList("UserMapper.queryCustomerInCusIds", cusIds);
	}
	
	/**
     * 通过标识更新未读数加一
     * 
     */
    public void updateUnReadMsgNumAddOne(String falg,Long cusId){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("falg", falg);
        map.put("cusId", cusId);
        this.update("UserMapper.updateUnReadMsgNumAddOne", map);
    }
    
    /**
     * 通过标识更新未读数清零
     * 
     */
    public void updateUnReadMsgNumReset(String falg,Long cusId){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("falg", falg);
        map.put("cusId", cusId);
        this.update("UserMapper.updateUnReadMsgNumReset", map);
    }


	@Override
	public void updateCusForLST(Long cusId, Date date) {
		Map<String,Object> map = new HashMap<String,Object>();
        map.put("cusId", cusId);
        map.put("date", date);
        this.update("UserMapper.updateCusForLST", map);
	}

	@Override
	public List<User> getUserListPage(User user, PageEntity page) {
		return this.queryForListPage("UserMapper.getUserListPage", user, page);
	}
	
}
