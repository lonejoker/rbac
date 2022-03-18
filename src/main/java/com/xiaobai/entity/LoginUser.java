package com.xiaobai.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @author 终于白发始于青丝
 * @create 2022-03-18 下午 21:39
 * @Version 1.0
 * @ClassName LoginUser
 * @Description 类方法说明：
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginUser implements UserDetails {

    private SysUser sysUser;

    /**
     * @author 终于白发始于青丝
     * @Classname LoginUser
     * @Description 类方法说明：返回权限信息
     * @Date 2022/3/14 上午 8:33
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
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
