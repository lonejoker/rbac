package com.xiaobai.service.impl;

import com.xiaobai.entity.LoginUser;
import com.xiaobai.entity.SysMenu;
import com.xiaobai.mapper.SysMenuMapper;
import com.xiaobai.mapper.SysRoleMapper;
import com.xiaobai.mapper.SysRoleMenuMapper;
import com.xiaobai.service.LoginService;
import com.xiaobai.service.SysMenuService;
import com.xiaobai.utils.BeanCopyUtils;
import com.xiaobai.utils.JwtUtil;
import com.xiaobai.utils.R;
import com.xiaobai.utils.RedisCache;
import com.xiaobai.vo.LoginUserVo;
import com.xiaobai.vo.RoleVo;
import com.xiaobai.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author 终于白发始于青丝
 * @create 2022-03-18 下午 21:45
 * @Version 1.0
 * @ClassName LoginServiceImpl
 * @Description 类方法说明：
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;
    @Autowired
    private SysMenuService sysMenuService;
    @Autowired
    private RedisCache redisCache;

    @Override
    public R login(UserVo userVo) {
        // AuthenticationManager authenticationManager进行用户认证
        // 将用户登录的用户名和密码封装成Authentication对象
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userVo.getUserName(), userVo.getPassword());
        // 让authenticationManager帮助我们进行认证
        // authenticationManage会调用UserDetailsServiceImpl的方法进行校验，封装UserDetails返回
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        // 判断认证是否成功
        if (Objects.isNull(authenticate)) {
            throw new RuntimeException("认证失败，登录失败");
        }
        // 因为debugger已经知道是loginuser类型了
        // 认证通过，使用userid生成jwt，并存入ResponseResult返回
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        // 因为jwt所需要的是一个string类型
        String userId = loginUser.getSysUser().getId().toString();

        // 查询用户权限及对应的菜单
        // 用户用户角色标识
        String roleName = loginUser.getRoleName();
        List<SysMenu> roleMenu = getRoleMenu(roleName);
        List<RoleVo> roleVos = BeanCopyUtils.copyListBean(roleMenu, RoleVo.class);

        String jwt = JwtUtil.createJWT(userId);
        Map<Object, Object> map = new HashMap<>();
        map.put("token", jwt);
        map.put("roles",roleVos);
        map.put("user", BeanCopyUtils.copyObject(loginUser.getSysUser(), LoginUserVo.class));
        // 将完整的用户信息存入redis，userId作为key
        redisCache.setCacheObject("rbac:" + userId, loginUser);
        return R.successCmd(map);
    }

    /**
     * @author 终于白发始于青丝
     * @Methodname getRoleMenu
     * @Description 类方法说明：用户角色标识
     * @Return 返回值：java.util.List<com.xiaobai.entity.SysMenu>
     * @Params java.lang.String roleName
     * @Date 2022/3/18 下午 22:53
     */
    private List<SysMenu> getRoleMenu(String roleName) {
        // 根据用户角色标识查询出角色id
        Long roleId = sysRoleMapper.selectRoleIdByRoleName(roleName);
        // 查询出当前角色的所有菜单id集合
        List<Long> menus = sysRoleMenuMapper.selectRolesByRoleId(roleId);
        // 查询出系统所有菜单
        List<SysMenu> menuAll = sysMenuService.findAllMenus("");
        // 创建一个筛选后的菜单集合
        List<SysMenu> list = new ArrayList<>();
        // 筛选当前用户菜单
        for (SysMenu sysMenu : menuAll) {
            // 父级菜单
            if (menus.contains(sysMenu.getId())) {
                list.add(sysMenu);
            }
            // 子级菜单
            List<SysMenu> children = sysMenu.getChildren();
            // removeIf() 移除children里面不在authoritys集合中的元素
            children.removeIf(child -> !menus.contains(child.getId()));

        }
        return list;
    }

    @Override
    public R logout() {
        // 获取SecurityContextHolder中的用户id
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long userid = loginUser.getSysUser().getId();
        // 删除redis的值，拼接key，需要用到用户id
        redisCache.deleteObject("rbac:" + userid);
        return R.successCm();
    }
}
