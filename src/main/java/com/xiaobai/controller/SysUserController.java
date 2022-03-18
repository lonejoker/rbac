package com.xiaobai.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.xiaobai.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import com.xiaobai.service.SysUserService;
import com.xiaobai.entity.SysUser;

import org.springframework.web.bind.annotation.RestController;

/**
 * @author 终于白发始于青丝
 * @create 2022-03-18 21:18:44
 * @Version 1.0
 * @ClassName SysUserController
 * @Description 类方法说明：
 */
@RestController
@RequestMapping("/sysUser")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @GetMapping("/page")
    public R sysUserPage(@RequestParam(required = false) Integer pageNum,@RequestParam(required = false) Integer pageSize) {
        return sysUserService.sysUserPage(pageNum, pageSize);
    }

    @GetMapping("/getSysUserInfo")
    public R getPages() {
        return sysUserService.getPages();
    }

    @PostMapping("/addSysUser")
    public R addSysUser(@RequestBody SysUser sysUser) {
        return sysUserService.addSysUser(sysUser);
    }

    @DeleteMapping("/delSysUser/{id}")
    public R delSysUser(@PathVariable Long id) {
        return sysUserService.delSysUser(id);
    }

    @PostMapping("/delSysUserAll/{ids}")
    public R delSysUserAll(@PathVariable List<Long> ids) {
        return sysUserService.delSysUserAll(ids);
    }

    @PutMapping("/updateSysUser")
    public R updateSysUser(@RequestParam Long id, @RequestParam String info) {
        return sysUserService.updateSysUser(id, info);
    }
}