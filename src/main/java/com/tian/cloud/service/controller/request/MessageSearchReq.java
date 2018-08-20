package com.tian.cloud.service.controller.request;

import lombok.Data;

/**
 * @author tianguang
 * 2018/8/20 下午8:56
 **/
@Data
public class MessageSearchReq {

    private Integer startId;

    private Integer len;

    private Long startTime;

    private Long endTime;
}
