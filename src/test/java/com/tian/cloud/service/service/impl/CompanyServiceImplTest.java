package com.tian.cloud.service.service.impl;

import com.google.common.collect.Lists;
import com.tian.cloud.service.TestBase;
import com.tian.cloud.service.dao.entity.Company;
import com.tian.cloud.service.service.CompanyService;
import org.junit.Test;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.Assert.*;

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
        List<Company> companies = Lists.newArrayList();
        Company company = new Company();
        company.setName("111");
        company.setStatus(1);
        company.setAddress("222");
        company.setPostCode("333");
        company.setEmail("444");
        company.setRecordPerson("aaa");
        company.setRecordPersonPhone("123");
        company.setCheckPerson("bbb");
        companyService.saveCompanyList(companies);
    }

    @Test
    public void updateCompany() {
    }
}