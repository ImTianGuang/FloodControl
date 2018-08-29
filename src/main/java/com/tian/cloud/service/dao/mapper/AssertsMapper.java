package com.tian.cloud.service.dao.mapper;

import com.tian.cloud.service.dao.entity.Asserts;

import java.util.List;

public interface AssertsMapper {

    void update(Asserts asserts);

    void insertBatch(List<Asserts> saveAsserts);

    List<Asserts> getByCompany(int companyId);
}
