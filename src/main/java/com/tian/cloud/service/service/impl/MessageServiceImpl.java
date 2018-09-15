package com.tian.cloud.service.service.impl;

import com.tian.cloud.service.controller.request.CommonSearchReq;
import com.tian.cloud.service.dao.entity.Message;
import com.tian.cloud.service.dao.mapper.MessageMapper;
import com.tian.cloud.service.enums.LineStatusEnum;
import com.tian.cloud.service.service.MessageService;
import com.tian.cloud.service.util.DateUtil;
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
            if (message.getStatus() == LineStatusEnum.DELETED.getCode()) {
                return;
            }
            message.setCreateTime(System.currentTimeMillis());
            message.setUpdateTime(message.getCreateTime());
            messageMapper.save(message);
        } else {
            message.setUpdateTime(System.currentTimeMillis());
            messageMapper.update(message);
        }
    }

    @Override
    public List<Message> search(CommonSearchReq request) {
        List<Message> messageList = messageMapper.search(request);
        for (Message message : messageList) {
            message.setCreateTimeStr(DateUtil.instantToStr(message.getCreateTime()));
        }
        return messageList;
    }

    @Override
    public Message getMessage(int id) {
        return messageMapper.getById(id);
    }
}
