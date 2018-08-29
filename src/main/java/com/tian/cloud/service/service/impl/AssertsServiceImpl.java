package com.tian.cloud.service.service.impl;

import com.google.common.collect.Lists;
import com.tian.cloud.service.dao.entity.Asserts;
import com.tian.cloud.service.dao.mapper.AssertsMapper;
import com.tian.cloud.service.service.AssertsService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author tianguang
 * 2018/8/27 下午7:50
 **/
@Service
public class AssertsServiceImpl implements AssertsService {

    @Resource
    private AssertsMapper assertsMapper;

    @Override
    public void saveOrUpdate(List<Asserts> assertsList) {
        if (CollectionUtils.isEmpty(assertsList)) {
            return;
        }
        List<Asserts> updateAsserts = Lists.newArrayList();
        List<Asserts> saveAsserts = Lists.newArrayList();
        for (Asserts asserts : assertsList) {
            if (asserts.getId() == null || asserts.getId() == 0) {
                saveAsserts.add(asserts);
            } else {
                updateAsserts.add(asserts);
            }
        }

        if (!CollectionUtils.isEmpty(saveAsserts)) {
            assertsMapper.insertBatch(saveAsserts);
        }

        for (Asserts asserts : updateAsserts) {
            assertsMapper.update(asserts);
        }

    }

    @Override
    public List<Asserts> getAssertsByCompany(int companyId) {
        return assertsMapper.getByCompany(companyId);
    }
}
