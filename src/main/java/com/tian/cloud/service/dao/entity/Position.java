package com.tian.cloud.service.dao.entity;

import lombok.Data;

/**
 * @author tianguang
 * 2018/8/17 下午6:02
 **/
@Data
public class Position {

    private Integer id;

    private Integer companyId;

    private String name;

    private Integer status;

    private long createTime;

    private long updateTime;
}
