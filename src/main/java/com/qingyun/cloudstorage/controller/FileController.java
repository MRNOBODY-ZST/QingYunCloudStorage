package com.qingyun.cloudstorage.controller;

import com.qingyun.cloudstorage.enumerate.ResponseCode;
import com.qingyun.cloudstorage.pojo.File;
import com.qingyun.cloudstorage.pojo.Response;
import com.qingyun.cloudstorage.service.FileService;
import com.qingyun.cloudstorage.service.FileStatisticsService;
import com.qingyun.cloudstorage.utils.MinioUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/file")
public class FileController {

    private final FileService fileService;
    private final MinioUtils minioUtils;
    private final FileStatisticsService fileStatisticsService;

    @Autowired
    private FileController(FileService fileService, MinioUtils minioUtils, FileStatisticsService fileStatisticsService) {
        this.fileService = fileService;
        this.minioUtils = minioUtils;
        this.fileStatisticsService = fileStatisticsService;
    }

    @GetMapping
    public Response<File> getFile(@RequestParam String fileId) {
        return Response.success(ResponseCode.SUCCESS, "success", fileService.getById(fileId));
    }

    @PostMapping
    public Response<String> postFile(@RequestParam MultipartFile file, @RequestParam String parentId) throws Exception {
        File multipartFile = minioUtils.uploadFile(file, parentId);
        fileService.save(multipartFile);

        // 更新文件统计信息
        String userId = multipartFile.getUserId();
        // 更新文件数量 +1
        fileStatisticsService.updateFileNum(1);
        // 更新存储空间大小
        fileStatisticsService.updateSize(file.getSize());

        return Response.success(ResponseCode.SUCCESS, "success", null);
    }

    @PostMapping("/avatar")
    public Response<String> postAvatar(@RequestParam MultipartFile file) throws Exception {
        File multipartFile = minioUtils.uploadFile(file, "-1");
        multipartFile.setUserId("0");
        fileService.save(multipartFile);

        // 更新头像统计信息（如果需要的话）
        fileStatisticsService.updateFileNum(1);
        fileStatisticsService.updateSize(file.getSize());

        return Response.success(ResponseCode.SUCCESS, "success", multipartFile.getEtag());
    }

    @GetMapping("/avatar")
    public Response<String> getAvatar(@RequestParam String etag) throws Exception {
        File avatar = fileService.getAvatar(etag);
        return Response.success(ResponseCode.SUCCESS, "success", minioUtils.generatePresignedUrl(avatar.getObjectName()));
    }

    @GetMapping("/download")
    public Response<String> getDownloadUrl(@RequestParam String filename, @RequestParam String parentId) throws Exception {
        File file = fileService.getByNameAndUserId(filename, parentId);
        if (file != null) {
            return Response.success(ResponseCode.SUCCESS, "success", minioUtils.generatePresignedUrl(filename));
        }
        return Response.error(ResponseCode.FORBIDDEN, "File not Found");
    }

    @GetMapping("/parentId")
    public Response<List<File>> getByParentId(@RequestParam String parentId) {
        return Response.success(ResponseCode.SUCCESS, "success", fileService.getByParentId(parentId));
    }

    @DeleteMapping
    public Response<String> deleteFile(@RequestParam String filename, @RequestParam String parentId) {
        File file = fileService.getByNameAndUserId(filename, parentId);
        if (file != null) {
            // 在删除文件之前获取文件信息
            String userId = file.getUserId();
            long fileSize = Long.parseLong(file.getSize()); // 假设File类中有size字段

            // 删除文件
            fileService.removeById(file);

            // 更新统计信息
            fileStatisticsService.updateFileNum(-1);  // 文件数量-1
            fileStatisticsService.updateSize(-fileSize);  // 减少存储空间使用量

            return Response.success(ResponseCode.SUCCESS, "success", null);
        }
        return Response.error(ResponseCode.ERROR, "Target File Not Exists");
    }
}