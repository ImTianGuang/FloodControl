package com.tian.cloud.service.dao.mapper;

import com.tian.cloud.service.controller.request.CommonSearchReq;
import com.tian.cloud.service.dao.entity.Message;

import java.util.List;

public interface MessageMapper {

    void save(Message message);

    void update(Message message);

    List<Message> search(CommonSearchReq req);
}
