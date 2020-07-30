package com.zcy.oss.config;

import com.zcy.oss.constant.MinioConstant;
import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @Author zhuangchongyi
 * @Description minio核心配置类
 * @Date 2020/7/28 15:22
 */
@Component
@EnableConfigurationProperties(MinioConstant.class)
public class MinioConfig {
    @Autowired
    private MinioConstant minioConstant;


    @Bean
    public MinioClient minioClient() throws Exception {
        return new MinioClient(minioConstant.getEndpoint(), minioConstant.getAccessKey(), minioConstant.getSecretKey());
    }
}
