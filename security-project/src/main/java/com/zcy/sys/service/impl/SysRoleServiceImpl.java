package com.zcy.sys.service.impl;

import com.zcy.sys.dao.SysRoleDao;
import com.zcy.sys.entity.SysRole;
import com.zcy.sys.service.ISysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysRoleServiceImpl implements ISysRoleService {
    @Autowired
    private SysRoleDao sysRoleDao;

    @Override
    public SysRole queryById(Integer roleId) {
        return sysRoleDao.queryById(roleId);
    }

    @Override
    public SysRole queryByRoleCode(String roleCode) {
        return sysRoleDao.queryByCode(roleCode);
    }
}
