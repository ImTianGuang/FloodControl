package com.tian.cloud.service.controller;

import com.tian.cloud.service.controller.request.*;
import com.tian.cloud.service.controller.response.BaseResponse;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tianguang
 * 2018/8/20 下午1:41
 **/
@RestController
public class ManageController {

    // addOrUpdate company
    public BaseResponse<Void> saveOrUpdateCompanyInfo(CompanyInfoUpdateInfoReq request) {
        return BaseResponse.success(null);
    }

    // addOrUpdate position
    public BaseResponse<Void> saveOrUpdatePosition(PositionUpdateReq request) {
        return BaseResponse.success(null);
    }

    // addOrUpdate commonType: situationType solutionType
    public BaseResponse<Void> saveOrUpdateCommonType(CompanyUpdateReq request) {
        return BaseResponse.success(null);
    }

    // addOrUpdate message
    public BaseResponse<Void> sqveOrUpdateMessage(MessageUpdateReq request) {
        return BaseResponse.success(null);
    }

    // addOrUpdate flood-situation
    public BaseResponse<Void> saveOrUpdateFloodSituation(FloodSituationUpdateReq request) {
        return BaseResponse.success(null);
    }

    // export company

    // export message flood-situation

}
