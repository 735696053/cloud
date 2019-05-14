package com.cloud.user.Enum;

import com.cloud.user.pojo.BaseResponse;

public enum ResponseEnum {
    SYSTEM_ERROR(500,"系统异常"),
    USER_ERROR_TOKEN(500,"token失效"),
    USER_ERROR_USERNAME(400,"用户名不存在");

    private int code;
    private String message;

    ResponseEnum(int code, String message) {
        this.code=code;
        this.message=message;
    }



    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
