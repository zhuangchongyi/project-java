package com.zcy.handler;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class HelloJobHandler {

    @XxlJob("helloJobHandler")
    public ReturnT<String> helloJobHandler(String param) {
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println(now + " XXL-JOB, Hello World.");
        return ReturnT.SUCCESS;
    }
}
