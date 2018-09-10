package com.tian.cloud.service.service;

import com.tian.cloud.service.controller.response.CompanyInfo;
import com.tian.cloud.service.dao.entity.Company;

import java.util.List;

public interface CompanyService {

    List<Company> selectAll();

    List<Company> search(String companyName);

    CompanyInfo getCompanyInfo(int companyId);

    Company saveOrUpdateCompanyInfo(CompanyInfo companyInfo);

    Company updateCompanyInfo(CompanyInfo companyInfo);

    void saveOrUpdate(List<Company> companyList);

    boolean saveOrUpdate(Company company);
}
