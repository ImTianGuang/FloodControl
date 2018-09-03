package com.tian.cloud.service.service.impl;

import com.tian.cloud.service.TestBase;
import com.tian.cloud.service.service.ExportService;
import com.tian.cloud.service.util.excel.ExcelExportUtil;
import com.tian.cloud.service.util.excel.MySheet;
import org.junit.Test;

import javax.annotation.Resource;

import java.util.List;

public class ExportServiceImplTest extends TestBase {

    @Resource
    private ExportService exportService;

    private static final String filePath = "/Users/tianguang/demo-user.xls";

    @Test
    public void buildAllUserSheetList() {
        List<MySheet> userSheetList = exportService.getAllUserSheetList();
        ExcelExportUtil.exportToFile(filePath, userSheetList);
    }
}