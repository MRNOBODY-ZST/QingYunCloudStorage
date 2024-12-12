package com.qingyun.cloudstorage.utils;

import com.qingyun.cloudstorage.config.MinioConfig;
import com.qingyun.cloudstorage.pojo.File;
import io.minio.*;
import io.minio.http.Method;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
public class MinioUtils {

    private final MinioConfig minioConfig;
    private final String bucketName;
    private final MinioClient minioClient;

    public MinioClient minioClient() {
        return MinioClient.builder().endpoint(minioConfig.getEndpoint(), minioConfig.getPort(), false).credentials(minioConfig.getAccessKey(), minioConfig.getSecretKey()).build();
    }

    @Autowired
    public MinioUtils(MinioConfig minioConfig) {
        this.minioConfig = minioConfig;
        this.bucketName = minioConfig.getBucketName();
        this.minioClient = minioClient();
    }


    public File uploadFile(String objectName, MultipartFile file, String parentId) throws Exception {
        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(bucketName)
                        .object(objectName)
                        .stream(file.getInputStream(), file.getSize(), -1)
                        .contentType(file.getContentType())
                        .build()
        );

        StatObjectResponse stat = minioClient.statObject(
                StatObjectArgs.builder()
                        .bucket(bucketName)
                        .object(objectName)
                        .build()
        );

        return new File(
                null,
                ThreadUtils.getUserId(),
                parentId,
                bucketName,
                objectName,
                stat.etag(),
                ((Long) file.getSize()).toString(),
                file.getContentType(),
                LocalDateTime.now(),
                LocalDateTime.ofInstant(stat.lastModified().toInstant(), ZoneId.systemDefault()),
                1
        );
    }


    public InputStream downloadFile(String objectName) throws Exception {
        return minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(bucketName)
                        .object(objectName)
                        .build()
        );
    }

    public String generatePresignedUrl(String objectName) throws Exception {
        return minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                        .method(Method.GET)
                        .bucket(bucketName)

                        .expiry(60 * 60) // URL expires in 1 hour
                        .build());
    }
}