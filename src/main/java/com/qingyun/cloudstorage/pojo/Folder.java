package com.qingyun.cloudstorage.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Folder {
    private String id;
    private String userId;
    private String parentId;
    private String folderName;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
