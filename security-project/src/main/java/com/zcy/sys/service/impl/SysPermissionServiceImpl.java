package com.zcy.sys.service.impl;

import com.zcy.sys.entity.SysPermission;
import com.zcy.sys.dao.SysPermissionDao;
import com.zcy.sys.service.ISysPermissionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户权限表(SysPermission)表服务实现类
 *
 * @author zhuangchongyi
 * @since 2020-06-30 12:43:19
 */
@Service("sysPermissionService")
public class SysPermissionServiceImpl implements ISysPermissionService {
    @Resource
    private SysPermissionDao sysPermissionDao;

    @Override
    public List<SysPermission> listByRoleId(Integer roleId) {
        return sysPermissionDao.listByRoleId(roleId);
    }
}