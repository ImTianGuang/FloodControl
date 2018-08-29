package com.tian.cloud.service.service;

import com.tian.cloud.service.controller.response.CompanyInfo;
import com.tian.cloud.service.dao.entity.User;

import java.util.List;

public interface UserService {
    void saveOrUpdate(List<CompanyInfo.PhoneInfo> phoneList);

    List<User> getUserByCompany(int companyId);
}
