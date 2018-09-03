package com.tian.cloud.service.controller.advice;

import com.tian.cloud.service.controller.response.BaseResponse;
import com.tian.cloud.service.exception.ErrorCode;
import com.tian.cloud.service.exception.InternalException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author tianguang
 * 2018/9/2 下午9:01
 **/
@RestControllerAdvice("com.tian.cloud.service.controller")
@Slf4j
public class ControllerAdvice {

    /**
     * 异常处理
     *
     * @return
     */
    @ExceptionHandler({Exception.class})
    @ResponseBody
    public Object handException(Exception e) {
        log.error("未知错误:", e);
        return BaseResponse.fail(ErrorCode.SYS_ERROR.getCode(), ErrorCode.SYS_ERROR.getMsg());
    }

    @ExceptionHandler({InternalException.class})
    @ResponseBody
    public Object handInternalException(InternalException e) {
        log.warn("内部错误:", e);
        return BaseResponse.fail(e.getErrorCode(), e.getErrorMsgCn());
    }
}
