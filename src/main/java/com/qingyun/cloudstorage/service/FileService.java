package com.qingyun.cloudstorage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qingyun.cloudstorage.pojo.File;

import java.util.List;

public interface FileService extends IService<File> {
    File getByNameAndUserId(String filename, String parentId);

    List<File> getByParentId(String parentId);

    File getAvatar(String etag);
}
