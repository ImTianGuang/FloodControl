package com.tian.cloud.service.dao.mapper;

import com.tian.cloud.service.controller.request.CommonSearchReq;
import com.tian.cloud.service.dao.entity.Message;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MessageMapper {

    void save(Message message);

    void update(Message message);

    List<Message> search(CommonSearchReq req);

    Message getById(@Param("id") int id);
}
