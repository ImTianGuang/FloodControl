package com.tian.cloud.service.dao.mapper;

import com.tian.cloud.service.dao.entity.CommonType;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface CommonTypeMapper {

    CommonType selectById(@Param("id") int id);

    List<CommonType> selectByTypeAndStatus(@RequestParam("commonTypeEnum") Integer commonTypeEnum, int status);

    List<CommonType> selectAllByStatus(int status);
}
