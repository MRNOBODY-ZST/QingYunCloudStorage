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
    public Response<File> getFile(@RequestParam String filename, @RequestParam String parentId) {
        return Response.success(ResponseCode.SUCCESS, "success", fileService.getByNameAndUserId(filename, parentId));
    }

    @PostMapping
    public Response<String> postFile(@RequestParam MultipartFile file, @RequestParam String parentId) throws Exception {
        fileService.save(minioUtils.uploadFile(file, parentId));

        return Response.success(ResponseCode.SUCCESS, "success", null);
    }

    @PostMapping("/avatar")
    public Response<String> postAvatar(@RequestParam MultipartFile file) throws Exception {
        File multipartFile = minioUtils.uploadFile(file, "-1");
        multipartFile.setUserId("0");
        fileService.save(multipartFile);
        return Response.success(ResponseCode.SUCCESS, "success", multipartFile.getEtag());
    }

    @GetMapping("/avatar")
    public Response<String> getAvatar(@RequestParam String etag) throws Exception {
        File avatar = fileService.getAvatar(etag);
        return Response.success(ResponseCode.SUCCESS, "success", minioUtils.generatePresignedUrl(avatar.getObjectName()));
    }

    @GetMapping("/download")
    public Response<String> getDownloadUrl(@RequestParam String filename, @RequestParam String parentId) throws Exception {

        if (fileService.getByNameAndUserId(filename, parentId) != null) {
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
        if (fileService.getByNameAndUserId(filename, parentId) != null) {
            fileService.removeById(fileService.getByNameAndUserId(filename, parentId));
            return Response.success(ResponseCode.SUCCESS, "success", null);
        }
        return Response.error(ResponseCode.ERROR, "Target File Not Exists");
    }
}
