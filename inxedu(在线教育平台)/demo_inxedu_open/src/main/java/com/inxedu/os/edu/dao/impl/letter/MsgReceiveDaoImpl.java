package com.inxedu.os.edu.dao.impl.letter;

import com.inxedu.os.common.dao.GenericDaoImpl;
import com.inxedu.os.common.entity.PageEntity;
import com.inxedu.os.edu.dao.letter.MsgReceiveDao;
import com.inxedu.os.edu.entity.letter.MsgReceive;
import com.inxedu.os.edu.entity.letter.QueryMsgReceive;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author www.inxedu.com
 */
@Repository("msgReceiveDao")
public class MsgReceiveDaoImpl extends GenericDaoImpl implements MsgReceiveDao {
    /**
     * 添加站内信
     *
     * @param msgReceive 站内信实体
     */
    public Long addMsgReceive(MsgReceive msgReceive) {
        return this.insert("MsgReceiveMapper.addMsgReceive", msgReceive);// 添加站内信
    }


    /**
     * 查询站内信收件箱
     *
     * @param msgReceive 站内信实体
     * @param page       分页参数
     * @return List<QueryMsgReceive> 站内信收件箱List
     * @throws Exception
     */
    public List<QueryMsgReceive> queryMsgReceiveByInbox(MsgReceive msgReceive, PageEntity page) throws Exception {
        return this.queryForListPage("MsgReceiveMapper.queryMsgReceiveByInbox", msgReceive, page);// 查询站内信收件箱
    }


    /**
     * 删除站内信过期消息
     */
    public Long delMsgReceivePast(Date time) throws Exception {
        return this.delete("MsgReceiveMapper.delMsgReceivePast", time);
    }

    /**
     * 删除收件箱
     *
     * @param msgReceive 站内信实体 通过站内信的id
     * @throws Exception
     */
    public Long delMsgReceiveInbox(MsgReceive msgReceive) throws Exception {
        return this.delete("MsgReceiveMapper.delMsgReceiveInbox", msgReceive);
    }

    /**
     * 更新收件箱所有信为已读
     *
     * @param msgReceive 站内信实体
     * @throws Exception
     */
    public void updateAllReadMsgReceiveInbox(MsgReceive msgReceive) throws Exception {
        this.update("MsgReceiveMapper.updateAllReadMsgReceiveInbox", msgReceive);// 更新收件箱所有信为已读
    }

    /**
     * 更新某种类型的站内信状态为已读
     *
     * @param msgReceive 传入type和站内信收信人id
     * @throws Exception
     */
    public void updateAllMsgReceiveReadByType(MsgReceive msgReceive) throws Exception {
        this.update("MsgReceiveMapper.updateAllMsgReceiveReadByType", msgReceive);// 更新某种类型的站内信状态为已读
    }

    /**
     * 批量添加消息
     *
     * @param msgReceiveList 消息的list
     */
    public Long addMsgReceiveBatch(List<MsgReceive> msgReceiveList) {
        return this.insert("MsgReceiveMapper.addMsgReceiveBatch", msgReceiveList);// 批量添加消息
    }
}
