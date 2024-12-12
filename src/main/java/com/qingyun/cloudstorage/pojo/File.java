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
    private String id;
    private String userId;
    private String parentId;
    private String objectName;
    private String etag;
    private String size;
    private String contentType;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    @JsonIgnore
    private Integer status;
}
