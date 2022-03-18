package com.xiaobai.service.impl;

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

import java.util.List;
/**
 * @author 终于白发始于青丝
 * @create 2022-03-18 08:43:18
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
            return R.successCMD(page);
        }

        @Override
        public R getPages() {
            QueryWrapper<SysMenu> wrapper = new QueryWrapper<>();
            wrapper.eq("delFlag", 0).orderByDesc("createTime");
            return R.successCMD(sysMenuMapper.selectList(wrapper));
        }

        @Override
        public R addSysMenu(SysMenu sysMenu) {
            // 判断是否为空

            SysMenu v = new SysMenu();
            // v.set赋值
            sysMenuMapper.insert(v);
            return R.successCM();
        }

        @Override
        public R delSysMenu(Long id) {
            SysMenu sysMenu = sysMenuMapper.selectById(id);
            sysMenu.setDelFlag(1);
            return R.successCMD(sysMenuMapper.updateById(sysMenu));
        }

        @Override
        public R delSysMenuAll(List<Long> ids) {
            QueryWrapper<SysMenu> wrapper = new QueryWrapper<>();
            wrapper.in("id", ids);
            for (SysMenu sysMenu : sysMenuMapper.selectList(wrapper)) {
                sysMenu.setDelFlag(1);
                sysMenuMapper.updateById(sysMenu);
            }
            return R.successCM();
        }

        @Override
        public R updateSysMenu(Long id, String info) {
            // 判断是否为空

            SysMenu sysMenu = new SysMenu();
            sysMenu.setId(id);
            // 赋值修改内容
            sysMenuMapper.updateById(sysMenu);
            return R.successCM();
        }
}
