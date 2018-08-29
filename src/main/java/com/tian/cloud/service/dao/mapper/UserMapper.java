package com.tian.cloud.service.dao.mapper;

import com.tian.cloud.service.dao.entity.User;

import java.util.List;

public interface UserMapper {
    void update(User user);

    void insertBatch(List<User> userList);

    List<User> getUserByCompany(int companyId);
}
