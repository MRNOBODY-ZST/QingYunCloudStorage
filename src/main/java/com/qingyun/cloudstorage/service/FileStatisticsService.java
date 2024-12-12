package com.qingyun.cloudstorage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qingyun.cloudstorage.pojo.FileStatistics;

public interface FileStatisticsService extends IService<FileStatistics> {
    // 文件数量增减
    void updateFileNum(long delta);

    // 文件夹数量增减
    void updateFolderNum(long delta);

    // 存储空间大小增减
    void updateSize(long delta);
}
