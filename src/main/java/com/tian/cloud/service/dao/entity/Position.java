package com.tian.cloud.service.dao.entity;

import lombok.Data;

/**
 * @author tianguang
 * 2018/8/17 下午6:02
 **/
@Data
public class Position {

    private Integer id;

    private String name;

    private Integer companyId;

    private long createTime;

    private long updateTime;
}