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

    @ExcelField(name = "工作电话")
    private String workPhone;

    @ExcelField(name = "个人电话")
    private String personPhone;

    @ExcelField(name = "传真")
    private String fax;

    private List<Pair> pairs = Lists.newArrayList(new Pair("新的一列", "333"));
}
