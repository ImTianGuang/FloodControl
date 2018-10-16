package com.tian.cloud.service.dao.entity;

import com.tian.cloud.service.enums.LineStatusEnum;
import lombok.Data;

/**
 * @author tianguang
 * 2018/8/20 上午11:12
 **/
@Data
public class CompanyUser {

    private Integer id;

    private Integer companyId;

    private String floodTitle = "";

    private Integer positionId = -1;

    private String positionName ="";

    private Integer status = 1;

    private String userName = "";

    private String userPhone = "";

    private String workPhone = "";

    private String fax = "";

    // 所属组织机构
    private Integer orgCode;

    private String orgTitle;

    private long createTime;

    private long updateTime;

    // for 前端
    private String titleDesc;
    private boolean isAutoFax;
    private boolean canAdd;

    public boolean getIsAutoFax() {
        return isAutoFax;
    }

    public void setIsAutoFax(boolean autoFax) {
        isAutoFax = autoFax;
    }

    public boolean getCanAdd() {
        return canAdd;
    }

    public void setCanAdd(boolean canAdd) {
        this.canAdd = canAdd;
    }
}
