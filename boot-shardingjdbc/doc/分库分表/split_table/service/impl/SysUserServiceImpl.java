package com.zcy.split_table.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zcy.split_table.dao.SysUserDao;
import com.zcy.split_table.entity.SysUser;
import com.zcy.split_table.service.SysUserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUser> implements SysUserService {

    @Override
    public List<SysUser> getUserList() {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("id");
        return baseMapper.selectList(queryWrapper);
    }
}
