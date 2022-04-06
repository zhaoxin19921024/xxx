package com.hy.dtn.vnm.aspect.systemdiag;

import java.lang.annotation.*;

/**
 * @author yjz
 * @version 1.0
 * @className SystemController
 * @date 2020/12/01 16:02
 * @description 自定义注解，拦截Service
 * @note 说明
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SystemServiceLog {

    String description() default "";
}
