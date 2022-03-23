package com.xiaobai.service;

import com.xiaobai.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaobai.utils.R;
import com.xiaobai.vo.UserInfo;
import com.xiaobai.vo.UserRegistryVo;

import java.util.List;
public interface SysUserService extends IService<SysUser> {

    R sysUserPage(Integer pageNum, Integer pageSize,String nickName);

    R getPages();

    R addSysUser(SysUser sysUser);

    R delSysUser(Long id);

    R delSysUserAll(List<Long> ids);

    R updateSysUser(UserInfo userInfo);

    R getRole(String roleName);

    R getRolesName();

    R registry(UserRegistryVo userRegistryVo) throws IllegalAccessException;

    R sysUserPageAll(Integer pageNum, Integer pageSize);
}
