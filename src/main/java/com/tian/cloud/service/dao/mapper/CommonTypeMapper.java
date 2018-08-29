package com.tian.cloud.service.dao.mapper;

import com.tian.cloud.service.dao.entity.CommonType;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommonTypeMapper {

    CommonType selectById(@Param("id") int id);

    List<CommonType> selectByTypeAndStatus(@Param("commonTypeEnum") Integer commonTypeEnum, @Param("status") int status);

    List<CommonType> selectAllByStatus(@Param("status") int status);

    void saveBatch(List<CommonType> saveList);

    void update(CommonType commonType);
}
