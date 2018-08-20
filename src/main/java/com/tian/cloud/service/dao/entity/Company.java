package com.tian.cloud.service.dao.entity;

import com.tian.cloud.service.enums.LineStatusEnum;
import lombok.Data;

/**
 * @author tianguang
 * 2018/8/17 下午5:58
 **/
@Data
public class Company {

    private Integer id;

    private String name;

    private Integer status;

    private String address;

    private String postCode;

    private String email;

    private String recordPerson;

    private String recordPersonPhone;

    private String checkPerson;

    private String checkPersonPhone;

    private long createTime;

    private long updateTime;
}
