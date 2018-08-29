package com.tian.cloud.service.service.impl;

import com.google.common.collect.Lists;
import com.tian.cloud.service.TestBase;
import com.tian.cloud.service.controller.response.CompanyInfo;
import com.tian.cloud.service.dao.entity.User;
import com.tian.cloud.service.dao.mapper.UserMapper;
import com.tian.cloud.service.enums.Orgnization;
import com.tian.cloud.service.service.UserService;
import org.junit.Test;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.Assert.*;

public class UserServiceImplTest extends TestBase {

    @Resource
    private UserService userService;

    @Test
    public void saveOrUpdate() {
        List<CompanyInfo.PhoneInfo> phoneInfos = Lists.newArrayList();
        CompanyInfo.PhoneInfo phoneInfo = new CompanyInfo.PhoneInfo();
        phoneInfo.setId(Orgnization.ORG1.getCode());
        phoneInfo.setName(Orgnization.ORG1.getMsg());
        List<User> userList = userService.getUserByCompany(0);
        for (User user : userList) {
            user.setUpdateTime(System.currentTimeMillis());
        }

        phoneInfo.setUserList(userList);

        phoneInfos.add(phoneInfo);
        userService.saveOrUpdate(phoneInfos);
        System.out.println(1);
    }

    @Test
    public void getUserByCompany() {
        List<User> userList = userService.getUserByCompany(0);
        System.out.println(1);
    }
}