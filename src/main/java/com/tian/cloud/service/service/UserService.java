package com.tian.cloud.service.service;

import com.tian.cloud.service.controller.response.CompanyInfo;

import java.util.List;

public interface UserService {
    void saveOrUpdate(List<CompanyInfo.PhoneInfo> phoneList);
}
