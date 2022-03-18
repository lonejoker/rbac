package com.xiaobai.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 终于白发始于青丝
 * @create 2022-03-18 下午 21:39
 * @Version 1.0
 * @ClassName LoginUser
 * @Description 类方法说明：
 */
@Data
@NoArgsConstructor
public class LoginUser implements UserDetails {

    private SysUser sysUser;
    private List<String> permissions;

    public LoginUser(SysUser sysUser, List<String> permissions) {
        this.sysUser = sysUser;
        this.permissions = permissions;
    }

    // 不需要把这个成员变量序列化存储到redis中
    @JSONField(serialize = false)
    private List<SimpleGrantedAuthority> authorities;

    /**
     * @author 终于白发始于青丝
     * @Classname LoginUser
     * @Description 类方法说明：返回权限信息
     * @Date 2022/3/14 上午 8:33
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (authorities != null) {
            return authorities;
        }
        // 把permissions类型的权限信息封装成SimpleGrantedAuthority对象
        // 方式一
        // authorities = new ArrayList<>();
        //for (String permission : permissions) {
        //    SimpleGrantedAuthority authority = new SimpleGrantedAuthority(permission);
        //    list.add(authority);
        //}
        //return authorities;
        // 方式二
        authorities = permissions.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        return authorities;
    }

    /**
     * @author 终于白发始于青丝
     * @Classname LoginUser
     * @Description 类方法说明：获取用户名
     * @Date 2022/3/14 上午 8:33
     */
    @Override
    public String getPassword() {
        return sysUser.getPassword();
    }

    /**
     * @author 终于白发始于青丝
     * @Classname LoginUser
     * @Description 类方法说明：获取密码
     * @Date 2022/3/14 上午 8:33
     */
    @Override
    public String getUsername() {
        return sysUser.getPassword();
    }

    /**
     * @author 终于白发始于青丝
     * @Classname LoginUser
     * @Description 类方法说明：帐户是否未过期
     * @Date 2022/3/14 上午 8:34
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * @author 终于白发始于青丝
     * @Classname LoginUser
     * @Description 类方法说明：帐户是否未锁定
     * @Date 2022/3/14 上午 8:35
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * @author 终于白发始于青丝
     * @Classname LoginUser
     * @Description 类方法说明：凭据是否未过期
     * @Date 2022/3/14 上午 8:35
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * @author 终于白发始于青丝
     * @Classname LoginUser
     * @Description 类方法说明：是否可用
     * @Date 2022/3/14 上午 8:34
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
