package com.tian.cloud.service.controller;

import com.google.common.collect.Lists;
import com.tian.cloud.service.controller.request.MessageSearchReq;
import com.tian.cloud.service.controller.response.BaseResponse;
import com.tian.cloud.service.controller.response.PageResponse;
import com.tian.cloud.service.dao.entity.Company;
import com.tian.cloud.service.dao.entity.Message;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author tianguang
 * 2018/8/20 下午1:57
 **/
@RestController
public class SearchController {

    // companyList
    public BaseResponse<List<Company>> searchCompany(String companyName) {
        return BaseResponse.success(Lists.newArrayList());
    }

    // messageList
    public PageResponse<Message> messageList(MessageSearchReq request) {
        return PageResponse.fail("", "", Lists.newArrayList());
    }
    // flood-situationList

}
