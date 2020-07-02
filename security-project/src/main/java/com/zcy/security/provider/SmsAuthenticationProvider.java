package com.zcy.security.provider;

import com.zcy.security.token.SmsAuthenticationToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Author zhuangchongyi
 * @Description 短信登陆鉴权 Provider，要求实现 AuthenticationProvider 接口
 * @Date 2020/6/30 11:43
 */
@Component
public class SmsAuthenticationProvider implements AuthenticationProvider {
    private UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SmsAuthenticationToken authenticationToken = (SmsAuthenticationToken) authentication;
        String mobile = (String) authentication.getPrincipal();
        // 校验
        checkSmsCode(mobile);
        // 获取用户信息
        UserDetails userDetails = userDetailsService.loadUserByUsername(mobile);
        // 此时鉴权成功后，应当重新 new 一个拥有鉴权的 authenticationResult 返回
        SmsAuthenticationToken resultAuthenticationToken = new SmsAuthenticationToken(userDetails, userDetails.getAuthorities());
        resultAuthenticationToken.setDetails(userDetails);
        return resultAuthenticationToken;
    }

    /**
     * 校验验证码
     *
     * @param mobile
     */
    private void checkSmsCode(String mobile) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String inputSmsCode = request.getParameter("smsCode");
        Map<String, Object> smsCode = (Map<String, Object>) request.getSession().getAttribute("smsCode");
        if (null == smsCode) {
            throw new BadCredentialsException("请重新获取验证码");
        }
        String applyMobile = (String) smsCode.get("mobile");
        String code = (String) smsCode.get("code");
        if (!applyMobile.equals(mobile)) {
            throw new BadCredentialsException("手机号码不一致");
        }
        if (!code.equals(inputSmsCode)) {
            throw new BadCredentialsException("验证码错误");
        }


    }

    public UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        // 判断 authentication 是不是 SmsCodeAuthenticationToken 的子类或子接口
        return SmsAuthenticationToken.class.equals(aClass);
    }
}
