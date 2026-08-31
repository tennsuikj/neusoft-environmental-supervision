package com.example.exception;

//全局异常（系统自带）
import com.example.common.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ControllerAdvice;



@ControllerAdvice("com.example.controller")
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody//json语句
    public Result error(Exception e){
        e.printStackTrace();
        return Result.error();
    }

    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public Result error(CustomException e){
        return Result.error(e.getCode(),e.getMsg());
    }
}
