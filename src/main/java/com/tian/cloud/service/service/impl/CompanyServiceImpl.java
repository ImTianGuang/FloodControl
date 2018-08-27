package com.tian.cloud.service.service.impl;

import com.google.common.collect.Lists;
import com.tian.cloud.service.SearchUtil;
import com.tian.cloud.service.controller.response.CompanyInfo;
import com.tian.cloud.service.dao.entity.Asserts;
import com.tian.cloud.service.dao.entity.Company;
import com.tian.cloud.service.dao.entity.User;
import com.tian.cloud.service.dao.mapper.CompanyMapper;
import com.tian.cloud.service.service.AssertsService;
import com.tian.cloud.service.service.CompanyService;
import com.tian.cloud.service.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author tianguang
 * 2018/8/25 下午9:07
 **/
@Service
@Slf4j
public class CompanyServiceImpl implements CompanyService {

    @Resource
    private CompanyMapper companyMapper;

    @Resource
    private UserService userService;

    @Resource
    private AssertsService assertsService;

    @Override
    public List<Company> selectAll() {
        return companyMapper.selectAll();
    }

    @Override
    public List<Company> search(String companyName) {
        return companyMapper.search(companyName);
    }

    @Override
    public CompanyInfo getCompanyInfo(int companyId) {
        Company company = companyMapper.selectById(companyId);
        List<Asserts> asserts = Lists.newArrayList();// todo
        List<User> users = Lists.newArrayList();
        CompanyInfo companyInfo = new CompanyInfo();
        return companyInfo;
    }

    @Override
    public boolean saveCompanyInfo(CompanyInfo companyInfo) {

        this.updateCompany(companyInfo.getCompany());
        assertsService.saveOrUpdate(companyInfo.getAssertsList());
        userService.saveOrUpdate(companyInfo.getPhoneList());
        return true;
    }

    public boolean updateCompany(Company company) {
        company.setUpdateTime(System.currentTimeMillis());
        return companyMapper.updateCompany(company) > 0;
    }
}
