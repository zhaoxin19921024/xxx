package com.hy.dtn.vnm.user.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yjz
 * @version 1.0
 * @className
 * @date 2020/11/11 17:51
 * @description 描述
 * @note 说明
 */
@Data
public class BoRoleObj implements Serializable {
    /**
     * 序列号
     */
    private static final long serialVersionUID = 32529863364025351L;

    private String jsdm;

    private String jsmc;

    private String jssm;

    private int xssx;

}