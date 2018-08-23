package com.tian.cloud.service.dao.mapper;

import com.tian.cloud.service.dao.entity.CommonType;
import org.apache.ibatis.annotations.Param;

public interface CommonTypeMapper {

    CommonType selectById(@Param("id") int id);
}
