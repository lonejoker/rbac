package com.xiaobai.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.xiaobai.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import com.xiaobai.service.SysLogService;
import com.xiaobai.entity.SysLog;

import org.springframework.web.bind.annotation.RestController;

/**
 * @author 终于白发始于青丝
 * @create 2022-03-19 14:36:46
 * @Version 1.0
 * @ClassName SysLogController
 * @Description 类方法说明：
 */
@RestController
@RequestMapping("/sysLog")
public class SysLogController {

    @Autowired
    private SysLogService sysLogService;

    @GetMapping("/page")
    public R sysLogPage(@RequestParam(required = false) Integer pageNum,@RequestParam(required = false) Integer pageSize) {
        return sysLogService.sysLogPage(pageNum, pageSize);
    }

    @GetMapping("/getSysLogInfo")
    public R getPages() {
        return sysLogService.getPages();
    }

    @PostMapping("/addSysLog")
    public R addSysLog(@RequestBody SysLog sysLog) {
        return sysLogService.addSysLog(sysLog);
    }

    @DeleteMapping("/delSysLog/{id}")
    public R delSysLog(@PathVariable Long id) {
        return sysLogService.delSysLog(id);
    }

    @PostMapping("/delSysLogAll/{ids}")
    public R delSysLogAll(@PathVariable List<Long> ids) {
        return sysLogService.delSysLogAll(ids);
    }

    @PutMapping("/updateSysLog")
    public R updateSysLog(@RequestParam Long id, @RequestParam String info) {
        return sysLogService.updateSysLog(id, info);
    }
}
