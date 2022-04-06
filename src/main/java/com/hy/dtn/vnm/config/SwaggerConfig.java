package com.hy.dtn.vnm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author yjz
 * @version 1.0
 * @className SwaggerConfig
 * @date 2020/12/07 16:56
 * @description swagger文档分类的配置信息
 * @note 说明
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /**
     * @return springfox.documentation.spring.web.plugins.Docket
     * @methodName swaggerDocket
     * @author yjz
     * @description 创建swaggerDocket
     * @date 2020/12/07 17:01
     * @note 修改说明
     */
    @Bean
    public Docket swaggerDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(swaggerInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.hy.dtn.vnm"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * @return springfox.documentation.service.ApiInfo
     * @methodName swaggerInfo
     * @author yjz
     * @description swagger的描述信息
     * @date 2020/12/07 17:02
     * @note 修改说明
     */
    public ApiInfo swaggerInfo() {
        return new ApiInfoBuilder()
                .title("监控指标管理系统")
                .description("南京华鹞信息科技有限公司")
                .termsOfServiceUrl("http://127.0.0.1:8091/")
                .version("0.1")
                .build();
    }

    /**
     * @return springfox.documentation.spring.web.plugins.Docket
     * @methodName webApiToLogin
     * @author yjz
     * @description 用户功能管理
     * @date 2020/12/07 17:04
     * @note 修改说明
     */
    @Bean
    public Docket webApiToLogin() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder().title("用户功能管理").build())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant("/api/tologin/**"))
                .build()
                .groupName("ToLogin")
                .pathMapping("/");
    }

    /**
     * @return springfox.documentation.spring.web.plugins.Docket
     * @description 资产管理
     * @methodName webApiAssets
     * @author yjz
     * @date 2021/03/03 10:51
     * @note 修改说明
     */
    @Bean
    public Docket webApiAssets() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder().title("资产管理").build())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant("/api/assets/**"))
                .build()
                .groupName("Assets")
                .pathMapping("/");
    }
}
