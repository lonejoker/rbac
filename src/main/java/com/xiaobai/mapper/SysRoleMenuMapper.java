package com.xiaobai.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaobai.entity.SysMenu;
import com.xiaobai.entity.SysRoleMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 终于白发始于青丝
 * @create 2022-03-18 21:18:48
 * @Version 1.0
 * @ClassName SysRoleMenu
 * @Description 类方法说明：
 */
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {

    List<Long> selectRolesByRoleId(@Param("roleId") Long roleId);
}
