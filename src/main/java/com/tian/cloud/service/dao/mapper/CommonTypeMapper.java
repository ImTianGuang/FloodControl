package com.tian.cloud.service.dao.mapper;

import com.tian.cloud.service.dao.entity.CommonType;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

public interface CommonTypeMapper {

    CommonType selectById(@Param("id") int id);

    List<CommonType> selectUsableByType(@Param("commonTypeEnum") Integer commonTypeEnum);

    List<CommonType> selectAllUsable();

    void saveBatch(@Param("saveList") List<CommonType> saveList);

    void update(CommonType commonType);

    List<CommonType> selectAllByType(int code);

    List<CommonType> selectAllByTypes(@Param("typeIdList") List<Integer> typeIdList);

    List<CommonType> selectByIdList(@Param("idList") List<Integer> situationIdList);
}
