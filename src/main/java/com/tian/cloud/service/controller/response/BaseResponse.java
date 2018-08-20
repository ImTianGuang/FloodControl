package com.tian.cloud.service.controller.response;

import lombok.Data;

/**
 * @author tianguang
 * 2018/8/20 下午2:01
 **/
@Data
public class BaseResponse<T> {

    private boolean ret;

    private String errorCode;

    private String errorMsg;

    private T data;

    public static <T> BaseResponse<T> success(T data) {
        BaseResponse<T> response = new BaseResponse<T>();
        response.setData(data);
        response.setRet(true);
        return response;
    }

    public static <T> BaseResponse<T> fail(String code, String msg) {
        BaseResponse<T> response = new BaseResponse<T>();
        response.setRet(false);
        response.setErrorCode(code);
        response.setErrorMsg(msg);
        return response;
    }
}
