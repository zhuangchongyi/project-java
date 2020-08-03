package com.zcy.master_slave.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zcy.master_slave.dao.MsUserDao;
import com.zcy.master_slave.entity.MsUser;
import com.zcy.master_slave.service.MsUserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MsUserServiceImpl extends ServiceImpl<MsUserDao, MsUser> implements MsUserService {
    @Override
    public boolean save(MsUser entity) {
        return super.save(entity);
    }

    @Override
    public List<MsUser> getUserList() {
        QueryWrapper<MsUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("id");
        return baseMapper.selectList(queryWrapper);
    }
}
