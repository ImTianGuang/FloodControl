package com.tian.cloud.service.service.impl;

import com.google.common.base.Joiner;
import com.google.common.collect.*;
import com.tian.cloud.service.dao.entity.Asserts;
import com.tian.cloud.service.dao.entity.CommonType;
import com.tian.cloud.service.dao.entity.Company;
import com.tian.cloud.service.dao.entity.User;
import com.tian.cloud.service.dao.mapper.AssertsMapper;
import com.tian.cloud.service.dao.mapper.CommonTypeMapper;
import com.tian.cloud.service.dao.mapper.CompanyMapper;
import com.tian.cloud.service.enums.CommonTypeEnum;
import com.tian.cloud.service.enums.LineStatusEnum;
import com.tian.cloud.service.enums.Orgnization;
import com.tian.cloud.service.model.export.ExportAsserts;
import com.tian.cloud.service.model.export.ExportUser;
import com.tian.cloud.service.model.export.Pair;
import com.tian.cloud.service.service.ExportService;
import com.tian.cloud.service.service.UserService;
import com.tian.cloud.service.util.excel.MySheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;

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
        List<Company> allCompany = companyMapper.selectAll();
        Map<Integer, Company> idCompanyMap = Maps.uniqueIndex(allCompany, Company::getId);
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
            List<ExportUser> exportUsers = toExportUser(floodTitleUserMap.get(floodTitle), idCompanyMap);
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
            exportAsserts.setAssertsList(assertsList);
            exportAssertsList.add(exportAsserts);
        }

        if (CollectionUtils.isEmpty(exportAssertsList)) {
            return null;
        }

        MySheet mySheet = new MySheet();
        mySheet.setSheetName("抢险物资和人员统计");
        mySheet.setDataList(exportAssertsList);
        return mySheet;
    }

    private static final List<String> headList = Lists.newArrayList(" ", "名称", "姓名", "职务", "办公电话", "手机", "传真");

    private static final String COMPANY_TITLE_FORMAT = "%s防汛指挥部通讯录";
    @Override
    public Workbook getCompanySummary() {
        List<Company> allCompany = companyMapper.selectAll();
        List<User> allUser = userService.getAllUser();
        List<Asserts> asserts = assertsMapper.selectAllUsable();
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("");

        if (!CollectionUtils.isEmpty(allCompany)) {
            for (Company company : allCompany) {

            }
        }
        return workbook;
    }

    private void addCompanyToSheet(int startRow, Sheet sheet, Company company, List<User> userList, List<Asserts> assertsList) {

        int org1EndRowNum = -1;
        int org2EndRowNum = -1;

        initHeadRow(startRow, sheet, company);
        startRow = startRow + 2;
        if (!CollectionUtils.isEmpty(userList)) {
            userList.sort(Comparator.comparingInt(User::getOrgCode));
            for (int i = 0;i < userList.size(); i++) {
                startRow = startRow + i;
                User user = userList.get(i);
                if (user.getOrgCode() == 0) {
                    org1EndRowNum = startRow;
                }
                addUserRow(startRow, sheet, user);
            }
        }

        startRow = startRow + 1;
        addCompanyInfo(startRow, sheet,"单位邮箱", company.getEmail());
        startRow = startRow + 1;
        addCompanyInfo(startRow, sheet,"地址", company.getAddress());
        startRow = startRow + 1;
        addCompanyInfo(startRow, sheet,"邮编", company.getPostCode());


    }

    private void addCompanyInfo(int startRow, Sheet sheet, String key, String value) {

        Row companyRow = sheet.createRow(startRow);
        Cell cell = companyRow.createCell(0);
        cell.setCellValue(Orgnization.ORG2.getCode());

        Cell cellKey = companyRow.createCell(1);
        cellKey.setCellValue(key);

        Cell cellValue = companyRow.createCell(2);
        cellValue.setCellValue(value);
    }

    private void addUserRow(int startRow, Sheet sheet, User user) {
        Row userRow = sheet.createRow(startRow);
        Cell org = userRow.createCell(0);
        org.setCellValue(user.getOrgTitle());

        Cell title = userRow.createCell(1);
        title.setCellValue(user.getFloodTitle());

        Cell name = userRow.createCell(2);
        name.setCellValue(user.getUserName());

        Cell position = userRow.createCell(3);
        position.setCellValue(user.getPositionId());//todo

        Cell workPhone = userRow.createCell(4);
        workPhone.setCellValue(user.getWorkPhone());

        Cell phone = userRow.createCell(5);
        phone.setCellValue(user.getUserPhone());

        Cell fax = userRow.createCell(6);
        fax.setCellValue(user.getFax());
    }

    private void initHeadRow(int startRow, Sheet sheet, Company company) {
        Row titleRow = sheet.createRow(startRow);
        Cell cell = titleRow.createCell(0, CellType.STRING);
        cell.setCellValue(String.format(COMPANY_TITLE_FORMAT, company.getName()));
        Row headRow = sheet.createRow(startRow + 1);
        for (int i = 0; i < headList.size(); i++) {
            Cell headCell = headRow.createCell(i, CellType.STRING);
            headCell.setCellValue(headList.get(i));
        }
    }

    private List<Pair> toExportAssertsList(Collection<Asserts> asserts, List<CommonType> assertsTypeList) {
        Multimap<Integer, Asserts> idAssertsMap = Multimaps.index(asserts, Asserts::getAssertsTypeId);
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

    private List<ExportUser> toExportUser(Collection<User> users, Map<Integer, Company> idCompanyMap) {
        List<ExportUser> exportUsers = Lists.newArrayList();
        for (User user : users) {
            ExportUser exportUser = new ExportUser();
            Company company = idCompanyMap.get(user.getCompanyId());
            if (company != null) {
                exportUser.setCompanyName(company.getName());
            } else {
                exportUser.setCompanyName("未知");
            }
            exportUser.setFax(user.getFax());
            exportUser.setName(user.getUserName());
            exportUser.setPersonPhone(user.getUserPhone());
            exportUser.setWorkPhone(user.getWorkPhone());

            exportUsers.add(exportUser);
        }
        return exportUsers;
    }
}
