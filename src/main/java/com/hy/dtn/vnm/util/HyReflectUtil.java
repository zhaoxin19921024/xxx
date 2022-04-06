package com.hy.dtn.vnm.util;

import cn.hutool.core.util.ReflectUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author yjz
 * @version 1.0
 * @className ReflectUtils
 * @date 2020/10/29 16:52
 * @description 反射相关的工具类
 * @note 说明
 * FIXME:启动大部分方法需要删除，使用ReflectUtil提供的相关方法
 */
public class HyReflectUtil extends ReflectUtil {

    /**
     * @param object 操作对象
     * @return java.util.List<java.lang.String>
     * @methodName getNameListBySelf
     * @author yjz
     * @description 仅获取自身类的字段名的集合
     * @date 2020/10/29
     * @note 修改说明
     */
    public static List<String> getNameListBySelf(Object object) {
        List<String> nameList = new ArrayList<>();
        for (Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            nameList.add(field.getName());
        }
        return nameList;
    }


    /**
     * @param object 操作对象
     * @return java.util.Set<java.lang.String>
     * @methodName getNameListByAll
     * @author yjz
     * @description 获取自身类（包含继承的父类属性）的字段名的集合
     * @date 2020/10/29
     * @note 返回值修改为set，实现去重，使用new ArrayList<String>(set),可转为list
     */
    public static Set<String> getNameListByAll(Object object) {
        Set<String> nameList = new HashSet<>();

        Class<?> clazz = object.getClass();
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                nameList.add(field.getName());
                System.out.println(field.getName());
            }
        }
        return nameList;
    }

    /**
     * @param object 操作对象
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @methodName getNameAndValueResult
     * @author yjz
     * @description 返回所有的值和其对应的值
     * @date 2020/10/29
     * @note 修改说明
     */
    public static Map<String, Object> getNameAndValueResult(Object object) {
        Map<String, Object> result = new HashMap<>(16);
        //循环获取属性值，以及其对应的值
        for (Field field : object.getClass().getDeclaredFields()) {
            //设置为可操作
            field.setAccessible(true);
            try {
                result.put(field.getName(), field.get(object));
            } catch (IllegalAccessException e) {
                //若执行异常，则填入空置
                result.put(field.getName(), null);
            }
        }
        return result;
    }

    /**
     * @param object     操作对象
     * @param methodName 方法名称
     * @param arg        参数列表
     * @return java.lang.Object
     * @description 调用指定方法并传入指定参数
     * @methodName executeMethod
     * @author yjz
     * @date 2021/01/28 17:42
     * @note 修改说明
     */
    public static Object executeMethod(Object object, String methodName, List<Object> arg) {
        if (object == null) {
            return null;
        }
        try {
            //根据方法名称获取方法，若存在多个同名方法，则返回第一个
            Method method = ReflectUtil.getMethodByName(object.getClass(), methodName);
            method.setAccessible(true);
            return method.invoke(object, new Object[]{arg.toArray()});
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * @param object 操作对象
     * @param name   字段名
     * @return java.lang.Object
     * @methodName getValueByName
     * @author yjz
     * @description 根据字段名，获取其对应的值
     * @date 2020/10/29
     * @note 修改说明
     */
    public static Object getValueByName(Object object, String name) {
        Field field = null;
        Class<?> clazz = object.getClass();

        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                field = clazz.getDeclaredField(name);
            } catch (Exception e) {
                //不操作
            }
        }
        if (field != null) {
            try {
                field.setAccessible(true);
                return field.get(object);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    /**
     * @param obj 待解析对象
     * @return java.lang.String
     * @description 获取当前实体类的类名（不含包路径）
     * @methodName getClassName
     * @author yjz
     * @date 2021/01/28 15:20
     * @note 修改说明
     */
    public static String getClassName(Object obj) {
        String name = obj.getClass().getName();
        return name.substring(name.lastIndexOf(".") + 1);
    }

}
