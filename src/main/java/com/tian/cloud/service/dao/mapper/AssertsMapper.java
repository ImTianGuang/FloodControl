package com.tian.cloud.service.dao.mapper;

import com.tian.cloud.service.dao.entity.Asserts;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AssertsMapper {

    void update(Asserts asserts);

    void insertBatch(@Param("assertsList") List<Asserts> assertsList);

    List<Asserts> getByCompany(int companyId);
}
