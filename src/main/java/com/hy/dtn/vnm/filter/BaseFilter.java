package com.hy.dtn.vnm.filter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

import javax.servlet.Filter;

/**
 * @author yjz
 * @version 1.0
 * @className BaseFilter
 * @date 2020/11/20 16:02
 * @description 拦截器的基类
 * @note 说明
 */
@PropertySource("classpath:config/application.properties")
public abstract class BaseFilter implements Filter {
    /**
     * 不需要校验session的url
     */
    @Value("${spring.noLoginUrl}")
    protected String[] noLoginUrl;
}
