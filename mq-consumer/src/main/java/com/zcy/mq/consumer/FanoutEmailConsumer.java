package com.zcy.mq.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Author zhuangchongyi
 * @Description 邮件消费者
 * @Date 2020/6/24 11:18
 */
@Component
@RabbitListener(queues = "fanout_email_queue")
public class FanoutEmailConsumer {

    @RabbitHandler
    public void process(String message) {
        System.out.println("邮件消费者获取的消息为:" + message);
    }

}
