package com.xiaobai.service.impl;

import com.xiaobai.entity.SysLog;
import com.xiaobai.mapper.SysLogMapper;
import com.xiaobai.service.SysLogService;
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
 * @create 2022-03-19 14:36:46
 * @Version 1.0
 * @ClassName SysLogServiceImpl
 * @Description 类方法说明：
 */
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements SysLogService {

        @Autowired
        private SysLogMapper sysLogMapper;

        @Override
        public R sysLogPage(Integer pageNum, Integer pageSize) {
            LambdaQueryWrapper<SysLog> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(SysLog::getDelFlag, 0).orderByDesc(SysLog::getCreateTime);
            Page<SysLog> page = new Page<>(pageNum, pageSize);
            page(page, wrapper);
            return R.successCmd(page);
        }

        @Override
        public R getPages() {
            QueryWrapper<SysLog> wrapper = new QueryWrapper<>();
            wrapper.eq("delFlag", 0).orderByDesc("createTime");
            return R.successCmd(sysLogMapper.selectList(wrapper));
        }

        @Override
        public R addSysLog(SysLog sysLog) {
            // 判断是否为空

            SysLog v = new SysLog();
            // v.set赋值
            sysLogMapper.insert(v);
            return R.successCm();
        }

        @Override
        public R delSysLog(Long id) {
            SysLog sysLog = sysLogMapper.selectById(id);
            sysLog.setDelFlag(1);
            return R.successCmd(sysLogMapper.updateById(sysLog));
        }

        @Override
        public R delSysLogAll(List<Long> ids) {
            QueryWrapper<SysLog> wrapper = new QueryWrapper<>();
            wrapper.in("id", ids);
            for (SysLog sysLog : sysLogMapper.selectList(wrapper)) {
                sysLog.setDelFlag(1);
                sysLogMapper.updateById(sysLog);
            }
            return R.successCm();
        }

        @Override
        public R updateSysLog(Long id, String info) {
            // 判断是否为空

            SysLog sysLog = new SysLog();
            sysLog.setId(id);
            // 赋值修改内容
            sysLogMapper.updateById(sysLog);
            return R.successCm();
        }
}
