package com.tian.cloud.service.dao.entity;

import com.tian.cloud.service.enums.SituationTargetEnum;
import lombok.Data;

/**
 * @author tianguang
 * 2018/8/20 上午11:21
 **/
@Data
public class FloodSituationDetail {

    private Integer id;

    private Integer floodSituationId;

    private SituationTargetEnum situationTargetEnum;

    private Integer targetId;

    private String targetValue;

    private long createTime;

    private long updateTime;
}
