package com.tian.cloud.service.dao.entity;

import com.tian.cloud.service.enums.LineStatusEnum;
import lombok.Data;

/**
 * @author tianguang
 * 2018/8/17 下午6:27
 **/
@Data
public class Message {

    private Integer id;

    private LineStatusEnum status;

    private String title;

    private String content;

    private long createTime;

    private long updateTime;
}
