package com.tian.cloud.service.controller.request;

import com.tian.cloud.service.dao.entity.Asserts;
import com.tian.cloud.service.dao.entity.Company;
import com.tian.cloud.service.dao.entity.CompanyUser;
import lombok.Data;

import java.util.List;

/**
 * @author tianguang
 * 2018/8/20 下午8:31
 **/
@Data
public class CompanyInfoUpdateInfoReq {
    // company
    private Company company;
    // users
    private List<CompanyUser> userList;
    // asserts
    private List<Asserts> assertsList;
    // plan todo


}
