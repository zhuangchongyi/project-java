package com.zcy.master_slave.controller;

import com.zcy.master_slave.entity.MsUser;
import com.zcy.master_slave.service.MsUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ms/user")
public class MsUserController {
    @Autowired
    MsUserService msUserService;

    @GetMapping
    public List<MsUser> list(){
        return msUserService.getUserList();
    }

    @PostMapping
    public boolean save(MsUser user){
        return msUserService.save(user);
    }
}
