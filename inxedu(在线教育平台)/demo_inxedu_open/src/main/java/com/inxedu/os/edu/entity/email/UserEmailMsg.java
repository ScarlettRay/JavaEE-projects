package com.inxedu.os.edu.entity.email;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户邮件消息
 * @author www.inxedu.com
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class UserEmailMsg implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1791597934814803374L;
    
    private int id;//主键
    private Long userId;//用户id
    private String email;//邮箱
    private String title;//邮箱标题
    private String content;//正文内容
    private Date createTime;//创建时间
    private String loginName;//操作人
    private String startDate;//开始时间
    private String endDate;//结束时间
    private Date sendTime;//发送时间
    private int status;//1 已发送 2 未发送
    private int type;//1 正常 2 定时

}
