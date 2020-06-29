package com.zcy.sys.dao;

import com.zcy.sys.entity.SysUserRole;

import java.util.List;

/**
 * 用户角色关联表(SysUserRole)表数据库访问层
 *
 * @author zhuangchongyi
 * @since 2020-06-29 14:54:13
 */
public interface SysUserRoleDao {

    List<SysUserRole> listByUserId(Integer userId);


}