package com.tian.cloud.service.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.tian.cloud.service.controller.request.CommonSearchReq;
import com.tian.cloud.service.controller.response.*;
import com.tian.cloud.service.dao.entity.*;
import com.tian.cloud.service.dao.mapper.CommonTypeMapper;
import com.tian.cloud.service.enums.CommonTypeEnum;
import com.tian.cloud.service.enums.LineStatusEnum;
import com.tian.cloud.service.service.CommonTypeService;
import com.tian.cloud.service.service.CompanyService;
import com.tian.cloud.service.service.MessageService;
import com.tian.cloud.service.service.SituationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
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
@Slf4j
public class SearchController {

    @Resource
    private CommonTypeService commonTypeService;

    @Resource
    private CompanyService companyService;

    @Resource
    private SituationService situationService;

    @Resource
    private MessageService messageService;

    // companyList
    @RequestMapping("/company")
    @ResponseBody
    public PageResponse<Company> searchCompany(
            @RequestParam(required = false) String companyName) {

        log.info("companyName:{}", companyName);
        List<Company> companies;
        if (StringUtils.isEmpty(companyName)) {
            companies = companyService.selectAll();
        } else {
            companies = companyService.search(companyName);
        }
        return PageResponse.success(companies, companies.size());
    }

    @RequestMapping("/companyInfo")
    @ResponseBody
    public BaseResponse<CompanyInfo> companyInfo(
            @RequestParam(required = true) Integer companyId) {

        CompanyInfo companyInfo = companyService.getCompanyInfo(companyId);
        return BaseResponse.success(companyInfo);
    }

    // messageList
    @RequestMapping("/messageList")
    @ResponseBody
    public PageResponse<Message> messageList(CommonSearchReq request) {
        List<Message> messageList = messageService.search(request);
        return PageResponse.success(messageList, Message::getId);
    }

    @RequestMapping("messageInfo")
    public BaseResponse<Message> messageInfo(int id) {
        Message message = messageService.getMessage(id);
        return BaseResponse.success(message);
    }

    @RequestMapping("/situationList")
    @ResponseBody
    public PageResponse<FloodSituation> situationList(CommonSearchReq request) {
        List<FloodSituation> situations = situationService.search(request);

        return PageResponse.success(situations, FloodSituation::getId);
    }

    @RequestMapping("/situationInfo")
    @ResponseBody
    public BaseResponse<FloodSituationInfo> floodSituationList(Integer situationId) {
        FloodSituationInfo situationInfo = situationService.getSituationInfo(situationId);
        return BaseResponse.success(situationInfo);
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
