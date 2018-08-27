package com.tian.cloud.service.dao.mapper;

import com.tian.cloud.service.dao.entity.Asserts;

import java.util.List;

public interface AssertsMapper {

    void updateBatch(List<Asserts> updateAsserts);

    void insertBatch(List<Asserts> saveAsserts);

}
