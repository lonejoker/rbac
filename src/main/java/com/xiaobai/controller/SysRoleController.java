package com.xiaobai.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.xiaobai.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import com.xiaobai.service.SysRoleService;
import com.xiaobai.entity.SysRole;

import org.springframework.web.bind.annotation.RestController;

/**
 * @author 终于白发始于青丝
 * @create 2022-03-18 21:18:44
 * @Version 1.0
 * @ClassName SysRoleController
 * @Description 类方法说明：
 */
@RestController
@RequestMapping("/sysRole")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    @GetMapping("/page")
    public R sysRolePage(@RequestParam(required = false) Integer pageNum,@RequestParam(required = false) Integer pageSize) {
        return sysRoleService.sysRolePage(pageNum, pageSize);
    }

    @GetMapping("/getSysRoleInfo")
    public R getPages() {
        return sysRoleService.getPages();
    }

    @PostMapping("/addSysRole")
    public R addSysRole(@RequestBody SysRole sysRole) {
        return sysRoleService.addSysRole(sysRole);
    }

    @DeleteMapping("/delSysRole/{id}")
    public R delSysRole(@PathVariable Long id) {
        return sysRoleService.delSysRole(id);
    }

    @PostMapping("/delSysRoleAll/{ids}")
    public R delSysRoleAll(@PathVariable List<Long> ids) {
        return sysRoleService.delSysRoleAll(ids);
    }

    @PutMapping("/updateSysRole")
    public R updateSysRole(@RequestParam Long id, @RequestParam String info) {
        return sysRoleService.updateSysRole(id, info);
    }
}
