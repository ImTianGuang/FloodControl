package com.tian.cloud.service.model.auth;

import com.tian.cloud.service.dao.entity.FloodUser;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author tianguang
 * 2018/9/20 下午3:35
 **/
@Data
@AllArgsConstructor
public class TokenCheckResult {

    private boolean result;

    private FloodUser floodUser;
}
