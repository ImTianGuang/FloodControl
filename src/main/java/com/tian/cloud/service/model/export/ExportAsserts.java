package com.tian.cloud.service.model.export;

import com.tian.cloud.service.util.excel.annotation.ExcelField;
import lombok.Data;

import java.util.List;

/**
 * @author tianguang
 * 2018/8/31 下午5:56
 **/
@Data
public class ExportAsserts {

    @ExcelField(name = "单位名称")
    private String companyName;

    @ExcelField(name = "防汛负责人")
    private String floodManager;

    @ExcelField(name = "防汛负责人电话")
    private String floodManagerPhone;

    private List<Pair> assertsList;
}
