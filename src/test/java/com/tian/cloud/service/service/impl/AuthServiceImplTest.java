package com.tian.cloud.service.service.impl;

import com.tian.cloud.service.TestBase;
import com.tian.cloud.service.controller.request.AccountCheckReq;
import com.tian.cloud.service.service.AuthService;
import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Resource;

import static org.junit.Assert.*;

public class AuthServiceImplTest extends TestBase {

    @Resource
    private AuthService authService;

    @Test
    public void checkAccount() {
    }

    @Test
    public void checkToken() {
        AccountCheckReq accountCheckReq = new AccountCheckReq();
        accountCheckReq.setUserName("tianguang");
        accountCheckReq.setPassword("123456");
        String token = authService.checkAccount(accountCheckReq);
//        boolean result = authService.checkToken(token);
//        Assert.assertTrue(result);
    }
}