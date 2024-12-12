package com.qingyun.cloudstorage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qingyun.cloudstorage.pojo.File;
import com.qingyun.cloudstorage.pojo.Folder;

import java.util.List;

public interface FolderService extends IService<Folder> {
    Folder getByEtagAndUserId(String etag, String userId);

    List<Folder> getAllFoldersByUserId(String userId);

    List<Folder> getFoldersByDirectory(String userId, String parentId);
}
