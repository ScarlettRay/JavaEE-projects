package com.inxedu.os.edu.entity.questions;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 问答标签
 *@author www.inxedu.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class QuestionsTag implements Serializable {
    private static final long serialVersionUID = -1912600357482790771L;
    private int questionsTagId; // 专业id
    private String questionsTagName;// 专业名称
    private int status;// 状态
    private Date createTime;// 创建时间
    private int parentId;// 父节点
}
