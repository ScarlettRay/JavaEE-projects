package com.inxedu.os.edu.entity.user;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 外部登录用户信息
 * @author www.inxedu.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class WebLoginUser implements Serializable{

   private static final long serialVersionUID = -4181611215034299276L;

   private Long id;// 主键 id
   private Long cusId;// 用户id
   private String email = "";// 邮件
   private String mobile = "";// 手机号
   private String nickname = "";// 用户名
   private String realname;// 真实姓名
   private int gender=0;// 性别：0男 1女
   private String avatar;// 头像地址
   private String userInfo="";// 用户简介
}
