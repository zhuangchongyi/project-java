package com.zcy.master_slave.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.zcy.master_slave.entity.MsUser;

import java.util.List;

public interface MsUserService extends IService<MsUser> {

    /**
     * 保存用户信息
     *
     * @param entity
     * @return
     */
    boolean save(MsUser entity);

    /**
     * 查询全部用户信息
     *
     * @return
     */
    List<MsUser> getUserList();
}
