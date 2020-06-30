package com.zcy.security.permission;

import com.zcy.sys.entity.SysPermission;
import com.zcy.sys.service.ISysPermissionService;
import com.zcy.sys.service.ISysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Author zhuangchongyi
 * @Description 权限验证处理
 * @Date 2020/6/30 13:02
 */
@Component
public class CustomPermissionEvaluator implements PermissionEvaluator {
    @Autowired
    private ISysPermissionService permissionService;
    @Autowired
    private ISysRoleService roleService;

    /**
     * 获取权限
     *
     * @param authentication 用户的权限身份
     * @param targetUrl 注解对应参数1
     * @param targetPermission 注解对应参数2
     * @return
     */
    @Override
    public boolean hasPermission(Authentication authentication, Object targetUrl, Object targetPermission) {
        // 获取loadUserByUsername()方法的用户信息
        User user = (User) authentication.getPrincipal();
        // 获取loadUserByUsername()方法的角色
        Collection<GrantedAuthority> authorities = user.getAuthorities();
        // 遍历所有角色
        for (GrantedAuthority authority : authorities) {
            String roleCode = authority.getAuthority();
            // 获取角色Id
            Integer roleId = roleService.queryByRoleCode(roleCode).getRoleId();
            List<SysPermission> permissionList = permissionService.listByRoleId(roleId);
            for (SysPermission sysPermission : permissionList) {
                // 获取权限集合
                List<String> permissions = sysPermission.getPermissions();
                // 如果访问的Url和权限用户符合的话，返回true
                if (targetUrl.equals(sysPermission.getUrl()) && permissions.contains(targetPermission)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable serializable, String s, Object o) {
        return false;
    }
}
