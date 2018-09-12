package com.tian.cloud.service.service.impl;

import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.base.Splitter;
import com.tian.cloud.service.controller.request.AccountCheckReq;
import com.tian.cloud.service.dao.entity.FloodUser;
import com.tian.cloud.service.dao.mapper.FloodUserMapper;
import com.tian.cloud.service.exception.ErrorCode;
import com.tian.cloud.service.exception.InternalException;
import com.tian.cloud.service.service.AuthService;
import com.tian.cloud.service.util.DesUtil;
import com.tian.cloud.service.util.MD5Util;
import jetbrick.util.StringUtils;
import jetbrick.util.codec.MD5Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author tianguang
 * 2018/9/9 下午8:43
 **/
@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    private static final String salt = "fasdfasdfasdf;ljgasdgewr";

    @Resource
    private FloodUserMapper floodUserMapper;

    private static final Joiner JOINER = Joiner.on(":").skipNulls();
    private static final Splitter SPLITTER = Splitter.on(':').trimResults();

    @Override
    public String checkAccount(AccountCheckReq checkReq) {
        try {
            FloodUser floodUser = floodUserMapper.selectByNameAndPass(checkReq.getUserName(), checkReq.getPassword());

            if (floodUser == null) {
                return "";
            }
            String md5 = calcuMD5(checkReq.getUserName(), checkReq.getPassword());
            String original = JOINER.join(System.currentTimeMillis(), checkReq.getUserName(), md5);
            return DesUtil.encrypt(original, salt);
        } catch (Exception e) {
            throw new InternalException(ErrorCode.LOGIN_FAIL, e);
        }
    }

    public boolean checkToken(String token) {
        try {
            if (StringUtils.isEmpty(token)) {
                return false;
            }
            String original = DesUtil.decrypt(token, salt);
            List<String> oriList = SPLITTER.splitToList(original);
            String userName = oriList.get(1);
            String md5 = oriList.get(2);
            if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(md5)) {
                return false;
            }
            FloodUser floodUser = floodUserMapper.selectByName(userName);
            if (floodUser == null) {
                return false;
            }
            String calculMD5 = calcuMD5(userName, floodUser.getPassword());
            return Objects.equal(md5, calculMD5);
        } catch (Exception e) {
            log.error("token校验失败,token:{}", token, e);
            return false;
        }
    }

    private String calcuMD5(String userName, String password) {
        return MD5Util.getMD5(salt + userName+ password);
    }
}
