package com.tian.cloud.service.dao.entity;

import lombok.Data;

/**
 * @author tianguang
 * 2018/8/20 上午11:13
 **/
@Data
public class Asserts {

    private int id;

    private int companyId;

    private int assertsTypeId;

    private int assertsValue;

    private long createTime;

    private long updateTime;
}
