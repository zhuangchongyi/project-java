package com.zcy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zcy.dao.SysUserDao;
import com.zcy.entity.SysUser;
import com.zcy.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUser> implements SysUserService {
    @Autowired
    private SysUserDao sysUserDao;


    @Override
    public List<SysUser> getUserList() {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("id");
        return baseMapper.selectList(queryWrapper);
    }

}
