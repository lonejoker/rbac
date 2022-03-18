package com.xiaobai.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author 终于白发始于青丝
 * @create 2022-03-18 下午 21:35
 * @Version 1.0
 * @ClassName RedisCache
 * @Description 类方法说明：
 */
@SuppressWarnings(value = {"unchecked", "rawtypes"})
@Component
public class RedisCache {
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * @author 终于白发始于青丝
     * @Methodname setCacheObject
     * @Description 类方法说明：缓存基本对象：Integer，String，实体类等
     * @Return 返回值：void
     * @Params java.lang.String key：缓存的键值
     * @Params T value：缓存的值
     * @Date 2022/2/3 下午 23:30
     */
    public <T> void setCacheObject(final String key, final T value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * @author 终于白发始于青丝
     * @Methodname setCacheObject
     * @Description 类方法说明：缓存基本对象：Integer，String，实体类等
     * @Return 返回值：void
     * @Params java.lang.String key：缓存的键值
     * @Params T value：缓存的值
     * @Params java.lang.Integer timeout：时间
     * @Params java.util.concurrent.TimeUnit timeUnit：时间颗粒度
     * @Date 2022/2/3 下午 23:32
     */
    public <T> void setCacheObject(final String key, final T value, final Integer timeout, final TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    /**
     * @author 终于白发始于青丝
     * @Methodname expire
     * @Description 类方法说明：设置有效时间
     * @Return 返回值：boolean
     * @Params java.lang.String key：reids键
     * @Params long timeout：超时时间
     * @Date 2022/2/3 下午 23:34
     */
    public boolean expire(final String key, final long timeout) {
        return expire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * @author 终于白发始于青丝
     * @Methodname expire
     * @Description 类方法说明：设置有效时间
     * @Return 返回值：boolean
     * @Params java.lang.String key：reids键
     * @Params long timeout：超时时间
     * @Params TimeUnit unit：时间单位
     * @Date 2022/2/3 下午 23:34
     */
    public boolean expire(final String key, final long timeout, final TimeUnit unit) {
        return redisTemplate.expire(key, timeout, unit);
    }

    /**
     * @author 终于白发始于青丝
     * @Methodname getCacheObject
     * @Description 类方法说明：获取缓存的基本对象
     * @Return 返回值：T：缓存键值对应的数据
     * @Params java.lang.String key：缓存键值
     * @Date 2022/2/3 下午 23:38
     */
    public <T> T getCacheObject(final String key) {
        ValueOperations<String, T> operation = redisTemplate.opsForValue();
        return operation.get(key);
    }

    /**
     * @author 终于白发始于青丝
     * @Methodname deleteObject
     * @Description 类方法说明：删除单个对象
     * @Return 返回值：boolean
     * @Params java.lang.String key
     * @Date 2022/2/3 下午 23:40
     */
    public boolean deleteObject(final String key) {
        return redisTemplate.delete(key);
    }

    /**
     * @author 终于白发始于青丝
     * @Methodname deleteObject
     * @Description 类方法说明：删除集合对象
     * @Return 返回值：long
     * @Params java.util.Collection collection
     * @Date 2022/2/3 下午 23:41
     */
    public long deleteObject(final Collection collection) {
        return redisTemplate.delete(collection);
    }

    /**
     * @author 终于白发始于青丝
     * @Methodname setCacheList
     * @Description 类方法说明：缓存List数据
     * @Return 返回值：long
     * @Params java.lang.String key：缓存的键值
     * @Params java.util.List<T> dataList：待缓存的list数据
     * @Date 2022/2/3 下午 23:42
     */
    public <T> long setCacheList(final String key, final List<T> dataList) {
        Long count = redisTemplate.opsForList().rightPushAll(key, dataList);
        return count == null ? 0 : count;
    }

    /**
     * @author 终于白发始于青丝
     * @Methodname getCacheList
     * @Description 类方法说明：获得缓存的list对象
     * @Return 返回值：java.util.List<T>
     * @Params java.lang.String key：缓存的键值
     * @Date 2022/2/3 下午 23:44
     */
    public <T> List<T> getCacheList(final String key) {
        return redisTemplate.opsForList().range(key, 0, -1);
    }

    public <T> BoundSetOperations<String, T> setCacheList(final String key, final Set<T> dataSet) {
        BoundSetOperations<String, T> setOperation = redisTemplate.boundSetOps(key);
        Iterator<T> it = dataSet.iterator();
        while (it.hasNext()) {
            setOperation.add(it.next());
        }
        return setOperation;
    }

    /**
     * @author 终于白发始于青丝
     * @Methodname getCacheSet
     * @Description 类方法说明：获得缓存的set
     * @Return 返回值：java.util.Set<T>
     * @Params java.lang.String key
     * @Date 2022/2/3 下午 23:48
     */
    public <T> Set<T> getCacheSet(final String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * @author 终于白发始于青丝
     * @Methodname getCacheMap
     * @Description 类方法说明：获得缓存的map
     * @Return 返回值：Map<String,T>
     * @Params java.lang.String key
     * @Date 2022/2/3 下午 23:49
     */
    public <T> Map<String, T> getCacheMap(final String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * @author 终于白发始于青丝
     * @Methodname setCacheMapValue
     * @Description 类方法说明：往Hash中存入数据
     * @Return 返回值：void
     * @Params java.lang.String key：redis键
     * @Params java.lang.String hkey：hash键
     * @Params T value：值
     * @Date 2022/2/3 下午 23:51
     */
    public <T> void setCacheMapValue(final String key, final String hkey, final T value) {
        redisTemplate.opsForHash().put(key, hkey, value);
    }

    /**
     * @author 终于白发始于青丝
     * @Methodname setCacheMapValue
     * @Description 类方法说明：获取Hash的数据
     * @Return 返回值：void
     * @Params java.lang.String key：redis键
     * @Params java.lang.String hkey：hash键
     * @Date 2022/2/3 下午 23:51
     */
    public <T> T getCacheMapValue(final String key, final String hkey) {
        HashOperations<String, String, T> opsForHash = redisTemplate.opsForHash();
        return opsForHash.get(key, hkey);
    }

    /**
     * @author 终于白发始于青丝
     * @Methodname delCacheMapValue
     * @Description 类方法说明：删除Hash的数据
     * @Return 返回值：void
     * @Params java.lang.String key
     * @Params java.lang.String hkey
     * @Date 2022/2/3 下午 23:55
     */
    public void delCacheMapValue(final String key, final String hkey) {
        HashOperations hashOperations = redisTemplate.opsForHash();
        hashOperations.delete(key, hkey);
    }

    /**
     * @author 终于白发始于青丝
     * @Methodname getMultiCacheMapValue
     * @Description 类方法说明：获取多个Hash的数据
     * @Return 返回值：java.util.List<T>
     * @Params java.lang.String key：redis键
     * @Params java.util.Collection<java.lang.Object> hkeys：Hash键集合
     * @Date 2022/2/3 下午 23:57
     */
    public <T> List<T> getMultiCacheMapValue(final String key, final Collection<Object> hkeys) {
        return redisTemplate.opsForHash().multiGet(key, hkeys);
    }

    /**
     * @author 终于白发始于青丝
     * @Methodname keys
     * @Description 类方法说明：获取缓存的基本对象列表
     * @Return 返回值：java.util.Collection<java.lang.String>：对象列表
     * @Params java.lang.String pattern：字符串前缀
     * @Date 2022/2/3 下午 23:58
     */
    public Collection<String> keys(final String pattern) {
        return redisTemplate.keys(pattern);
    }
}