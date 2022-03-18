package com.xiaobai.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.util.Date;
import java.util.List;

/**
 * @author 终于白发始于青丝
 * @create 2022-03-18 21:22:26
 * @Version 1.0
 * @ClassName SysUserRole
 * @Description 类方法说明：
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "sys_user_role")
public class SysUserRole {
    /**
     * 用户id
    */
    private Long userId;
    /**
     * 角色id
    */
    private Long roleId;

}
