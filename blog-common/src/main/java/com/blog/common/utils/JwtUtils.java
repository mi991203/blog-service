package com.blog.common.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT工具类
 */
@Component
public class JwtUtils {

    // 注入RedisUtils
    @Autowired
    private RedisUtils redisUtils;

    // 静态实例，用于静态方法调用
    private static RedisUtils staticRedisUtils;

    @PostConstruct
    public void init() {
        staticRedisUtils = redisUtils;
    }

    /**
     * 密钥
     */
    private static final String SECRET = "blog-secret-key-2025-songtao";

    /**
     * 过期时间（7天）
     */
    private static final long EXPIRATION = 7 * 24 * 60 * 60 * 1000;

    /**
     * 生成token
     */
    public static String generateToken(Long userId, String mobile, String username) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("mobile", mobile);
        claims.put("username", username);

        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();

        // 将token存储到Redis中
        staticRedisUtils.setToken(token, userId, EXPIRATION / 1000);

        return token;
    }

    /**
     * 解析token
     */
    public static Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 从token中获取用户ID
     */
    public static Long getUserId(String token) {
        // 首先从Redis中获取
        Long userId = staticRedisUtils.getUserIdByToken(token);
        if (userId != null) {
            return userId;
        }

        // 如果Redis中没有，则从token中解析
        Claims claims = parseToken(token);
        return Long.valueOf(claims.get("userId").toString());
    }

    /**
     * 从token中获取用户名
     */
    public static String getUsername(String token) {
        Claims claims = parseToken(token);
        return claims.getSubject();
    }

    /**
     * 验证token是否过期
     */
    public static boolean isTokenExpired(String token) {
        try {
            Claims claims = parseToken(token);
            Date expiration = claims.getExpiration();
            return expiration.before(new Date());
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * 验证token是否有效
     */
    public static boolean validateToken(String token) {
        try {
            // 检查Redis中是否存在该token
            if (!staticRedisUtils.hasToken(token)) {
                return false;
            }

            parseToken(token);
            return !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 删除token
     */
    public static void removeToken(String token) {
        staticRedisUtils.deleteToken(token);
    }
}