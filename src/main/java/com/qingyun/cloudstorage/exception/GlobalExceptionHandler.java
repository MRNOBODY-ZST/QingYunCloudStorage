package com.qingyun.cloudstorage.exception;

import com.qingyun.cloudstorage.enumerate.ResponseCode;
import com.qingyun.cloudstorage.pojo.Response;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public Response<String> exceptionHandler(Exception exception) {
        return Response.error(ResponseCode.ERROR,exception.getMessage());
    }

}
