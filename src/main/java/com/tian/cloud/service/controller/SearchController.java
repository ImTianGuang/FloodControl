package com.tian.cloud.service.controller;

import com.google.common.collect.Lists;
import com.tian.cloud.service.controller.request.CommonSearchReq;
import com.tian.cloud.service.controller.response.BaseResponse;
import com.tian.cloud.service.controller.response.FloodSituationInfo;
import com.tian.cloud.service.controller.response.PageResponse;
import com.tian.cloud.service.dao.entity.CommonType;
import com.tian.cloud.service.dao.entity.Company;
import com.tian.cloud.service.dao.entity.Message;
import com.tian.cloud.service.dao.mapper.CommonTypeMapper;
import com.tian.cloud.service.enums.LineStatusEnum;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author tianguang
 * 2018/8/20 下午1:57
 **/
@RestController
@RequestMapping("/search")
public class SearchController {

    // companyList
    @RequestMapping("/company")
    @ResponseBody
    public PageResponse<List<Company>> searchCompany(String companyName, Integer companyId) {

        List<Company> companies = Lists.newArrayList();
        Company company = new Company();
        company.setId(0);
        company.setStatus(LineStatusEnum.USABLE.getCode());
        company.setAddress("address");
        company.setPostCode("111111");
        company.setEmail("company@qq.com");

        Company company1 = new Company();
        company1.setId(0);
        company1.setStatus(LineStatusEnum.USABLE.getCode());
        company1.setAddress("address");
        company1.setPostCode("111111");
        company1.setEmail("company@qq.com");

        companies.add(company);
        companies.add(company1);
        return PageResponse.success(companies, 2);
    }

    // messageList
    @RequestMapping("/message")
    @ResponseBody
    public PageResponse<Message> messageList(CommonSearchReq request) {
        return PageResponse.fail("", "", Lists.newArrayList());
    }

    // flood-situationList
    @RequestMapping("/situation")
    @ResponseBody
    public PageResponse<FloodSituationInfo> floodSituationList(CommonSearchReq request) {
        return PageResponse.success(Lists.newArrayList(), 10);
    }

    @Resource
    private CommonTypeMapper commonTypeMapper;

    @RequestMapping("commonTypeList")
    @ResponseBody
    public PageResponse<CommonType> commonTypeList(int commonTypeEnum) {
        CommonType commonType = commonTypeMapper.selectById(0);
        return PageResponse.success(Lists.newArrayList(commonType), 0);
    }
}
