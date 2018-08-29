package com.tian.cloud.service.service.impl;

import com.tian.cloud.service.dao.entity.Message;
import com.tian.cloud.service.dao.mapper.MessageMapper;
import com.tian.cloud.service.service.MessageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author tianguang
 * 2018/8/29 下午9:52
 **/
@Service
public class MessageServiceImpl implements MessageService {

    @Resource
    private MessageMapper messageMapper;

    @Override
    public void saveOrUpdate(Message message) {
        if (message.getId() == null) {
            messageMapper.save(message);
        } else {
            messageMapper.update(message);
        }
    }
}
