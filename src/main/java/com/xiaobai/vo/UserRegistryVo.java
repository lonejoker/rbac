package com.xiaobai.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author 终于白发始于青丝
 * @create 2022-03-19 下午 15:59
 * @Version 1.0
 * @ClassName UserRegistryVo
 * @Description 类方法说明：
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistryVo {
    private String userName;
    private String password;
    private String email;
    private String phone;
    private String radio;
}
