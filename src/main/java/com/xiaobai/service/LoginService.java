package com.xiaobai.service;

import com.xiaobai.utils.R;
import com.xiaobai.vo.UserRegistryVo;
import com.xiaobai.vo.UserVo;

public interface LoginService {
    R login(UserVo userVo);

    R logout();

    R roleLoginList(String roleName);

    R registry(UserRegistryVo userRegistryVo) throws IllegalAccessException;
}
