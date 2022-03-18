package com.xiaobai.service.impl;

import com.xiaobai.entity.SysRole;
import com.xiaobai.mapper.SysRoleMapper;
import com.xiaobai.service.SysRoleService;
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
 * @create 2022-03-18 21:18:44
 * @Version 1.0
 * @ClassName SysRoleServiceImpl
 * @Description 类方法说明：
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

        @Autowired
        private SysRoleMapper sysRoleMapper;

        @Override
        public R sysRolePage(Integer pageNum, Integer pageSize) {
            LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(SysRole::getDelFlag, 0).orderByDesc(SysRole::getCreateTime);
            Page<SysRole> page = new Page<>(pageNum, pageSize);
            page(page, wrapper);
            return R.successCmd(page);
        }

        @Override
        public R getPages() {
            QueryWrapper<SysRole> wrapper = new QueryWrapper<>();
            wrapper.eq("delFlag", 0).orderByDesc("createTime");
            return R.successCmd(sysRoleMapper.selectList(wrapper));
        }

        @Override
        public R addSysRole(SysRole sysRole) {
            // 判断是否为空

            SysRole v = new SysRole();
            // v.set赋值
            sysRoleMapper.insert(v);
            return R.successCm();
        }

        @Override
        public R delSysRole(Long id) {
            SysRole sysRole = sysRoleMapper.selectById(id);
            sysRole.setDelFlag(1);
            return R.successCmd(sysRoleMapper.updateById(sysRole));
        }

        @Override
        public R delSysRoleAll(List<Long> ids) {
            QueryWrapper<SysRole> wrapper = new QueryWrapper<>();
            wrapper.in("id", ids);
            for (SysRole sysRole : sysRoleMapper.selectList(wrapper)) {
                sysRole.setDelFlag(1);
                sysRoleMapper.updateById(sysRole);
            }
            return R.successCm();
        }

        @Override
        public R updateSysRole(Long id, String info) {
            // 判断是否为空

            SysRole sysRole = new SysRole();
            sysRole.setId(id);
            // 赋值修改内容
            sysRoleMapper.updateById(sysRole);
            return R.successCm();
        }
}
