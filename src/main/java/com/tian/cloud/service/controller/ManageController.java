package com.tian.cloud.service.controller;

import com.tian.cloud.service.controller.request.*;
import com.tian.cloud.service.controller.response.BaseResponse;
import com.tian.cloud.service.dao.entity.CommonType;
import com.tian.cloud.service.dao.entity.Company;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author tianguang
 * 2018/8/20 下午1:41
 **/
@RestController
@RequestMapping("/manage/")
@Slf4j
public class ManageController {

    @RequestMapping("updateCompanyList")
    @ResponseBody
    public BaseResponse<Boolean> updateCompanyList(@RequestBody List<Company> companyList) {
        log.info("companyList:{}", companyList);
        return BaseResponse.success(true);
    }

    // addOrUpdate company
    @RequestMapping("updateCompany")
    @ResponseBody
    public BaseResponse<Void> saveOrUpdateCompanyInfo(CompanyInfoUpdateInfoReq request) {
        return BaseResponse.success(null);
    }

    @RequestMapping("updatePosition")
    @ResponseBody
    public BaseResponse<Void> saveOrUpdatePosition(PositionUpdateReq request) {
        return BaseResponse.success(null);
    }

    @RequestMapping("updateCommonType")
    @ResponseBody
    public BaseResponse<Boolean> saveOrUpdateCommonType(@RequestBody List<CommonType> commonTypeList) {
        log.info("commonType:{}", commonTypeList);
        return BaseResponse.success(true);
    }

    @RequestMapping("updateMessage")
    @ResponseBody
    public BaseResponse<Void> saveOrUpdateMessage(MessageUpdateReq request) {
        return BaseResponse.success(null);
    }

    @RequestMapping("updateSituation")
    @ResponseBody
    public BaseResponse<Void> saveOrUpdateFloodSituation(FloodSituationUpdateReq request) {
        return BaseResponse.success(null);
    }

    // export company

    // export message flood-situation

}
