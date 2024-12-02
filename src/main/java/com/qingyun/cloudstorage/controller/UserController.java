package com.qingyun.cloudstorage.controller;

import cn.hutool.crypto.digest.DigestUtil;
import com.qingyun.cloudstorage.enumerate.ResponseCode;
import com.qingyun.cloudstorage.pojo.Response;
import com.qingyun.cloudstorage.pojo.User;
import com.qingyun.cloudstorage.service.UserService;
import com.qingyun.cloudstorage.utils.JwtUtils;
import com.qingyun.cloudstorage.utils.ThreadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    private final JwtUtils jwtUtils;

    @Autowired
    private UserController(UserService userService, JwtUtils jwtUtils) {
        this.userService = userService;
        this.jwtUtils = jwtUtils;
    }

    @GetMapping
    private Response<User> getUser() {
        return Response.success(ResponseCode.SUCCESS, "success", userService.getById(ThreadUtils.getUserId()));
    }

    @PostMapping("/register")
    private Response<String> postUser(User user) {
        userService.register(user);
        return Response.success(ResponseCode.SUCCESS, "success", null);
    }

    @PostMapping("/login")
    private Response<String> postUserLogin(String username, String password) {
        User user = userService.lambdaQuery().eq(User::getUserName, username).one();
        if (user == null || user.getPassword().equals(DigestUtil.md5Hex(password))) {
            return Response.error(ResponseCode.ERROR, "Incorrect Username or Password");
        }
        return Response.success(ResponseCode.SUCCESS, "success", jwtUtils.generateToken(user.getId(), username));
    }


}
