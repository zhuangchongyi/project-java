package com.zcy.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zcy.entity.SysUser;
import com.zcy.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class SysUserController {
    @Autowired
    SysUserService sysUserService;

    @GetMapping
    public List<SysUser> list(){
        return sysUserService.getUserList();
    }

    @GetMapping("/{current}/{size}")
    public IPage<SysUser> get(@PathVariable("current") int current, @PathVariable("size") int size){
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("id");
        return sysUserService.page(new Page<>(current,size), queryWrapper);
    }

    @PostMapping
    public boolean save(SysUser user){
        return sysUserService.save(user);
    }
}
