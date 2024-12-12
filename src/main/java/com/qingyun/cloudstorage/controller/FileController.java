package com.qingyun.cloudstorage.controller;

import com.qingyun.cloudstorage.enumerate.ResponseCode;
import com.qingyun.cloudstorage.mapper.FileMapper;
import com.qingyun.cloudstorage.pojo.File;
import com.qingyun.cloudstorage.pojo.Response;
import com.qingyun.cloudstorage.service.FileService;
import com.qingyun.cloudstorage.utils.MinioUtils;
import com.qingyun.cloudstorage.utils.ThreadUtils;
import jakarta.validation.constraints.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/file")
public class FileController {

    private final FileService fileService;
    private final MinioUtils minioUtils;

    @Autowired
    private FileController(FileService fileService, MinioUtils minioUtils) {
        this.fileService = fileService;
        this.minioUtils = minioUtils;
    }

    @GetMapping
    public Response<File> getFile(@RequestParam String filename) {
        return Response.success(ResponseCode.SUCCESS, "success", fileService.getByNameAndUserId(filename));
    }

    @PostMapping
    public Response<Null> postFile(@RequestParam("file") MultipartFile multipartFile, @RequestParam("public") String parentId) throws Exception {
        File file = minioUtils.uploadFile(multipartFile, parentId);
        fileService.save(file);
        return Response.success(ResponseCode.SUCCESS, "success", null);
    }

    @GetMapping("/download")
    public Response<String> getDownloadUrl(@RequestParam String filename) throws Exception {
        if(fileService.getByNameAndUserId(filename) != null) {
            return Response.success(ResponseCode.SUCCESS, "success", minioUtils.generatePresignedUrl(filename));
        }
        return Response.error(ResponseCode.FORBIDDEN, "File not Found");
    }
}
