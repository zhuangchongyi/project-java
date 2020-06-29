package com.zcy.sys.entity;

import java.io.Serializable;

/**
 * 用户角色关联表(SysUserRole)实体类
 *
 * @author makejava
 * @since 2020-06-29 14:30:59
 */
public class SysUserRole implements Serializable {
    private static final long serialVersionUID = -22669659056973213L;
    /**
    * 用户编号
    */
    private Integer userId;
    /**
    * 角色编号
    */
    private Integer roleId;


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

}