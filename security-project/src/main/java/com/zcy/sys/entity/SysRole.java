package com.zcy.sys.entity;

import java.io.Serializable;

/**
 * 用户角色表(SysRole)实体类
 *
 * @author makejava
 * @since 2020-06-29 14:30:04
 */
public class SysRole implements Serializable {
    private static final long serialVersionUID = -85706519932954265L;
    /**
    * 角色编号
    */
    private Integer roleId;
    /**
    * 角色编码
    */
    private String roleCode;
    /**
    * 角色名称
    */
    private String roleName;


    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String toString() {
        return "SysRole{" +
                "roleId=" + roleId +
                ", roleCode='" + roleCode + '\'' +
                ", roleName='" + roleName + '\'' +
                '}';
    }
}