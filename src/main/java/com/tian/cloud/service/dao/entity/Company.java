package com.tian.cloud.service.dao.entity;

import lombok.Data;

/**
 * @author tianguang
 * 2018/8/17 下午5:58
 **/
@Data
public class Company {

    private int id;

    private String name;

    private String address;

    private String postCode;

    private String email;

    private long createTime;

    private long updateTime;
}
