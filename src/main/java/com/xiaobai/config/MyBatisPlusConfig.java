package com.xiaobai.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 终于白发始于青丝
 * @create 2022-02-11 上午 11:09
 * @program Interface-springboot
 * @Version 1.0
 * @ClassName MyBatisPlusConfig
 * @Description 类方法说明：myabtisplus配置
 */
@Configuration
public class MyBatisPlusConfig {
    /**
     * @author 终于白发始于青丝
     * @Methodname mybatisPlusInterceptor
     * @Description 类方法说明：分页配置
     * @Return 返回值：com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor
     * @Params
     * @Date 2022/2/19 下午 22:49
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor plusInterceptor = new MybatisPlusInterceptor();
        plusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return plusInterceptor;
    }
}