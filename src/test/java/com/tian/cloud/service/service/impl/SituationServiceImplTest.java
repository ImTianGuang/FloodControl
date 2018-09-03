package com.tian.cloud.service.service.impl;

import com.tian.cloud.service.TestBase;
import com.tian.cloud.service.controller.request.CommonSearchReq;
import com.tian.cloud.service.dao.entity.FloodSituation;
import com.tian.cloud.service.service.SituationService;
import org.junit.Test;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.Assert.*;

public class SituationServiceImplTest extends TestBase {

    @Resource
    private SituationService situationService;

    @Test
    public void saveOrUpdate() {
    }

    @Test
    public void search() {
        CommonSearchReq commonSearchReq = new CommonSearchReq();
        commonSearchReq.setLen(15);
        List<FloodSituation> floodSituationList = situationService.search(commonSearchReq);
        System.out.println();
    }
}