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
 * @ClassName SysRoleMenu
 * @Description 类方法说明：
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "sys_role_menu")
public class SysRoleMenu {
    /**
     * 角色id
     */
    private Long roleId;
    /**
     * 菜单id
     */
    private Long menuId;

}
