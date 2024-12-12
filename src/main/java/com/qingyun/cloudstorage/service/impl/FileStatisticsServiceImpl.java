package com.qingyun.cloudstorage.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingyun.cloudstorage.mapper.FileStatisticsMapper;
import com.qingyun.cloudstorage.pojo.FileStatistics;
import com.qingyun.cloudstorage.service.FileStatisticsService;
import org.springframework.stereotype.Service;

@Service
public class FileStatisticsServiceImpl extends ServiceImpl<FileStatisticsMapper, FileStatistics> implements FileStatisticsService {
}
