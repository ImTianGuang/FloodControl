package com.tian.cloud.service.service.impl;


import com.tian.cloud.service.config.UploadConfig;
import com.tian.cloud.service.enums.UploadType;
import com.tian.cloud.service.service.FileService;
import com.tian.cloud.service.util.DateUtil;
import com.tian.cloud.service.util.ParamCheckUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author tianguang
 * 2018/9/17 下午3:17
 **/
@Service
@Slf4j
public class FileServiceImpl implements FileService {

    @Resource
    private UploadConfig uploadConfig;

    @Override
    public String uploadFile(MultipartFile uploadFile, UploadType uploadType) throws Exception {
        ParamCheckUtil.assertTrue(!uploadFile.isEmpty(), "文件为空");
        String savePath = uploadConfig.getUploadDir(uploadType);
        File file = new File(savePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        String fileName = uploadFile.getOriginalFilename();
        String filePath = savePath + "/" + fileName;
        File file1 = new File(filePath);

        int i=0;
        while (file1.exists()) {
            i++;
            filePath = savePath + "/" + i + "_" + fileName;
            file1 = new File(filePath);
        }
        uploadFile.transferTo(file1);
        return filePath;
    }

    @Override
    public void backUpFile(String dbFileName, UploadType uploadType) {
        File file = new File(dbFileName);
        LocalDateTime now = LocalDateTime.now();
        File backUpFile = new File(getBakPath(dbFileName, uploadType) + "_" + now.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        File bakDirectory = backUpFile.getParentFile();
        if (!bakDirectory.exists()) {
            bakDirectory.mkdirs();
        }
        boolean success = file.renameTo(backUpFile);
        log.info("backUpFile:{}, result:{}", dbFileName, success);
    }

    private String getBakPath(String dbFileName, UploadType uploadType) {
        return dbFileName.replace("/" + uploadType.getDirectory() + "/", "/" +uploadType.getDirectory() + "_back/");
    }
}
