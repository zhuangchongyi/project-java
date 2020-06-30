package com.zcy.security.strategy;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author zhuangchongyi
 * @Description 处理旧用户登陆失败的逻辑
 * @Date 2020/6/30 15:53
 */
public class CustomExpiredSessionStrategy implements SessionInformationExpiredStrategy {
    private ObjectMapper objectMapper = new ObjectMapper();
    //private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();


    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
        Map<Object, Object> map = new HashMap<>();
        map.put("code", 0);
        map.put("msg", "已经另一台机器登录，您被迫下线，时间：" +
                new SimpleDateFormat("YYYY-MM-DD hh:mm:ss").format(event.getSessionInformation().getLastRequest()));
        // 转json字符串
        String result = objectMapper.writeValueAsString(map);
        HttpServletResponse response = event.getResponse();
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(result);

        // 如果是跳转html页面，url代表跳转的地址
        // redirectStrategy.sendRedirect(event.getRequest(), event.getResponse(), "url");
    }
}
