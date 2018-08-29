package com.tian.cloud.service.dao.mapper;

import com.tian.cloud.service.dao.entity.FloodSituation;

/**
 * @author tianguang
 * 2018/8/28 下午10:08
 **/
public interface SituationMapper {

    FloodSituation getById(Integer situationId);

    void update(FloodSituation floodSituation);
}
