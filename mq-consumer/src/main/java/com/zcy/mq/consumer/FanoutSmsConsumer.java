package com.zcy.mq.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Author zhuangchongyi
 * @Description 短信消费者
 * @Date 2020/6/24 11:19
 */
@Component
@RabbitListener(queues = "fanout_sms_queue")
public class FanoutSmsConsumer {

    @RabbitHandler
    public void process(String message) {
        System.out.println("短信消费者获取的消息为:" + message);
    }
}
