package com.qingyun.cloudstorage.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingyun.cloudstorage.mapper.ShareMapper;
import com.qingyun.cloudstorage.pojo.Share;
import com.qingyun.cloudstorage.service.ShareService;
import org.springframework.stereotype.Service;

@Service
public class ShareServiceImpl extends ServiceImpl<ShareMapper, Share> implements ShareService {
}
