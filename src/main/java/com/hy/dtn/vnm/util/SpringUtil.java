package com.hy.dtn.vnm.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author yjz
 * @version 1.0
 * @className SpringUtil
 * @date 2020/11/16 15:43
 * @description 描述
 * @note 说明
 */
@Component
public class SpringUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (SpringUtil.applicationContext == null) {
            SpringUtil.applicationContext = applicationContext;
        }
    }

    /**
     * @return org.springframework.context.ApplicationContext
     * @methodName getApplicationContext
     * @author yjz
     * @description 获取applicationContext
     * @date 2020/11/16 15:46
     * @note 修改说明
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * @param name 类名
     * @return java.lang.Object
     * @methodName getBean
     * @author yjz
     * @description 根据name获取Bean
     * @date 2020/11/16 15:48
     * @note 修改说明
     */
    public static Object getBean(String name) {
        return applicationContext.getBean(name);
    }

    /**
     * @param tClass class
     * @return T
     * @methodName getBean
     * @author yjz
     * @description 根据Class获取Bean
     * @date 2020/11/16 15:49
     * @note 修改说明
     */
    public static <T> T getBean(Class<T> tClass) {
        return applicationContext.getBean(tClass);
    }

    /**
     * @param name   类名
     * @param tClass class
     * @return T
     * @methodName getBean
     * @author yjz
     * @description 根据name、Class获取Bean
     * @date 2020/11/16 15:49
     * @note 修改说明
     */
    public static <T> T getBean(String name, Class<T> tClass) {
        return applicationContext.getBean(name, tClass);
    }

}
