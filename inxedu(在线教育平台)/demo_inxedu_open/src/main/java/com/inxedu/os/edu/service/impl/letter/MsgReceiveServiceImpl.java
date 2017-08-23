package com.inxedu.os.edu.service.impl.letter;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inxedu.os.common.cache.EHCacheUtil;
import com.inxedu.os.common.constants.CacheConstans;
import com.inxedu.os.common.entity.PageEntity;
import com.inxedu.os.common.util.ObjectUtils;
import com.inxedu.os.edu.dao.letter.MsgReceiveDao;
import com.inxedu.os.edu.entity.letter.LetterConstans;
import com.inxedu.os.edu.entity.letter.MsgReceive;
import com.inxedu.os.edu.entity.letter.MsgSystem;
import com.inxedu.os.edu.entity.letter.QueryMsgReceive;
import com.inxedu.os.edu.entity.user.User;
import com.inxedu.os.edu.service.letter.MsgReceiveService;
import com.inxedu.os.edu.service.letter.MsgSystemService;
import com.inxedu.os.edu.service.user.UserService;

/**
 * @description 站内信的实现
 * @author www.inxedu.com
 */
@Service("msgReceiveService")
public class MsgReceiveServiceImpl implements MsgReceiveService {
	//logger
	Logger logger = LoggerFactory.getLogger(MsgReceiveServiceImpl.class);
    @Autowired
    private MsgReceiveDao msgReceiveDao;
    @Autowired
    private UserService userService;
    
    @Autowired
    private MsgSystemService msgSystemService;

    

    
    /**
     * 查询站内信收件箱
     *
     * @param msgReceive 站内信实体
     * @param page       分页参数
     * @return List<QueryMsgReceive> 站内信的list
     * @throws Exception
     */
    public List<QueryMsgReceive> queryMsgReceiveByInbox(MsgReceive msgReceive, PageEntity page) throws Exception {
    	//查询用户信息
   	    User user  = userService.queryUserById((Integer.parseInt(msgReceive.getReceivingCusId().toString())));
        Date lastTime = user.getLastSystemTime();
        //查询系统发送的未读消息（根据用户的最后登录时间）
        List<MsgSystem> MSlist = msgSystemService.queryMSListByLT(lastTime);
        if (ObjectUtils.isNotNull(MSlist)) {
            List<MsgReceive> msgrcList = new ArrayList<MsgReceive>();
            //查出未读的系统消息插入到系统中 更新
            for (MsgSystem mgstm : MSlist) {
                MsgReceive msgReceive1 = new MsgReceive();
                msgReceive1.setContent(mgstm.getContent());
                msgReceive1.setAddTime(new Date());
                msgReceive1.setReceivingCusId(msgReceive.getReceivingCusId());
                msgReceive1.setStatus(LetterConstans.LETTER_STATUS_READ);
                msgReceive1.setType(LetterConstans.LETTER_TYPE_SYSTEMINFORM);
                msgReceive1.setUpdateTime(new Date());
                msgReceive1.setShowname((user.getShowName()!=null&&!user.getShowName().equals(""))?user.getShowName():user.getEmail());
                msgReceive1.setCusId(0L);
                msgrcList.add(msgReceive1);
            }
            //批量添加站内信
            this.addMsgReceiveBatch(msgrcList);
        }
         
         //查询站内信
        List<QueryMsgReceive> queryMsgReceiveList = msgReceiveDao.queryMsgReceiveByInbox(msgReceive, page);
        // 更新所有收件箱为已读
        updateAllReadMsgReceiveInbox(msgReceive);
        //清除粉丝未读消息的缓存
        userService.updateUnReadMsgNumReset("msgNum", msgReceive.getReceivingCusId());
        //更新用户的最后系统消息时间
        userService.updateCusForLST(msgReceive.getReceivingCusId(), new Date());
        return queryMsgReceiveList;
    }



    /**
     * 删除站内信过期消息
     */
    public Long delMsgReceivePast(Date time) throws Exception {
        return msgReceiveDao.delMsgReceivePast(time);
    }

    /**
     * 删除收件箱
     *
     * @param msgReceive 站内信实体 通过站内信的id
     * @throws Exception
     */
    public Long delMsgReceiveInbox(MsgReceive msgReceive) throws Exception {
        return msgReceiveDao.delMsgReceiveInbox(msgReceive);// 更新站内信的状态 删除收件箱
    }

    /**
     * 更新收件箱所有信为已读
     *
     * @param msgReceive 站内信实体
     * @throws Exception
     */
    public void updateAllReadMsgReceiveInbox(MsgReceive msgReceive) throws Exception {
        msgReceiveDao.updateAllReadMsgReceiveInbox(msgReceive);// 更新收件箱所有信为已读
    }

    /**
     * 发送系统消息
     *
     * @param content 要发送的内容
     * @param cusId   用户id
     * @throws Exception
     */
    public String addSystemMessageByCusId(String content, Long cusId) throws Exception {
        
        User userExpandDto = userService.queryUserById(Integer.parseInt(cusId.toString()));
        
        MsgReceive msgReceive = new MsgReceive();
        msgReceive.setContent(content);// 添加站内信的内容
        msgReceive.setCusId(Long.valueOf(0));
        msgReceive.setReceivingCusId(cusId);// 要发送的用户id
        msgReceive.setStatus(LetterConstans.LETTER_STATUS_UNREAD);// 消息未读状态
        msgReceive.setType(LetterConstans.LETTER_TYPE_SYSTEMINFORM);// 系统消息
        msgReceive.setUpdateTime(new Date());// 更新时间s
        msgReceive.setAddTime(new Date());// 添加时间
        if (userExpandDto != null && userExpandDto.getShowName() != null) {// 如果不为空则set showname
            msgReceive.setShowname(userExpandDto.getShowName());// 会员名
        } else {// 如果为空则set 空字符串
            msgReceive.setShowname("");// 会员名
        }
        try{
        	msgReceiveDao.addMsgReceive(msgReceive);
        	userService.updateUnReadMsgNumAddOne("sysMsgNum", cusId);
        }catch(Exception e){
        	logger.error("addSystemMessageByCusId---send message is error", e);
        }
        
        return "success";
    }

    /**
     * 查询该用户未读消息数量
     *
     * @param content 要发送的内容
     * @return 返回该用户四种类型每个的未读消息的数量和总的未读数量
     * @throws Exception
     */
    public Map<String, String> queryUnReadMsgReceiveNumByCusId(Long cusId) throws Exception {
        @SuppressWarnings("unchecked")
		Map<String,String> map =new HashMap<String,String>();
        User userExpandDto=userService.queryUserById(Integer.parseInt(cusId.toString()));
        //未读系统自动消息数
        int smNum = userExpandDto.getSysMsgNum();
        //未读站内信数
        int mNum = userExpandDto.getMsgNum();
        //上次查询系统消息时间
        Date lastTime = userExpandDto.getLastSystemTime();
        //根据时间查询系统消息
        List<MsgSystem> MSlist = msgSystemService.queryMSListByLT(lastTime);

        map.put("mNum", mNum + "");
        if (ObjectUtils.isNotNull(MSlist)) {
            map.put("SMNum", smNum + MSlist.size() + "");
            map.put("unReadNum", mNum + MSlist.size() + smNum + "");
        } else {
            map.put("SMNum", smNum + "");
            map.put("unReadNum", mNum +  smNum + "");
        }
        return map;// 返回查好的数据
    }

    /**
     * 更新某种类型的站内信状态为已读
     *
     * @param msgReceive 传入type 传入type和站内信收信人id
     * @throws Exception
     */
    public void updateAllMsgReceiveReadByType(MsgReceive msgReceive) throws Exception {
        msgReceiveDao.updateAllMsgReceiveReadByType(msgReceive);// 更新消息为已读
    }

    /**
     * 批量添加消息
     *
     * @param msgReceiveList 消息的list
     */
    public Long addMsgReceiveBatch(List<MsgReceive> msgReceiveList) {
        return msgReceiveDao.addMsgReceiveBatch(msgReceiveList);
    }

}
