package com.xiaobai.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.xiaobai.entity.SysRole;
import com.xiaobai.entity.SysRoleMenu;
import com.xiaobai.entity.SysUser;
import com.xiaobai.entity.SysUserRole;
import com.xiaobai.enums.MyEnum;
import com.xiaobai.mapper.*;
import com.xiaobai.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaobai.utils.PubMethod;
import com.xiaobai.vo.UserInfo;
import com.xiaobai.vo.UserRegistryVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaobai.enumeration.RInfo;
import com.xiaobai.utils.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 终于白发始于青丝
 * @create 2022-03-18 21:18:44
 * @Version 1.0
 * @ClassName SysUserServiceImpl
 * @Description 类方法说明：
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysMenuMapper sysMenuMapper;
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public R sysUserPage(Integer pageNum, Integer pageSize, String nickName) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getDelFlag, 0)
                .like(SysUser::getNickName, nickName)
                .orderByAsc(SysUser::getCreateTime);
        Page<SysUser> page = new Page<>(pageNum, pageSize);
        page(page, wrapper);
        return R.successCmd(page);
    }

    @Override
    public R sysUserPageAll(Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getDelFlag, 0)
                .orderByAsc(SysUser::getCreateTime);
        Page<SysUser> page = new Page<>(pageNum - 1, pageSize);
        page(page, wrapper);
        return R.successCmd(page);
    }

    @Override
    public R getPages() {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("delFlag", 0).orderByDesc("createTime");
        return R.successCmd(sysUserMapper.selectList(wrapper));
    }

    @Override
    public R addSysUser(SysUser sysUser) {
        // 判断是否为空

        SysUser v = new SysUser();
        // v.set赋值
        sysUserMapper.insert(v);
        return R.successCm();
    }

    @Override
    public R delSysUser(Long id) {
        SysUser sysUser = sysUserMapper.selectById(id);
        sysUser.setDelFlag(1);
        return R.successCmd(sysUserMapper.updateById(sysUser));
    }

    @Override
    public R delSysUserAll(List<Long> ids) {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.in("id", ids);
        for (SysUser sysUser : sysUserMapper.selectList(wrapper)) {
            sysUser.setDelFlag(1);
            sysUserMapper.updateById(sysUser);
        }
        return R.successCm();
    }

    @Override
    public R updateSysUser(UserInfo userInfo) {
        SysUser sysUser = new SysUser();
        if (StringUtils.isNotEmpty(userInfo.getNickName())) {
            sysUser.setNickName(userInfo.getNickName());
        }
        if (StringUtils.isNotEmpty(userInfo.getEmail())) {
            sysUser.setEmail(userInfo.getEmail());
        }
        if (StringUtils.isNotEmpty(userInfo.getPassword())) {
            sysUser.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        }
        if (StringUtils.isNotEmpty(userInfo.getPhone())) {
            sysUser.setPhone(userInfo.getPhone());
        }
        if (StringUtils.isNotEmpty(userInfo.getRoleName())) {
            sysUser.setRoleName(userInfo.getRoleName());
        }
        if (StringUtils.isNotEmpty(userInfo.getUserAvatar())) {
            sysUser.setUserAvatar(userInfo.getUserAvatar());
        }
        sysUser.setId(userInfo.getId());
        sysUser.setUpdateTime(DateUtil.date());
        sysUserMapper.updateById(sysUser);
        return R.successCm();
    }

    @Override
    public R getRole(String roleName) {
        return R.successCmd(getRoleMenus(roleName));
    }

    @Override
    public R getRolesName() {
        List<String> rolesNameList = getRolesNames();
        return R.successCmd(rolesNameList);
    }

    @Override
    public R registry(UserRegistryVo userRegistryVo) throws IllegalAccessException {
        if (!PubMethod.reflect(userRegistryVo)) {
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
        List<String> list = getRolesNames();
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.select("id")
                .orderByDesc("id")
                .last("limit 1");
        Long id = sysUserMapper.selectOne(wrapper).getId();
        SysUser sysUser = new SysUser();
        sysUser.setUserName(userRegistryVo.getUserName());
        sysUser.setPassword(passwordEncoder.encode(userRegistryVo.getPassword()));
        sysUser.setNickName(MyEnum.ROLE_NICK_NAME + id);
        sysUser.setEmail(userRegistryVo.getEmail());
        sysUser.setPhone(userRegistryVo.getPhone());
        sysUser.setSex(userRegistryVo.getRadio());
        if (list.contains(userRegistryVo.getRoleName())) {
            sysUser.setRoleName(userRegistryVo.getRoleName() + MyEnum.NEW);
        } else if (StringUtils.isNotEmpty(userRegistryVo.getRoleName())) {
            sysUser.setRoleName(userRegistryVo.getRoleName());
        } else {
            sysUser.setRoleName(MyEnum.ROLE_USER + id);
        }
        sysUser.setCreateTime(DateUtil.date());
        sysUser.setUpdateTime(DateUtil.date());
        if (list.contains(userRegistryVo.getRoleName())) {
            insertRole(userRegistryVo.getRoleName() + MyEnum.NEW);
        } else if (StringUtils.isNotEmpty(userRegistryVo.getRoleName())) {
            insertRole(userRegistryVo.getRoleName());
        } else {
            insertRole(MyEnum.ROLE_USER + id);
        }
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

    private List<String> getRolesNames() {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("DISTINCT role_name");
        List<String> list = new ArrayList<>();
        for (SysUser sysUser : sysUserMapper.selectList(queryWrapper)) {
            list.add(sysUser.getRoleName());
        }
        return list;
    }

    private List<Object> getRoleMenus(String roleName) {
        List<Long> menuId = sysRoleMapper.selectRoleNames(sysRoleMapper.selectRoleIdByRoleName(roleName));
        List<Object> list = new ArrayList<>();
        for (int i = 0; i < menuId.size(); i++) {
            list.add(sysMenuMapper.getMenuName(menuId.get(i)));
        }
        return list;
    }
}
