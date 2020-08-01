package com.zcy.dao;

import com.zcy.entity.User;
/**
 * @Author zhuangchongyi
 * @Description UserDao，用来操作用户信息
 * @Date 2020/7/31 17:11
 */
public interface UserDao extends BaseDao<User, Long> {
    User findByName(String name);
}
