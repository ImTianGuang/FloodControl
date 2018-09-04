package com.tian.cloud.service.dao.mapper;

import com.tian.cloud.service.dao.entity.FloodSituationDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FloodSituationDetailMapper {

    List<FloodSituationDetail> getBySituationId(@Param("situationId") Integer situationId);

    void saveBatch(@Param("saveList") List<FloodSituationDetail> saveList);

    void update(FloodSituationDetail detail);

    void deleteByTargetId(@Param("targetId") Integer targetId);
}
