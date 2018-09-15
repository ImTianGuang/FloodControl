package com.tian.cloud.service.service.impl;

import com.tian.cloud.service.TestBase;
import com.tian.cloud.service.dao.entity.Message;
import com.tian.cloud.service.service.MessageService;
import org.junit.Test;

import javax.annotation.Resource;

import static org.junit.Assert.*;

public class MessageServiceImplTest extends TestBase {

    @Resource
    private MessageService messageService;

    @Test
    public void saveOrUpdate() {
        Message message = new Message();
        message.setId(1);
        message.setCompanyId(0);
        message.setStatus(1);
        message.setMessageType(1);
        message.setContent("333");
        message.setTitle("222");
        message.setCreateTime(System.currentTimeMillis());
        message.setUpdateTime(System.currentTimeMillis());

//        messageService.saveOrUpdate(message);
    }
}