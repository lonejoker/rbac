package com.xiaobai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaobai.entity.LoginUser;
import com.xiaobai.entity.SysUser;
import com.xiaobai.mapper.SysMenuMapper;
import com.xiaobai.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author 终于白发始于青丝
 * @create 2022-03-18 下午 21:38
 * @Version 1.0
 * @ClassName UserDetailsServiceImpl
 * @Description 类方法说明：
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 查询用户信息
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        // 根据用户名查询用户信息
        wrapper.eq(SysUser::getUserName, username);
        SysUser user = sysUserMapper.selectOne(wrapper);
        // 如果没有查询到用户信息，为null就抛出异常
        if (Objects.isNull(user)) {
            throw new RuntimeException("用户名或者密码错误");
        }
        // todo 查询对应的权限信息
        // 因为UserDetails是一个接口，封装的话要创建他的实现类
        // 将数据封装成UserDetails返回
        //List<String> list = new ArrayList<>(Arrays.asList("test", "demo"));
        List<String> list = sysMenuMapper.selectByUserId(user.getId());
        return new LoginUser(user, list);
    }
}
