package com.tian.cloud.service.controller;

import com.tian.cloud.service.controller.request.*;
import com.tian.cloud.service.controller.response.BaseResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tianguang
 * 2018/8/20 下午1:41
 **/
@RestController
@RequestMapping("/manage/")
public class ManageController {

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
    public BaseResponse<Void> saveOrUpdateCommonType(CompanyUpdateReq request) {
        return BaseResponse.success(null);
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
