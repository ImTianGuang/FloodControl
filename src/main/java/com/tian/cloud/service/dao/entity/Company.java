package com.tian.cloud.service.dao.entity;

import lombok.Data;

/**
 * @author tianguang
 * 2018/8/17 下午5:58
 **/
@Data
public class Company {

    private Integer id;

    private String name = "";

    private Integer status;

    private String address = "";

    private String postCode = "";

    private String email = "";

    private String recordPerson = "";

    private String recordPersonPhone = "";

    private String checkPerson = "";

    private String checkPersonPhone = "";

    private String floodPlan = "";

    private long createTime = System.currentTimeMillis();

    private long updateTime = System.currentTimeMillis();
}
