package com.tian.cloud.service.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.tian.cloud.service.controller.request.CommonSearchReq;
import com.tian.cloud.service.controller.response.BaseResponse;
import com.tian.cloud.service.controller.response.FloodSituationInfo;
import com.tian.cloud.service.controller.response.PageResponse;
import com.tian.cloud.service.controller.response.TypeSummary;
import com.tian.cloud.service.dao.entity.CommonType;
import com.tian.cloud.service.dao.entity.Company;
import com.tian.cloud.service.dao.entity.Message;
import com.tian.cloud.service.dao.mapper.CommonTypeMapper;
import com.tian.cloud.service.enums.CommonTypeEnum;
import com.tian.cloud.service.enums.LineStatusEnum;
import com.tian.cloud.service.service.CommonTypeService;
import com.tian.cloud.service.service.CompanyService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author tianguang
 * 2018/8/20 下午1:57
 **/
@RestController
@RequestMapping("/search")
public class SearchController {

    @Resource
    private CommonTypeService commonTypeService;

    @Resource
    private CompanyService companyService;

    // companyList
    @RequestMapping("/company")
    @ResponseBody
    public PageResponse<List<Company>> searchCompany(
            @RequestParam(required = false) String companyName) {

        if (2==1) {
            return PageResponse.pageFail("001", "未知异常");
        }
        List<Company> companies = Lists.newArrayList();
        Company company = new Company();
        company.setId(0);
        company.setStatus(LineStatusEnum.USABLE.getCode());
        company.setName("公司1");
        company.setAddress("address");
        company.setPostCode("111111");
        company.setEmail("company@qq.com");

        Company company1 = new Company();
        company1.setId(0);
        company1.setStatus(LineStatusEnum.USABLE.getCode());
        company1.setName("公司2");
        company1.setAddress("address");
        company1.setPostCode("111111");
        company1.setEmail("company@qq.com");

        companies.add(company);
        companies.add(company1);
        return PageResponse.success(companies, 2);
    }

    @RequestMapping("/companyInfo")
    @ResponseBody
    public BaseResponse<Company> companyInfo(
            @RequestParam(required = true) Integer companyId) {

        if (2==1) {
            return BaseResponse.fail("001", "未知异常");
        }
        Company company = new Company();
        company.setId(0);
        company.setStatus(LineStatusEnum.USABLE.getCode());
        company.setName("公司1");
        company.setAddress("address");
        company.setPostCode("111111");
        company.setEmail("company@qq.com");

        return BaseResponse.success(company);
    }

    // messageList
    @RequestMapping("/messageList")
    @ResponseBody
    public PageResponse<Message> messageList(CommonSearchReq request) {
        return PageResponse.pageFail("", "");
    }

    @RequestMapping("messageInfo")
    public BaseResponse<Message> messageInfo(int id) {
        Message message = new Message();
        message.setTitle("通知");
        message.setContent("通知内容\n通知内容");
        return BaseResponse.success(message);
    }

    // flood-situationList
    @RequestMapping("/situation")
    @ResponseBody
    public PageResponse<FloodSituationInfo> floodSituationList(CommonSearchReq request) {
        return PageResponse.success(Lists.newArrayList(), 10);
    }

    @RequestMapping("commonTypeList")
    @ResponseBody
    public PageResponse<CommonType> commonTypeList(HttpServletRequest request, Integer commonTypeEnum) {
        List<CommonType> commonTypeList = commonTypeService.selectByType(commonTypeEnum);
        return PageResponse.success(commonTypeList, commonTypeList.size());
    }

    @RequestMapping("typeSummary")
    @ResponseBody
    public BaseResponse<TypeSummary> typeSummary() {
        TypeSummary typeSummary = new TypeSummary();

        Multimap<Integer, CommonType> multimap = commonTypeService.selectAll();
        typeSummary.setCompanyList(companyService.selectAll());
        typeSummary.setPositionList((List<CommonType>) multimap.get(CommonTypeEnum.POSITION.getCode()));
        typeSummary.setSituationTypeList((List<CommonType>) multimap.get(CommonTypeEnum.SITUATION.getCode()));
        typeSummary.setSolutionTypeList((List<CommonType>) multimap.get(CommonTypeEnum.SOLUTION.getCode()));
        return BaseResponse.success(typeSummary);
    }
}
