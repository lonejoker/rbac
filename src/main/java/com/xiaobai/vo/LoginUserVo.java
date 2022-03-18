package com.xiaobai.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 终于白发始于青丝
 * @create 2022-03-18 下午 22:32
 * @Version 1.0
 * @ClassName LoginUserVo
 * @Description 类方法说明：
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginUserVo {
    private Long id;
    private String userName;
    private String nickName;
    private String status;
    private String email;
    private String phone;
    private String sex;
    private String userAvatar;
    private String userType;
    private String roleName;
}
