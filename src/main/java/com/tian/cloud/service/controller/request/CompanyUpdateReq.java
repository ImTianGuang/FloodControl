package com.tian.cloud.service.controller.request;

import com.tian.cloud.service.enums.LineStatusEnum;
import lombok.Data;

/**
 * @author tianguang
 * 2018/8/20 下午2:15
 **/
@Data
public class CompanyUpdateReq {

    private Integer id;

    private String name;

    private Integer status;

    private String address;

    private String postCode;

    private String email;

    private String recordPerson;

    private String recordPersonPhone;

    private String checkPerson;

    private String checkPersonPhone;
}
