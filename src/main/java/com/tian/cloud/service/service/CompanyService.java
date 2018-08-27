package com.tian.cloud.service.service;

import com.tian.cloud.service.dao.entity.Company;

import java.util.List;

public interface CompanyService {

    List<Company> selectAll();

    List<Company> search(String companyName);
}
