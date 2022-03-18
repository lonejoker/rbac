package com.xiaobai.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.xiaobai.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import com.xiaobai.service.SysMenuService;
import com.xiaobai.entity.SysMenu;

import org.springframework.web.bind.annotation.RestController;

/**
 * @author 终于白发始于青丝
 * @create 2022-03-18 08:43:18
 * @Version 1.0
 * @ClassName SysMenuController
 * @Description 类方法说明：
 */
@RestController
@RequestMapping("/sysMenu")
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;

    @GetMapping("/page")
    public R sysMenuPage(@RequestParam(required = false) Integer pageNum,@RequestParam(required = false) Integer pageSize) {
        return sysMenuService.sysMenuPage(pageNum, pageSize);
    }

    @GetMapping("/getSysMenuInfo")
    public R getPages() {
        return sysMenuService.getPages();
    }

    @PostMapping("/addSysMenu")
    public R addSysMenu(@RequestBody SysMenu sysMenu) {
        return sysMenuService.addSysMenu(sysMenu);
    }

    @DeleteMapping("/delSysMenu/{id}")
    public R delSysMenu(@PathVariable Long id) {
        return sysMenuService.delSysMenu(id);
    }

    @PostMapping("/delSysMenuAll/{ids}")
    public R delSysMenuAll(@PathVariable List<Long> ids) {
        return sysMenuService.delSysMenuAll(ids);
    }

    @PutMapping("/updateSysMenu")
    public R updateSysMenu(@RequestParam Long id, @RequestParam String info) {
        return sysMenuService.updateSysMenu(id, info);
    }
}
