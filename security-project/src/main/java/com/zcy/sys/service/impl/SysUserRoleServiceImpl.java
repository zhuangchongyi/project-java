package com.zcy.sys.service.impl;

import com.zcy.sys.dao.SysUserRoleDao;
import com.zcy.sys.entity.SysUserRole;
import com.zcy.sys.service.ISysUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysUserRoleServiceImpl implements ISysUserRoleService {
    @Autowired
    private SysUserRoleDao sysUserRoleDao;
    @Override
    public List<SysUserRole> listByUserId(Integer userId) {
        return sysUserRoleDao.listByUserId(userId);
    }
}
