package com.inxedu.os.edu.dao.impl.mobile;

import com.inxedu.os.common.dao.GenericDaoImpl;
import com.inxedu.os.common.entity.PageEntity;
import com.inxedu.os.edu.dao.mobile.UserMobileMsgDao;
import com.inxedu.os.edu.entity.mobile.UserMobileMsg;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


/**
 *@author www.inxedu.com
 */
@Repository("userMobileDao")
public class UserMobileMsgDaoImpl extends GenericDaoImpl implements UserMobileMsgDao {
	/**
     * 短信记录列表
     * 
     * @param userMobileMsg
     * @param page
     * @return
     */
    public List<UserMobileMsg> queryUserMobileMsgList(UserMobileMsg userMobileMsg, PageEntity page) {
        return this.queryForListPage("UserMobileMsgMapper.queryUserMobileMsgList", userMobileMsg, page);
    }
    
    /**
     * 获得单个记录
     * 
     * @param id
     * @return
     */
    public UserMobileMsg queryUserMobileMsgById(Long id) {
        return this.selectOne("UserMobileMsgMapper.queryUserMobileMsgById", id);
    }
    
    /**
     * 删除短信
     */
    public void delUserMobileMsg(Long id){
    	this.delete("UserMobileMsgMapper.delUserMobileMsg", id);
    }
    
    /**
     * 修改短信
     * @param userMobileMsg
     */
    public void updateUserMobileMsg(UserMobileMsg userMobileMsg) {
		this.update("UserMobileMsgMapper.updateUserMobileMsg", userMobileMsg);
	}
    
    /**
     * 添加发送用户短信记录
     * @return
     */
    public Long addUserMobileMsg(List<UserMobileMsg> userMobileMsgList) {
        return this.insert("UserMobileMsgMapper.addUserMobileMsg", userMobileMsgList);
    }
    
    /**
     * 查询和当前时间相等的短信记录 发送
     * @return
     */
    public List<UserMobileMsg> queryNowMobileMsgList(Date nowDate){
    	return this.selectList("UserMobileMsgMapper.queryNowMobileMsgList", nowDate);
    }
    
    /**
     * 修改短信发送状态
     */
    public void updateMsgStatus(Long id){
    	this.update("UserMobileMsgMapper.updateMsgStatus", id);
    }
}
