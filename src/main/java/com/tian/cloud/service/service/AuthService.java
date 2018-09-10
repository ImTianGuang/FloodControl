package com.tian.cloud.service.service;

import com.tian.cloud.service.controller.request.AccountCheckReq;

public interface AuthService {

    boolean checkAccount(AccountCheckReq checkReq);
}
