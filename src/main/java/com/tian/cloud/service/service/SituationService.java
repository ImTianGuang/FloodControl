package com.tian.cloud.service.service;

import com.tian.cloud.service.controller.request.CommonSearchReq;
import com.tian.cloud.service.controller.response.FloodSituationInfo;
import com.tian.cloud.service.dao.entity.FloodSituation;

import java.util.List;

public interface SituationService {

    FloodSituationInfo getSituationInfo(Integer situationId);

    void saveOrUpdate(FloodSituationInfo situationInfo);

    List<FloodSituation> search(CommonSearchReq request);
}
