package com.tian.cloud.service.dao.mapper;

import com.tian.cloud.service.dao.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    void update(User user);

    void insertBatch(@Param("userList") List<User> userList);

    List<User> getUserByCompany(int companyId);

    List<User> getAllUsableUser();

    void deleteByPositionId(@Param("id") Integer id);

    void deleteByFloodTitle(@Param("floodTitle") String floodTitle);
}
