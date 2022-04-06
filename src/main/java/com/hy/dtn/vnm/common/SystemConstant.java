package com.hy.dtn.vnm.common;

import lombok.Data;

/**
 * @author yjz
 * @version 1.0
 * @className SystemConstant
 * @date 2020/12/04 15:39
 * @description 系统常量
 * @note 说明
 */
@Data
public class SystemConstant {

    /**
     * 资产树根节点的显示名称
     */
    public static final String ASSETS_TREE_ROOT_TITLE = "资产管理分类";

    /**
     * 资产树根节点的key值
     */
    public static final String ASSETS_TREE_ROOT_KEY = "root";

    /**
     * 资产注册
     */
    public static final Integer ASSETS_REGISTER = 1;

    /**
     * 资产反注册
     */
    public static final Integer ASSETS_UNREGISTER = 0;



    public static final int SERVER_RESULT_STATE_SUCCESS = 1;

}
