package com.zcy.controller;

import com.zcy.common.core.vo.ResultVo;
import com.zcy.entity.Role;
import com.zcy.entity.User;
import com.zcy.service.LoginService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {
    @Autowired
    private LoginService loginService;

    /**
     * 登录
     *
     * @param user
     * @return
     */
    @PostMapping("/login")
    public ResultVo login(User user) {
        // 添加用户认证
        UsernamePasswordToken token = new UsernamePasswordToken(user.getName(), user.getPassword());
        //进行校验，捕获异常，返回相应的异常信息
        SecurityUtils.getSubject().login(token);
        return ResultVo.success().msg("登陆成功");
    }

    /**
     * 添加用户
     *
     * @param user
     * @return
     */
    @PostMapping("/user")
    public ResultVo addUser(User user) {
        return ResultVo.success().data(loginService.addUser(user));
    }

    /**
     * 添加角色
     *
     * @param role
     * @return
     */
    @PostMapping("/role")
    public ResultVo addRole(Role role) {
        loginService.addRole(role);
        return ResultVo.success().msg("添加成功");
    }

    /**
     * 查询用户信息
     *
     * @param name
     * @return
     */
    @GetMapping("/user/{name}")
    public ResultVo findUserByName(@PathVariable("name") String name) {
        return ResultVo.success().data(loginService.findByName(name));
    }


    /**
     * 使用shiro权限注解
     *
     * @return
     */
    @RequiresRoles("admin")// 设置角色访问
    @RequiresPermissions("create") // 设定权限访问
    @GetMapping("/role")
    public String createRole() {
        return "create success";
    }

    @GetMapping("/index")
    public String index() {
        return "index page";
    }

    @GetMapping("/error")
    public String error() {
        return "error page";
    }

    /**
     * 退出的时候是get请求，主要是用于退出
     *
     * @return
     */
    @GetMapping(value = "/login")
    public String login() {
        return "login page";
    }

    @GetMapping(value = "/logout")
    public String logout() {
        return "logout page";
    }
}
