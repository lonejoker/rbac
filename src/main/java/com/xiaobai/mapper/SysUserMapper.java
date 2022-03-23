package com.xiaobai.mapper;

import com.xiaobai.entity.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author 终于白发始于青丝
 * @create 2022-03-18 21:18:44
 * @Version 1.0
 * @ClassName SysUserMapper
 * @Description 类方法说明：
 */
public interface SysUserMapper extends BaseMapper<SysUser> {


    int updateInfo(@Param("email") String email, @Param("nickName") String nickName,@Param("id")  Long id);
}
