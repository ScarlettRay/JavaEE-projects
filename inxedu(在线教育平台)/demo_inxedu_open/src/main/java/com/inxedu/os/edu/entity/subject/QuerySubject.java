package com.inxedu.os.edu.entity.subject;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * @author www.inxedu.com
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class QuerySubject implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -1912600357482790771L;
    /**
     * 专业id
     */
    private int subjectId = -1;
    /**
     * 专业名称
     */
    private String subjectName;
    /**
     * 状态
     */
    private int status;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;
    /**
     * 父节点
     */
    private int parentId = -1;
    /**
     * 等级 1，2，3
     */
    // private int level;
    //图片
    private String image;
}
