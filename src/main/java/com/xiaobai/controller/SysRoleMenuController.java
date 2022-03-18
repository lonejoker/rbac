package com.xiaobai.controller;



import com.xiaobai.entity.SysRoleMenu;
import com.xiaobai.service.SysRoleMenuService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.Serializable;
import java.util.List;

/**
 * @author 终于白发始于青丝
 * @create 2022-03-18 21:18:48
 * @Version 1.0
 * @ClassName SysRoleMenu
 * @Description 类方法说明：
 */
@RestController
@RequestMapping("/sysRoleMenu")
public class SysRoleMenuController {
    @Autowired
    private SysRoleMenuService sysRoleMenuService;
    
    
}
