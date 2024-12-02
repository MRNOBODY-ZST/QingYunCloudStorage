package com.qingyun.cloudstorage.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private String id;
    private String uuid;
    private String userName;
    private String userImage;
    @JsonIgnore
    private String password;
    private String email;
    private String role;
    @JsonIgnore
    private String status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

}
