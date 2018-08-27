package com.tian.cloud.service.service.impl;

import com.tian.cloud.service.SearchUtil;
import com.tian.cloud.service.dao.entity.Company;
import com.tian.cloud.service.dao.mapper.CompanyMapper;
import com.tian.cloud.service.service.CompanyService;
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

    @Override
    public List<Company> selectAll() {
        return companyMapper.selectAll();
    }

    @Override
    public List<Company> search(String companyName) {
        return companyMapper.search(companyName);
    }
}
