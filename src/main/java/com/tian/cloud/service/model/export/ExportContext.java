package com.tian.cloud.service.model.export;

import com.tian.cloud.service.dao.entity.Asserts;
import com.tian.cloud.service.dao.entity.CommonType;
import com.tian.cloud.service.dao.entity.Company;
import com.tian.cloud.service.dao.entity.CompanyUser;
import lombok.Data;

import java.util.List;

/**
 * @author tianguang
 * 2018/9/6 下午2:07
 **/
@Data
public class ExportContext {

    private List<Company> allCompany;

    private List<CompanyUser> allUsableUser;

    private List<Asserts> usableAsserts;

    private List<CommonType> assertsTypeList;

    private List<CommonType> positionTypeList;
}
