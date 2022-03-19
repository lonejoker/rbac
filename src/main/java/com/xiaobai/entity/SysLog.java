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
 * @create 2022-03-19 14:36:46
 * @Version 1.0
 * @ClassName SysLog
 * @Description 类方法说明：
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_log")
public class SysLog implements Serializable {

    private static final long serialVersionUID = 1L;
    //主键id
    @TableId(type = IdType.AUTO)
    private Long id;
    //访问url
    private String url;
    //业务信息名
    private String businessName;
    //请求方式
    private String httpMethod;
    //方法所在的类
    private String classMethod;
    //ip地址
    private String ip;
    //请求参数
    private String requestArgs;
    //响应信息
    private String response;
    // 请求时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date createTime;
    // 是否删除（0未删除，1已删除）
    private Integer delFlag;
}
