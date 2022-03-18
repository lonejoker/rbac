package com.xiaobai.controller;

import com.xiaobai.service.LoginService;
import com.xiaobai.utils.R;
import com.xiaobai.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
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
    public R login(@RequestBody UserVo userVo) {
        return loginService.login(userVo);
    }

    @GetMapping("/logout")
    public R logout() {
        return loginService.logout();
    }
}