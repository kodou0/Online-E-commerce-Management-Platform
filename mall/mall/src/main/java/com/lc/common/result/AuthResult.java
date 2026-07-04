package com.lc.common.result;


import lombok.Data;

//权限相关的结果返回类
@Data
public class AuthResult<T> {
    private Integer code;

    private String message;

    private T data;

    public AuthResult(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
