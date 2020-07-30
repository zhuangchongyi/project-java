package com.zcy.oss;

import com.zcy.common.utils.DateUtil;
import com.zcy.common.utils.IdUtil;
import com.zcy.oss.service.MinioService;
import io.minio.*;
import io.minio.messages.Item;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MinioOssApplicationTests {
    @Autowired
    MinioService minioService;
    @Autowired
    MinioClient minioClient;

    @Test
    public void test2() {
//        System.out.println(minioService.removeBucketMandatory("test"));
        try {
            Iterable<Result<Item>> results = minioService.listObjects("test");
            for (Result<Item> result : results) {
                Item item = result.get();
                System.out.println(item.lastModified() + "\t" + item.size() + "\t" + item.objectName());
            }
            Iterable<Result<Item>> results2 = minioClient.listObjects("test");
            for (Result<Item> result : results2) {
                Item item = result.get();
                System.out.println(item.lastModified() + "\t" + item.size() + "\t" + item.objectName());
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void test1() {
        try {
            Resource resource = new ClassPathResource("/static/wx.jpg");
            String filename = resource.getFilename();
            String filePath = resource.getFile().getPath();
            InputStream stream = resource.getInputStream();
            System.out.println(filePath);
            String objectName = DateUtil.datePath("yyyy/MM/") + IdUtil.simpleUUID() + filename.substring(filename.lastIndexOf('.'));
            System.out.println(objectName);
            String bucketName = "images";
            minioService.putObject(bucketName,objectName,stream);


            System.out.println(minioService.getObjectUrl(bucketName, objectName));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Test
    public void upload() {
        try {
            // 图片路径
            Resource resource = new ClassPathResource("/static/wx.jpg");
            String filename = resource.getFilename();
            String type = filename.substring(filename.lastIndexOf('.'));
            String objectName = DateUtil.datePath("yyyy/MM/") + IdUtil.simpleUUID() + type;
            System.out.println(objectName);
            String bucketName = "images";
            // 检查存储桶是否已经存在
            if (minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())) {
                System.out.println("Bucket already exists.");
            } else {
                // 创建一个名为asiatrip的存储桶，用于存储照片的zip文件。
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }

            // 使用putObject上传一个文件到存储桶中。
            InputStream stream = resource.getInputStream();
            String filePath = resource.getFile().getPath();
            Path path = Paths.get(filePath);
            //方式1
            minioClient.putObject(bucketName, objectName, filePath,null);
            //方式2
//            PutObjectOptions options = new PutObjectOptions(stream.available(), 0);
//            options.setContentType(Files.probeContentType(path));
//            minioClient.putObject(bucketName,objectName,stream, options);
            //方式3
//            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
//                    .bucket(bucketName)
//                    .object(objectName)
//                    .stream(stream,stream.available(),-1)
//                    .contentType(Files.probeContentType(path))
//                    .build();
//            minioClient.putObject(putObjectArgs);
//            stream.close();

            System.out.println(String.format("%s is successfully uploaded as %s to `%s` bucket.", filePath, objectName, bucketName));
            System.out.println(minioClient.getObjectUrl(bucketName, objectName));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void download() {
        InputStream in = null;
        OutputStream out = null;
        try {
            String bucketName = "images";
            String objectName = "/2020/07/fec6998626cd46a18e7662fce8a59a4f.jpg";
            String fileName = System.currentTimeMillis() + objectName.substring(objectName.lastIndexOf('.'));
            String savePath = "E:/idea_java_dev/RuoYi-Vue-master/doc/download/" + fileName;
            in = minioClient.getObject(bucketName, objectName);
            File file = new File(savePath);
            if (!file.exists()) {
                file.createNewFile();
            }
            out = new FileOutputStream(file);
            int n = 0;
            byte[] b = new byte[1024];
            while ((n = in.read(b)) != -1) {
                out.write(b, 0, n);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @Test
    public void delete() {
        try {
            String bucketName = "images";
            String objectName = "/2020/07/c37ecca84f224b81af388afd8c5f6ba5.jpg";
            minioClient.removeObject(bucketName, objectName);
            System.out.println(String.format("%s successfully delete to %s", objectName, bucketName));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    @Test
    public void deleteBucket() {
        try {
            String bucketName = "test2";
//            minioClient.removeBucket(bucketName);//The bucket you tried to delete is not empty
//            minioClient.removeIncompleteUpload("images","/2020/07/184ae36f27ad4e7dabfa5dc201144ab7.jpg");
//            minioClient.removeAllBucketNotification(bucketName);
            System.out.println(minioClient.presignedGetObject(bucketName, "barcode.jpg"));
            System.out.println(String.format("successfully delete to %s", bucketName));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }

    @Test
    public void list() {
        try {
            Iterable<Result<Item>> results = minioClient.listObjects("test3");
            for (Result<Item> result : results) {
                Item item = result.get();
                System.out.println(item.objectName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void bucketExists() {
        try {
            boolean exists = minioClient.bucketExists("images");
            System.out.println(exists);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
