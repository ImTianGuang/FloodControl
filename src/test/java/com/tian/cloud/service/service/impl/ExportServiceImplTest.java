package com.tian.cloud.service.service.impl;

import com.tian.cloud.service.TestBase;
import com.tian.cloud.service.service.ExportService;
import com.tian.cloud.service.util.OhMyEmail;
import com.tian.cloud.service.util.excel.ExcelExportUtil;
import com.tian.cloud.service.util.excel.MySheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;

import javax.annotation.Resource;

import java.io.File;
import java.util.List;

public class ExportServiceImplTest extends TestBase {

    @Resource
    private ExportService exportService;

    private static final String filePath = "/Users/tianguang/demo-user.xls";

    @Test
    public void buildAllUserSheetList() throws Exception {
        List<MySheet> userSheetList = exportService.getAllUserSheetList();
        MySheet asserts = exportService.getAssertsSheet();
        if (asserts != null) {
            userSheetList.add(asserts);
        }
        ExcelExportUtil.exportToFile(filePath, userSheetList);
        File file = new File(filePath);
        OhMyEmail.subject("test")
                .from("tianguang")
                .to("308929467@qq.com")
                .text("信件内容")
                .attach(file, "统计")
                .send();
    }

    @Test
    public void companyInfo() throws Exception {
        Workbook workbook = exportService.exportAll();
        ExcelExportUtil.writeToFile(workbook, filePath);

    }
}