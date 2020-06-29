package com.zcy.sys.service;

import com.zcy.sys.entity.SysUser;

public interface ISysUserService {

    SysUser selectById(Integer id);

    SysUser selectByCode(String code);
}
