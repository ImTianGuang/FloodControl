package com.tian.cloud.service.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author tianguang
 * 2018/8/20 下午2:06
 **/
@Data
@AllArgsConstructor
public class ErrorCode {

    private String code;

    private String msg;

    public static final ErrorCode SYS_ERROR = new ErrorCode("0001", "系统异常");
    public static final ErrorCode PARAM_ERROR = new ErrorCode("0002", "参数错误");
    public static final ErrorCode EMAIL_SEND_FAIL = new ErrorCode("0003", "邮件发送失败");
}
