package com.tian.cloud.service.util.excel;

import lombok.Data;
import org.apache.poi.hssf.util.HSSFColor;

import java.util.List;

/**
 * @author tianguang
 * 2018/9/3 下午3:46
 **/
@Data
public class MySheet {

    private String sheetName;

    private HSSFColor.HSSFColorPredefined sheetColor;

    private List<?> dataList;
}
