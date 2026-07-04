package com.lc.common.result;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;


public enum ResultCode {

    OK(HttpStatus.OK, 20000, "Success"),
    CREATED(HttpStatus.CREATED, 20100, "Created"),
    ACCEPTED(HttpStatus.ACCEPTED, 20200, "Accepted"),
    MULTIPLE_CHOICES(HttpStatus.MULTIPLE_CHOICES, 30000, "Multiple choices"),
    MOVED_PERMANENTLY(HttpStatus.MOVED_PERMANENTLY, 30100, "Move Permanently"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, 40000, "Bad request"),
    BAD_REQUEST_INVALID_PARAMETER(HttpStatus.BAD_REQUEST, 40001, "Invalid parameter"),
    BAD_REQUEST_INVALID_PARAMETER_IS_BLANK(HttpStatus.BAD_REQUEST, 40002, "Invalid parameter, the parameter should not be blank"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, 40100, "Unauthorized"),
    FORBIDDEN(HttpStatus.FORBIDDEN, 40300, "Forbidden"),
    NOT_FOUND(HttpStatus.NOT_FOUND, 40400, "Not found"),
    NOT_ACCEPTABLE(HttpStatus.NOT_ACCEPTABLE, 40600, "Not acceptable"),
    CONFLICT(HttpStatus.CONFLICT, 40900, "Conflict"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 50000, "Internal server error");


    private final HttpStatus httpStatus;

    private final Integer code;

    private final String message;

    ResultCode(HttpStatus httpStatus, Integer code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @JsonValue
    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "ResultCode{" +
                "httpStatus=" + httpStatus +
                ", code=" + code +
                ", message='" + message + '\'' +
                '}';
    }

    @JsonCreator
    public static ResultCode getByCode(Integer code) {
        for (ResultCode resultCode : ResultCode.values()) {
            if (ObjectUtils.nullSafeEquals(code, resultCode.getCode())) {
                return resultCode;
            }
        }
        return null;
    }
}
