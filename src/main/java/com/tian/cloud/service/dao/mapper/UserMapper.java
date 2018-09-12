package com.tian.cloud.service.dao.mapper;

import com.tian.cloud.service.dao.entity.CompanyUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    void update(CompanyUser user);

    void insertBatch(@Param("userList") List<CompanyUser> userList);

    List<CompanyUser> getUserByCompany(int companyId);

    List<CompanyUser> getAllUsableUser();

    void deleteByPositionId(@Param("positionId") Integer positionId);

    void deleteByFloodTitle(@Param("floodTitle") String floodTitle);

    void deleteByCompanyId(@Param("companyId") Integer companyId);
}
