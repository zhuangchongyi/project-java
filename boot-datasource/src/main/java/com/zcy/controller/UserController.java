package com.zcy.controller;

import com.zcy.mapper2.SysUserDao;
import com.zcy.mapper1.UserDao;
import com.zcy.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private UserDao userDao;

    @PostMapping
    public int add(SysUser user) {
        sysUserDao.insert(user);
        user.setId(user.getId() + 1);
        return userDao.insert(user);
    }
}
