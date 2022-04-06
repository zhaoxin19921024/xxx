package com.hy.dtn.vnm.rpc.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author yjz
 * @version 1.0
 * @className AssetsRegisterConfig
 * @date 2021/01/01 14:32
 * @description 配置信息
 * @note 说明
 */
@Configuration
@PropertySource(value = {"classpath:/config/assets.properties", "file:${config.assets.path}"}, ignoreResourceNotFound = true)
@ConfigurationProperties(prefix = "assets")
@Data
public class AssetsConfig {

    private String server;

    private int port;

}
