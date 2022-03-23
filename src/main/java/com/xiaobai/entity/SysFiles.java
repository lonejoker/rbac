package com.xiaobai.entity;

import java.util.Date;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;
import java.util.List;

/**
 * @author 终于白发始于青丝
 * @create 2022-03-23 13:43:12
 * @Version 1.0
 * @ClassName SysFiles
 * @Description 类方法说明：
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "sys_files")
public class SysFiles {
    //  id
    @TableId(type = IdType.AUTO)
    private Long id;
    // 文件名称
    private String name;
    // 文件类型
    private String type;
    // 文件大小（kb）
    private Long size;
    // 下载链接
    private String url;
    // 是否禁用链接（状态：0正常，1禁用）
    private String enable;
    // md5
    private String md5;
    // 上传时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date createTime;
    // 逻辑删除（0未删除，1已删除）
    private Integer delFlag;

}
