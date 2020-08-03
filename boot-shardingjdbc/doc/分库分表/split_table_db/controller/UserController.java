package com.zcy.split_table_db.controller;

import com.zcy.split_table_db.entity.User;
import com.zcy.split_table_db.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/s/user")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping
    public List<User> list(){
        return userService.getUserList();
    }

    @PostMapping
    public boolean save(User user){
        return userService.save(user);
    }
}
