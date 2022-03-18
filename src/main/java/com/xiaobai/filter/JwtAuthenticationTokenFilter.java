package com.xiaobai.filter;

import com.xiaobai.entity.LoginUser;
import com.xiaobai.utils.JwtUtil;
import com.xiaobai.utils.RedisCache;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.lang.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @author 终于白发始于青丝
 * @create 2022-03-18 下午 21:55
 * @Version 1.0
 * @ClassName JwtAuthenticationTokenFilter
 * @Description 类方法说明：
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    public RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 获取token，请求头名：token
        String token = request.getHeader("token");
        if (!Strings.hasText(token)) {
            // 放行
            filterChain.doFilter(request, response);
            return;
        }
        // 解析token
        String userId;
        try {
            Claims claims = JwtUtil.parseJWT(token);
            userId = claims.getSubject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("token非法");
        }
        // 从redis中获取用户信息
        String rediskey = "rbac:" + userId;
        LoginUser loginuser = redisCache.getCacheObject(rediskey);
        if (Objects.isNull(loginuser)){
            throw new RuntimeException("用户未登录");
        }
        // 存入SecurityContextHolder，需要的是一个authentication类型
        // TODO 获取权限信息，封装Authentication
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginuser, null, loginuser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        // 最后放行
        filterChain.doFilter(request,response);
    }
}
