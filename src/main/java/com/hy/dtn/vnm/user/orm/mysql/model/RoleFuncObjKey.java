package com.hy.dtn.vnm.user.orm.mysql.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yjz
 * @version 1.0
 * @className
 * @date 2020/11/12 14:20
 * @description 描述
 * @note 说明
 */
@Data
public class RoleFuncObjKey implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 7896117409438831192L;

    private String jsdm;

    private String mkdm;

}