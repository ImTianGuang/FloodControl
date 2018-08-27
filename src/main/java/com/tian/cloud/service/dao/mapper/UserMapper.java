package com.tian.cloud.service.dao.mapper;

import com.tian.cloud.service.dao.entity.User;

import java.util.List;

public interface UserMapper {
    void updateBatch(List<User> updateList);

    void insertBatch(List<User> saveList);
}
