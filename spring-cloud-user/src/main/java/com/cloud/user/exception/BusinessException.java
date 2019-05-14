package com.cloud.user.exception;

import com.cloud.user.Enum.ResponseEnum;

public class BusinessException extends Exception {
    private int code;
    private String message;

    public BusinessException(int code,String message) {
        this.message=message;
        this.code = code;
    }

    public BusinessException(ResponseEnum responseEnum){
        super(responseEnum.getMessage());
        this.code=responseEnum.getCode();
    }

    //直接构建系统内部错误
    public static BusinessException systemException(String message){
        return new BusinessException(ResponseEnum.SYSTEM_ERROR.getCode(),message);
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
