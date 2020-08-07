package com.zcy.task;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * spring task 是spring自主开发的轻量级定时任务框架，不需要依赖其他额外的包，配置较为简单
 * Spring Task缺陷
 * Spring Task 本身不支持持久化，也没有推出官方的分布式集群模式，只能靠开发者在业务应用中自己手动扩展实现，无法满足可视化，易配置的需求
 */
@EnableScheduling
@Configuration
public class SpringTask {
    @Scheduled(cron = "1/5 * * * * ?")
    public void startTask() {
        System.out.println("spring task 每过五秒钟执行一次");
    }
}
