package com.hy.dtn.vnm.aspect.verify;

import java.lang.annotation.*;

/**
 * @author yjz
 * @version 1.0
 * @className HyVerifyParamsAdd
 * @date 2021/01/30 14:01
 * @description 参数校验类型：初步定义为add,update,search,del。待后续完善
 * @note 说明
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value = ElementType.FIELD)
public @interface HyVerifyParamsType {

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
     * 忽略
     */
    String TYPE_IGNORE = "ignore";

    /**
     * 排序字段
     */
    int sort() default 0;

    /**
     * type值可能为{@code "add"},{@code "update"},{@code "del"},{@code "search"},{@code "ignore" 忽略}
     * 默认为除去忽略以外的所有数据
     */
    String[] value() default {"add", "update", "del", "search"};
}
