package com.tian.cloud.service.controller.response;

import lombok.Data;

import java.util.List;

/**
 * @author tianguang
 * 2018/8/20 下午8:47
 **/
@Data
public class PageResponse<T> extends BaseResponse<List<T>> {

    private int total;

    public static <T> PageResponse success(List<T> data, int total) {
        PageResponse<T> response = new PageResponse<>();
        response.setRet(true);
        response.setData(data);
        response.setTotal(total);
        return response;
    }

    public static <T> PageResponse<T> pageFail(String code, String msg) {
        PageResponse<T> response = new PageResponse<>();
        response.setRet(false);
        response.setErrorCode(code);
        response.setErrorMsg(msg);
        return response;
    }
}
