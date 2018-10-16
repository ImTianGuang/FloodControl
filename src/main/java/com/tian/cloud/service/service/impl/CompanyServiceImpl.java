package com.tian.cloud.service.service.impl;

import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.tian.cloud.service.controller.response.CompanyInfo;
import com.tian.cloud.service.controller.response.CompanySituationTypes;
import com.tian.cloud.service.dao.entity.*;
import com.tian.cloud.service.dao.mapper.CommonTypeMapper;
import com.tian.cloud.service.dao.mapper.CompanyMapper;
import com.tian.cloud.service.enums.CommonTypeEnum;
import com.tian.cloud.service.enums.LineStatusEnum;
import com.tian.cloud.service.enums.Orgnization;
import com.tian.cloud.service.exception.ErrorCode;
import com.tian.cloud.service.exception.InternalException;
import com.tian.cloud.service.service.AssertsService;
import com.tian.cloud.service.service.CompanyService;
import com.tian.cloud.service.service.UserService;
import com.tian.cloud.service.util.FileUtils;
import com.tian.cloud.service.util.ParamCheckUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author tianguang
 * 2018/8/25 下午9:07
 **/
@Service
@Slf4j
public class CompanyServiceImpl implements CompanyService {

    @Resource
    private CompanyMapper companyMapper;

    @Resource
    private UserService userService;

    @Resource
    private AssertsService assertsService;

    @Resource
    private CommonTypeMapper commonTypeMapper;

    private static final Splitter COMA_SPLITTER = Splitter.on(",").omitEmptyStrings().trimResults();

    private static final Joiner JOINER = Joiner.on(",").skipNulls();

    @Override
    public List<Company> selectAll() {
        return companyMapper.selectAllUsable();
    }

    @Override
    public List<Company> search(String companyName) {
        return companyMapper.search(companyName);
    }

    @Override
    public CompanyInfo getCompanyInfo(int companyId) {
        if (companyId == -1) {
            return emptyCompanyInfo();
        }
        Company company = companyMapper.selectById(companyId);
        ParamCheckUtil.assertTrue(company != null, "单位不存在");
        List<Asserts> asserts = assertsService.getAssertsByCompany(companyId);
        List<CompanyUser> users = userService.getUserByCompany(companyId);
        users = fillAbsentUsers(users, company);
        asserts = fillAbsentAsserts(asserts);
        List<CompanyInfo.PhoneInfo> phoneInfos = toPhoneInfos(users);
        CompanyInfo companyInfo = new CompanyInfo();
        companyInfo.setAssertsList(asserts);
        if (!StringUtils.isEmpty(company.getFloodPlan())) {
            company.setFloodPlan(FileUtils.getFileName(company.getFloodPlan()));
        }
        companyInfo.setCompany(company);
        companyInfo.setPhoneList(phoneInfos);
        return companyInfo;
    }

    @Override
    public CompanySituationTypes situationTypesOfCompany(Integer companyId) {
        Company company = companyMapper.selectById(companyId);
        ParamCheckUtil.assertTrue(company != null, "单位不存在");
        List<Integer> situationIdList = COMA_SPLITTER.splitToList(company.getSituationIds()).stream().map(Integer::valueOf).collect(Collectors.toList());
        List<Integer> solutionIdList = COMA_SPLITTER.splitToList(company.getSolutionIds()).stream().map(Integer::valueOf).collect(Collectors.toList());
        List<CommonType> situationTypeList = commonTypeMapper.selectByIdList(situationIdList);
        List<CommonType> solutionTypeList = commonTypeMapper.selectByIdList(solutionIdList);

        CompanySituationTypes result = new CompanySituationTypes();
        result.setSituationTypeList(situationTypeList);
        result.setSolutionTypeList(solutionTypeList);
        result.setCompanyId(companyId);
        return result;
    }

    private CompanyInfo emptyCompanyInfo() {
        CompanyInfo companyInfo = new CompanyInfo();
        Company company = new Company();
        company.setId(-1);
        company.setStatus(LineStatusEnum.USABLE.getCode());
        List<CompanyUser> users = Lists.newArrayList();
        users = fillAbsentUsers(users, company);
        List<CompanyInfo.PhoneInfo> phoneInfos = toPhoneInfos(users);
        List<Asserts> asserts = Lists.newArrayList();
        asserts = fillAbsentAsserts(asserts);

        companyInfo.setCompany(company);
        companyInfo.setPhoneList(phoneInfos);
        companyInfo.setAssertsList(asserts);
        return companyInfo;
    }

    private List<Asserts> fillAbsentAsserts(List<Asserts> assertsList) {
        List<Asserts> result = Lists.newArrayList();

        Multimap<Integer, Asserts> typeIdMap = Multimaps.index(assertsList, Asserts::getAssertsTypeId);
        List<CommonType> commonTypeList = commonTypeMapper.selectUsableByType(CommonTypeEnum.ASSERTS.getCode());

        for (CommonType assertsType: commonTypeList) {
            Collection<Asserts> assertsCollection = typeIdMap.get(assertsType.getId());
            if (CollectionUtils.isEmpty(assertsCollection)) {
                Asserts asserts = new Asserts();
                asserts.setStatus(1);
                asserts.setAssertsValue("");
                asserts.setAssertsTypeName(assertsType.getName());
                asserts.setAssertsTypeId(assertsType.getId());
                asserts.setAssertsDesc(assertsType.getTypeDesc());
                if (StringUtils.isEmpty(asserts.getAssertsDesc())) {
                    asserts.setAssertsDesc("请输入" + assertsType.getName());
                }
                result.add(asserts);
            } else {
                for (Asserts asserts : assertsCollection) {
                    asserts.setAssertsDesc(assertsType.getTypeDesc());
                    if (StringUtils.isEmpty(asserts.getAssertsDesc())) {
                        asserts.setAssertsDesc("请输入" + assertsType.getName());
                    }
                    result.add(asserts);
                }
            }

        }
        return result;
    }
    private List<CompanyUser> fillAbsentUsers(List<CompanyUser> userList, Company company) {
        List<CompanyUser> result = Lists.newArrayList();
        userList = userList == null ? Lists.newArrayList() : userList;
        Multimap<String, CompanyUser> titleUserMap = Multimaps.index(userList, CompanyUser::getFloodTitle);
        List<CommonType> floodTitleList = commonTypeMapper.selectUsableByType(CommonTypeEnum.FLOOD_TITLE.getCode());
        createDefaultIfEmpty(floodTitleList, CommonTypeEnum.FLOOD_TITLE);
        for (CommonType title : floodTitleList) {
            List<CompanyUser> users = (List<CompanyUser>) titleUserMap.get(title.getName());
            if (CollectionUtils.isEmpty(users)) {
                CompanyUser user = new CompanyUser();
                user.setCompanyId(company.getId() == null ? -1 : company.getId());
                user.setOrgCode(Orgnization.ORG1.getCode());
                user.setOrgTitle(Orgnization.ORG1.getMsg());
                if (Orgnization.isOrg2Title(title.getName())) {
                    if (title.getName().startsWith("汛期24小时")) {
                        user.setIsAutoFax(true);
                    }
                    user.setOrgCode(Orgnization.ORG2.getCode());
                    user.setOrgTitle(Orgnization.ORG2.getMsg());
                }
                user.setFloodTitle(title.getName());
                user.setTitleDesc(title.getTypeDesc());
                user.setPositionId(-1);
                if (title.getName().contains("指挥部成员")) {
                    user.setCanAdd(true);
                }
                result.add(user);
            } else {
                for (int i=0;i<users.size(); i ++) {
                    CompanyUser companyUser = users.get(i);
                    if (i==0 && title.getName().contains("指挥部成员")) {
                        companyUser.setCanAdd(true);
                    }
                    if (Orgnization.isOrg2Title(title.getName())) {
                        if (title.getName().startsWith("非汛期")) {
                            companyUser.setIsAutoFax(true);
                        }
                        companyUser.setOrgCode(Orgnization.ORG2.getCode());
                        companyUser.setOrgTitle(Orgnization.ORG2.getMsg());
                    }
                    companyUser.setTitleDesc(title.getTypeDesc());
                    result.add(companyUser);
                }
            }
        }

        return result;
    }


    private boolean containAsserts(List<Asserts> asserts, Integer id) {
        for (Asserts asserts1 : asserts) {
            if (Objects.equal(asserts1.getAssertsTypeId(),id)) {
                return true;
            }
        }
        return false;
    }

    private List<CompanyUser> defaultUsers(List<CompanyUser> userList, Company company) {
        if (CollectionUtils.isEmpty(userList)) {
            userList = Lists.newArrayList();
        }

        List<CommonType> commonTypeList = commonTypeMapper.selectUsableByType(CommonTypeEnum.POSITION.getCode());
        List<CommonType> floodTitleList = commonTypeMapper.selectUsableByType(CommonTypeEnum.FLOOD_TITLE.getCode());
        createDefaultIfEmpty(commonTypeList, CommonTypeEnum.POSITION);
        createDefaultIfEmpty(floodTitleList, CommonTypeEnum.FLOOD_TITLE);

        CommonType commonType = commonTypeList.get(0);
        CompanyUser user = new CompanyUser();
        user.setCompanyId(company.getId() == null ? -1 : company.getId());
        user.setOrgCode(Orgnization.ORG1.getCode());
        user.setOrgTitle(Orgnization.ORG1.getMsg());
        user.setFloodTitle(floodTitleList.get(0).getName());
        user.setPositionId(commonType.getId());

        CompanyUser user1 = new CompanyUser();
        user1.setCompanyId(company.getId() == null ? -1 : company.getId());
        user1.setOrgCode(Orgnization.ORG2.getCode());
        user1.setOrgTitle(Orgnization.ORG2.getMsg());
        user1.setPositionId(commonType.getId());
        user1.setFloodTitle(floodTitleList.get(0).getName());

        boolean containOrg1 = false;
        boolean containOrg2 = false;

        for (CompanyUser user2 : userList) {
            if (user2.getOrgCode() == Orgnization.ORG1.getCode()) {
                containOrg1 = true;
            }
            if (user2.getOrgCode() == Orgnization.ORG2.getCode()) {
                containOrg2 = true;
            }
        }

        if (!containOrg1) {
            userList.add(user);
        }
        if (!containOrg2) {
            userList.add(user1);
        }
        return userList;
    }

    private void createDefaultIfEmpty(List<CommonType> commonTypeList, CommonTypeEnum commonTypeEnum) {
        if (CollectionUtils.isEmpty(commonTypeList)) {
            commonTypeList = Lists.newArrayList();
            CommonType commonType = new CommonType();
            commonType.setStatus(LineStatusEnum.USABLE.getCode());
            commonType.setName("其他");
            commonType.setCommonTypeEnum(commonTypeEnum.getCode());
            commonType.setCreateTime(System.currentTimeMillis());
            commonType.setUpdateTime(commonType.getUpdateTime());
            commonTypeList.add(commonType);
            commonTypeMapper.saveBatch(commonTypeList);
        }
    }

    @Override
    public Company updateCompanyInfo(CompanyInfo companyInfo) {
        if (companyInfo.getCompany().getStatus() == LineStatusEnum.DELETED.getCode()) {
            userService.deleteByCompanyId(companyInfo.getCompany().getId());
            assertsService.deleteByCompanyId(companyInfo.getCompany().getId());
        }
        ensureCompanyId(companyInfo.getCompany(), companyInfo.getAssertsList(), companyInfo.getPhoneList());
        this.updateCompany(companyInfo.getCompany());
        assertsService.saveOrUpdate(companyInfo.getAssertsList());
        userService.saveOrUpdate(companyInfo.getPhoneList());
        return companyInfo.getCompany();
    }

    public Company saveOrUpdateCompanyInfo(CompanyInfo companyInfo) {

        Company company = companyInfo.getCompany();
        ParamCheckUtil.assertTrue(!StringUtils.isEmpty(companyInfo.getCompany().getName()), "单位名称不能为空");

        Company dbCompany = companyMapper.selectByName(companyInfo.getCompany().getName());
        if (dbCompany != null && !Objects.equal(company.getId(), dbCompany.getId())) {
            throw new InternalException(ErrorCode.PARAM_ERROR, "单位名称不能重复");
        }
        if (companyInfo.getCompany().getId() != -1) {
            return this.updateCompanyInfo(companyInfo);
        } else {
            return this.saveCompanyInfo(companyInfo);
        }

    }

    private Company saveCompanyInfo(CompanyInfo companyInfo) {
        Company company = companyInfo.getCompany();
        companyMapper.insert(company);
        ParamCheckUtil.assertTrue(company.getId() != -1, "添加单位失败");
        ensureCompanyId(company, companyInfo.getAssertsList(), companyInfo.getPhoneList());
        assertsService.saveOrUpdate(companyInfo.getAssertsList());
        userService.saveOrUpdate(companyInfo.getPhoneList());
        return company;
    }

    private void ensureCompanyId(Company company, List<Asserts> assertsList, List<CompanyInfo.PhoneInfo> phoneList) {
        if (!CollectionUtils.isEmpty(assertsList)) {
            for (Asserts asserts : assertsList) {
                asserts.setCompanyId(company.getId());
            }
        }
        if (!CollectionUtils.isEmpty(phoneList)) {
            for (CompanyInfo.PhoneInfo phoneInfo : phoneList) {
                if (CollectionUtils.isEmpty(phoneInfo.getUserList())) {
                    continue;
                }
                for (CompanyUser user : phoneInfo.getUserList()) {
                    user.setCompanyId(company.getId());
                }
            }
        }
    }

    @Override
    public void saveOrUpdate(List<Company> companyList) {
        if (CollectionUtils.isEmpty(companyList)) {
            return;
        }
        List<Company> updateList = Lists.newArrayList();
        List<Company> saveList = Lists.newArrayList();
        for (Company company : companyList) {
            if (company.getId() == null || company.getId() == 0) {
                if (company.getStatus() == LineStatusEnum.USABLE.getCode()) {
                    saveList.add(company);
                }
            } else {
                company.setUpdateTime(System.currentTimeMillis());
                updateList.add(company);
            }
        }

        if (!CollectionUtils.isEmpty(updateList)) {
            for (Company update : updateList) {
                companyMapper.updateCompany(update);
            }
        }

        if (!CollectionUtils.isEmpty(saveList)) {
            companyMapper.insertBatch(saveList);
        }
    }

    @Override
    public boolean saveOrUpdate(Company company) {
        ParamCheckUtil.assertTrue(!StringUtils.isEmpty(company.getName()), "单位名称必填");
        if (company.getId() != null) {
            this.updateCompany(company);
        }
        if (company.getId() == null && company.getStatus() == LineStatusEnum.USABLE.getCode()) {
            Company dbRecord = companyMapper.selectByName(company.getName());
            ParamCheckUtil.assertTrue(dbRecord == null, "已存在相同单位名称:" + company.getName());
            companyMapper.insertBatch(Lists.newArrayList(company));
        }
        return false;
    }

    public boolean updateCompany(Company company) {

        company.setUpdateTime(System.currentTimeMillis());
        return companyMapper.updateCompany(company) > 0;
    }

    private List<CompanyInfo.PhoneInfo> toPhoneInfos(List<CompanyUser> users) {
        if (CollectionUtils.isEmpty(users)) {
            return Lists.newArrayList();
        }
        ImmutableListMultimap<Integer, CompanyUser> orgCodeMap = Multimaps.index(users, CompanyUser::getOrgCode);
        List<CompanyInfo.PhoneInfo> phoneInfos = Lists.newArrayList();
        for (Orgnization orgnization : Orgnization.values()) {
            List<CompanyUser> userList = orgCodeMap.get(orgnization.getCode());
            CompanyInfo.PhoneInfo phoneInfo = new CompanyInfo.PhoneInfo();
            phoneInfo.setId(orgnization.getCode());
            phoneInfo.setName(orgnization.getMsg());
            phoneInfo.setUserList(userList);

            phoneInfos.add(phoneInfo);
        }

        return phoneInfos;
    }
}
