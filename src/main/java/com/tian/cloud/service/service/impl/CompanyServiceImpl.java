package com.tian.cloud.service.service.impl;

import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.tian.cloud.service.SearchUtil;
import com.tian.cloud.service.controller.response.CompanyInfo;
import com.tian.cloud.service.dao.entity.Asserts;
import com.tian.cloud.service.dao.entity.Company;
import com.tian.cloud.service.dao.entity.User;
import com.tian.cloud.service.dao.mapper.CompanyMapper;
import com.tian.cloud.service.enums.Orgnization;
import com.tian.cloud.service.service.AssertsService;
import com.tian.cloud.service.service.CompanyService;
import com.tian.cloud.service.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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
        List<Asserts> asserts = assertsService.getAssertsByCompany(companyId);
        List<User> users = userService.getUserByCompany(companyId);
        List<CompanyInfo.PhoneInfo> phoneInfos = toPhoneInfos(users);
        CompanyInfo companyInfo = new CompanyInfo();
        companyInfo.setAssertsList(asserts);
        companyInfo.setCompany(company);
        companyInfo.setPhoneList(phoneInfos);
        return companyInfo;
    }

    @Override
    public boolean saveCompanyInfo(CompanyInfo companyInfo) {

        this.updateCompany(companyInfo.getCompany());
        assertsService.saveOrUpdate(companyInfo.getAssertsList());
        userService.saveOrUpdate(companyInfo.getPhoneList());
        return true;
    }

    @Override
    public void saveCompanyList(List<Company> companyList) {
        if (CollectionUtils.isEmpty(companyList)) {
            return;
        }
        List<Company> updateList = Lists.newArrayList();
        List<Company> saveList = Lists.newArrayList();
        for (Company company : companyList) {
            if (company.getId() == null || company.getId() == 0) {
                saveList.add(company);
            } else {
                updateList.add(company);
            }
        }

        if (!CollectionUtils.isEmpty(updateList)) {
            for (Company update : updateList) {
                companyMapper.updateCompany(update);
            }
        }

        if (!CollectionUtils.isEmpty(saveList)) {
            companyMapper.insertBatch(saveList);
        }
    }

    public boolean updateCompany(Company company) {
        company.setUpdateTime(System.currentTimeMillis());
        return companyMapper.updateCompany(company) > 0;
    }

    private List<CompanyInfo.PhoneInfo> toPhoneInfos(List<User> users) {
        if (CollectionUtils.isEmpty(users)) {
            return Lists.newArrayList();
        }
        ImmutableListMultimap<Integer, User> orgCodeMap = Multimaps.index(users, User::getOrgCode);
        List<CompanyInfo.PhoneInfo> phoneInfos = Lists.newArrayList();
        for (Orgnization orgnization : Orgnization.values()) {
            List<User> userList = orgCodeMap.get(orgnization.getCode());
            CompanyInfo.PhoneInfo phoneInfo = new CompanyInfo.PhoneInfo();
            phoneInfo.setId(orgnization.getCode());
            phoneInfo.setName(orgnization.getMsg());
            phoneInfo.setUserList(userList);

            phoneInfos.add(phoneInfo);
        }

        return phoneInfos;
    }
}
