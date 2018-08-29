package com.tian.cloud.service.service;

import com.tian.cloud.service.controller.response.FloodSituationInfo;

public interface SituationService {

    FloodSituationInfo getSituationInfo(Integer situationId);

    void saveOrUpdate(FloodSituationInfo situationInfo);
}
