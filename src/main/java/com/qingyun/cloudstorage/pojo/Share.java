package com.qingyun.cloudstorage.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Share {
    private String id;
    private String userId;
    private String fileId;
    private String duration;
    @JsonIgnore
    private String status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}