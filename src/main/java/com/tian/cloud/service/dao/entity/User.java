package com.tian.cloud.service.dao.entity;

import com.tian.cloud.service.enums.LineStatusEnum;
import lombok.Data;

/**
 * @author tianguang
 * 2018/8/20 上午11:12
 **/
@Data
public class User {

    private Integer id;

    private Integer companyId;

    private String floodOrgTitle;

    private Integer positionId;

    private Integer status;

    private String userName;

    private String userPhone;

    private String workPhone;

    private String fax;

    private long createTime;

    private long updateTime;
}
