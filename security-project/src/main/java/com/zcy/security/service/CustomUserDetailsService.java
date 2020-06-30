package com.zcy.security.service;

import com.zcy.sys.entity.SysRole;
import com.zcy.sys.entity.SysUser;
import com.zcy.sys.entity.SysUserRole;
import com.zcy.sys.service.ISysRoleService;
import com.zcy.sys.service.ISysUserRoleService;
import com.zcy.sys.service.ISysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
    private Logger log = LoggerFactory.getLogger(CustomUserDetailsService.class);

    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysRoleService roleService;

    @Autowired
    private ISysUserRoleService userRoleService;

    @Override
    public UserDetails loadUserByUsername(String userCode) throws UsernameNotFoundException {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        // 查询用户信息
        SysUser sysUser = userService.selectByCode(userCode);
        // 判断用户是否
        if (sysUser == null) {
            log.info(userCode + " 该用户不存在");
            throw new UsernameNotFoundException("该用户不存在");
        }
        log.info(sysUser.toString());
        // 查询添加权限
        List<SysUserRole> sysUserRoles = userRoleService.listByUserId(sysUser.getUserId());
        for (SysUserRole sysUserRole : sysUserRoles) {
            SysRole sysRole = roleService.queryById(sysUserRole.getRoleId());
            authorities.add(new SimpleGrantedAuthority(sysRole.getRoleCode()));
            log.info(sysRole.toString());
        }
        // 返回userDetails实现类
        return new User(sysUser.getUserCode(), sysUser.getPassword(), authorities);
    }
}
