package com.tian.cloud.service.service.impl;

import com.google.common.collect.Lists;
import com.tian.cloud.service.TestBase;
import com.tian.cloud.service.dao.entity.Asserts;
import com.tian.cloud.service.service.AssertsService;
import org.junit.Test;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.Assert.*;

public class AssertsServiceImplTest extends TestBase {

    @Resource
    private AssertsService assertsService;

    @Test
    public void saveOrUpdate() {
        List<Asserts> asserts = assertsService.getAssertsByCompany(0);
        for (Asserts asserts1 : asserts) {
            asserts1.setUpdateTime(System.currentTimeMillis());
        }
        assertsService.saveOrUpdate(Lists.newArrayList(asserts));
    }

    @Test
    public void getAssertsByCompany() {
    }
}