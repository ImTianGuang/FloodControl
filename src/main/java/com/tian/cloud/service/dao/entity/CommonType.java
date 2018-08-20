package com.tian.cloud.service.dao.entity;

import com.tian.cloud.service.enums.CommenTypeEnum;
import lombok.Data;

/**
 * @author tianguang
 * 2018/8/20 下午1:48
 **/
@Data
public class CommonType {

    private Integer id;

    private CommenTypeEnum commenTypeEnum;

    private String name;

    private long createTime;

    private long updateTIme;
}
