package com.qingyun.cloudstorage.controller;

import cn.hutool.crypto.digest.DigestUtil;
import com.qingyun.cloudstorage.enumerate.ResponseCode;
import com.qingyun.cloudstorage.pojo.Response;
import com.qingyun.cloudstorage.pojo.User;
import com.qingyun.cloudstorage.service.UserService;
import com.qingyun.cloudstorage.utils.JwtUtils;
import com.qingyun.cloudstorage.utils.ThreadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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

    @PostMapping
    private Response<String> postUser(User user) {
        userService.register(user);
        return Response.success(ResponseCode.SUCCESS, "success", null);
    }

    @PatchMapping
    private Response<String> patchUser(User user) {
        if (user.getId().equals(ThreadUtils.getUserId())) {
            userService.updateById(user);
            return Response.success(ResponseCode.SUCCESS, "success", null);
        }
        return Response.error(ResponseCode.ERROR, "No Privilege");
    }

    @PostMapping("/login")
    private Response<String> postUserLogin(@RequestParam String username, @RequestParam String password) {
        User user = userService.lambdaQuery().eq(User::getUsername, username).one();
        if (user == null || !user.getPassword().equals(DigestUtil.md5Hex(password))) {
            return Response.error(ResponseCode.ERROR, "Incorrect Username or Password");
        }
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        claims.put("role", user.getRole());
        return Response.success(ResponseCode.SUCCESS, "success", JwtUtils.encode(claims));
    }


}
