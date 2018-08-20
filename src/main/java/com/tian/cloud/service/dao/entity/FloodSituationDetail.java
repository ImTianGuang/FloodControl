package com.tian.cloud.service.dao.entity;

import com.tian.cloud.service.enums.SituationEnum;
import lombok.Data;

/**
 * @author tianguang
 * 2018/8/20 上午11:21
 **/
@Data
public class FloodSituationDetail {

    private Integer id;

    private Integer floodSituationId;

    private SituationEnum situationEnum;

    private Integer situationId;

    private String situationName;

    private String situationValue;

    private long createTime;

    private long updateTime;
}
