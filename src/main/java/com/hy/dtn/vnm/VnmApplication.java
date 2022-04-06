package com.hy.dtn.vnm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author yjz
 * @version 1.0
 * @className Application
 * @date 2021/03/01 17:02
 * @description SpringBoot项目启动类
 * @note 说明
 */
@MapperScan(basePackages = {"com.hy.dtn.vnm.**.dao"})
@SpringBootApplication(scanBasePackages = "com.hy")
@EnableTransactionManagement
public class VnmApplication {

    public static void main(String[] args) {
        SpringApplication.run(VnmApplication.class, args);
    }
}
