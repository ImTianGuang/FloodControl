package com.tian.cloud.service.dao.mapper;

import com.tian.cloud.service.dao.entity.Company;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CompanyMapper {

    Company selectById(@Param("id") Integer id);

    List<Company> selectAll();

    List<Company> search(@Param("companyName") String companyName);

    int updateCompany(Company company);

    void insertBatch(@Param("saveList") List<Company> saveList);
}
