package com.tian.cloud.service.dao.entity;

import lombok.Data;

/**
 * @author tianguang
 * 2018/9/7 下午6:52
 **/
@Data
public class FloodUser {

    private Integer id;

    private String userName;

    private String password;

    private Integer status;

    private Boolean isSuper = false;

    private long createTime = System.currentTimeMillis();

    private long updateTime = System.currentTimeMillis();

    public Boolean getIsSuper() {
        return isSuper;
    }

    public void setIsSuper(Boolean aSuper) {
        isSuper = aSuper;
    }
}
