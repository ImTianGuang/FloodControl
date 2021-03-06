package com.tian.cloud.service.controller;

import com.google.common.collect.Multimap;
import com.google.common.collect.Ordering;
import com.tian.cloud.service.controller.request.CommonSearchReq;
import com.tian.cloud.service.controller.response.*;
import com.tian.cloud.service.dao.entity.*;
import com.tian.cloud.service.enums.CommonTypeEnum;
import com.tian.cloud.service.exception.ErrorCode;
import com.tian.cloud.service.service.CommonTypeService;
import com.tian.cloud.service.service.CompanyService;
import com.tian.cloud.service.service.MessageService;
import com.tian.cloud.service.service.SituationService;
import com.tian.cloud.service.util.DateUtil;
import com.tian.cloud.service.util.FileUtils;
import com.tian.cloud.service.util.ParamCheckUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Comparator;
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
    public PageResponse<Message> messageList(@RequestBody CommonSearchReq request) {
        if (request.getStartDateStr() != null) {
            request.setStartTime(DateUtil.str2Date(request.getStartDateStr(), DateUtil.FMT_YYYY_MM1).getTime());
        }
        if (request.getEndDateStr() != null) {
            request.setEndTime(DateUtil.str2Date(request.getEndDateStr(), DateUtil.FMT_YYYY_MM1).getTime());
        }
        List<Message> messageList = messageService.search(request);
        return PageResponse.success(messageList, Message::getId);
    }

    @RequestMapping("messageInfo")
    public BaseResponse<Message> messageInfo(int id) {
        Message message = messageService.getMessage(id);
        message.setAttatch(FileUtils.getFileName(message.getAttatch()));
        return BaseResponse.success(message);
    }

    @RequestMapping("/situationList")
    @ResponseBody
    public PageResponse<FloodSituation> situationList(@RequestBody CommonSearchReq request) {
        if (request.getStartDateStr() != null) {
            request.setStartTime(DateUtil.str2Date(request.getStartDateStr(), DateUtil.FMT_YYYY_MM1).getTime());
        }
        if (request.getEndDateStr() != null) {
            request.setEndTime(DateUtil.str2Date(request.getEndDateStr(), DateUtil.FMT_YYYY_MM1).getTime());
        }

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
        typeSummary.setAssertsTypeList((List<CommonType>) multimap.get(CommonTypeEnum.ASSERTS.getCode()));
        typeSummary.setFloodTitleList((List<CommonType>) multimap.get(CommonTypeEnum.FLOOD_TITLE.getCode()));

        return BaseResponse.success(typeSummary);
    }

    @RequestMapping("companyFloodTypes")
    @ResponseBody
    public BaseResponse<CompanySituationTypes> companySituationTypes(Integer companyId) {
        ParamCheckUtil.assertTrue(companyId != null, ErrorCode.PARAM_ERROR.getMsg());
        return BaseResponse.success(companyService.situationTypesOfCompany(companyId));
    }
}
