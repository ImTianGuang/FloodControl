package com.tian.cloud.service.service.impl;

import com.google.common.collect.Lists;
import com.tian.cloud.service.controller.response.CompanyInfo;
import com.tian.cloud.service.dao.entity.CompanyUser;
import com.tian.cloud.service.dao.mapper.UserMapper;
import com.tian.cloud.service.service.UserService;
import com.tian.cloud.service.util.ParamCheckUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author tianguang
 * 2018/8/27 下午7:51
 **/
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public void saveOrUpdate(List<CompanyInfo.PhoneInfo> phoneList) {
      if (CollectionUtils.isEmpty(phoneList)) {
            return;
        }
        List<CompanyUser> updateList = Lists.newArrayList();
        List<CompanyUser> saveList = Lists.newArrayList();
        for (CompanyInfo.PhoneInfo phoneInfo : phoneList) {
            if (CollectionUtils.isEmpty(phoneInfo.getUserList())) {
                continue;
            }
            for (CompanyUser user : phoneInfo.getUserList()) {
                ParamCheckUtil.assertTrue(!StringUtils.isEmpty(user.getFloodTitle()), "名称不能为空");

                if (user.getId() == null) {
                    if (user.getStatus() == 1) {
                        saveList.add(user);
                    }
                } else {
                    updateList.add(user);
                }
            }
        }
        if (!CollectionUtils.isEmpty(saveList)) {
            userMapper.insertBatch(saveList);
        }

        if (!CollectionUtils.isEmpty(updateList)) {
            for (CompanyUser user : updateList) {
                userMapper.update(user);
            }
        }
    }

    @Override
    public List<CompanyUser> getUserByCompany(int companyId) {
        return userMapper.getUserByCompany(companyId);
    }

    @Override
    public List<CompanyUser> getAllUsableUser() {
        return userMapper.getAllUsableUser();
    }
}
