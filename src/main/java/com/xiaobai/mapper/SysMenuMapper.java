package com.xiaobai.mapper;

import com.xiaobai.entity.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @author 终于白发始于青丝
 * @create 2022-03-18 21:18:23
 * @Version 1.0
 * @ClassName SysMenuMapper
 * @Description 类方法说明：
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {
    List<String> selectByUserId(Long id);

    String getMenuName(Long menuid);
}
