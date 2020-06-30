package com.zcy.sys.controller;

import com.zcy.sys.utils.VerifyCodeUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * @Author zhuangchongyi
 * @Description 获取验证码
 * @Date 2020/6/30 10:19
 */
@Controller
public class VerifyCodeController {

    @RequestMapping("/getVerifyCode")
    public void getVerifyCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        VerifyCodeUtil.generateVerifyCode(request, response);
    }
}
