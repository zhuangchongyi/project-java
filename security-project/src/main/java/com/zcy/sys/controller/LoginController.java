package com.zcy.sys.controller;

import com.zcy.sys.service.ISysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户表(SysUser)表控制层
 *
 * @author zhuangchongyi
 * @since 2020-06-29 15:22:05
 */
@Controller
public class LoginController {
    private Logger log = LoggerFactory.getLogger(LoginController.class);

    @Resource
    private ISysUserService sysUserService;

    @RequestMapping("/")
    public String showHame() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info("当前登录用户为:" + name);
        return "index.html";
    }

    @RequestMapping("/login")
    public String showLogin() {
        return "login.html";
    }

    @RequestMapping("/login/error")
    public void showLoginError(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html;charset=utf-8");
        AuthenticationException exception = (AuthenticationException) request.getSession().getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
        try {
            response.getWriter().write(exception.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/superAdmin")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')") // @PreAuthorize 用于判断用户是否有指定权限，没有就不能访问
    public String printSuperAdmin() {
        return "如果你看见这句话，说明你有超级管理员角色";
    }

    @RequestMapping("/admin")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String printAdmin() {
        return "如果你看见这句话，说明你有普通管理员角色";
    }

    @RequestMapping("/user")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_USER')")
    public String printUser() {
        return "如果你看见这句话，说明你有普通用户角色";
    }

}