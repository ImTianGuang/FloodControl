package com.tian.cloud.service.dao.mapper;

import com.tian.cloud.service.dao.entity.CommonType;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommonTypeMapper {

    CommonType selectById(@Param("id") int id);

    List<CommonType> selectByType(@Param("commonTypeEnum") Integer commonTypeEnum);

    List<CommonType> selectAll();

    void saveBatch(@Param("saveList") List<CommonType> saveList);

    void update(CommonType commonType);
}
