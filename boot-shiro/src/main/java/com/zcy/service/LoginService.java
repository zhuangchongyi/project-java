package com.zcy.service;

import com.zcy.entity.Role;
import com.zcy.entity.User;

public interface LoginService {
    User addUser(User user);

    Role addRole(Role role);

    User findByName(String name);
}
