package com.xiaobai.vo;

import com.xiaobai.entity.SysMenu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 终于白发始于青丝
 * @create 2022-03-18 下午 23:30
 * @Version 1.0
 * @ClassName RoleVo
 * @Description 类方法说明：
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleVo {
    private Long id;
    private String menuName;
    private String path;
    private String component;
    private String perms;
    private String icon;
    private List<SysMenu> children;
    private String remark;
}
