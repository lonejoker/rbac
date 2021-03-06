package com.xiaobai.mapper;

import com.xiaobai.entity.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 终于白发始于青丝
 * @create 2022-03-18 21:18:44
 * @Version 1.0
 * @ClassName SysRoleMapper
 * @Description 类方法说明：
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

    Long selectRoleIdByRoleName(@Param("roleName") String roleName);

    List<Long> selectRoleNames(Long roleId);
}
