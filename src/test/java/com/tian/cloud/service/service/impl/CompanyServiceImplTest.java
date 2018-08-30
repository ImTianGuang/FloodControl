package com.tian.cloud.service.service.impl;

import com.google.common.collect.Lists;
import com.tian.cloud.service.TestBase;
import com.tian.cloud.service.dao.entity.Company;
import com.tian.cloud.service.service.CompanyService;
import org.junit.Test;

import javax.annotation.Resource;

import java.util.List;

public class CompanyServiceImplTest extends TestBase {

    @Resource
    private CompanyService companyService;

    @Test
    public void selectAll() {
    }

    @Test
    public void search() {
    }

    @Test
    public void getCompanyInfo() {
    }

    @Test
    public void saveCompanyInfo() {
    }

    @Test
    public void saveCompanyList() {
        List<Company> companies = companyService.selectAll();
        int i = 0;
        for (Company company : companies) {
            company.setName("名称" + i++);
            company.setUpdateTime(System.currentTimeMillis());
        }
        companyService.saveOrUpdate(companies);
    }

}