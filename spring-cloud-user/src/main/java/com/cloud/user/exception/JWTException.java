package com.cloud.user.exception;

import com.cloud.user.Enum.ResponseEnum;
import com.cloud.user.pojo.BaseResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class JWTException  {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public BaseResponse jsonErrorHandler(HttpServletRequest request, Exception e) throws Exception{
        BaseResponse response = new BaseResponse();
        if(e instanceof BusinessException ){
            response.setCode(((BusinessException)e).getCode());
            response.setMessage(e.getMessage());
        }else{
            response.setCode(ResponseEnum.SYSTEM_ERROR.getCode());
            response.setMessage(ResponseEnum.SYSTEM_ERROR.getMessage());
        }
        return response;
    }
}
