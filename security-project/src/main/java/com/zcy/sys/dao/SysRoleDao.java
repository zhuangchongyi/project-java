package com.zcy.sys.dao;

import com.zcy.sys.entity.SysRole;

/**
 * 用户角色表(SysRole)表数据库访问层
 *
 * @author zhuangchongyi
 * @since 2020-06-29 14:54:11
 */
public interface SysRoleDao {

    SysRole queryById(Integer roleId);

}