package com.xiaobai.controller;

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
    public String test(){
        return "test demo";
    }
    @RequestMapping("/demo")
    public String demo(){
        return "demo demo";
    }
}
