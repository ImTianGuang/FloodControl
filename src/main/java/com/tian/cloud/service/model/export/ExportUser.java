package com.tian.cloud.service.model.export;

import com.google.common.collect.Lists;
import com.tian.cloud.service.util.excel.annotation.ExcelField;
import lombok.Data;

import java.util.List;

/**
 * @author tianguang
 * 2018/8/31 下午5:46
 **/
@Data
public class ExportUser {

    @ExcelField(name = "单位")
    private String companyName;

    @ExcelField(name = "姓名")
    private String name;

    @ExcelField(name = "职位")
    private String position;

    @ExcelField(name = "座机")
    private String workPhone;

    @ExcelField(name = "手机")
    private String personPhone;

}
