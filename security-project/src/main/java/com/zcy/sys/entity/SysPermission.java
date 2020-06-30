package com.zcy.sys.entity;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * 用户权限表(SysPermission)实体类
 *
 * @author zhuangchongyi
 * @since 2020-06-30 12:43:19
 */
public class SysPermission implements Serializable {
    private static final long serialVersionUID = 555556314464684465L;
    /**
     * 权限编号
     */
    private Integer permissionId;
    /**
     * 请求url
     */
    private String url;
    /**
     * 角色编号
     */
    private Integer roleId;
    /**
     * 权限
     */
    private String permission;

    private List<String> permissions;

    public List<String> getPermissions() {
        return Arrays.asList(permission.trim().split(","));
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    public Integer getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

}