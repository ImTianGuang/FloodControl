package com.tian.cloud.service.service;

import com.tian.cloud.service.dao.entity.Message;

public interface MessageService {

    void saveOrUpdate(Message message);
}
