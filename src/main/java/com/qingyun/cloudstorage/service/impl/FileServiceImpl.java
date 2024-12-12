package com.qingyun.cloudstorage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingyun.cloudstorage.mapper.FileMapper;
import com.qingyun.cloudstorage.pojo.File;
import com.qingyun.cloudstorage.service.FileService;
import com.qingyun.cloudstorage.utils.ThreadUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, File> implements FileService {
    @Override
    public File getByNameAndUserId(String filename) {
        String userId = ThreadUtils.getUserId();
        return this.getOne(new QueryWrapper<File>().eq("object_name", filename).and(wrapper -> wrapper.eq("user_id", userId).or().eq("user_id", "0")).last("LIMIT 1"));
    }

    @Override
    public List<File> getAllFilesByUserId(String userId) {
        QueryWrapper<File> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        return this.list(queryWrapper);
    }

    @Override
    public List<File> getFilesByDirectory(String parentId) {
        QueryWrapper<File> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", ThreadUtils.getUserId()).eq("parent_id", parentId);
        return this.list(queryWrapper);
    }

}
