package com.qingyun.cloudstorage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingyun.cloudstorage.mapper.FileStatisticsMapper;
import com.qingyun.cloudstorage.pojo.FileStatistics;
import com.qingyun.cloudstorage.service.FileStatisticsService;
import com.qingyun.cloudstorage.utils.ThreadUtils;
import org.springframework.stereotype.Service;

@Service
public class FileStatisticsServiceImpl extends ServiceImpl<FileStatisticsMapper, FileStatistics> implements FileStatisticsService {
    @Override
    public void updateFileNum(long delta) {
        // 获取用户的统计信息
        QueryWrapper<FileStatistics> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", ThreadUtils.getUserId());
        FileStatistics statistics = this.getOne(wrapper);

        if (statistics != null) {
            // 转换为数值进行计算
            long currentNum = Long.parseLong(statistics.getFile_num());
            long newNum = currentNum + delta;
            // 确保数值不小于0
            newNum = Math.max(0, newNum);

            // 更新数值
            statistics.setFile_num(String.valueOf(newNum));
            this.updateById(statistics);
        }
    }

    @Override
    public void updateFolderNum(long delta) {
        QueryWrapper<FileStatistics> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", ThreadUtils.getUserId());
        FileStatistics statistics = this.getOne(wrapper);

        if (statistics != null) {
            long currentNum = Long.parseLong(statistics.getFolder_num());
            long newNum = currentNum + delta;
            newNum = Math.max(0, newNum);

            statistics.setFolder_num(String.valueOf(newNum));
            this.updateById(statistics);
        }
    }

    @Override
    public void updateSize(long delta) {
        QueryWrapper<FileStatistics> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", ThreadUtils.getUserId());
        FileStatistics statistics = this.getOne(wrapper);

        if (statistics != null) {
            long currentSize = Long.parseLong(statistics.getSize());
            long newSize = currentSize + delta;
            newSize = Math.max(0, newSize);

            statistics.setSize(String.valueOf(newSize));
            this.updateById(statistics);
        }
    }
}
