package com.tian.cloud.service.dao.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author tianguang
 * 2018/8/20 下午1:48
 **/
@Data
@NoArgsConstructor
public class CommonType {

    private Integer id;

    private Integer commonTypeEnum;

    private Integer status;

    private String name;

    private long createTime;

    private long updateTime;
}
