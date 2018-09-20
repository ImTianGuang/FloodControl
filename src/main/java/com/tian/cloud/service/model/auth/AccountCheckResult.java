package com.tian.cloud.service.model.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author tianguang
 * 2018/9/20 下午3:46
 **/
@Data
@AllArgsConstructor
public class AccountCheckResult {

    private boolean result;

    private boolean isSuper;

    private String token;
}
