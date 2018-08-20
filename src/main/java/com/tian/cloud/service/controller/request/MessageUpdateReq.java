package com.tian.cloud.service.controller.request;

import lombok.Data;

/**
 * @author tianguang
 * 2018/8/20 下午4:15
 **/
@Data
public class MessageUpdateReq {

    private Integer id;

    private Integer status;

    private String title;

    private String content;
}
