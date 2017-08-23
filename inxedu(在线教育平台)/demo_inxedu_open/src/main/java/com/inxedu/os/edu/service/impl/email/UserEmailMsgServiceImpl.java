package com.inxedu.os.edu.service.impl.email;

import com.inxedu.os.common.entity.PageEntity;
import com.inxedu.os.common.service.email.EmailService;
import com.inxedu.os.common.util.DateUtils;
import com.inxedu.os.common.util.ObjectUtils;
import com.inxedu.os.edu.dao.email.UserEmailMsgDao;
import com.inxedu.os.edu.entity.email.UserEmailMsg;
import com.inxedu.os.edu.service.email.EmailThread;
import com.inxedu.os.edu.service.email.UserEmailMsgService;
import com.inxedu.os.edu.service.mobile.UserMobileMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author http://www.inxedu.com
 */
@Service("userEmailMsgService")
public class UserEmailMsgServiceImpl implements UserEmailMsgService {
	@Autowired
    private UserEmailMsgDao userEmailMsgDao;
	@Autowired
    private EmailService emailService;
	@Autowired
	private UserMobileMsgService userMobileMsgService;
	
	/**
     * 查询记录
     * 
     * @param userEmailMsg
     * @param page
     * @return
     */

    public List<UserEmailMsg> queryUserEmailMsgList(UserEmailMsg userEmailMsg,
													PageEntity page) {
        return userEmailMsgDao.queryUserEmailMsgList(userEmailMsg, page);
    }

    /**
     * 获得单个记录
     * 
     * @param id
     * @return
     */
    public UserEmailMsg queryUserEmailMsgById(Long id) {
        return userEmailMsgDao.queryUserEmailMsgById(id);
    }
    
    /**
     * 添加发送用户邮箱记录
     * 
     * @param userEmailMsg
     * @return
     */
    public void addUserEmailMsg(List<UserEmailMsg> userEmailMsg) {
        userEmailMsgDao.addUserEmailMsg(userEmailMsg);
    }
    
    /**
     * 更新 UserEmailMsg
     */
    public void updateUserEmailMsgById(UserEmailMsg userEmailMsg){
        userEmailMsgDao.updateUserEmailMsgById(userEmailMsg);
    }


    /**
     * 删除发送邮件记录
     */
    public void delUserEmailMsgById(Long id){
        userEmailMsgDao.delUserEmailMsgById(id);
    }
    
    /**
     * 发送邮件和短信定时service
     */
    public void queryTimingSendEmailMsg()throws Exception{
        UserEmailMsg userEmailMsg = new UserEmailMsg();
        userEmailMsg.setSendTime(new Date());
        userEmailMsg.setType(2);//类型为定时的
        userEmailMsg.setStatus(2);//找未发送的
        List<UserEmailMsg> userEmailMsgList = userEmailMsgDao.queryUserEmailList(userEmailMsg);

        if(ObjectUtils.isNotNull(userEmailMsgList)){
            for(UserEmailMsg u:userEmailMsgList){
                System.out.println(DateUtils.dateToStr(new Date(), "yyyy-MM-dd hh:mm:ss")+":发送定时邮件邮件"+u.getEmail());
                //发送邮件
                batchSendEmail(u.getEmail().split(","), u.getContent(), u.getTitle(),3);
                u.setStatus(1);
                userEmailMsgDao.updateUserEmailStatus(u);
            }
        }
        userMobileMsgService.timingSendMsg(new Date());
    }
    
    /**
     * 起四个线程批量发送邮件
     */
    public void batchSendEmail(String[] mailto, String text, String title,int num){
        if(ObjectUtils.isNotNull(mailto)){
            List<String> list = new ArrayList<String>();
            list.addAll(Arrays.asList(mailto));
            EmailThread emailThread = new EmailThread(list,text,title,emailService);
            System.out.println("批量发送邮件线程启动：线程数："+num+"发送邮件数："+mailto.length);
            System.out.println("开始发送时间"+ DateUtils.getNowTime());
            //启动多少线程
            for(int i=0;i<num;i++){
                new Thread(emailThread).start();
            }
        }
    }
    
    /**
	 * 验证邮箱格式 去重
	 * 
	 * @param emailStr
	 */
	public Map<String, Object> checkEmail(String emailStr) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		emailStr = emailStr.replaceAll("\r\n", "");// 去除空格回车
		emailStr = emailStr.replaceAll(" ", "");// 去除空格回车
		String[] lm = emailStr.split(",");// 定义数组

		List list = new ArrayList();// new一个arralist
		Set set = new HashSet();// new 一个hashset
		set.addAll(Arrays.asList(lm));// 将数组转为list并存入set中，就可以去掉重复项了
		for (Iterator it = set.iterator(); it.hasNext();) {
			list.add(it.next());// 遍历set 将所有元素键入list中
		}
		String noRepeatList = list.toString();
		noRepeatList = noRepeatList.replace("[", "");
		noRepeatList = noRepeatList.replace("]", "");
		noRepeatList = noRepeatList.replace(" ", "");
		noRepeatList = noRepeatList.trim();

		boolean flag = true;
		String errorMessage = "";
		String[] lms = noRepeatList.split(",");
		if (lms.length > 0) {
			for (int i = 0; i < lms.length; i++) {
				if (!lms[i].trim().matches("^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$")) {
					flag = false;
					errorMessage = lms[i] + "格式有误";
					break;
				}
			}
		}

		returnMap.put("flag", flag);
		returnMap.put("returnList", noRepeatList);
		returnMap.put("errorMessage", errorMessage);
		return returnMap;
	}
	
	/**
	 * 验证手机格式 去掉重复的方法
	 * 
	 * @param mobileArr
	 *            字符串
	 */
	public Map<String, Object> checkMobile(String mobileArr) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		mobileArr = mobileArr.replaceAll("\r\n", "");// 去除空格回车
		mobileArr = mobileArr.replaceAll(" ", "");// 去除空格回车
		String[] lm = mobileArr.split(",");// 定义数组
		List list = new ArrayList();// new一个arralist
		Set set = new HashSet();// new 一个hashset
		set.addAll(Arrays.asList(lm));// 将数组转为list并存入set中，就可以去掉重复项了
		for (Iterator it = set.iterator(); it.hasNext();) {
			list.add(it.next());// 遍历set 将所有元素键入list中
		}
		String noRepeatList = list.toString();
		noRepeatList = noRepeatList.replace("[", "");
		noRepeatList = noRepeatList.replace("]", "");
		noRepeatList = noRepeatList.replace(" ", "");
		noRepeatList = noRepeatList.trim();

		String flag = "true";
		String errorMobile = "";
		String[] lms = noRepeatList.split(",");
		if (lms.length > 0) {
			for (int i = 0; i < lms.length; i++) {
				if (!lms[i].trim().matches("^1[0-9]{10}$")) {
					flag = "false";
					errorMobile = lms[i] + "格式有误";
					break;
				}
			}
		}
		returnMap.put("flag", flag);
		returnMap.put("mobileList", noRepeatList);
		returnMap.put("errorMobile", errorMobile);
		return returnMap;
	}
}
