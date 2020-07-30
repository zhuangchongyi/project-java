package com.zcy.oss;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.zcy.common.utils.IdUtil;
import com.zcy.oss.constant.AliyunOssConstant;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.io.InputStream;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AliyunOssApplicationTests {

    @Test
    public void uploadFile() {
        OSS ossClient = null;
        InputStream in = null;
        try {
            // 新建一个Ossclient实例
            ossClient = new OSSClientBuilder().build(AliyunOssConstant.ENDPOINT, AliyunOssConstant.ACCESS_KEY_ID, AliyunOssConstant.ACCESS_KEY_SECRET);
            // 图片路径
            Resource resource = new ClassPathResource("/static/image.jpg");
            System.err.println(resource.getURL().getPath());
            // 上传文件名
            String fileName = IdUtil.simpleUUID() +".jpg";
            in = resource.getInputStream();
            ossClient.putObject(AliyunOssConstant.BUCKET_NAME, fileName, in);
            System.err.println("upload success!");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != ossClient) {
                ossClient.shutdown();
            }
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
