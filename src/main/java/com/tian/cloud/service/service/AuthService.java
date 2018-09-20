package com.tian.cloud.service.service;

import com.tian.cloud.service.controller.request.AccountCheckReq;
import com.tian.cloud.service.model.auth.AccountCheckResult;
import com.tian.cloud.service.model.auth.TokenCheckResult;

public interface AuthService {

    AccountCheckResult checkAccount(AccountCheckReq checkReq);

    boolean changePassword(String token, String newPassword);

    TokenCheckResult checkToken(String token);

    boolean addAccount(String token, String name, String pass, boolean isSuper);

    boolean delAccount(String token, String name);
}
