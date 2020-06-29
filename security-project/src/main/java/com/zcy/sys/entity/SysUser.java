package com.zcy.sys.entity;

import java.io.Serializable;

/**
 * 用户表(SysUser)实体类
 *
 * @author zhuangchongyi
 * @since 2020-06-29 15:22:04
 */
public class SysUser implements Serializable {
    private static final long serialVersionUID = -99430656214963795L;
    /**
    * 用户编号
    */
    private Integer userId;
    /**
    * 用户名
    */
    private String userName;
    /**
    * 用户编码
    */
    private String userCode;
    /**
    * 密码
    */
    private String password;
    /**
    * 性别
    */
    private String gender;


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "SysUser{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userCode='" + userCode + '\'' +
                ", password='" + password + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }
}