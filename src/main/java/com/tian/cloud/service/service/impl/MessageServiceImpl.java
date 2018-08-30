package com.tian.cloud.service.service.impl;

import com.tian.cloud.service.controller.request.CommonSearchReq;
import com.tian.cloud.service.dao.entity.Message;
import com.tian.cloud.service.dao.mapper.MessageMapper;
import com.tian.cloud.service.service.MessageService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

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

    @Override
    public List<Message> search(CommonSearchReq request) {
        return messageMapper.search(request);
    }

    @Override
    public Message getMessage(int id) {
        return null;
    }
}
