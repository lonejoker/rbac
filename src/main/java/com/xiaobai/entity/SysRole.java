package com.xiaobai.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;
import java.util.List;

/**
 * @author 终于白发始于青丝
 * @create 2022-03-18 21:18:44
 * @Version 1.0
 * @ClassName SysRole
 * @Description 类方法说明：
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_role")
public class SysRole implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 角色名
     */
    private String roleName;

    /**
     * 角色权限标识
     */
    private String roleKey;

    /**
     * 逻辑删除（0未删除，1已删除）
     */
    private Integer delFlag;

    /**
     * 角色状态（0正常，1停用）
     */
    private String status;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date updateTime;

    /**
     * 备注
     */
    private String remark;


}
