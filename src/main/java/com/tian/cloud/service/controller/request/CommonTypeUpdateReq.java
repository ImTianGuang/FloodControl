package com.tian.cloud.service.controller.request;

import lombok.Data;

/**
 * @author tianguang
 * 2018/8/20 下午3:50
 **/
@Data
public class CommonTypeUpdateReq {

    private Integer id;

    private Integer commonTypeCode;

    private String name;
}
