package com.inxedu.os.edu.service.impl.mobile;


import com.inxedu.os.common.entity.PageEntity;
import com.inxedu.os.common.util.DateUtils;
import com.inxedu.os.common.util.ObjectUtils;
import com.inxedu.os.edu.dao.mobile.UserMobileMsgDao;
import com.inxedu.os.edu.entity.mobile.UserMobileMsg;
import com.inxedu.os.edu.service.impl.email.UserEmailMsgServiceImpl;
import com.inxedu.os.edu.service.mobile.SmsBatchThread;
import com.inxedu.os.edu.service.mobile.UserMobileMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author www.inxedu.com
 */
@Service("userMobileMsgService")
public class UserMobileMsgServiceImpl implements UserMobileMsgService {
    @Autowired
    private UserMobileMsgDao userMobileMsgDao;
    
    /**
     * 短信记录列表
     * 
     * @param userMobileMsg
     * @param page
     * @return
     */
    public List<UserMobileMsg> queryUserMobileMsgList(UserMobileMsg userMobileMsg, PageEntity page) {
        return userMobileMsgDao.queryUserMobileMsgList(userMobileMsg, page);
    }
    
    /**
     * 获得单个记录
     * 
     * @param id
     * @return
     */
    public UserMobileMsg queryUserMobileMsgById(Long id) {
        return userMobileMsgDao.queryUserMobileMsgById(id);
    }
    
    /**
     * 删除短信
     */
    public void delUserMobileMsg(Long id){
    	userMobileMsgDao.delUserMobileMsg(id);
    }

    /**
     * 修改短信
     * @param userMobileMsg
     */
    public void updateUserMobileMsg(UserMobileMsg userMobileMsg){
    	userMobileMsgDao.updateUserMobileMsg(userMobileMsg);
    }
    
    /**
     * 添加发送用户短信记录
     * 
     * @param userMobileMsg
     * @return
     */
    public void addUserMobileMsg(List<UserMobileMsg> userMobileMsg) {
        userMobileMsgDao.addUserMobileMsg(userMobileMsg);
    }
    
    /**
     * 起多个线程批量发送手机
     */
    public void batchSendMobileMsg(String text,String[] mailto,int num){
        if(ObjectUtils.isNotNull(mailto)){
            List<String> list = new ArrayList<String>();
            list.addAll(Arrays.asList(mailto));
            SmsBatchThread smsBatchThread = new SmsBatchThread(list,text);
            System.out.println("批量发送短信线程启动：线程数："+num+"发送邮件数："+mailto.length);
            System.out.println("短信开始发送时间"+ DateUtils.getNowTime());
            //启动多少线程
            for(int i=0;i<num;i++){
                new Thread(smsBatchThread).start();
            }
        }
    }
    
    /**
     * 查询和当前时间相等的短信记录 发送
     */
    public List<UserMobileMsg> queryNowMobileMsgList(Date nowDate){
    	return userMobileMsgDao.queryNowMobileMsgList(nowDate);
    }
    
    /**
     * 修改短信发送状态
     */
    public void updateMsgStatus(Long id){
    	userMobileMsgDao.updateMsgStatus(id);
    }
    
    /**
     * 定时发送  调用的方法
     */
    public void timingSendMsg(Date nowDate) throws Exception{
		List<UserMobileMsg> mobileMsgs=this.queryNowMobileMsgList(nowDate);
        if(ObjectUtils.isNotNull(mobileMsgs)){
            for (int i = 0; i < mobileMsgs.size(); i++) {
                String msgContent=mobileMsgs.get(i).getContent();
                String destNumber=mobileMsgs.get(i).getMobile();
                System.out.println(DateUtils.dateToStr(new Date(), "yyyy-MM-dd hh:mm:ss")+":发送短信"+destNumber);
                //发送
                this.batchSendMobileMsg(msgContent, destNumber.split(","),3);
                //修改短信发送状态
                this.updateMsgStatus(Long.valueOf(mobileMsgs.get(i).getId()));
            }
        }
	}
}
