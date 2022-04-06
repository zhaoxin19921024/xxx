package com.hy.dtn.vnm.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

/**
 * @ClassName MessageConfig
 * @Author yjz
 * @Date 2020/11/02 10:55
 * @Version 1.0
 * @Description 配置返回信息
 * @Note 说明
 */
@Configuration
@Slf4j
public class MessageConfig {

    @Value("${spring.messages.basename}")
    private String[] BaseName;

    @Bean
    public ResourceBundleMessageSource resourceBundleMessageSource() {
        ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();
        resourceBundleMessageSource.setBasenames(BaseName);
        resourceBundleMessageSource.setDefaultEncoding("UTF-8");
        resourceBundleMessageSource.setAlwaysUseMessageFormat(true);
        return resourceBundleMessageSource;
    }

    /**
     * messageSource
     *
     * @return
     */
    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource resourceBundleMessageSource = resourceBundleMessageSource();
        resourceBundleMessageSource.setDefaultEncoding("UTF-8");
        return resourceBundleMessageSource;
    }

}
