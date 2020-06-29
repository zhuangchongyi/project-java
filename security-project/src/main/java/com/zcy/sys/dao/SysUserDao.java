package com.zcy.sys.dao;

import com.zcy.sys.entity.SysUser;

/**
 * 用户表(SysUser)表数据库访问层
 *
 * @author zhuangchongyi
 * @since 2020-06-29 15:22:04
 */
public interface SysUserDao {

    SysUser selectByCode(String code);

    SysUser queryById(Integer userId);

}