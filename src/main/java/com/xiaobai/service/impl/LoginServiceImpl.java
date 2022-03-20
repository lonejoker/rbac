package com.xiaobai.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaobai.entity.*;
import com.xiaobai.enumeration.RInfo;
import com.xiaobai.enums.MyEnum;
import com.xiaobai.mapper.SysRoleMapper;
import com.xiaobai.mapper.SysRoleMenuMapper;
import com.xiaobai.mapper.SysUserMapper;
import com.xiaobai.mapper.SysUserRoleMapper;
import com.xiaobai.service.LoginService;
import com.xiaobai.service.SysMenuService;
import com.xiaobai.utils.*;
import com.xiaobai.vo.LoginUserVo;
import com.xiaobai.vo.RoleVo;
import com.xiaobai.vo.UserRegistryVo;
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
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;
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
        map.put("roles", roleVos);
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

    @Override
    public R roleLoginList(String roleName) {
        List<SysMenu> roleMenu = getRoleMenu(roleName);
        List<RoleVo> roleVos = BeanCopyUtils.copyListBean(roleMenu, RoleVo.class);
        Map<String, Object> map = new HashMap<>();
        //String path = "/home";
        //String component = "() => import('@/views/Home.vue')";
        //String redirect = "/home/index";
        //map.put("path",path);
        //map.put("component",component);
        //map.put("redirect",redirect);
        map.put("role", roleVos);
        return R.successCmd(map);
    }

    @Override
    public R registry(UserRegistryVo userRegistryVo) throws IllegalAccessException {
        if (!EntityNull.reflect(userRegistryVo)) {
            //SysUser sysUser = insertUser(userRegistryVo);
            insertInfo(userRegistryVo);
            return R.successCm();
        }
        return R.error(RInfo.ERROR4xx.getCode(), "请认真填写内容");
    }

    private void insertInfo(UserRegistryVo userRegistryVo) {
        SysUser sysUser = insertUser(userRegistryVo);
        sysUserMapper.insert(sysUser);
        insertUserRole();
    }


    private SysUser insertUser(UserRegistryVo userRegistryVo) {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.select("id")
                .orderByDesc("id")
                .last("limit 1");
        Long id = sysUserMapper.selectOne(wrapper).getId();
        SysUser sysUser = new SysUser();
        sysUser.setUserName(userRegistryVo.getUserName());
        sysUser.setPassword(userRegistryVo.getPassword());
        sysUser.setNickName(MyEnum.ROLE_NICK_NAME + id);
        sysUser.setEmail(userRegistryVo.getEmail());
        sysUser.setPhone(userRegistryVo.getPhone());
        sysUser.setSex(userRegistryVo.getSex());
        sysUser.setRoleName(MyEnum.ROLE_USER + id);
        sysUser.setCreateTime(DateUtil.date());
        sysUser.setUpdateTime(DateUtil.date());
        insertRole(MyEnum.ROLE_USER + id);
        return sysUser;
    }

    private void insertUserRole() {
        QueryWrapper<SysUser> wrapper1 = new QueryWrapper<>();
        wrapper1.select("id")
                .orderByDesc("id")
                .last("limit 1");
        Long id1 = sysUserMapper.selectOne(wrapper1).getId();
        QueryWrapper<SysRole> wrapper2 = new QueryWrapper<>();
        wrapper2.select("id")
                .orderByDesc("id")
                .last("limit 1");
        Long id2 = sysRoleMapper.selectOne(wrapper2).getId();
        SysUserRole sysUserRole = new SysUserRole();
        sysUserRole.setUserId(id1);
        sysUserRole.setRoleId(id2);
        sysUserRoleMapper.insert(sysUserRole);
        List<Long> list = new ArrayList<>();
        list.add(MyEnum.DEFAULT_MENU_ID);
        for (int i = 0; i < list.size(); i++) {
            SysRoleMenu sysRoleMenu = new SysRoleMenu();
            sysRoleMenu.setRoleId(id2);
            sysRoleMenu.setMenuId(list.get(i));
            sysRoleMenuMapper.insert(sysRoleMenu);
        }
    }

    private void insertRole(String roleName) {
        QueryWrapper<SysRole> wrapper = new QueryWrapper<>();
        wrapper.select("id")
                .orderByDesc("id")
                .last("limit 1");
        Long id = sysRoleMapper.selectOne(wrapper).getId();
        SysRole sysRole = new SysRole();
        sysRole.setRoleName(MyEnum.ROLE_NAME_REMARK + id);
        sysRole.setRoleKey(MyEnum.ROLE_KEY + id);
        sysRole.setCreateTime(DateUtil.date());
        sysRole.setUpdateTime(DateUtil.date());
        sysRole.setRemark(MyEnum.ROLE_NAME_REMARK + id);
        sysRole.setFlag(roleName);
        sysRoleMapper.insert(sysRole);
    }
}
