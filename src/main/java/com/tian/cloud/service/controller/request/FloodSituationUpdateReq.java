package com.tian.cloud.service.controller.request;

import com.tian.cloud.service.enums.SituationEnum;
import lombok.Data;

import java.util.List;

/**
 * @author tianguang
 * 2018/8/20 下午4:33
 **/
@Data
public class FloodSituationUpdateReq {

    private Integer floodSituationId;

    private Long startTime;

    private Long endTime;

    private String floodDesc;

    private List<SituationDetail> situationDetails;

    private List<SituationDetail> solutionDetails;

    @Data
    public static class SituationDetail {

        private Integer situationId;

        private Integer status;

        private String situationValue;
    }
}
