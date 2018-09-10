package com.tian.cloud.service.service;

import com.tian.cloud.service.controller.response.CompanyInfo;
import com.tian.cloud.service.dao.entity.CompanyUser;

import java.util.List;

public interface UserService {
    void saveOrUpdate(List<CompanyInfo.PhoneInfo> phoneList);

    List<CompanyUser> getUserByCompany(int companyId);

    List<CompanyUser> getAllUsableUser();

    void deleteByCompanyId(Integer id);
}
