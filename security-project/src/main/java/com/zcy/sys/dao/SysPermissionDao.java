package com.zcy.sys.dao;

import com.zcy.sys.entity.SysPermission;

import java.util.List;

/**
 * 用户权限表(SysPermission)表数据库访问层
 *
 * @author zhuangchongyi
 * @since 2020-06-30 12:43:19
 */
public interface SysPermissionDao {

    List<SysPermission> listByRoleId(Integer roleId);


}