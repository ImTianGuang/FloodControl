package com.tian.cloud.service.service.impl;

import com.tian.cloud.service.TestBase;
import com.tian.cloud.service.dao.entity.CommonType;
import com.tian.cloud.service.enums.CommonTypeEnum;
import com.tian.cloud.service.service.CommonTypeService;
import org.junit.Test;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.Assert.*;

public class CommonTypeServiceImplTest extends TestBase {

    @Resource
    private CommonTypeService commonTypeService;
    @Test
    public void saveOrUpdate() {

        List<CommonType> commonTypeList = commonTypeService.selectByType(CommonTypeEnum.SITUATION.getCode());

//        commonTypeService.saveOrUpdate(commonTypeList);
    }
}