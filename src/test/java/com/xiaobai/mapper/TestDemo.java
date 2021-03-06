package com.xiaobai.mapper;

import com.xiaobai.entity.LoginUser;
import com.xiaobai.entity.SysUser;
import com.xiaobai.utils.RedisCache;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public void testPassword() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        // 加密
        String encode = passwordEncoder.encode("1");
        System.out.println(encode);
        // 校验
        boolean matches = passwordEncoder.matches("1", "$2a$10$emTf.LAwSJ9Yb9xJMH2BUuRGtZQCbDxOq7Ul0NCHEkxfb4vCSNpwW");
        System.out.println(matches);
    }

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Test
    public void testMenu() {
        List<String> list = sysMenuMapper.selectByUserId(1L);
        System.out.println(list);
    }

    @Test
    public void testlength() {
        String length = "";
        System.out.println(length.length());
    }

    @Test
    public void test() {
        Map<Object, Object> map = new HashMap<>();
        map.put("12", 1);
        map.put("2", 2);
        map.put("3", 3);
        int num = 3;
        for (Map.Entry<Object, Object> keySet : map.entrySet()) {
            System.out.println(keySet.getValue() + "---" + keySet.getKey());
        }
    }

    @Value("${server.url}")
    private String url;
    @Value("${server.port}")
    private Integer port;

    @Test
    public void testurl() {
        System.out.println(url + ":" + port);
    }
    @Autowired
    private RedisCache redisCache;
    @Test
    public void testid() {
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long userid = loginUser.getSysUser().getId();
        // 删除redis的值，拼接key，需要用到用户id
        Object cacheObject = redisCache.getCacheObject("rbac:" + userid);
        System.out.println(cacheObject);
    }
}