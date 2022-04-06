package com.hy.dtn.vnm.user.orm.mysql.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yjz
 * @version 1.0
 * @className DictItemObj
 * @date 2020/11/12 14:46
 * @description 描述
 * @note 说明
 */
@Data
public class DictItemObj extends DictItemObjKey implements Serializable {
    private static final long serialVersionUID = -1620734583330017156L;

    private String tmnr;

    private Short xssx;
}