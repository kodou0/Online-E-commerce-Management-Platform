package com.lc.common.result;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result<T> {

    private ResultCode code;

    private String message;

    private T data;

    private Integer pageNum;

    private Integer pageSize;

    private Integer total;

    public Result() {
    }

    public Result(ResultCode code) {
        this(code, code.getMessage());
    }

    public Result(ResultCode code, T data) {
        this(code, code.getMessage(), data);
    }

    public Result(ResultCode code, String message) {

        this(code, message, null);
    }

    public Result(ResultCode code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Result(ResultCode code, String message, T data,Integer pageNum,Integer pageSize,Integer total) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.pageSize=pageSize;
        this.pageNum=pageNum;
        this.total=total;
    }

    public Result(ResultCode code, T data, Integer total) {
        this.code = code;
        this.data = data;
        this.total = total;
    }


    public static <T> Result<T> ok() {
        return new Result<>(ResultCode.OK);
    }

    public static <T> Result<T> ok(T data) {
        return new Result<>(ResultCode.OK, data);
    }

    public static <T> Result<T> ok(T data,String message,Integer pageNum,Integer pageSize,Integer total) {
        return new Result<>(ResultCode.OK, message,data,pageNum,pageSize,total);
    }

    public static <T> Result<T> created() {
        return new Result<>(ResultCode.CREATED);
    }

    public static <T> Result<T> created(T data) {
        return new Result<>(ResultCode.CREATED, data);
    }

    public static <T> Result<T> error() {
        return new Result<>(ResultCode.BAD_REQUEST);
    }

    public static <T> Result<T> error(String message) {
        return new Result<>(ResultCode.BAD_REQUEST,message);
    }

    public ResultCode getCode() {
        return code;
    }

    public void setCode(ResultCode code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
