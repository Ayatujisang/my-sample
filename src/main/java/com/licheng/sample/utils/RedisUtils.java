package com.licheng.sample.utils;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/*
 * @author LiCheng
 * @date  2024-02-15 20:42:05
 *
 * redis工具类
 */

@Component
@SuppressWarnings("all")
public class RedisUtils {

    private static RedisTemplate getRedisTemplate() {
        RedisTemplate redisTemplate = UtilContext.getBean("redisTemplate", RedisTemplate.class);
        return redisTemplate;
    }

    /**
     * 存放有过期时间为分钟的键值对
     *
     * @param keyName
     * @param value
     */
    public static void setForExpMinute(Object keyName, Object value, int expTime) {
        getRedisTemplate().opsForValue().set(keyName, value, expTime, TimeUnit.MINUTES);
    }

    /**
     * 存放有过期时间为秒的键值对
     *
     * @param keyName
     * @param value
     */
    public static void setForExpSecond(Object keyName, Object value, int expTime) {
        getRedisTemplate().opsForValue().set(keyName, value, expTime, TimeUnit.SECONDS);
    }

    public static Object get(Object keyName) {
        getRedisTemplate().opsForValue().get(keyName);
        return getRedisTemplate().opsForValue().get(keyName);
    }

    public static boolean hasKey(Object keyName) {
        return getRedisTemplate().hasKey(keyName);
    }

    /**
     * 删除key
     *
     * @param keyName
     */
    public static void delete(Object keyName) {
        getRedisTemplate().delete(keyName);
    }

    /**
     * 自增key 并返回
     *
     * @param keyName
     * @return
     */
    public Long incrKey(String keyName) {
        try {
            Long increment = getRedisTemplate().opsForValue().increment(keyName);
            return increment;
        } catch (Exception e) {
            throw new RuntimeException("建名:[" + keyName + "]无法自增");
        }

    }


}
