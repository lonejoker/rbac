package com.xiaobai.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 终于白发始于青丝
 * @create 2022-03-18 下午 22:24
 * @Version 1.0
 * @ClassName CorsConfig
 * @Description 类方法说明：
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 设置允许跨域的路径
        registry.addMapping("/**")
                // 设置允许跨域请求的域名
                .allowedOriginPatterns("*")
                // 是否允许cookie
                .allowCredentials(true)
                // 设置允许的请求方式
                .allowedMethods("GET","POST","PUT","DELETE","OPTIONS")
                // 设置允许的header属性
                .allowedHeaders("*");
        // 跨域允许时间
        //.maxAge(3600);
    }
}