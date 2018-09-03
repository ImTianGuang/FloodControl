package com.tian.cloud.service.service.impl;

import com.tian.cloud.service.service.ExportService;
import com.tian.cloud.service.service.UserService;
import com.tian.cloud.service.util.excel.MySheet;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author tianguang
 * 2018/9/3 下午3:57
 **/
@Service
public class ExportServiceImpl implements ExportService {
    
    @Resource
    private UserService userService;

    @Override
    public List<MySheet> buildAllUserSheetList() {
        return null;
    }
}
