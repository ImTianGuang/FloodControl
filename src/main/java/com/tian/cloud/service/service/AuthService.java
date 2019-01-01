package com.tian.cloud.service.service;

import com.tian.cloud.service.controller.request.AccountCheckReq;
import com.tian.cloud.service.controller.request.AccountUpdateReq;
import com.tian.cloud.service.dao.entity.FloodUser;
import com.tian.cloud.service.model.auth.AccountCheckResult;
import com.tian.cloud.service.model.auth.TokenCheckResult;

import java.util.List;

public interface AuthService {

    AccountCheckResult checkAccount(AccountCheckReq checkReq);

    boolean changePassword(String token, String oldPass, String newPassword);

    TokenCheckResult checkToken(String token);

    boolean addAccount(String token, String name, String pass, boolean isSuper);

    boolean delAccount(String token, String name);

    List<FloodUser> accountList(String token);

    void saveOrUpdate(AccountUpdateReq req);
}
