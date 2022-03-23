package com.xiaobai.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 终于白发始于青丝
 * @create 2022-03-23 下午 21:26
 * @Version 1.0
 * @ClassName UserInfo
 * @Description 类方法说明：
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {
    private String email;
    private Long id;
    private String nickName;
    private String password;
    private String phone;
    private String roleName;
    private String userAvatar;
}
