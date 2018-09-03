package com.tian.cloud.service.service.impl;

import com.google.common.base.Joiner;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.tian.cloud.service.dao.entity.Asserts;
import com.tian.cloud.service.dao.entity.CommonType;
import com.tian.cloud.service.dao.entity.Company;
import com.tian.cloud.service.dao.entity.User;
import com.tian.cloud.service.dao.mapper.AssertsMapper;
import com.tian.cloud.service.dao.mapper.CommonTypeMapper;
import com.tian.cloud.service.dao.mapper.CompanyMapper;
import com.tian.cloud.service.enums.CommonTypeEnum;
import com.tian.cloud.service.enums.LineStatusEnum;
import com.tian.cloud.service.model.export.ExportAsserts;
import com.tian.cloud.service.model.export.ExportUser;
import com.tian.cloud.service.model.export.Pair;
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

    @Resource
    private CompanyMapper companyMapper;

    @Resource
    private AssertsMapper assertsMapper;

    @Resource
    private CommonTypeMapper commonTypeMapper;

    private static final Joiner PLUS_JOINER = Joiner.on("+").skipNulls();
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
            mySheets.add(mySheet);
        }
        return mySheets;
    }

    @Override
    public MySheet getAssertsSheet() {
        List<Company> allCompany = companyMapper.selectAll();
        List<CommonType> assertsTypeList = commonTypeMapper.selectUsableByType(CommonTypeEnum.ASSERTS.getCode());
        List<Asserts> asserts = assertsMapper.selectAllUsable();
        Multimap<Integer, Asserts> companyIdMap = Multimaps.index(asserts, Asserts::getCompanyId);

        List<ExportAsserts> exportAssertsList = Lists.newArrayList();
        for (Company company : allCompany) {
            if (LineStatusEnum.DELETED.getCode() == company.getStatus()) {
                continue;
            }
            ExportAsserts exportAsserts = new ExportAsserts();
            exportAsserts.setCompanyName(company.getName());
            exportAsserts.setFloodManager(company.getFloodManager());
            exportAsserts.setFloodManagerPhone(company.getFloodManagerPhone());
            List<Pair> assertsList = toExportAssertsList(companyIdMap.get(company.getId()), assertsTypeList);

            exportAssertsList.add(exportAsserts);
        }

        return null;
    }

    private List<Pair> toExportAssertsList(Collection<Asserts> asserts, List<CommonType> assertsTypeList) {
        Multimap<Integer, Asserts> idAssertsMap = Multimaps.index(asserts, Asserts::getId);
        List<Pair> pairList = Lists.newArrayList();
        for (CommonType assertsType : assertsTypeList) {
            Collection<Asserts> thisTypeAsserts = idAssertsMap.get(assertsType.getId());
            if (CollectionUtils.isEmpty(thisTypeAsserts)) {
                pairList.add(new Pair(assertsType.getName(), "0"));
            } else {
                String value = PLUS_JOINER.join(Collections2.transform(thisTypeAsserts, Asserts::getAssertsValue));
                pairList.add(new Pair(assertsType.getName(), value));
            }
        }
        return pairList;
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
