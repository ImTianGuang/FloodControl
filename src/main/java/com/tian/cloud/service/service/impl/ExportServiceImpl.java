package com.tian.cloud.service.service.impl;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.*;
import com.tian.cloud.service.config.ExportConfig;
import com.tian.cloud.service.controller.request.CommonSearchReq;
import com.tian.cloud.service.dao.entity.*;
import com.tian.cloud.service.dao.mapper.*;
import com.tian.cloud.service.enums.CommonTypeEnum;
import com.tian.cloud.service.enums.LineStatusEnum;
import com.tian.cloud.service.enums.Orgnization;
import com.tian.cloud.service.model.export.ExportAsserts;
import com.tian.cloud.service.model.export.ExportContext;
import com.tian.cloud.service.model.export.ExportUser;
import com.tian.cloud.service.model.export.Pair;
import com.tian.cloud.service.service.ExportService;
import com.tian.cloud.service.service.UserService;
import com.tian.cloud.service.util.OhMyEmail;
import com.tian.cloud.service.util.ParamCheckUtil;
import com.tian.cloud.service.util.excel.ExcelExportUtil;
import com.tian.cloud.service.util.excel.MySheet;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import java.io.File;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author tianguang
 * 2018/9/3 下午3:57
 **/
@Service
@Slf4j
public class ExportServiceImpl implements ExportService {

    @Resource
    private UserService userService;

    @Resource
    private CompanyMapper companyMapper;

    @Resource
    private AssertsMapper assertsMapper;

    @Resource
    private CommonTypeMapper commonTypeMapper;

    @Resource
    private ExportConfig exportConfig;

    @Resource
    private FloodSituationDetailMapper floodSituationDetailMapper;

    @Resource
    private SituationMapper situationMapper;

    private static final List<String> headList = Lists.newArrayList(" ", "名称", "姓名", "职务", "办公电话", "手机", "传真");

    private static final String COMPANY_TITLE_FORMAT = "%s防汛指挥部通讯录";

    private static final Joiner PLUS_JOINER = Joiner.on("+").skipNulls();

    private static final Splitter SPLITTER = Splitter.on(';').trimResults().omitEmptyStrings();

    @Override
    public List<MySheet> buildAllUserSheetList(ExportContext context) {
        List<Company> allCompany = context.getAllCompany();
        Map<Integer, Company> idCompanyMap = Maps.uniqueIndex(allCompany, Company::getId);
        List<User> allUser = context.getAllUsableUser();
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
    public MySheet buildAssertsSheet(ExportContext context) {
        List<Company> allCompany = context.getAllCompany();
        List<CommonType> assertsTypeList = context.getAssertsTypeList();
        List<Asserts> asserts = context.getUsableAsserts();
        Multimap<Integer, Asserts> companyIdMap = Multimaps.index(asserts, Asserts::getCompanyId);

        List<ExportAsserts> exportAssertsList = Lists.newArrayList();
        for (Company company : allCompany) {
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

    @Override
    public Workbook buildCompanySummary(Workbook workbook, ExportContext context) {
        List<Company> allCompany = context.getAllCompany();
        List<User> allUsableUser = context.getAllUsableUser();
        List<Asserts> asserts = context.getUsableAsserts();
        List<CommonType> positionList = context.getAssertsTypeList();
        Map<Integer, CommonType> idAndPositionMap = Maps.uniqueIndex(positionList, CommonType::getId);
        Sheet sheet = workbook.createSheet("汇总");

        CellStyle cellStyle = getCellStyle(workbook);

        //设置自动换行
        cellStyle.setWrapText(true);

        int startRow = -1;
        if (!CollectionUtils.isEmpty(allCompany)) {
            for (Company company : allCompany) {
                if (company.getStatus() != LineStatusEnum.USABLE.getCode()) {
                    continue;
                }
                startRow ++;
                startRow = addCompanyToSheet(startRow, workbook, sheet, cellStyle, company, allUsableUser, asserts, idAndPositionMap);
            }
        }
        for (int i = 0; i < headList.size(); i++) {
            sheet.autoSizeColumn(i);
            int width = sheet.getColumnWidth(i);
            width = width * 13 / 10;
            sheet.setColumnWidth(i,width);
        }
        return workbook;
    }

    @Override
    public Workbook buildAll() {
        ExportContext context = new ExportContext();
        List<Company> allUsableCompany = companyMapper.selectAllUsable();
        List<User> allUsableUser = userService.getAllUsableUser();
        List<Asserts> allUsableAsserts = assertsMapper.selectAllUsable();
        List<CommonType> assertsTypeList = commonTypeMapper.selectUsableByType(CommonTypeEnum.ASSERTS.getCode());
        List<CommonType> positionTypeList = commonTypeMapper.selectUsableByType(CommonTypeEnum.POSITION.getCode());

        context.setAllCompany(allUsableCompany);
        context.setAllUsableUser(allUsableUser);
        context.setUsableAsserts(allUsableAsserts);
        context.setAssertsTypeList(assertsTypeList);
        context.setPositionTypeList(positionTypeList);

        List<MySheet> userSheetList = buildAllUserSheetList(context);
        MySheet assertsSheet = buildAssertsSheet(context);
        if (assertsSheet != null) {
            userSheetList.add(assertsSheet);
        }
        Workbook workbook = ExcelExportUtil.exportWorkbook(userSheetList);
        buildCompanySummary(workbook, context);
        return workbook;
    }

    @Override
    public void exportAll(String emails) {
        ParamCheckUtil.assertTrue(!StringUtils.isEmpty(emails), "邮箱必填");
        try {

            Workbook workbook = buildAll();

            String filePath = exportConfig.getFilePath() + "companyBooks.xls";
            ExcelExportUtil.writeToFile(workbook, filePath);
            File file = new File(filePath);

            OhMyEmail.subject("汛前通讯录-导出")
                    .from("防汛小程序")
                    .to(emails)
                    .text("汛前通讯录已导出，请查看附件")
                    .attach(file, "汛前通讯录.xls")
                    .send();
        } catch (Exception e) {
            log.error("导出错误:", e);
        }
    }

    @Override
    public void exportFlood(CommonSearchReq searchReq) {
        List<FloodSituation> floodSituations = situationMapper.search(searchReq);
        if (CollectionUtils.isEmpty(floodSituations)) {
            return;
        }
        List<FloodSituationDetail> details = floodSituationDetailMapper.getBySituationIdList(Lists.transform(floodSituations, FloodSituation::getId));
        List<Company> companyList = companyMapper.selectByIdList(Lists.transform(floodSituations, FloodSituation::getCompanyId));
        Workbook workbook = buildFloodWorkBook(floodSituations, details, companyList);
    }

    private Workbook buildFloodWorkBook(List<FloodSituation> floodSituations, List<FloodSituationDetail> details, List<Company> companyList) {
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("汛期中-实时上报表");
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

    private CellStyle getCellStyle(Workbook workbook, HSSFColor.HSSFColorPredefined color) {
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        //垂直居中
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        //设置边框
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);

        cellStyle.setFillForegroundColor(color.getIndex());
        cellStyle.setFillBackgroundColor(color.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return cellStyle;
    }

    private int addCompanyToSheet(int startRow, Workbook workbook, Sheet sheet, CellStyle cellStyle, Company company, List<User> userList, List<Asserts> assertsList, Map<Integer, CommonType> idAndPositionMap) {

        int headStartRowNum = startRow;
        int org1EndRowNum = -1;
        int org2EndRowNum = -1;
        int assertsRowNum = -1;

        initHeadRow(startRow, sheet, workbook, company);
        startRow = startRow + 2;
        if (!CollectionUtils.isEmpty(userList)) {
            userList.sort(Comparator.comparingInt(User::getOrgCode));
            for (int i = 0;i < userList.size(); i++) {
                startRow = startRow + 1;
                User user = userList.get(i);
                if (user.getOrgCode() == 0) {
                    org1EndRowNum = startRow;
                }
                addUserRow(startRow, sheet, cellStyle, user, idAndPositionMap);
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
        createCell(floodManager, cellStyle, 0, "防汛抢险人员、物资统计");
        createCell(floodManager, cellStyle, 1, "抢险人员及抢险物资负责人");
        createCell(floodManager, cellStyle, 2, "");
        createCell(floodManager, cellStyle, 3, company.getFloodManager());
        createCell(floodManager, cellStyle, 4, "抢险人员及抢险物资负责人电话");
        createCell(floodManager, cellStyle, 5, "");
        createCell(floodManager, cellStyle, 6, company.getFloodManagerPhone());

        mergeCell(sheet, startRow, startRow, 1, 2);
        mergeCell(sheet, startRow, startRow, 4, 5);

        if (!CollectionUtils.isEmpty(assertsList)) {
            Row assertRow = null;
            for (int i = 0;i < assertsList.size(); i++) {
                if (i % 3 == 0) {
                    startRow = startRow + 1;
                    assertRow = createRow(sheet, startRow);
                    createCell(assertRow, cellStyle, 0, "防汛抢险人员、物资统计");
                }
                Asserts asserts = assertsList.get(i);
                int assertsIdx = (i % 3) * 2;
                createCell(assertRow, cellStyle, assertsIdx + 1, asserts.getAssertsTypeName());
                createCell(assertRow, cellStyle, assertsIdx + 2, asserts.getAssertsValue());
            }
        }

        assertsRowNum = startRow;

        startRow = startRow + 1;
        Row endRow = createRow(sheet, startRow);
        createCell(endRow, cellStyle, 0, "填表人");
        createCell(endRow, cellStyle, 1, company.getRecordPerson());

        createCell(endRow, cellStyle, 2, "填表人电话");
        createCell(endRow, cellStyle, 3, company.getRecordPersonPhone());

        createCell(endRow, cellStyle, 4, "审核人");
        createCell(endRow, cellStyle, 5, company.getCheckPerson());

        Date updateDate = new Date(company.getUpdateTime());
        LocalDateTime localDateTime = LocalDateTime.ofInstant(updateDate.toInstant(), ZoneId.systemDefault());

        createCell(endRow, cellStyle, 6, "填表日期:" + localDateTime.toLocalDate().format(DateTimeFormatter.ISO_LOCAL_DATE));
//        Cell checkPersonPhoneValue = createCell(endRow, cellStyle, 7, company.getCheckPersonPhone());

        startRow = startRow + 1;
        createRow(sheet, startRow);

        startRow = startRow + 1;
        createRow(sheet, startRow);

        startRow = startRow + 1;
        createRow(sheet, startRow);

        startRow = startRow + 1;
        createRow(sheet, startRow);

        mergeCell(sheet, headStartRowNum, headStartRowNum + 1, 0, 6);
        if (org1EndRowNum != -1) {
            mergeCell(sheet, headStartRowNum+3, org1EndRowNum, 0, 0);//指挥部
        }
        if (org2EndRowNum != -1) {
            mergeCell(sheet, org1EndRowNum + 1, org2EndRowNum, 0, 0);//指挥部
        }
        if (assertsRowNum != -1) {
            mergeCell(sheet, org2EndRowNum + 1, assertsRowNum, 0, 0);//指挥部
        }
//        mergeCell(sheet, headStartRowNum, headStartRowNum + 1, 0, 6);
//        mergeCell(sheet, he•adStartRowNum, headStartRowNum + 1, 0, 6);

        return startRow;
    }

    private void mergeCell(Sheet sheet, int firstRow, int lastRow, int firstCol, int lastCol) {
        if ((firstRow == lastRow) && (firstCol == lastCol)) {
            return;
        }
        // 合并单元格
        CellRangeAddress cra =new CellRangeAddress(firstRow, lastRow, firstCol, lastCol);
        sheet.addMergedRegionUnsafe(cra);

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
        createCell(companyRow, cellStyle, 2, "");
        mergeCell(sheet, startRow, startRow, 1, 2);
        createCell(companyRow, cellStyle, 3, value);
        createCell(companyRow, cellStyle, 4, "");
        createCell(companyRow, cellStyle, 5, "");
        createCell(companyRow, cellStyle, 6, "");
        mergeCell(sheet, startRow, startRow, 3, 6);
    }

    private void addUserRow(int startRow, Sheet sheet, CellStyle cellStyle, User user, Map<Integer, CommonType> idAndPositionMap) {
        Row userRow = sheet.createRow(startRow);
        createCell(userRow, cellStyle, 0, user.getOrgTitle());

        createCell(userRow, cellStyle, 1, user.getFloodTitle());

        createCell(userRow, cellStyle, 2, user.getUserName());

        CommonType position = idAndPositionMap.get(user.getPositionId());
        createCell(userRow, cellStyle, 3, position == null ? "未知": position.getName());

        createCell(userRow, cellStyle, 4, user.getWorkPhone());

        createCell(userRow, cellStyle, 5, user.getUserPhone());

        createCell(userRow, cellStyle, 6, user.getFax());
    }

    private void initHeadRow(int startRow, Sheet sheet, Workbook workbook, Company company) {
        Row titleRow = sheet.createRow(startRow);
        // HSSFColor.HSSFColorPredefined.LIGHT_ORANGE
        createCell(titleRow, getCellStyle(workbook), 0, String.format(COMPANY_TITLE_FORMAT, company.getName()));
        sheet.createRow(startRow + 1);

        Row headRow = sheet.createRow(startRow + 2);
        for (int i = 0; i < headList.size(); i++) {
            createCell(headRow, getCellStyle(workbook, HSSFColor.HSSFColorPredefined.LIGHT_GREEN), i, headList.get(i));
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
            if (company == null) {
                continue;
            }
            exportUser.setCompanyName(company.getName());
            exportUser.setFax(user.getFax());
            exportUser.setName(user.getUserName());
            exportUser.setPersonPhone(user.getUserPhone());
            exportUser.setWorkPhone(user.getWorkPhone());

            exportUsers.add(exportUser);
        }
        return exportUsers;
    }
}
