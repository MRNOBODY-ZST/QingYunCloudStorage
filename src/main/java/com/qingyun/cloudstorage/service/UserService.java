package com.qingyun.cloudstorage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qingyun.cloudstorage.pojo.User;

public interface UserService extends IService<User> {
    public void register(User user);
}
