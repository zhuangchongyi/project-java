package com.zcy.mq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @Author zhuangchongyi
 * @Description 交换机绑定队列配置类
 * @Date 2020/6/24 10:31
 */
@Component
public class FanoutConfig {
    /**
     * 邮件队列
     */
    private String FANOUT_EMAIL_QUEUE = "fanout_email_queue";
    /**
     * 短信服务队列
     */
    private String FANOUT_SMS_QUEUE = "fanout_sms_queue";
    /**
     * 交换机名称
     */
    private String EXCHANGE_NAME = "fanoutExchange";

    /**
     * 定义邮件队列
     *
     * @return
     */
    @Bean
    public Queue fanoutEmailQueue() {
        return new Queue(FANOUT_EMAIL_QUEUE);
    }

    /**
     * 定义短信队列
     *
     * @return
     */
    @Bean
    public Queue fanoutSmsQueue() {
        return new Queue(FANOUT_SMS_QUEUE);
    }

    /**
     * 定义交换机
     *
     * @return
     */
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(EXCHANGE_NAME);
    }

    /**
     * 队列与交换机绑定邮件队列
     *
     * @param fanoutEmailQueue 邮件队列
     * @param fanoutExchange   交换机
     * @return
     */
    @Bean
    public Binding bindExchangeEmail(Queue fanoutEmailQueue, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(fanoutEmailQueue).to(fanoutExchange);
    }

    /**
     * 队列与交换机绑定短信队列
     *
     * @param fanoutSmsQueue 短信队列
     * @param fanoutExchange 交换机
     * @return
     */
    @Bean
    public Binding bindExchangeSms(Queue fanoutSmsQueue, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(fanoutSmsQueue).to(fanoutExchange);
    }

}
