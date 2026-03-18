package com.blog.common.interceptor;

import com.blog.common.constant.Constants;
import com.blog.common.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * JWT拦截器
 */
@Slf4j
@Component
public class JwtInterceptor implements HandlerInterceptor {
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        String method = request.getMethod();
        
        log.debug("JWT拦截器处理请求：{}，方法：{}", requestURI, method);
        
        // 放行OPTIONS请求（跨域预检请求）
        if ("OPTIONS".equalsIgnoreCase(method)) {
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }
        
        // 从请求头获取token
        String token = request.getHeader(Constants.TOKEN_HEADER);
        
        if (!StringUtils.hasText(token)) {
            log.warn("请求未携带token，URI：{}", requestURI);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"code\":401,\"message\":\"未登录或token已过期\"}");
            return false;
        }
        
        // 去除Bearer前缀
        if (token.startsWith(Constants.TOKEN_PREFIX)) {
            token = token.substring(Constants.TOKEN_PREFIX.length());
        }
        System.out.println("user用户token:"+token);
        // 验证token
        if (!JwtUtils.validateToken(token)) {
            log.warn("token验证失败，URI：{}", requestURI);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"code\":401,\"message\":\"token无效或已过期\"}");
            return false;
        }
        
        // 将用户信息存入request
        Long userId = JwtUtils.getUserId(token);
        String username = JwtUtils.getUsername(token);
        request.setAttribute(Constants.USER_ID, userId);
        request.setAttribute(Constants.USERNAME, username);
        
        log.debug("JWT验证通过，用户ID：{}，用户名：{}", userId, username);
        return true;
    }
}