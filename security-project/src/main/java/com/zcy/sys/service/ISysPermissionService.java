package com.zcy.sys.service;

import com.zcy.sys.entity.SysPermission;

import java.util.List;

/**
 * 用户权限表(SysPermission)表服务接口
 *
 * @author zhuangchongyi
 * @since 2020-06-30 12:43:19
 */
public interface ISysPermissionService {

    List<SysPermission> listByRoleId(Integer roleId);

}