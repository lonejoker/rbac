package com.xiaobai.controller;



import com.xiaobai.entity.SysUserRole;
import com.xiaobai.service.SysUserRoleService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.Serializable;
import java.util.List;

/**
 * @author 终于白发始于青丝
 * @create 2022-03-18 21:18:47
 * @Version 1.0
 * @ClassName SysUserRole
 * @Description 类方法说明：
 */
@RestController
@RequestMapping("/sysUserRole")
public class SysUserRoleController {
    @Autowired
    private SysUserRoleService sysUserRoleService;
    
    
}
