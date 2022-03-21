package com.xiaobai.service.impl;

import com.xiaobai.entity.SysUser;
import com.xiaobai.mapper.SysUserMapper;
import com.xiaobai.service.SysUserService;
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
 * @ClassName SysUserServiceImpl
 * @Description 类方法说明：
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

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
    public R updateSysUser(Long id, String info) {
        // 判断是否为空

        SysUser sysUser = new SysUser();
        sysUser.setId(id);
        // 赋值修改内容
        sysUserMapper.updateById(sysUser);
        return R.successCm();
    }
}
