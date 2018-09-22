package com.tian.cloud.service.model.export;

import com.tian.cloud.service.util.excel.annotation.ExcelField;
import lombok.Data;

/**
 * @author tianguang
 * 2018/9/22 下午3:29
 **/
@Data
public class ExportUserV1 {
    @ExcelField(name = "单位")
    private String companyName;

    @ExcelField(name = "电话")
    private String workPhone;

    @ExcelField(name = "传真")
    private String fax;
}
