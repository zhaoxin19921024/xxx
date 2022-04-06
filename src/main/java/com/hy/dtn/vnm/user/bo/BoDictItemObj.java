package com.hy.dtn.vnm.user.bo;

import com.hy.dtn.vnm.user.orm.mysql.model.DictItemObj;
import lombok.Data;

import java.io.Serializable;

/**
 * @author yjz
 * @version 1.0
 * @className
 * @date 2020/11/11 17:49
 * @description 业务对象字典项信息类
 * @note 说明
 */
@Data
public class BoDictItemObj implements Serializable {

    private static final long serialVersionUID = 8717727064469923984L;
    /**
     * 字典项标识
     */
    private String tmbs;

    /**
     * 字典项内容
     */
    private String tmnr;

    public BoDictItemObj() {
    }

    public BoDictItemObj(DictItemObj itm) {
        this.tmbs = itm.getTmbs();
        this.tmnr = itm.getTmnr();
    }

}
