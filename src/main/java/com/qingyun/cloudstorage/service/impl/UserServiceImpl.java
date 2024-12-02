package com.qingyun.cloudstorage.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingyun.cloudstorage.mapper.UserMapper;
import com.qingyun.cloudstorage.pojo.User;
import com.qingyun.cloudstorage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public void register(User user) {
        user.setId(null);
        user.setUserImage(null);
        user.setUuid(IdUtil.getSnowflakeNextIdStr());
        user.setPassword(DigestUtil.md5Hex(user.getPassword()));
        userMapper.insert(user);
    }

}
