package com.tian.cloud.service.controller.response;

import com.google.common.base.Function;
import com.google.common.base.Supplier;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author tianguang
 * 2018/8/20 下午8:47
 **/
@Data
public class PageResponse<T> extends BaseResponse<List<T>> {

    private int nextId;

    public static <T> PageResponse success(List<T> data, int nextId) {
        PageResponse<T> response = new PageResponse<>();
        response.setRet(true);
        response.setData(data);
        response.setNextId(nextId);
        return response;
    }

    public static <T> PageResponse success(List<T> data, Function<T, Integer> idFunction) {

        int next = -1;
        if (!CollectionUtils.isEmpty(data)) {
            next = idFunction.apply(data.get(data.size()-1));
        }
        return success(data, next);
    }

    public static <T> PageResponse<T> pageFail(String code, String msg) {
        PageResponse<T> response = new PageResponse<>();
        response.setRet(false);
        response.setErrorCode(code);
        response.setErrorMsg(msg);
        return response;
    }
}
