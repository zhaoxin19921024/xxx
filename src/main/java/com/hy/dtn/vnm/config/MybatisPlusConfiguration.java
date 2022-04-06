package com.hy.dtn.vnm.config;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.MybatisXMLLanguageDriver;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.transaction.SpringManagedTransactionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Date;

/**
 * MyBatis Plus参数配置
 * http://mp.baomidou.com
 *
 * @author yjz
 * @version 1.0.0 2020-10-22
 */
@Configuration
@Slf4j
public class MybatisPlusConfiguration {

    @Resource
    private DataSource dataSource;

    @Value("${mybatis-plus.global-config.db-config.id-type}")
    private String idType;

    @Value("${mybatis-plus.mapper-locations}")
    private String mapperLocations;

    @Value("${mybatis-plus.typeAliasesPackage}")
    private String typeAliasesPackage;

    /**
     * 初始化分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    /**
     * 初始化公共字段自动填充功能
     */
    @Component
    public static class MetaObjectHandlerConfiguration implements MetaObjectHandler {
        @Override
        public void insertFill(MetaObject metaObject) {
            Date today = new Date();
            this.fillStrategy(metaObject, "creationDate", today);
            this.fillStrategy(metaObject, "lastUpdateDate", today);
        }

        @Override
        public void updateFill(MetaObject metaObject) {
            this.fillStrategy(metaObject, "lastUpdateDate", new Date());
        }
    }


    @Bean("sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean mybatisSqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
        mybatisSqlSessionFactoryBean.setDataSource(dataSource);
        mybatisSqlSessionFactoryBean.setTypeAliasesPackage("com.bitter.storage.domain");
        mybatisSqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mapperLocations));

        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.setDefaultScriptingLanguage(MybatisXMLLanguageDriver.class);
        configuration.setJdbcTypeForNull(JdbcType.NULL);
        mybatisSqlSessionFactoryBean.setConfiguration(configuration);

        mybatisSqlSessionFactoryBean.setTransactionFactory(new SpringManagedTransactionFactory());
        return mybatisSqlSessionFactoryBean.getObject();
    }
}
