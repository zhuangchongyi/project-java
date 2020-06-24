package com.zcy.mq.controller;

import com.zcy.mq.producer.FanoutProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FanoutController {
    @Autowired
    private FanoutProducer fanoutProducer;

    @GetMapping("sendFanout")
    public String sendFanout(String queueName){
        fanoutProducer.send(queueName);
        return "success";
    }

}
