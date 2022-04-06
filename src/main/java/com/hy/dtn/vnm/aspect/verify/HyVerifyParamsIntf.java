package com.hy.dtn.vnm.aspect.verify;

import java.lang.annotation.*;

/**
 * @author yjz
 * @version 1.0
 * @className HyVerifyParams
 * @date 2020/11/05 15:31
 * @description hy自定义参数校验注解
 * @note 说明
 */
@Target(value = {ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HyVerifyParamsIntf {
    /**
     * 新增
     */
    String TYPE_ADD = "add";
    /**
     * 修改
     */
    String TYPE_UPDATE = "update";
    /**
     * 删除
     */
    String TYPE_DEL = "del";
    /**
     * 查询
     */
    String TYPE_SEARCH = "search";

    /**
     * 网络模板
     */
    String SCANNEDAREA_WLMB = "netTemplate";
    /**
     * 应用场景
     */
    String SCANNEDAREA_SCENE = "scene";

    /**
     * type值可能为{@code "add"},{@code "update"},{@code "del"},{@code "search"}
     * 后续用来完善新增和更新的区分
     * 初始化默认值为空，代表当前默认对全部字段进行校验
     *
     * @return String
     */
    String type() default TYPE_ADD;

    /**
     * 需要扫描的类型，待后续完善
     * netTemplate或者scene
     * 采用CommConst中的配置信息
     *
     * @return String
     */
    String scannedArea() default SCANNEDAREA_SCENE;

    /**
     * 目前可去除,当前需要校验参数的类
     *
     * @return CLass
     */
    @Deprecated
    Class<?>[] value() default {};


}
