package com.qingyun.cloudstorage.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Folder {
    private Long id;
    private Long userId;
    private Long parentId;
    private String bucketName;
    private String folderName;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
