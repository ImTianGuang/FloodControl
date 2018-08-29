package com.tian.cloud.service.service.impl;

import com.tian.cloud.service.TestBase;
import com.tian.cloud.service.dao.entity.Asserts;
import com.tian.cloud.service.service.AssertsService;
import org.junit.Test;

import javax.annotation.Resource;

import static org.junit.Assert.*;

public class AssertsServiceImplTest extends TestBase {

    @Resource
    private AssertsService assertsService;

    @Test
    public void saveOrUpdate() {
        Asserts asserts = new Asserts();
    }

    @Test
    public void getAssertsByCompany() {
    }
}