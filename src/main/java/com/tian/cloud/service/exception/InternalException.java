package com.tian.cloud.service.exception;

import lombok.Data;

/**
 * @author tianguang
 * 2018/8/20 下午2:05
 **/
@Data
public class InternalException extends RuntimeException {

    private String errorCode;

    private String errorMsgCn;

    public InternalException(String code, String errorMsgCn, String errorMsg) {
        super(errorMsg);
        this.errorCode = code;
        this.errorMsgCn = errorMsgCn;
    }

    public InternalException(ErrorCode errorCode) {
        super(errorCode.getCode());
        this.errorCode = errorCode.getCode();
        this.errorMsgCn = errorCode.getMsg();
    }
}
