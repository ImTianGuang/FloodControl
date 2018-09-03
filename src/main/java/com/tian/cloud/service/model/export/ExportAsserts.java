package com.tian.cloud.service.model.export;

import lombok.Data;

import java.util.List;

/**
 * @author tianguang
 * 2018/8/31 下午5:56
 **/
@Data
public class ExportAsserts {

    private String companyName;

    private String floodManager;

    private String floodManagerPhone;

    private List<Pair> assertsList;
}
