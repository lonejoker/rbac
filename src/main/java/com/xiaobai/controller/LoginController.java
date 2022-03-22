package com.xiaobai.controller;

import com.xiaobai.annotation.SystemLog;
import com.xiaobai.service.LoginService;
import com.xiaobai.utils.R;
import com.xiaobai.vo.UserRegistryVo;
import com.xiaobai.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author 终于白发始于青丝
 * @create 2022-03-18 下午 21:43
 * @Version 1.0
 * @ClassName LoginController
 * @Description 类方法说明：
 */
@RestController
@RequestMapping("/user")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    @SystemLog(businessName =  "用户登录")
    public R login(@RequestBody UserVo userVo) {
        return loginService.login(userVo);
    }

    @PostMapping("/logout")
    @SystemLog(businessName =  "用户退出")
    public R logout() {
        return loginService.logout();
    }

    @PostMapping("/registry")
    @SystemLog(businessName =  "用户注册")
    public R registry(@RequestBody UserRegistryVo userRegistryVo) throws IllegalAccessException {
        //System.out.println(userRegistryVo);
        //return  null;
        return loginService.registry(userRegistryVo);
    }

    @GetMapping("/getRoles")
    @SystemLog(businessName =  "获取权限菜单")
    public R roleLogin(@RequestParam String roleName) {
        return loginService.roleLoginList(roleName);
    }

    @GetMapping("/getMenuName")
    @SystemLog(businessName =  "获取权限菜单重载")
    public R getRole(@RequestParam String roleName) {
        return loginService.getRole(roleName);
    }

    @GetMapping("/getRolesName")
    @SystemLog(businessName =  "获取角色列表")
    public R getRolesName(){
        return loginService.getRolesName();
    }
}