package com.tian.cloud.service.controller.response;

import com.tian.cloud.service.dao.entity.CommonType;
import lombok.Data;

import java.util.List;

/**
 * @author tianguang
 * 2018/10/15 下午3:55
 **/
@Data
public class CompanySituationTypes {

    private Integer companyId;

    private List<CommonType> solutionTypeList;
    private List<CommonType> situationTypeList;
}
