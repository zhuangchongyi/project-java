package com.zcy.split_table.controller;

import com.zcy.split_table.entity.SysUser;
import com.zcy.split_table.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sys/user")
public class SysUserController {
    @Autowired
    SysUserService sysUserService;

    @GetMapping
    public List<SysUser> list(){
        return sysUserService.getUserList();
    }

    @PostMapping
    public boolean save(SysUser user){
        return sysUserService.save(user);
    }
}
