package com.xiaobai.mapper;

import com.xiaobai.entity.SysUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

/**
 * @author 终于白发始于青丝
 * @create 2022-03-18 下午 21:36
 * @Version 1.0
 * @ClassName TestDemo
 * @Description 类方法说明：
 */
@SpringBootTest
public class TestDemo {
    @Autowired
    private SysUserMapper sysUserMapper;

    @Test
    public void testUser() {
        List<SysUser> users = sysUserMapper.selectList(null);
        System.out.println(users);
    }

    //    测试BCryptPasswordEncoder
    @Test
    public void testPassword(){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        // 加密
        String encode = passwordEncoder.encode("1");
        System.out.println(encode);
        // 校验
        boolean matches = passwordEncoder.matches("1", "$2a$10$emTf.LAwSJ9Yb9xJMH2BUuRGtZQCbDxOq7Ul0NCHEkxfb4vCSNpwW");
        System.out.println(matches);
    }
}