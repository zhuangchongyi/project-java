package com.zcy.oss.service;


import io.minio.*;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InvalidExpiresRangeException;
import io.minio.http.Method;
import io.minio.messages.Bucket;
import io.minio.messages.DeleteError;
import io.minio.messages.DeleteObject;
import io.minio.messages.Item;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * @Author zhuangchongyi
 * @Description minio 依赖版本7.1.0
 * @Date 2020/7/29 9:56
 */
@Component
public class MinioService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MinioClient minioClient;

    private static final int DEFAULT_EXPIRY_TIME = 7 * 24 * 3600;

    /**
     * 检查存储桶是否存在
     *
     * @param bucketName 存储桶名称
     * @return true 已存在
     */
    public boolean bucketExists(String bucketName) throws Exception {
        if (minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())) {
            return true;
        }
        return false;
    }

    /**
     * 创建存储桶
     *
     * @param bucketName 存储桶名称
     * @return false已存在
     */
    public boolean makeBucket(String bucketName) throws Exception {
        if (bucketExists(bucketName)) {
            return false;
        } else {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            return true;
        }
    }

    /**
     * 列出所有存储桶名称
     *
     * @return
     */
    public List<String> listBucketNames() throws Exception {
        List<Bucket> bucketList = listBuckets();
        List<String> bucketListName = new ArrayList<>();
        for (Bucket bucket : bucketList) {
            bucketListName.add(bucket.name());
        }
        return bucketListName;
    }

    /**
     * 列出所有存储桶
     *
     * @return
     */
    public List<Bucket> listBuckets() throws Exception {
        return minioClient.listBuckets();
    }

    /**
     * 删除存储桶
     *
     * @param bucketName 存储桶名称
     * @return
     */
    public boolean removeBucket(String bucketName) throws Exception {
        if (bucketExists(bucketName)) {
            Iterable<Result<Item>> myObjects = listObjects(bucketName);
            for (Result<Item> result : myObjects) {
                Item item = result.get();
                // 有对象文件，则删除失败
                if (item.size() > 0) {
                    logger.info("删除存储桶失败，存在子文件");
                    return false;
                }
            }
            // 删除存储桶，注意，只有存储桶为空时才能删除成功。
            minioClient.removeBucket(RemoveBucketArgs.builder().bucket(bucketName).build());
            if (!bucketExists(bucketName)) {
                return true;
            }

        }
        return false;
    }

    /**
     * 列出存储桶中的所有对象名称
     *
     * @param bucketName 存储桶名称
     * @return
     */
    public List<String> listObjectNames(String bucketName) throws Exception {
        List<String> listObjectNames = new ArrayList<>();
        if (bucketExists(bucketName)) {
            Iterable<Result<Item>> myObjects = listObjects(bucketName);
            for (Result<Item> result : myObjects) {
                Item item = result.get();
                listObjectNames.add(item.objectName());
            }
        }
        return listObjectNames;
    }

    /**
     * 列出存储桶中的所有对象
     *
     * @param bucketName 存储桶名称
     * @return
     */
    public Iterable<Result<Item>> listObjects(String bucketName) throws Exception {
        if (bucketExists(bucketName)) {
            return minioClient.listObjects(ListObjectsArgs.builder().bucket(bucketName).recursive(true).build());
        }
        return null;
    }

    /**
     * 通过文件上传到对象(本地)
     *
     * @param bucketName 存储桶名称
     * @param objectName 存储桶里的对象名称
     * @param fileName   File name
     * @return
     */
    public boolean putObject(String bucketName, String objectName, String fileName) throws Exception {
        if (bucketExists(bucketName)) {
            minioClient.uploadObject(UploadObjectArgs.builder()
                    .bucket(bucketName).object(objectName).filename(fileName).build());
            ObjectStat statObject = statObject(bucketName, objectName);
            if (statObject != null && statObject.length() > 0) {
                return true;
            }
        }
        return false;

    }

    /**
     * 文件上传
     *
     * @param bucketName
     * @param objectName
     * @param multipartFile
     */
    public void putObject(String bucketName, String objectName, MultipartFile multipartFile) throws Exception {
        if (bucketExists(bucketName)) {
            InputStream stream = multipartFile.getInputStream();
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName).object(objectName)
                    .stream(stream, stream.available(), -1)
                    .contentType(multipartFile.getContentType())
                    .build());
        }
    }

    /**
     * 通过InputStream上传对象
     *
     * @param bucketName 存储桶名称
     * @param objectName 存储桶里的对象名称
     * @param stream     要上传的流
     * @return
     */
    public boolean putObject(String bucketName, String objectName, InputStream stream) throws Exception {
        if (bucketExists(bucketName)) {
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName).object(objectName)
                    .stream(stream, stream.available(), -1)
                    .build());
            ObjectStat statObject = statObject(bucketName, objectName);
            if (statObject != null && statObject.length() > 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 以流的形式获取一个文件对象
     *
     * @param bucketName 存储桶名称
     * @param objectName 存储桶里的对象名称
     * @return
     */
    public InputStream getObject(String bucketName, String objectName) throws Exception {
        if (bucketExists(bucketName)) {
            ObjectStat statObject = statObject(bucketName, objectName);
            if (statObject != null && statObject.length() > 0) {
                InputStream stream = minioClient.getObject(GetObjectArgs.builder().bucket(bucketName).object(objectName).build());
                return stream;
            }
        }
        return null;
    }

    /**
     * 以流的形式获取一个文件对象（断点下载）
     *
     * @param bucketName 存储桶名称
     * @param objectName 存储桶里的对象名称
     * @param offset     起始字节的位置
     * @param length     要读取的长度 (可选，如果无值则代表读到文件结尾)
     * @return
     */
    public InputStream getObject(String bucketName, String objectName, long offset, Long length) throws Exception {
        if (bucketExists(bucketName)) {
            ObjectStat statObject = statObject(bucketName, objectName);
            if (statObject != null && statObject.length() > 0) {
                InputStream stream = minioClient.getObject(GetObjectArgs.builder()
                        .bucket(bucketName).object(objectName)
                        .offset(offset).length(length).build());
                return stream;
            }
        }
        return null;
    }

    /**
     * 下载并将文件保存到本地
     *
     * @param bucketName 存储桶名称
     * @param objectName 存储桶里的对象名称
     * @param fileName   File name
     * @return
     */
    public boolean getObject(String bucketName, String objectName, String fileName) throws Exception {
        if (bucketExists(bucketName)) {
            ObjectStat statObject = statObject(bucketName, objectName);
            if (statObject != null && statObject.length() > 0) {
                minioClient.downloadObject(DownloadObjectArgs.builder()
                        .bucket(bucketName).object(objectName).filename(fileName).build());
                return true;
            }
        }
        return false;
    }

    /**
     * 删除一个对象
     *
     * @param bucketName 存储桶名称
     * @param objectName 存储桶里的对象名称
     */
    public boolean removeObject(String bucketName, String objectName) throws Exception {
        if (bucketExists(bucketName)) {
            minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(objectName).build());
            return true;
        }
        return false;
    }

    /**
     * 删除指定桶的多个文件对象,返回删除错误的对象列表，全部删除成功，返回空列表
     *
     * @param bucketName  存储桶名称
     * @param objectNames 含有要删除的多个object名称的迭代器对象
     * @return
     */
    public List<String> removeObject(String bucketName, List<String> objectNames) throws Exception {
        List<String> deleteErrorNames = new ArrayList<>();
        if (bucketExists(bucketName)) {
            Stream<DeleteObject> stream = StreamSupport.stream(objectNames.spliterator(), false).map(name -> new DeleteObject(name));
            Iterable<Result<DeleteError>> results = minioClient.removeObjects(RemoveObjectsArgs.builder()
                    .bucket(bucketName).objects(stream::iterator).build());
            for (Result<DeleteError> result : results) {
                DeleteError error = result.get();
                deleteErrorNames.add(error.objectName());
            }
        }
        return deleteErrorNames;
    }

    /**
     * 生成一个给HTTP GET请求用的presigned URL。
     * 浏览器/移动端的客户端可以用这个URL进行下载，即使其所在的存储桶是私有的。这个presigned URL可以设置一个失效时间，默认值是7天。
     *
     * @param bucketName 存储桶名称
     * @param objectName 存储桶里的对象名称
     * @param expires    失效时间（以秒为单位），默认是7天，不得大于七天
     * @return
     */
    public String presignedGetObject(String bucketName, String objectName, Integer expires) throws Exception {
        String url = "";
        if (bucketExists(bucketName)) {
            if (expires < 1 || expires > DEFAULT_EXPIRY_TIME) {
                throw new InvalidExpiresRangeException(expires, "expires must be in range of 1 to " + DEFAULT_EXPIRY_TIME);
            }
            url = minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                    .bucket(bucketName).object(objectName)
                    .method(Method.GET).expiry(expires).build());
        }
        return url;
    }

    /**
     * 生成一个给HTTP PUT请求用的presigned URL。
     * 浏览器/移动端的客户端可以用这个URL进行上传，即使其所在的存储桶是私有的。这个presigned URL可以设置一个失效时间，默认值是7天。
     *
     * @param bucketName 存储桶名称
     * @param objectName 存储桶里的对象名称
     * @param expires    失效时间（以秒为单位），默认是7天，不得大于七天
     * @return
     */

    public String presignedPutObject(String bucketName, String objectName, Integer expires) throws Exception {
        String url = "";
        if (bucketExists(bucketName)) {
            if (expires < 1 || expires > DEFAULT_EXPIRY_TIME) {
                throw new InvalidExpiresRangeException(expires, "expires must be in range of 1 to " + DEFAULT_EXPIRY_TIME);
            }
            url = minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                    .bucket(bucketName).object(objectName)
                    .method(Method.PUT).expiry(expires).build());
        }
        return url;
    }

    /**
     * 获取对象的元数据
     *
     * @param bucketName 存储桶名称
     * @param objectName 存储桶里的对象名称
     * @return
     */
    public ObjectStat statObject(String bucketName, String objectName) throws Exception {
        if (bucketExists(bucketName)) {
            ObjectStat statObject = minioClient.statObject(StatObjectArgs.builder()
                    .bucket(bucketName).object(objectName).build());
            return statObject;
        }
        return null;
    }

    /**
     * 文件访问路径
     *
     * @param bucketName 存储桶名称
     * @param objectName 存储桶里的对象名称
     * @return
     */
    public String getObjectUrl(String bucketName, String objectName) throws Exception {
        String url = "";
        if (bucketExists(bucketName)) {
            url = minioClient.getObjectUrl(bucketName, objectName);
        }
        return url;
    }

    /**
     * 强制删除存储桶以及所有存储桶内文件
     *
     * @param bucketName
     * @return
     * @throws Exception
     */
    public boolean removeBucketMandatory(String bucketName) {
        try {
            if (bucketExists(bucketName)) {
                List<String> objectNames = listObjectNames(bucketName);
                if (objectNames.isEmpty()) {
                    return removeBucket(bucketName);
                }
                for (String objectName : objectNames) {
                    System.out.println(URLDecoder.decode(objectName, "utf-8"));
                    removeObject(bucketName, URLDecoder.decode(objectName, "utf-8"));
                }
                if (listObjectNames(bucketName).isEmpty()) {
                    return removeBucket(bucketName);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 下载文件
     *
     * @param bucketName
     * @param fileName
     * @param originalName
     * @param response
     */
    public void downloadFile(String bucketName, String fileName, String originalName, HttpServletResponse response) {
        try {

            InputStream file = getObject(bucketName, fileName);
            String filename = new String(fileName.getBytes("ISO8859-1"), StandardCharsets.UTF_8);
            if (StringUtils.isNotEmpty(originalName)) {
                fileName = originalName;
            }
            response.setHeader("Content-Disposition", "attachment;filename=" + filename);
            ServletOutputStream servletOutputStream = response.getOutputStream();
            int len;
            byte[] buffer = new byte[1024];
            while ((len = file.read(buffer)) > 0) {
                servletOutputStream.write(buffer, 0, len);
            }
            servletOutputStream.flush();
            file.close();
            servletOutputStream.close();
        } catch (ErrorResponseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

