package com.xiaobai.service;

import com.xiaobai.entity.SysLog;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaobai.utils.R;
import java.util.List;
public interface SysLogService extends IService<SysLog> {

    R sysLogPage(Integer pageNum, Integer pageSize);

    R getPages();

    R addSysLog(SysLog sysLog);

    R delSysLog(Long id);

    R delSysLogAll(List<Long> ids);

    R updateSysLog(Long id, String info);
}
