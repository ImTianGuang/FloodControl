package com.tian.cloud.service.service;

import com.tian.cloud.service.util.excel.MySheet;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.List;

public interface ExportService {

    List<MySheet> getAllUserSheetList();

    MySheet getAssertsSheet();

    Workbook getCompanySummary();
}
