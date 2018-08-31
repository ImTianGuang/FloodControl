package com.tian.cloud.service.dao.mapper;

import com.tian.cloud.service.controller.request.CommonSearchReq;
import com.tian.cloud.service.dao.entity.FloodSituation;

import java.util.List;

/**
 * @author tianguang
 * 2018/8/28 下午10:08
 **/
public interface SituationMapper {

    FloodSituation getById(Integer situationId);

    void update(FloodSituation floodSituation);

    void save(FloodSituation floodSituation);

    List<FloodSituation> search(CommonSearchReq request);
}
