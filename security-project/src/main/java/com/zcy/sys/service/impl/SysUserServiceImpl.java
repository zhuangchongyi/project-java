package com.zcy.sys.service.impl;

import com.zcy.sys.dao.SysUserDao;
import com.zcy.sys.entity.SysUser;
import com.zcy.sys.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysUserServiceImpl implements ISysUserService {
    @Autowired
    private SysUserDao sysUserDao;

    @Override
    public SysUser selectById(Integer id) {
        return sysUserDao.queryById(id);
    }

    @Override
    public SysUser selectByCode(String code) {
        return sysUserDao.selectByCode(code);
    }
}
