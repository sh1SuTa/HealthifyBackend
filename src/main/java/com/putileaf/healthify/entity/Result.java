package com.putileaf.healthify.entity;


//统一响应结果

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Result<T> {
    private Integer code;//业务状态码  0-成功  1-失败
    private String message;//提示信息
    private T data;//响应数据

    //快速返回操作成功响应结果(不带响应数据)
    public static Result<String> success(String message) {
        return new Result<>(0, message, null);
    }
    public static <E> Result<E> success(String message,E data) {
        return new Result<>(0, message, data);
    }
    public static Result<String> error(String message) {
        return new Result<>(1, message, null);
    }
}
