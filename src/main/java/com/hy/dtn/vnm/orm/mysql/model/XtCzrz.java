package com.hy.dtn.vnm.orm.mysql.model;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author yjz
 * @version 1.0
 * @className
 * @date 2020/12/01 15:43
 * @description 操作日志 t_xt_czrz
 * @note 说明
 */
@Data
public class XtCzrz implements Serializable {
    /**
     * 版本号
     */
    private static final long serialVersionUID = 8080742665459869934L;

    /**
     * 操作ID 32位UUID
     */
    private String lid;

    /**
     * 用户ID
     */
    private String uid;

    /**
     * 用户姓名
     */
    private String xm;

    /**
     * 操作时间
     */
    private Timestamp tm;

    /**
     * 操作类型
     */
    private String lx;

    /**
     * 操作内容
     */
    private String nr;

    /**
     * 操作结果 0：失败，1：成功
     */
    private Integer jg;

    /**
     * 失败原因
     */
    private String sbyy;
}