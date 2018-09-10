package com.tian.cloud.service.service.impl;

import com.tian.cloud.service.controller.request.AccountCheckReq;
import com.tian.cloud.service.dao.entity.FloodUser;
import com.tian.cloud.service.dao.mapper.FloodUserMapper;
import com.tian.cloud.service.service.AuthService;
import com.tian.cloud.service.util.ParamCheckUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author tianguang
 * 2018/9/9 下午8:43
 **/
@Service
public class AuthServiceImpl implements AuthService {

    @Resource
    private FloodUserMapper floodUserMapper;
    @Override
    public boolean checkAccount(AccountCheckReq checkReq) {
        FloodUser floodUser = floodUserMapper.selectByNameAndPass(checkReq.getUserName(), checkReq.getPassword());

        return floodUser != null;
    }
}
