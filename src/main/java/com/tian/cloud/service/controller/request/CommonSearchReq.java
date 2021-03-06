package com.tian.cloud.service.controller.request;

import lombok.Data;

/**
 * @author tianguang
 * 2018/8/20 下午8:56
 **/
@Data
public class CommonSearchReq {

    private Integer startId;

    private Integer len;

    private String keyword;

    private String startDateStr;

    private String endDateStr;

    private Long startTime;

    private Long endTime;

    private  String emails;
}
