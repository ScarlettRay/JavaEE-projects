package com.inxedu.os.edu.service.email;


import com.inxedu.os.common.entity.PageEntity;
import com.inxedu.os.edu.entity.email.UserEmailMsg;

import java.util.List;
import java.util.Map;

/**
 * @author http://www.inxedu.com
 */
public interface UserEmailMsgService {
	/**
	 * 查询记录
	 * 
	 * @param userEmailMsg
	 * @param page
	 * @return
	 */
	public List<UserEmailMsg> queryUserEmailMsgList(UserEmailMsg userEmailMsg, PageEntity page);
	
	/**
	 * 获得单个记录
	 * 
	 * @param id
	 * @return
	 */
	public UserEmailMsg queryUserEmailMsgById(Long id);
	
	/**
	 * 添加发送用户邮件记录
	 * @return
	 */
	public void addUserEmailMsg(List<UserEmailMsg> userEmailMsgList);
	
	/**
     * 更新 UserEmailMsg
     */
    public void updateUserEmailMsgById(UserEmailMsg userEmailMsg);
    
    /**
     * 删除发送邮件记录
     */
    public void delUserEmailMsgById(Long id);
    
    /**
     * 发送邮件和短信定时service
     */
    public void queryTimingSendEmailMsg()throws Exception;
	
	/**
     * 起四个线程批量发送邮件
     */
    public void batchSendEmail(String[] mailto, String text, String title, int num);
    
    /**
	 * 验证邮箱格式 去重
	 */
	public Map<String, Object> checkEmail(String emailStr);
	
	/**
	 * 验证手机格式 去掉重复的方法
	 */
	public Map<String, Object> checkMobile(String mobileArr);
}
