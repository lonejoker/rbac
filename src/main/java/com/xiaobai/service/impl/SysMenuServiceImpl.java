package com.xiaobai.service.impl;

import cn.hutool.core.util.StrUtil;
import com.xiaobai.entity.SysMenu;
import com.xiaobai.mapper.SysMenuMapper;
import com.xiaobai.service.SysMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
import java.util.stream.Collectors;

/**
 * @author 终于白发始于青丝
 * @create 2022-03-18 21:18:23
 * @Version 1.0
 * @ClassName SysMenuServiceImpl
 * @Description 类方法说明：
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Override
    public R sysMenuPage(Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysMenu::getDelFlag, 0).orderByDesc(SysMenu::getCreateTime);
        Page<SysMenu> page = new Page<>(pageNum, pageSize);
        page(page, wrapper);
        return R.successCmd(page);
    }

    @Override
    public R getPages() {
        QueryWrapper<SysMenu> wrapper = new QueryWrapper<>();
        wrapper.eq("delFlag", 0).orderByDesc("createTime");
        return R.successCmd(sysMenuMapper.selectList(wrapper));
    }

    @Override
    public R addSysMenu(SysMenu sysMenu) {
        // 判断是否为空

        SysMenu v = new SysMenu();
        // v.set赋值
        sysMenuMapper.insert(v);
        return R.successCm();
    }

    @Override
    public R delSysMenu(Long id) {
        SysMenu sysMenu = sysMenuMapper.selectById(id);
        sysMenu.setDelFlag(1);
        return R.successCmd(sysMenuMapper.updateById(sysMenu));
    }

    @Override
    public R delSysMenuAll(List<Long> ids) {
        QueryWrapper<SysMenu> wrapper = new QueryWrapper<>();
        wrapper.in("id", ids);
        for (SysMenu sysMenu : sysMenuMapper.selectList(wrapper)) {
            sysMenu.setDelFlag(1);
            sysMenuMapper.updateById(sysMenu);
        }
        return R.successCm();
    }

    @Override
    public R updateSysMenu(Long id, String info) {
        // 判断是否为空

        SysMenu sysMenu = new SysMenu();
        sysMenu.setId(id);
        // 赋值修改内容
        sysMenuMapper.updateById(sysMenu);
        return R.successCm();
    }

    // 查出系统所有菜单
    @Override
    public List<SysMenu> findAllMenus(String name) {
        LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(name)) {
            wrapper.like(SysMenu::getMenuName, name);
        }
        // 查询所有数据
        List<SysMenu> list = list(wrapper);
        // 找出pid为null的一级菜单
        List<SysMenu> parentNodes = list.stream().filter(menu -> menu.getParentId() == null).collect(Collectors.toList());
        List<SysMenu> parentNodess = list.stream().filter(menu -> menu.getParentId() != null).collect(Collectors.toList());
        List<SysMenu> parentNodesss = list.stream().filter(menu -> menu.getParentId() != null).collect(Collectors.toList());
        // 找出一级菜单的子菜单
        for (SysMenu parentNode : parentNodes) {
            // 筛选所有数据中pid等于父级id的数据为二级菜单
            // parentNode为每一个一级菜单
            // parentNode.getId()为一级菜单的id
            // p.getParentId()为每一个一级菜单的父级id
            parentNode.setChildren(list.stream().filter(p -> parentNode.getId().equals(p.getParentId())).collect(Collectors.toList()));
            for (SysMenu parent : parentNodess) {
                parent.setChildren(list.stream().filter(p -> parent.getId().equals(p.getParentId())).collect(Collectors.toList()));
                for (SysMenu parents : parentNodesss) {
                    parents.setChildren(list.stream().filter(p -> parents.getId().equals(p.getParentId())).collect(Collectors.toList()));
                }
            }
        }
        return parentNodes;
    }
}
