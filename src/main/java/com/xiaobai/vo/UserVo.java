package com.xiaobai.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 终于白发始于青丝
 * @create 2022-03-18 下午 21:44
 * @Version 1.0
 * @ClassName UserVo
 * @Description 类方法说明：
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserVo {
    private String userName;
    private String password;
}
