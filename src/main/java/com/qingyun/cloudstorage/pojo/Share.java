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
    private Long id;
    private Long userId;
    private Long fileId;
    private Integer duration;
    @JsonIgnore
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}