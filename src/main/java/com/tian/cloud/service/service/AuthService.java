package com.tian.cloud.service.service;

import com.tian.cloud.service.controller.request.AccountCheckReq;

public interface AuthService {

    String checkAccount(AccountCheckReq checkReq);

    boolean checkToken(String token);
}
