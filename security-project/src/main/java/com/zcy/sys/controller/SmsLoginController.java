package com.zcy.sys.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 用户表(SysUser)表控制层
 *
 * @author zhuangchongyi
 * @since 2020-06-29 15:22:05
 */
@Controller
public class SmsLoginController {
    private Logger logger = LoggerFactory.getLogger(getClass());


    @RequestMapping("/sms/code")
    @ResponseBody
    public void sms(String mobile, HttpSession session) {
        String code = String.valueOf(new Random().nextInt(9000) + 1000);
        Map<String, Object> map = new HashMap<>();
        map.put("mobile", mobile);
        map.put("code", code);

        session.setAttribute("smsCode", map);

        logger.info("{}：为 {} 设置短信验证码：{}", session.getId(), mobile, code);
    }

}