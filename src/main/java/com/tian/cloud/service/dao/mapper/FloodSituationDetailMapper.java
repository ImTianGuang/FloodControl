package com.tian.cloud.service.dao.mapper;

import com.tian.cloud.service.dao.entity.FloodSituationDetail;

import java.util.List;

public interface FloodSituationDetailMapper {

    List<FloodSituationDetail> getBySituationId(Integer situationId);

    void saveBatch(List<FloodSituationDetail> saveList);

    void update(FloodSituationDetail detail);
}
