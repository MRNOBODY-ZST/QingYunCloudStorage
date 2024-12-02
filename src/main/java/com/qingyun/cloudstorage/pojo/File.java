package com.qingyun.cloudstorage.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class File {
    private Long id;
    private Long userId;
    private Long parentId;
    private String bucketName;
    private String objectName;
    private String etag;
    private Long size;
    private String contentType;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    @JsonIgnore
    private Integer status;
}
