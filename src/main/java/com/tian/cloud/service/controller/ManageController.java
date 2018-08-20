package com.tian.cloud.service.controller;

import com.tian.cloud.service.controller.request.CompanyUpdateReq;
import com.tian.cloud.service.controller.request.PositionUpdateReq;
import com.tian.cloud.service.controller.response.BaseResponse;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tianguang
 * 2018/8/20 下午1:41
 **/
@RestController
public class ManageController {

    // addOrUpdate company

    public BaseResponse<Void> saveOrUpdateCompany(CompanyUpdateReq request) {
        return BaseResponse.success(null);
    }

    // addOrUpdate position
    public BaseResponse<Void> saveOrUpdatePosition(PositionUpdateReq request) {
        return BaseResponse.success(null);
    }

    // addOrUpdate commonType: situationType solutionType


    // add message

    // add flood-situation

    // addOrUpdate phones,asserts,plan


    // export company

    // export message flood-situation

}
