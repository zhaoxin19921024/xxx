package com.hy.dtn.vnm.rpc.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author yjz
 * @version 1.0
 * @className DeployConfig
 * @date 2021/01/01 14:32
 * @description 配置信息
 * @note 说明
 */
@Configuration
@PropertySource(value = {"classpath:/config/deploy.properties", "file:${config.deploy.path}"}, ignoreResourceNotFound = true)
@ConfigurationProperties(prefix = "deploy")
@Data
public class DeployConfig {

    /**
     * 应用部署服务ip
     */
    private String server;
    /**
     * 应用部署服务端口
     */
    private int port;
    /**
     * 应用部署服务接口名
     */
    private String service;

}
