package com.qingyun.cloudstorage.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Response<T> {
    private Integer code;
    private String message;
    private T data;
    private LocalDateTime timestamp;

    public static <T> Response<T> success(Integer code, String message, T data) {
        return new Response<>(code, message, data, LocalDateTime.now());
    }

    public static <T> Response<T> error(Integer code, String message) {

        return new Response<>(code, message, null, LocalDateTime.now());
    }
}
