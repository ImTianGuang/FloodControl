package com.tian.cloud.service.util.excel;

import com.tian.cloud.service.util.excel.annotation.DynamicField;
import com.tian.cloud.service.util.excel.annotation.ExcelField;
import com.tian.cloud.service.util.excel.annotation.ExcelSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Excel导出工具
 *
 * @author xuxueli 2017-09-08 22:27:20
 */
public class ExcelExportUtil {
    private static Logger logger = LoggerFactory.getLogger(ExcelExportUtil.class);


    public static Workbook exportWorkbook(List<MySheet> sheetList) {
        Workbook workbook = new HSSFWorkbook();
        for (MySheet mySheet : sheetList) {
            makeSheet(workbook, mySheet);
        }
        return workbook;
    }

    private static void makeSheet(Workbook workbook, MySheet mySheet){
        try {
            List<?> sheetDataList = mySheet.getDataList();

            String sheetName = mySheet.getSheetName();
            int headColorIndex = mySheet.getSheetColor().getIndex();

            Sheet existSheet = workbook.getSheet(sheetName);
            if (existSheet != null) {
                for (int i = 2; i <= 1000; i++) {
                    String newSheetName = sheetName.concat(String.valueOf(i));  // avoid sheetName repetition
                    existSheet = workbook.getSheet(newSheetName);
                    if (existSheet == null) {
                        sheetName = newSheetName;
                        break;
                    } else {
                        continue;
                    }
                }
            }

            Sheet sheet = workbook.createSheet(sheetName);

            // data
            if (sheetDataList==null || sheetDataList.size()==0) {
                return;
            }
            // sheet field
            Class<?> sheetClass = sheetDataList.get(0).getClass();
            List<Field> fields = new ArrayList<Field>();
            if (sheetClass.getDeclaredFields()!=null && sheetClass.getDeclaredFields().length>0) {
                for (Field field: sheetClass.getDeclaredFields()) {
                    if (Modifier.isStatic(field.getModifiers())) {
                        continue;
                    }
                    fields.add(field);
                }
            }

            if (fields==null || fields.size()==0) {
                throw new RuntimeException(">>>>>>>>>>> xxl-excel error, data field can not be empty.");
            }

            // sheet header row
            Row headRow = sheet.createRow(0);
            int headColumn = 0;
            for (int i = 0; i < fields.size(); i++) {

                // field
                Field field = fields.get(i);
                ExcelField excelField = field.getAnnotation(ExcelField.class);
                if (excelField != null) {
                    String fieldName = excelField.name();
                    createCell(workbook, headColorIndex, headRow, headColumn, fieldName);
                    headColumn++;
                } else {
                    Class<?> fieldType = field.getType();
                    if (List.class.equals(fieldType)) {
                        Object rowData = sheetDataList.get(0);
                        field.setAccessible(true);
                        Object fieldValue = field.get(rowData);
                        List<DynamicField> dynamicFields = (List<DynamicField>) fieldValue;
                        for (DynamicField dynamicField : dynamicFields) {
                            String columnName = dynamicField.columnName();
                            createCell(workbook, headColorIndex, headRow, headColumn, columnName);
                            headColumn++;
                        }
                    }
                }
            }

            // sheet data rows
            for (int dataIndex = 0; dataIndex < sheetDataList.size(); dataIndex++) {
                int rowIndex = dataIndex+1;
                Object rowData = sheetDataList.get(dataIndex);

                Row rowX = sheet.createRow(rowIndex);

                int dataColumn = 0;
                for (int i = 0; i < fields.size(); i++) {
                    Field field = fields.get(i);
                    field.setAccessible(true);
                    Object fieldValue = field.get(rowData);
                    Class<?> fieldType = field.getType();
                    if (List.class.equals(fieldType)) {
                        List<DynamicField> dynamicFields = (List<DynamicField>) fieldValue;
                        for (DynamicField dynamicField : dynamicFields) {
                            String fieldValueString = String.valueOf(dynamicField.fieldValue());
                            Cell cellX = rowX.createCell(dataColumn, CellType.STRING);
                            cellX.setCellValue(fieldValueString);
//                        cellX.setCellStyle(fieldDataStyleArr[dataColumn]);
                            dataColumn++;
                        }
                    } else {
                        String fieldValueString = FieldReflectionUtil.formatValue(field, fieldValue);
                        Cell cellX = rowX.createCell(dataColumn, CellType.STRING);
                        cellX.setCellValue(fieldValueString);
//                        cellX.setCellStyle(fieldDataStyleArr[dataColumn]);
                        dataColumn++;
                    }
                }
            }

            for (int i = 0; i < 12; i++) {
                sheet.autoSizeColumn(i);
                int width = sheet.getColumnWidth(i);
                width = width * 17 / 10;
                sheet.setColumnWidth(i, width);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private static void createCell(Workbook workbook, int headColorIndex, Row headRow, int i, String fieldName) {
        CellStyle headStyle = workbook.createCellStyle();
        headStyle.setAlignment(HorizontalAlignment.CENTER);
        //垂直居中
        headStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        //设置边框
        headStyle.setBorderTop(BorderStyle.THIN);
        headStyle.setBorderRight(BorderStyle.THIN);
        headStyle.setBorderBottom(BorderStyle.THIN);
        headStyle.setBorderLeft(BorderStyle.THIN);

        headStyle.setWrapText(true);
        if (headColorIndex > -1) {
            headStyle.setFillForegroundColor((short) headColorIndex);
            headStyle.setFillBackgroundColor((short) headColorIndex);
            headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        }

        // head-field data
        Cell cellX = headRow.createCell(i, CellType.STRING);
        cellX.setCellStyle(headStyle);
        cellX.setCellValue(String.valueOf(fieldName));
    }

    /**
     * 导出Excel文件到磁盘
     *
     * @param filePath
     */
    public static void exportToFile(String filePath, List<MySheet> sheetList){
        // workbook
        Workbook workbook = exportWorkbook(sheetList);

        writeToFile(workbook, filePath);

    }

    public static void writeToFile(Workbook workbook, String filePath) {
        FileOutputStream fileOutputStream = null;
        try {
            // workbook 2 FileOutputStream
            fileOutputStream = new FileOutputStream(filePath);
            workbook.write(fileOutputStream);

            // flush
            fileOutputStream.flush();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        } finally {
            try {
                if (fileOutputStream!=null) {
                    fileOutputStream.close();
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                throw new RuntimeException(e);
            }
        }
    }
    /**
     * 导出Excel字节数据
     *
     * @return
     */
    public static byte[] exportToBytes(List<MySheet> sheetList){
        // workbook
        Workbook workbook = exportWorkbook(sheetList);

        ByteArrayOutputStream byteArrayOutputStream = null;
        byte[] result = null;
        try {
            // workbook 2 ByteArrayOutputStream
            byteArrayOutputStream = new ByteArrayOutputStream();
            workbook.write(byteArrayOutputStream);

            // flush
            byteArrayOutputStream.flush();

            result = byteArrayOutputStream.toByteArray();
            return result;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        } finally {
            try {
                if (byteArrayOutputStream != null) {
                    byteArrayOutputStream.close();
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                throw new RuntimeException(e);
            }
        }
    }

}
