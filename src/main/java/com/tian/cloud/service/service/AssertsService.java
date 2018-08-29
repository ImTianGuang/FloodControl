package com.tian.cloud.service.service;

import com.tian.cloud.service.dao.entity.Asserts;

import java.util.List;

public interface AssertsService {

    void saveOrUpdate(List<Asserts> assertsList);

    List<Asserts> getAssertsByCompany(int companyId);
}
