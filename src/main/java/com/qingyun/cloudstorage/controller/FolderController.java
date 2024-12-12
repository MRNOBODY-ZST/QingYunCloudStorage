package com.qingyun.cloudstorage.controller;

import com.qingyun.cloudstorage.enumerate.ResponseCode;
import com.qingyun.cloudstorage.pojo.Folder;
import com.qingyun.cloudstorage.pojo.Response;
import com.qingyun.cloudstorage.service.FolderService;
import com.qingyun.cloudstorage.utils.ThreadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/folder")
public class FolderController {

    private final FolderService folderService;

    @Autowired
    public FolderController(FolderService folderService) {
        this.folderService = folderService;
    }

    @GetMapping
    public Response<Folder> getFolder(@RequestParam String folderId) {
        return Response.success(ResponseCode.SUCCESS, "success", folderService.getById(folderId));
    }

    @PostMapping
    public Response<String> postFolder(@RequestParam String folderName, @RequestParam String parentId) {
        if (folderService.getByParentIdAndFolderName(folderName, parentId) == null) {
            folderService.save(new Folder(null, ThreadUtils.getUserId(), parentId, folderName, null, null));
            return Response.success(ResponseCode.SUCCESS, "success", null);
        }
        return Response.error(ResponseCode.ERROR, "Folder Already Exists");
    }

    @DeleteMapping
    public Response<Void> deleteFolder(@RequestParam String folderName, @RequestParam String parentId) {
        if (folderService.getByParentIdAndFolderName(folderName, parentId) != null) {
            folderService.removeById(folderService.getByParentIdAndFolderName(folderName, parentId));
            return Response.success(ResponseCode.SUCCESS, "success", null);
        }
        return Response.error(ResponseCode.ERROR, "Target Folder Not Exist");
    }

    @GetMapping("/parentId")
    public Response<List<Folder>> getByParentId(@RequestParam String parentId) {
        return Response.success(ResponseCode.SUCCESS,"success",folderService.getFolderByParentId(parentId));
    }
}
