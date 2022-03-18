package com.xiaobai.utils;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

/**
 * @author 终于白发始于青丝
 * @create 2022-03-17 下午 21:42
 * @program rbac
 * @Version 1.0
 * @ClassName Generator
 * @Description 类方法说明：代码生成器
 */
public class Generator {
    public static void main(String[] args) {
        generators();
    }
    private static void generators() {
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/rbacdemo?serverTimezone=GMT%2B8", "root", "1q2w3e")
                .globalConfig(builder -> {
                    builder.author("zybfsyqs") // 设置作者
                            //.enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .commentDate("yyyy-MM-dd HH:mm:ss")
                            .disableOpenDir()
                            .dateType(DateType.ONLY_DATE)
                            .outputDir("E:\\程序代码\\idea\\rbac\\src\\main\\java\\"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.xiaobai") // 设置父包名
                            .moduleName(null) // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, "E:\\程序代码\\idea\\rbac\\src\\main\\resources\\mapper")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude("sys_menu") // 设置需要生成的表名
                            .entityBuilder().enableLombok() // lombok
                            .controllerBuilder()  // 开启驼峰转连字符
                            .enableRestStyle()  // 开启生成@RestController 控制器
                            .serviceBuilder().formatServiceFileName("%sService");
                            //.addTablePrefix("sys_"); // 设置过滤表前缀
                })
                //.templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();

    }
}
