package com.tian.cloud.service.dao.entity;

import lombok.Data;

/**
 * @author tianguang
 * 2018/9/7 下午6:52
 **/
@Data
public class FloodUser {

    private int id;

    private String userName;

    private String password;

    private Integer status;

    private long createTime;

    private long updateTime;
}
