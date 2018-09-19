package com.tian.cloud.service.service.impl;

import com.tian.cloud.service.TestBase;
import com.tian.cloud.service.enums.UploadType;
import com.tian.cloud.service.service.FileService;
import org.junit.Test;

import javax.annotation.Resource;

import static org.junit.Assert.*;

public class FileServiceImplTest extends TestBase {

    @Resource
    private FileService fileService;

    @Test
    public void backUpFile() {
        fileService.backUpFile("/var/file/upload/floodPlan/压测数据-27.pdf", UploadType.FLOOD_PLAN);
    }
}