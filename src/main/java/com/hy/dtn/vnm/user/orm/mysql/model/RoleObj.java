package com.hy.dtn.vnm.user.orm.mysql.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yjz
 * @version 1.0
 * @className RoleObj
 * @date 2020/11/12 14:22
 * @description 描述
 * @note 说明
 */
@Data
public class RoleObj implements Serializable {
    /**
     * 序列号
     */
    private static final long serialVersionUID = 32529863364025351L;

    private String jsdm;

    private String jsmc;

    private String jssm;

    private Integer xssx;

}