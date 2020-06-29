package com.zcy.sys.service;

import com.zcy.sys.entity.SysUserRole;

import java.util.List;

public interface ISysUserRoleService {
    List<SysUserRole> listByUserId(Integer userId);

}
