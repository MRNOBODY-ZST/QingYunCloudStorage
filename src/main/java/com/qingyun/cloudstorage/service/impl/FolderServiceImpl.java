package com.qingyun.cloudstorage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingyun.cloudstorage.mapper.FolderMapper;
import com.qingyun.cloudstorage.pojo.Folder;
import com.qingyun.cloudstorage.service.FolderService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FolderServiceImpl extends ServiceImpl<FolderMapper, Folder> implements FolderService {
    @Override
    public Folder getByEtagAndUserId(String etag, String userId) {
        return this.getOne(new QueryWrapper<Folder>().eq("etag", etag).and(wrapper -> wrapper.eq("user_id", userId).or().eq("user_id", "0")).last("LIMIT 1"));
    }

    @Override
    public List<Folder> getAllFoldersByUserId(String userId) {
        QueryWrapper<Folder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        return this.list(queryWrapper);
    }

    @Override
    public List<Folder> getFoldersByDirectory(String userId, String parentId) {
        QueryWrapper<Folder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId).eq("parent_id", parentId);
        return this.list(queryWrapper);
    }
}
