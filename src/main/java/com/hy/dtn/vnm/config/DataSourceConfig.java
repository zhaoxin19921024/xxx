package com.hy.dtn.vnm.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * 开启sql配置 druid
 *
 * @author yjz
 */
@EnableTransactionManagement
@Configuration
public class DataSourceConfig {


    @Value("${spring.datasource.druid.driver-class-name}")
    private String jdbcDriver;
    @Value("${spring.datasource.druid.url}")
    private String jdbcUrl;
    @Value("${spring.datasource.druid.username}")
    private String jdbcUsername;
    @Value("${spring.datasource.druid.password}")
    private String jdbcPassword;


    /**
     * 生成与spring-dao.xml对应的bean dataSource
     *
     * @return
     */
    @Bean(name = "dataSource")
    public DataSource dataSource() {
        return DruidDataSourceBuilder.create().build();
//        return new DruidDataSource();
    }

}
