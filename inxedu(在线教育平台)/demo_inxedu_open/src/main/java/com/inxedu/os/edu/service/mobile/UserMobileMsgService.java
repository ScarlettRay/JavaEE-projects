package com.inxedu.os.edu.service.mobile;

import com.inxedu.os.common.entity.PageEntity;
import com.inxedu.os.edu.entity.mobile.UserMobileMsg;

import java.util.Date;
import java.util.List;


/**
 * @author www.inxedu.com
 */
public interface UserMobileMsgService {
	/**
	 * 短信记录列表
	 * 
	 * @param userMobileMsg
	 * @param page
	 * @return
	 */
	public List<UserMobileMsg> queryUserMobileMsgList(UserMobileMsg userMobileMsg, PageEntity page);
	
	/**
	 * 获得单个记录
	 * 
	 * @param id
	 * @return
	 */
	public UserMobileMsg queryUserMobileMsgById(Long id);
	
	/**
     * 删除短信
     */
    public void delUserMobileMsg(Long id);
    
    /**
     * 修改短信
     * @param userMobileMsg
     */
    public void updateUserMobileMsg(UserMobileMsg userMobileMsg);
    
    /**
	 * 添加发送用户短信记录
	 * @return
	 */
	public void addUserMobileMsg(List<UserMobileMsg> userMobileMsgList);
	
	/**
     * 起多个线程批量发送手机
     */
    public void batchSendMobileMsg(String text, String[] mailto, int num);
    
    /**
     * 定时发送  调用的方法
     */
    public void timingSendMsg(Date nowDate) throws Exception;
    
    /**
     * 查询和当前时间相等的短信记录 发送
     */
    public List<UserMobileMsg> queryNowMobileMsgList(Date nowDate);
    
    /**
     * 修改短信发送状态
     */
    public void updateMsgStatus(Long id);
}
