package com.tian.cloud.service.util.model;

import com.tian.cloud.service.util.excel.ExcelExportUtil;
import com.tian.cloud.service.util.excel.ExcelImportUtil;
import com.tian.cloud.service.util.excel.MySheet;
import org.apache.poi.hssf.util.HSSFColor;
import org.assertj.core.util.Lists;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * FUN Test
 *
 * @author xuxueli 2017-09-08 22:41:19
 */
public class Test {

    public static void main(String[] args) {

        /**
         * Mock数据，Java对象列表
         */
        List<ShopDTO> shopDTOList = new ArrayList<ShopDTO>();
        for (int i = 0; i < 100; i++) {
            ShopDTO shop = new ShopDTO(true, "商户"+i, (short) i, 1000+i, 10000+i, (float) (1000+i), (double) (10000+i), new Date());
            shopDTOList.add(shop);
        }
        String filePath = "/Users/tianguang/demo-sheet.xls";

        MySheet mySheet = new MySheet();
        mySheet.setDataList(shopDTOList);
        mySheet.setSheetName("商户列表");
        mySheet.setSheetColor(HSSFColor.HSSFColorPredefined.LIGHT_GREEN);
        /**
         * Excel导出：Object 转换为 Excel
         */
        ExcelExportUtil.exportToFile(filePath, Lists.newArrayList(mySheet));

        /**
         * Excel导入：Excel 转换为 Object
          */
        List<Object> list = ExcelImportUtil.importExcel(filePath, ShopDTO.class);

        System.out.println(list);

    }

}
