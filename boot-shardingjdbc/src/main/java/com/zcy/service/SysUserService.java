package com.zcy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zcy.entity.SysUser;

import java.util.List;

public interface SysUserService extends IService<SysUser> {

    /**
     * 查询全部用户信息
     *
     * @return
     */
    List<SysUser> getUserList();

}
