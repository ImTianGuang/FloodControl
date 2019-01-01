package com.tian.cloud.service.controller.request;

import lombok.Data;

/**
 * @author tianguang
 * 2018/9/20 下午10:52
 **/
@Data
public class ChangePassReq {

    private String token;

    private String oldPass;

    private String newPass;
}
