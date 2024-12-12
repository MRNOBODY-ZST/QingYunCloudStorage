package com.qingyun.cloudstorage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qingyun.cloudstorage.pojo.File;
import com.qingyun.cloudstorage.pojo.Folder;

import java.util.List;

public interface FolderService extends IService<Folder> {
    Folder getByParentIdAndFolderName(String folderName, String parentId);

    List<Folder> getFolderByParentId(String parentId);
}
