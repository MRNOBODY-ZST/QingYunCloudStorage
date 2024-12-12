package com.qingyun.cloudstorage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingyun.cloudstorage.mapper.FolderMapper;
import com.qingyun.cloudstorage.pojo.Folder;
import com.qingyun.cloudstorage.service.FolderService;
import com.qingyun.cloudstorage.utils.ThreadUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FolderServiceImpl extends ServiceImpl<FolderMapper, Folder> implements FolderService {
    @Override
    public Folder getByParentIdAndFolderName(String folderName, String parentId) {
        QueryWrapper<Folder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("folder_name",folderName).eq("parent_id",parentId).eq("user_id", ThreadUtils.getUserId());
        return this.getOne(queryWrapper);
    }

    @Override
    public List<Folder> getFolderByParentId(String parentId) {
        QueryWrapper<Folder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", ThreadUtils.getUserId()).eq("parent_id", parentId);
        return this.list(queryWrapper);
    }
}
