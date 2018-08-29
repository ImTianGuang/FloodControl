package com.tian.cloud.service.dao.mapper;

import com.tian.cloud.service.dao.entity.Message;

public interface MessageMapper {

    void save(Message message);

    void update(Message message);
}
