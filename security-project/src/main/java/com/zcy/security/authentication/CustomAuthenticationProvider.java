package com.zcy.security.authentication;

import com.zcy.security.service.CustomUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author zhuangchongyi
 * @Description 自定义AuthenticationProvider实现校验
 * @Date 2020/6/30 11:43
 */
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationProvider.class.getName());
    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 获取用户输入的用户名和密码
        String inputName = authentication.getName();
        String inputPassword = authentication.getCredentials().toString();

        CustomWebAuthenticationDetails details = (CustomWebAuthenticationDetails) authentication.getDetails();

        String verifyCode = details.getVerifyCode();
        if (!validVerifyCode(verifyCode)) {
            throw new DisabledException("验证码输入错误");
        }
        // userDetails为数据库中查询到的用户信息
        UserDetails userDetails = userDetailsService.loadUserByUsername(inputName);
        // 如果是自定义AuthenticationProvider，需要手动密码校验
        String password = userDetails.getPassword();
        if (!BCrypt.checkpw(inputPassword, password)) {
            throw new BadCredentialsException("密码错误");
        }

        return new UsernamePasswordAuthenticationToken(inputName, password, userDetails.getAuthorities());
    }

    /**
     * 校验验证码
     *
     * @param inputCode
     * @return
     */
    private boolean validVerifyCode(String inputCode) {
        // 获取当前线程绑定的request对象
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String verifyCode = (String) request.getSession().getAttribute("verifyCode");
        logger.info("验证码：" + verifyCode + "用户输入：" + inputCode);
        return verifyCode.equalsIgnoreCase(inputCode);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        // 和UsernamePasswordAuthenticationToken比较
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}
