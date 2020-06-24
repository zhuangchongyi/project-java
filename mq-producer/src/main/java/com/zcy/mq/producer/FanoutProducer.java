package com.zcy.mq.producer;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Author zhuangchongyi
 * @Description 生产者投递消息
 * @Date 2020/6/24 10:55
 */
@Component
public class FanoutProducer {
    @Autowired
    private AmqpTemplate amqpTemplate;

    /**
     * 发送消息
     *
     * @param queueName 队列名称
     */
    public void send(String queueName) {
        String msg = "Hello RabbitMQ " + new Date().getTime();
        System.out.println("send msg: " + msg + " to " + queueName);
        amqpTemplate.convertAndSend(queueName,msg);
    }
}
