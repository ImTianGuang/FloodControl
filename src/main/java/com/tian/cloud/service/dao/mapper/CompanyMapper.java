package com.tian.cloud.service.dao.mapper;

import com.tian.cloud.service.dao.entity.Company;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CompanyMapper {

    Company selectById(@Param("id") Integer id);

    List<Company> selectAll();
}
