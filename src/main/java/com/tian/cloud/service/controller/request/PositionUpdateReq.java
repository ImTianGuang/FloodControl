package com.tian.cloud.service.controller.request;

import lombok.Data;

/**
 * @author tianguang
 * 2018/8/20 下午2:18
 **/
@Data
public class PositionUpdateReq {

    private Integer id;

    private String name;

    private Integer companyId;
}
