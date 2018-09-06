package com.tian.cloud.service.model.export;

import com.tian.cloud.service.dao.entity.Company;
import lombok.Data;

import java.util.List;

/**
 * @author tianguang
 * 2018/9/6 下午2:07
 **/
@Data
public class ExportContext {

    private List<Company> allCompany;
}
