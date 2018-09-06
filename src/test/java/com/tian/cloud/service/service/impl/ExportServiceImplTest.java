package com.tian.cloud.service.service.impl;

import com.tian.cloud.service.TestBase;
import com.tian.cloud.service.config.ExportConfig;
import com.tian.cloud.service.service.ExportService;
import com.tian.cloud.service.util.excel.ExcelExportUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Resource;

public class ExportServiceImplTest extends TestBase {

    @Resource
    private ExportService exportService;

    @Resource
    private ExportConfig exportConfig;

    private static final String filePath = "/Users/tianguang/demo-user.xls";

    @Test
    public void buildAllUserSheetList() throws Exception {
//        List<MySheet> userSheetList = exportService.buildAllUserSheetList();
//        MySheet asserts = exportService.buildAssertsSheet();
//        if (asserts != null) {
//            userSheetList.add(asserts);
//        }
//        ExcelExportUtil.exportToFile(filePath, userSheetList);
//        File file = new File(filePath);
//        OhMyEmail.subject("test")
//                .from("tianguang")
//                .to("308929467@qq.com")
//                .text("信件内容")
//                .attach(file, "统计")
//                .send();
    }

    @Test
    public void companyInfo() throws Exception {
        exportService.exportAll("308929467@qq.com;1412777822@qq.com");

    }
}