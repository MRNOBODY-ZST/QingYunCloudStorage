package com.qingyun.cloudstorage.utils;

import com.qingyun.cloudstorage.config.MinioConfig;
import com.qingyun.cloudstorage.pojo.File;
import com.qingyun.cloudstorage.pojo.Folder;
import io.minio.*;
import io.minio.http.Method;
import io.minio.messages.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

//@Component
public class MinioUtils {

//    private final MinioClient minioClient;
//    private final String bucketName;
//
//    @Autowired
//    public MinioUtils(MinioConfig minioConfig) {
//        this.bucketName = minioConfig.getBucketName();
//        this.minioClient = MinioClient.builder()
//                .endpoint(minioConfig.getEndpoint(), minioConfig.getPort(), false)
//                .credentials(minioConfig.getAccessKey(), minioConfig.getSecretKey())
//                .build();
//
//        try {
//            // Create bucket if it doesn't exist
//            boolean exists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
//            if (!exists) {
//                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
//            }
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to initialize MinIO bucket", e);
//        }
//    }

//    private Long parseParentId(String objectName) {
//        int lastSlashIndex = objectName.lastIndexOf('/');
//        if (lastSlashIndex == -1) {
//            return 0L; // Root directory
//        }
//        String parentPath = objectName.substring(0, lastSlashIndex);
//        // Here you might want to implement a way to map paths to folder IDs
//        // For now, returning 0 as default
//        return 0L;
//    }

//    public File uploadFile(String objectName, MultipartFile file) throws Exception {
//        // Add content type metadata
//        Map<String, String> headers = new HashMap<>();
//        headers.put("Content-Type", file.getContentType());
//
//        PutObjectArgs putObjectArgs = PutObjectArgs.builder()
//                .bucket(bucketName)
//                .object(objectName)
//                .stream(file.getInputStream(), file.getSize(), -1)
//                .contentType(file.getContentType())
//                .headers(headers)
//                .build();
//
//        ObjectWriteResponse response = minioClient.putObject(putObjectArgs);
//
//        StatObjectResponse stat = minioClient.statObject(
//                StatObjectArgs.builder()
//                        .bucket(bucketName)
//                        .object(objectName)
//                        .build()
//        );
//
//        return new File(
//                null,
//                ThreadUtils.getUserId(),
//                parseParentId(objectName),
//                bucketName,
//                objectName,
//                response.etag(),
//                file.getSize(),
//                file.getContentType(),
//                LocalDateTime.now(),
//                LocalDateTime.ofInstant(stat.lastModified().toInstant(), ZoneId.systemDefault()),
//                1
//        );
//    }
//
//    public Folder createFolder(String folderPath) throws Exception {
//        if (!folderPath.endsWith("/")) {
//            folderPath += "/";
//        }
//
//        // Create empty content to represent folder
//        minioClient.putObject(
//                PutObjectArgs.builder()
//                        .bucket(bucketName)
//                        .object(folderPath)
//                        .stream(new ByteArrayInputStream(new byte[]{}), 0, -1)
//                        .build()
//        );
//
//        return new Folder(
//                null,
//                ThreadUtils.getUserId(),
//                parseParentId(folderPath),
//                bucketName,
//                folderPath,
//                LocalDateTime.now(),
//                LocalDateTime.now()
//        );
//    }
//
//    public InputStream downloadFile(String objectName) throws Exception {
//        return minioClient.getObject(
//                GetObjectArgs.builder()
//                        .bucket(bucketName)
//                        .object(objectName)
//                        .build()
//        );
//    }
//
//    public String generatePresignedUrl(String objectName, int expiryMinutes) throws Exception {
//        return minioClient.getPresignedObjectUrl(
//                GetPresignedObjectUrlArgs.builder()
//                        .method(Method.GET)
//                        .bucket(bucketName)
//                        .object(objectName)
//                        .expiry(expiryMinutes, TimeUnit.MINUTES)
//                        .build());
//    }
//
//    public void deleteFile(String objectName) throws Exception {
//        minioClient.removeObject(
//                RemoveObjectArgs.builder()
//                        .bucket(bucketName)
//                        .object(objectName)
//                        .build()
//        );
//    }
//
//    public void deleteFolder(String folderPath) throws Exception {
//        if (!folderPath.endsWith("/")) {
//            folderPath += "/";
//        }
//
//        Iterable<Result<Item>> results = minioClient.listObjects(
//                ListObjectsArgs.builder()
//                        .bucket(bucketName)
//                        .prefix(folderPath)
//                        .recursive(true)
//                        .build()
//        );
//
//        for (Result<Item> result : results) {
//            minioClient.removeObject(
//                    RemoveObjectArgs.builder()
//                            .bucket(bucketName)
//                            .object(result.get().objectName())
//                            .build()
//            );
//        }
//    }
//
//    public List<File> listFiles(String prefix) throws Exception {
//        List<File> files = new ArrayList<>();
//
//        Iterable<Result<Item>> results = minioClient.listObjects(
//                ListObjectsArgs.builder()
//                        .bucket(bucketName)
//                        .prefix(prefix)
//                        .recursive(false)
//                        .build()
//        );
//
//        for (Result<Item> result : results) {
//            Item item = result.get();
//            if (!item.isDir()) {
//                StatObjectResponse stat = minioClient.statObject(
//                        StatObjectArgs.builder()
//                                .bucket(bucketName)
//                                .object(item.objectName())
//                                .build()
//                );
//
//                files.add(new File(
//                        null,
//                        ThreadUtils.getUserId(),
//                        parseParentId(item.objectName()),
//                        bucketName,
//                        item.objectName(),
//                        item.etag(),
//                        item.size(),
//                        stat.contentType(),
//                        LocalDateTime.now(),
//                        LocalDateTime.ofInstant(stat.lastModified().toInstant(), ZoneId.systemDefault()),
//                        1
//                ));
//            }
//        }
//
//        return files;
//    }
//
//    public boolean existsObject(String objectName) {
//        try {
//            minioClient.statObject(
//                    StatObjectArgs.builder()
//                            .bucket(bucketName)
//                            .object(objectName)
//                            .build()
//            );
//            return true;
//        } catch (Exception e) {
//            return false;
//        }
//    }
}