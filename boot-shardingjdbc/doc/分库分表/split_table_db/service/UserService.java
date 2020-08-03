package com.zcy.split_table_db.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zcy.split_table_db.entity.User;

import java.util.List;

public interface UserService extends IService<User> {

    /**
     * 保存用户信息
     *
     * @param entity
     * @return
     */
    boolean save(User entity);

    /**
     * 查询全部用户信息
     *
     * @return
     */
    List<User> getUserList();
}
