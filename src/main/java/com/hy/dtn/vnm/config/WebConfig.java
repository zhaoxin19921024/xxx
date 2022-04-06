package com.hy.dtn.vnm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @author yjz
 * @version 1.0
 * @className WebConfig
 * @date 2021/03/01 17:22
 * @description 跨域设置
 * @note 说明
 */
@Configuration
public class WebConfig {


    /**
     * @return org.springframework.web.filter.CorsFilter
     * @description 跨域访问
     * @methodName corsFilter
     * @author yjz
     * @date 2021/03/01 17:22
     * @note 修改说明
     */
    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();
        // 允许cookies跨域
        config.setAllowCredentials(true);
        // 允许向该服务器提交请求的URI，*表示全部允许。。这里尽量限制来源域，比如http://xxx:8080 ,以降低安全风险。。
        config.addAllowedOrigin("*");
        // 允许访问的头信息,*表示全部
        config.addAllowedHeader("*");
        // 预检请求的缓存时间（秒），即在这个时间段里，对于相同的跨域请求不会再预检了
        config.setMaxAge(18000L);
        // 允许提交请求的方法，*表示全部允许，也可以单独设置GET、PUT等
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}