package com.xiaobai.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 终于白发始于青丝
 * @create 2022-03-18 下午 21:28
 * @Version 1.0
 * @ClassName Testcontroller
 * @Description 类方法说明：
 */
@RestController
public class Testcontroller {
    @RequestMapping("/test")
    @PreAuthorize("hasAuthority('index')")
    public String test() {
        return "test demo";
    }

    @RequestMapping("/demo")
    @PreAuthorize("hasAuthority('index')")
    public String demo() {
        return "demo demo";
    }
}
