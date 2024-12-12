package com.qingyun.cloudstorage.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qingyun.cloudstorage.enumerate.ResponseCode;
import com.qingyun.cloudstorage.pojo.Response;
import com.qingyun.cloudstorage.pojo.Share;
import com.qingyun.cloudstorage.service.ShareService;
import com.qingyun.cloudstorage.utils.ThreadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/share")
public class ShareController {

    private final ShareService shareService;

    @Autowired
    public ShareController(ShareService shareService) {
        this.shareService = shareService;
    }

    @GetMapping
    public Response<Share> getShare(@RequestParam String shareId) {
        return Response.success(ResponseCode.SUCCESS, "success", shareService.getById(shareId));
    }

    @PostMapping
    public Response<Share> postShare(@RequestParam String fileId, @RequestParam String duration) {
        shareService.save(new Share(null, ThreadUtils.getUserId(), fileId, duration, "1", null, null));
        return Response.success(ResponseCode.SUCCESS, "success", null);
    }

    @DeleteMapping
    public Response<Share> deleteShare(@RequestParam String shareId) {
        shareService.removeById(shareId);
        return Response.success(ResponseCode.SUCCESS, "success", null);
    }

    @PatchMapping
    public Response<Share> patchShare(@RequestParam Share share) {
        shareService.updateById(share);
        return Response.success(ResponseCode.SUCCESS, "success", null);
    }

    @GetMapping("/all")
    public Response<List<Share>> getAllShare() {
        // Create query wrapper to filter by current user ID
        QueryWrapper<Share> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", ThreadUtils.getUserId());

        // Get all shares for current user
        List<Share> shares = shareService.list(queryWrapper);

        return Response.success(ResponseCode.SUCCESS, "success", shares);
    }
}
