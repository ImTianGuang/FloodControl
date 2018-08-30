package com.tian.cloud.service.service;

import com.tian.cloud.service.controller.request.CommonSearchReq;
import com.tian.cloud.service.dao.entity.Message;

import java.util.List;

public interface MessageService {

    void saveOrUpdate(Message message);

    List<Message> search(CommonSearchReq request);

    Message getMessage(int id);
}
