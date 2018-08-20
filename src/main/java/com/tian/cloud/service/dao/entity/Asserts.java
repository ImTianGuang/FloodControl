package com.tian.cloud.service.dao.entity;

import lombok.Data;

/**
 * @author tianguang
 * 2018/8/20 上午11:13
 **/
@Data
public class Asserts {

    private Integer id;

    private Integer companyId;

    private Integer assertsTypeId;

    private String assertsTypeName;

    private String assertsValue;

    private Integer status;

    private long createTime;

    private long updateTime;
}
