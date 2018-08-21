package com.tian.cloud.service.controller.response;

import com.tian.cloud.service.dao.entity.Company;
import com.tian.cloud.service.dao.entity.FloodSituation;
import com.tian.cloud.service.dao.entity.FloodSituationDetail;
import lombok.Data;

import java.util.List;

/**
 * @author tianguang
 * 2018/8/21 下午1:36
 **/
@Data
public class FloodSituationInfo {

    private Company company;

    private FloodSituation floodSituation;

    private List<FloodSituationDetail> situationDetailList;

    private List<FloodSituationDetail> solutionDetailList;
}
