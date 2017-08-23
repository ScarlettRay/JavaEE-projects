package com.inxedu.os.edu.entity.subject;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author www.inxedu.com
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Subject implements Serializable {
    private static final long serialVersionUID = -1912600357482790771L;
    private int subjectId; // 专业id
    private String subjectName;// 专业名称
    private int status;// 状态
    private Date createTime;// 创建时间
    private int parentId;// 父节点
    private int sort;//排序字段
}
