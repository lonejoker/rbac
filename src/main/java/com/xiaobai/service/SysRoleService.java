package com.xiaobai.service;

import com.xiaobai.entity.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaobai.utils.R;
import java.util.List;
public interface SysRoleService extends IService<SysRole> {

    R sysRolePage(Integer pageNum, Integer pageSize);

    R getPages();

    R addSysRole(SysRole sysRole);

    R delSysRole(Long id);

    R delSysRoleAll(List<Long> ids);

    R updateSysRole(Long id, String info);
}
