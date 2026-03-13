package com.blog.common.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Redis工具类
 */
@Component
public class RedisUtils {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 设置token
     * @param token token值
     * @param userId 用户ID
     * @param expireTime 过期时间（秒）
     */
    public void setToken(String token, Long userId, Long expireTime) {
        redisTemplate.opsForValue().set(token, userId, expireTime, TimeUnit.SECONDS);
    }

    /**
     * 根据token获取用户ID
     * @param token token值
     * @return 用户ID
     */
    public Long getUserIdByToken(String token) {
        Object userId = redisTemplate.opsForValue().get(token);
        return userId != null ? Long.valueOf(userId.toString()) : null;
    }

    /**
     * 删除token
     * @param token token值
     */
    public void deleteToken(String token) {
        redisTemplate.delete(token);
    }

    /**
     * 判断token是否存在
     * @param token token值
     * @return 是否存在
     */
    public boolean hasToken(String token) {
        System.out.println("redisTemplate.hasKey(token) = " + redisTemplate.hasKey(token));
        // 打印出dis中的 key
        System.out.println("redisTemplate.keys(\"*\") = " + redisTemplate.keys("*"));
        return redisTemplate.hasKey(token);
    }

    /**
     * 设置字符串值
     * @param key 键
     * @param value 值
     * @param expireTime 过期时间（秒），-1表示永不过期
     */
    public void setString(String key, String value, Long expireTime) {
        if (expireTime == null || expireTime < 0) {
            // 永不过期
            redisTemplate.opsForValue().set(key, value);
        } else {
            // 设置过期时间
            redisTemplate.opsForValue().set(key, value, expireTime, TimeUnit.SECONDS);
        }
    }

    /**
     * 获取字符串值
     * @param key 键
     * @return 值
     */
    public String getString(String key) {
        Object value = redisTemplate.opsForValue().get(key);
        return value != null ? value.toString() : null;
    }
}