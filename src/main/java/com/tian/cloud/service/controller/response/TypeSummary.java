package com.tian.cloud.service.controller.response;

import com.google.common.collect.Multimap;
import com.tian.cloud.service.dao.entity.CommonType;
import com.tian.cloud.service.dao.entity.Company;
import lombok.Data;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author tianguang
 * 2018/8/25 下午8:55
 **/
@Data
public class TypeSummary {

    private List<Company> companyList;

    private List<CommonType> solutionTypeList;
    private List<CommonType> situationTypeList;
    private List<CommonType> positionList;
    private List<CommonType> assertsTypeList;
}
