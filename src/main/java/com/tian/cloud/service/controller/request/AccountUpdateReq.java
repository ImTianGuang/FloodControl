package com.tian.cloud.service.controller.request;

import com.tian.cloud.service.dao.entity.FloodUser;
import lombok.Data;

import java.util.List;

/**
 * @author tianguang
 * 2018/9/20 下午11:25
 **/
@Data
public class AccountUpdateReq {

    private List<FloodUser> accountList;

    private String token;
}
