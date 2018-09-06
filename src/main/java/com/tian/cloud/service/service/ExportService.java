package com.tian.cloud.service.service;

import com.tian.cloud.service.controller.request.CommonSearchReq;
import com.tian.cloud.service.model.export.ExportContext;
import com.tian.cloud.service.util.excel.MySheet;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.List;

public interface ExportService {

    List<MySheet> buildAllUserSheetList(ExportContext context);

    MySheet buildAssertsSheet(ExportContext context);

    Workbook buildCompanySummary(Workbook workbook, ExportContext context);

    Workbook buildAll();

    void exportAll(String emails);

    void exportFlood(CommonSearchReq searchReq);
}
