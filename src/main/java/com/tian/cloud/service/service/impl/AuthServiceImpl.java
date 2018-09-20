package com.tian.cloud.service.service.impl;

import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tian.cloud.service.controller.request.AccountCheckReq;
import com.tian.cloud.service.controller.request.AccountUpdateReq;
import com.tian.cloud.service.enums.LineStatusEnum;
import com.tian.cloud.service.model.auth.AccountCheckResult;
import com.tian.cloud.service.model.auth.TokenCheckResult;
import com.tian.cloud.service.dao.entity.FloodUser;
import com.tian.cloud.service.dao.mapper.FloodUserMapper;
import com.tian.cloud.service.exception.ErrorCode;
import com.tian.cloud.service.exception.InternalException;
import com.tian.cloud.service.service.AuthService;
import com.tian.cloud.service.util.DesUtil;
import com.tian.cloud.service.util.MD5Util;
import com.tian.cloud.service.util.ParamCheckUtil;
import jetbrick.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

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
    public AccountCheckResult checkAccount(AccountCheckReq checkReq) {
        try {
            FloodUser floodUser = floodUserMapper.selectByNameAndPass(checkReq.getUserName(), checkReq.getPassword());

            if (floodUser == null) {
                return new AccountCheckResult(false, false, "");
            }
            String md5 = calcuMD5(checkReq.getUserName(), checkReq.getPassword());
            String original = JOINER.join(System.currentTimeMillis(), checkReq.getUserName(), md5);
            String token = DesUtil.encrypt(original, salt);
            return new AccountCheckResult(true, floodUser.getIsSuper(), token);
        } catch (Exception e) {
            throw new InternalException(ErrorCode.LOGIN_FAIL, e);
        }
    }

    @Override
    public boolean changePassword(String token, String newPassword) {
        ParamCheckUtil.assertTrue(!org.springframework.util.StringUtils.isEmpty(newPassword), "密码不能为空");
        TokenCheckResult result = this.checkToken(token);
        ParamCheckUtil.assertTrue(result.isResult(), "鉴权失败");
        FloodUser floodUser = result.getFloodUser();
        int row = floodUserMapper.updatePassword(floodUser.getId(), newPassword);
        return row > 0;
    }

    @Override
    public boolean addAccount(String token, String name, String pass, boolean isSuper) {
        TokenCheckResult result = this.checkToken(token);
        ParamCheckUtil.assertTrue(result.isResult() && result.getFloodUser().getIsSuper(), "鉴权失败");
        FloodUser floodUser = floodUserMapper.selectByName(name);
        ParamCheckUtil.assertTrue(floodUser == null, "存在相同名字的账号");
        int row =0;
        return row > 0;
    }

    @Override
    public boolean delAccount(String token, String name) {
        TokenCheckResult result = this.checkToken(token);
        ParamCheckUtil.assertTrue(result.isResult() && result.getFloodUser().getIsSuper(), "鉴权失败");
        FloodUser floodUser = floodUserMapper.selectByName(name);
        ParamCheckUtil.assertTrue(floodUser != null, "账号不存在");
        int row = floodUserMapper.deleteUser(floodUser.getId());
        return row > 0;
    }

    @Override
    public List<FloodUser> accountList(String token) {
        TokenCheckResult result = this.checkToken(token);
        ParamCheckUtil.assertTrue(result.getFloodUser().getIsSuper(), "鉴权失败");
        List<FloodUser> floodUsers = floodUserMapper.selectAllUsable();
        floodUsers = floodUsers.stream().filter(floodUser -> !floodUser.getUserName().equals(result.getFloodUser().getUserName())).collect(Collectors.toList());
        return floodUsers;
    }

    @Override
    public void saveOrUpdate(AccountUpdateReq req) {
        TokenCheckResult result = this.checkToken(req.getToken());
        ParamCheckUtil.assertTrue(result.getFloodUser().getIsSuper(), "鉴权失败");
        ParamCheckUtil.assertTrue(!CollectionUtils.isEmpty(req.getAccountList()), "无账号信息");
        List<FloodUser> reqUserList = req.getAccountList().stream().filter(floodUser -> {
            if (floodUser.getId() != null && floodUser.getId() > 0) {
                return true;
            }
            if (floodUser.getStatus() == LineStatusEnum.USABLE.getCode()) {
                return true;
            }
            return false;
        }).collect(Collectors.toList());

        checkRepeat(reqUserList);

        List<FloodUser> saveList = Lists.newArrayList();
        List<FloodUser> updateList = Lists.newArrayList();
        for (FloodUser floodUser : reqUserList) {
            if (floodUser.getId() == null || floodUser.getId() == -1) {
                saveList.add(floodUser);
            } else {
                updateList.add(floodUser);
            }
        }

        for (FloodUser floodUser : saveList) {
            floodUserMapper.insert(floodUser);
        }

        for (FloodUser floodUser : updateList) {
            floodUserMapper.updatePassword(floodUser.getId(), floodUser.getPassword());
        }
    }

    private void checkRepeat(List<FloodUser> floodUsers) {
        try {
            Maps.uniqueIndex(floodUsers, FloodUser::getUserName);
        } catch (Exception e) {
            throw new InternalException(ErrorCode.PARAM_ERROR, "用户名不能重复", e);
        }
    }

    public TokenCheckResult checkToken(String token) {
        try {
            if (StringUtils.isEmpty(token)) {
                return new TokenCheckResult(false, null);
            }
            String original = DesUtil.decrypt(token, salt);
            List<String> oriList = SPLITTER.splitToList(original);
            String userName = oriList.get(1);
            String md5 = oriList.get(2);
            if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(md5)) {
                return new TokenCheckResult(false, null);
            }
            FloodUser floodUser = floodUserMapper.selectByName(userName);
            if (floodUser == null) {
                return new TokenCheckResult(false, null);
            }
            String calculMD5 = calcuMD5(userName, floodUser.getPassword());
            boolean result = Objects.equal(md5, calculMD5);
            if (result) {
                return new TokenCheckResult(result, floodUser);
            } else {
                return new TokenCheckResult(false, null);
            }
        } catch (Exception e) {
            log.error("token校验失败,token:{}", token, e);
            return new TokenCheckResult(false, null);
        }
    }

    private String calcuMD5(String userName, String password) {
        return MD5Util.getMD5(salt + userName+ password);
    }
}
