package com.hy.dtn.vnm.user.orm.mysql.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author FuncObj
 * @version 1.0
 * @className
 * @date 2020/11/12 14:13
 * @description 功能模块
 * @note 说明
 */
@NoArgsConstructor
@Data
public class FuncObj implements Serializable {
    /**
     * 序列号
     */
    private static final long serialVersionUID = 1890720769340050991L;

    /**
     * 模块代码
     */
    private String mkdm;

    /**
     * 模块名称
     */
    private String mkmc;

    /**
     * 显示顺序
     */
    private Short xssx;

    /**
     * 路由表数据
     */
    private String lyb;

}