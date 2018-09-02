package com.tian.cloud.service.util;

import com.tian.cloud.service.exception.ErrorCode;
import com.tian.cloud.service.exception.InternalException;

/**
 * @author tianguang
 * 2018/8/27 下午7:56
 **/
public class ParamCheckUtil {

    public static void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new InternalException(ErrorCode.PARAM_ERROR, message);
        }
    }
}
