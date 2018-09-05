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
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

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
        Sheet sheet = workbook.createSheet("汇总");

        CellStyle cellStyle = getCellStyle(workbook);

        //设置自动换行
        cellStyle.setWrapText(true);

        if (!CollectionUtils.isEmpty(allCompany)) {
            for (Company company : allCompany) {
                addCompanyToSheet(1, sheet, cellStyle, company, allUser, asserts);
            }
        }
        return workbook;
    }

    private CellStyle getCellStyle(Workbook workbook) {
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        //垂直居中
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        //设置边框
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        return cellStyle;
    }

    private void addCompanyToSheet(int startRow, Sheet sheet, CellStyle cellStyle, Company company, List<User> userList, List<Asserts> assertsList) {

        int org1EndRowNum = -1;
        int org2EndRowNum = -1;
        int assertsRowNum = -1;

        initHeadRow(startRow, sheet, cellStyle, company);
        startRow = startRow + 2;
        if (!CollectionUtils.isEmpty(userList)) {
            userList.sort(Comparator.comparingInt(User::getOrgCode));
            for (int i = 0;i < userList.size(); i++) {
                startRow = startRow + 1;
                User user = userList.get(i);
                if (user.getOrgCode() == 0) {
                    org1EndRowNum = startRow;
                }
                addUserRow(startRow, sheet, user);
            }
        }

        startRow = startRow + 1;
        addCompanyInfo(startRow, sheet,cellStyle, "单位邮箱", company.getEmail());
        startRow = startRow + 1;
        addCompanyInfo(startRow, sheet,cellStyle, "地址", company.getAddress());
        startRow = startRow + 1;
        addCompanyInfo(startRow, sheet,cellStyle, "邮编", company.getPostCode());

        org2EndRowNum = startRow;

        startRow = startRow + 1;
        Row floodManager = sheet.createRow(startRow);
        Cell fMCell0 = createCell(floodManager, cellStyle, 0, "物资..");
        Cell fMCell1 = createCell(floodManager, cellStyle, 1, "抢险人员及抢险物资负责人");
        Cell fMCell2 = createCell(floodManager, cellStyle, 2, company.getFloodManager());
        Cell fMCell3 = createCell(floodManager, cellStyle, 3, "抢险人员及抢险物资负责人电话");
        Cell fMCell4 = createCell(floodManager, cellStyle, 4, company.getFloodManagerPhone());


        if (!CollectionUtils.isEmpty(assertsList)) {
            startRow = startRow + 1;
            Row assertRow = createRow(sheet, startRow);
            for (int i = 0;i < assertsList.size(); i++) {
                if (i % 3 == 0) {
                    startRow = startRow + 1;
                    assertRow = createRow(sheet, startRow);
                    createCell(assertRow, cellStyle, 0, "物资..");
                }
                Asserts asserts = assertsList.get(i);
                int assertsIdx = i % 3;
                createCell(assertRow, cellStyle, assertsIdx, asserts.getAssertsTypeName());
                createCell(assertRow, cellStyle, assertsIdx, asserts.getAssertsValue());
            }
        }

        startRow = startRow + 1;
        Row endRow = createRow(sheet, startRow);
        Cell recordPerson = createCell(endRow, cellStyle, 0, "填表人");
        Cell recordPersonName = createCell(endRow, cellStyle, 1, company.getRecordPerson());

        Cell recordPersonPhone = createCell(endRow, cellStyle, 2, "填表人电话");
        Cell recordPersonPhoneValue = createCell(endRow, cellStyle, 3, company.getRecordPersonPhone());

        Cell checkPerson = createCell(endRow, cellStyle, 4, "审核人");
        Cell checkPersonName = createCell(endRow, cellStyle, 5, company.getCheckPerson());

        Cell checkPersonPhone = createCell(endRow, cellStyle, 6, "审核人电话");
        Cell checkPersonPhoneValue = createCell(endRow, cellStyle, 7, company.getCheckPersonPhone());

        startRow = startRow + 1;
        createRow(sheet, startRow);

        startRow = startRow + 1;
        createRow(sheet, startRow);

        for (int i = 0; i < headList.size(); i++) {
            sheet.autoSizeColumn(i);
//            sheet.setColumnWidth(i,sheet.getColumnWidth(i)*13/10);
        }

        // 合并单元格
        CellRangeAddress cra =new CellRangeAddress(1, 3, 1, 3); // 起始行, 终止行, 起始列, 终止列
        sheet.addMergedRegion(cra);

        // 使用RegionUtil类为合并后的单元格添加边框
        RegionUtil.setBorderBottom(1, cra, sheet); // 下边框
        RegionUtil.setBorderLeft(1, cra, sheet); // 左边框
        RegionUtil.setBorderRight(1, cra, sheet); // 有边框
        RegionUtil.setBorderTop(1, cra, sheet); // 上边框
    }

    private Row createRow(Sheet sheet, int rownum) {
        return sheet.createRow(rownum);
    }

    private Cell createCell (Row row, CellStyle cellStyle, int column, String value) {
        Cell cell = row.createCell(column);
        value = StringUtils.isEmpty(value) ? "无" : value;
        cell.setCellValue(value);
        cell.setCellStyle(cellStyle);
        return cell;
    }
    private void addCompanyInfo(int startRow, Sheet sheet, CellStyle cellStyle, String key, String value) {

        Row companyRow = createRow(sheet, startRow);
        createCell(companyRow, cellStyle, 0, Orgnization.ORG2.getMsg());
        createCell(companyRow, cellStyle, 1, key);
        createCell(companyRow, cellStyle, 2, value);
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

    private void initHeadRow(int startRow, Sheet sheet, CellStyle cellStyle, Company company) {
        Row titleRow = sheet.createRow(startRow);
        createCell(titleRow, cellStyle, 0, String.format(COMPANY_TITLE_FORMAT, company.getName()));

        Row headRow = sheet.createRow(startRow + 1);
        for (int i = 0; i < headList.size(); i++) {
            createCell(headRow, cellStyle, i, headList.get(i));
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
