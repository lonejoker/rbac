package com.xiaobai.service;

import com.xiaobai.entity.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaobai.utils.R;
import java.util.List;
public interface SysMenuService extends IService<SysMenu> {

    R sysMenuPage(Integer pageNum, Integer pageSize);

    R getPages();

    R addSysMenu(SysMenu sysMenu);

    R delSysMenu(Long id);

    R delSysMenuAll(List<Long> ids);

    R updateSysMenu(Long id, String info);

    List<SysMenu> findAllMenus(String name);
}
