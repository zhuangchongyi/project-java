package com.zcy.security.authentication;

import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;
/**
 * @Author zhuangchongyi
 * @Description 获取用户登录时携带的额外信息
 * @Date 2020/6/30 11:30
 */
public class CustomWebAuthenticationDetails extends WebAuthenticationDetails {

    private static final long serialVersionUID = -7121876476113955422L;
    private String verifyCode;

    public String getVerifyCode() {
        return verifyCode;
    }

    public CustomWebAuthenticationDetails(HttpServletRequest request) {
        super(request);
        // 页面输入的获取验证码
        this.verifyCode = request.getParameter("verifyCode");
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append(super.toString()).append("; VerifyCode: ")
                .append(this.getVerifyCode()).toString();
    }
}
