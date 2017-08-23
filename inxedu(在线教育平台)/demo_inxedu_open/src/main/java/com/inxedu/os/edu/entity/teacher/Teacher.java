package com.inxedu.os.edu.entity.teacher;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 讲师实体
 * @author www.inxedu.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Teacher implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 838162101564081713L;
    private int id;// 主键自增
    private String name;// 讲师名称
    private String education;// 讲师资历
    private String career;// 讲师简介
    private int isStar;// 头衔 1高级讲师2首席讲师
    private String picPath;// 头像
    private int status;// 状态:0正常1删除
    private java.util.Date createTime;// 创建时间
    private java.util.Date updateTime;// 更新时间
    private int subjectId;//专业ID
    private int sort;//排序
}
