package com.zcy.service.impl;

import com.zcy.dao.RoleDao;
import com.zcy.dao.UserDao;
import com.zcy.entity.Role;
import com.zcy.entity.User;
import com.zcy.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;


    @Override
    public User addUser(User user) {
        return userDao.save(user);
    }

    @Override
    public Role addRole(Role role) {
        return roleDao.save(role);
    }

    @Override
    public User findByName(String name) {
        return userDao.findByName(name);
    }
}
