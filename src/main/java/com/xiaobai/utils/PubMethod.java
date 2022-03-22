package com.xiaobai.utils;

import java.lang.reflect.Field;

/**
 * @author 终于白发始于青丝
 * @create 2022-03-19 下午 16:51
 * @Version 1.0
 * @ClassName PubMethod
 * @Description 类方法说明：
 */
// https://www.cnblogs.com/ynxrsoft/p/7444453.html
public class PubMethod {
    public static Boolean reflect(Object obj) throws IllegalAccessException {
        Class voClass = obj.getClass();
        Field[] fields = voClass.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            field.setAccessible(true);
            if (fields[i].get(obj).toString().isEmpty() || "".equals(fields[i].get(obj).toString()) || obj.toString().length() != 5) {
                return false;
            }
        }
        return true;
    }
}
