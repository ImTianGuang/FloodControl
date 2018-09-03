package com.tian.cloud.service.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.tian.cloud.service.dao.entity.User;
import com.tian.cloud.service.model.export.ExportUser;
import com.tian.cloud.service.service.ExportService;
import com.tian.cloud.service.service.UserService;
import com.tian.cloud.service.util.excel.MySheet;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

/**
 * @author tianguang
 * 2018/9/3 下午3:57
 **/
@Service
public class ExportServiceImpl implements ExportService {

    @Resource
    private UserService userService;

    @Override
    public List<MySheet> getAllUserSheetList() {
        List<User> allUser = userService.getAllUser();
        Multimap<String, User> floodTitleUserMap = Multimaps.index(allUser, User::getFloodTitle);
        List<MySheet> mySheets = Lists.newArrayList();
        for (String floodTitle : floodTitleUserMap.keySet()) {
            MySheet mySheet = new MySheet();
            mySheet.setSheetName(floodTitle);
            Collection<User> users = floodTitleUserMap.get(floodTitle);
            if (CollectionUtils.isEmpty(users)) {
                continue;
            }
            List<ExportUser> exportUsers = toExportUser(floodTitleUserMap.get(floodTitle));
            mySheet.setDataList(exportUsers);
        }
        return mySheets;
    }

    private List<ExportUser> toExportUser(Collection<User> users) {
        List<ExportUser> exportUsers = Lists.newArrayList();
        for (User user : users) {
            ExportUser exportUser = new ExportUser();
            exportUser.setCompanyName("测试");
            exportUser.setFax(user.getFax());
            exportUser.setName(user.getUserName());
            exportUser.setPersonPhone(user.getUserPhone());
            exportUser.setWorkPhone(user.getWorkPhone());

            exportUsers.add(exportUser);
        }
        return exportUsers;
    }
}
