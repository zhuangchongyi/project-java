package com.zcy.security.filter;

import org.springframework.security.authentication.DisabledException;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author zhuangchongyi
 * @Description 验证码过滤器
 * @Date 2020/6/30 10:32
 */
public class VerifyCodeFilter extends OncePerRequestFilter {
    private static final PathMatcher pathMatcher = new AntPathMatcher();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (isProtectedUrl(request)) {
            // 获得发送过来的验证码
            String inputCode = request.getParameter("verifyCode");
            // 校验验证码
            if (!validVerifyCode(inputCode)) {
                // 手动设置异常
                request.getSession().setAttribute("SPRING_SECURITY_LAST_EXCEPTION", new DisabledException("验证码输入错误"));
                // 转发到error URL
                request.getRequestDispatcher("/login/error").forward(request, response);
            } else {
                filterChain.doFilter(request, response);// 发行
            }
        } else {
            filterChain.doFilter(request, response);// 发行
        }
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

    /**
     * 手动拦截POST方式的login请求
     *
     * @param request
     * @return
     */
    private boolean isProtectedUrl(HttpServletRequest request) {
        return "POST".equals(request.getMethod()) && pathMatcher.match("/login", request.getServletPath());
    }
}
