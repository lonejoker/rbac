package com.xiaobai.utils;

import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 终于白发始于青丝
 * @create 2022-02-06 下午 20:32
 * @program BlogProject
 * @Version 1.0
 * @ClassName BeanCopyUtils
 */
public class BeanCopyUtils {
    private BeanCopyUtils() {
    }

    /**
     * @author 终于白发始于青丝
     * @Methodname copyOneBean
     * @Description 类方法说明：属性拷贝，单一
     * @Return 返回值：java.lang.Object
     * @Params java.lang.Object source
     * @Params java.lang.Class clazz
     * @Date 2022/2/6 下午 20:33
     */
    public static <V> V copyOneBean(Object source, Class<V> clazz) {
        // 创建目标对象
        V result = null;
        try {
            result = clazz.newInstance();
            // 实现属性拷贝
            BeanUtils.copyProperties(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 返回结果
        return result;
    }

    /**
     * @author 终于白发始于青丝
     * @Methodname copyListBean
     * @Description 类方法说明：属性拷贝，list
     * @Return 返回值：java.util.List<V>
     * @Params java.util.List<java.lang.Object> list
     * @Params java.lang.Class<V> clazz
     * @Date 2022/2/6 下午 20:37
     */
    public static <O, V> List<V> copyListBean(List<O> list, Class<V> clazz) {
        return list.stream().map(o -> copyOneBean(o, clazz)).collect(Collectors.toList());
    }

    public static <T> T copyObject(Object source, Class<T> target) {
        T temp = null;
        try {
            temp = target.newInstance();
            if (null != source) {
                BeanUtils.copyProperties(source, temp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return temp;
    }

}
