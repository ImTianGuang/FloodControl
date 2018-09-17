package com.tian.cloud.service.service.impl;


import com.tian.cloud.service.config.UploadConfig;
import com.tian.cloud.service.service.FileService;
import com.tian.cloud.service.util.ParamCheckUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;

/**
 * @author tianguang
 * 2018/9/17 下午3:17
 **/
@Service
public class FileServiceImpl implements FileService {

    @Resource
    private UploadConfig uploadConfig;

    @Override
    public String uploadFile(MultipartFile uploadFile, String directory) throws Exception {
        ParamCheckUtil.assertTrue(!uploadFile.isEmpty(), "文件为空");
        String savePath = uploadConfig.getFilePath() + "/" + directory;
        File file = new File(savePath);
        if (!file.exists()) {
            file.mkdir();
        }
        String fileName = uploadFile.getOriginalFilename();
        String filePath = savePath + "/" + fileName;
        File file1 = new File(filePath);
        ParamCheckUtil.assertTrue(!file1.exists(), "存在相同名称的文件，请重新上传");
        uploadFile.transferTo(file1);
        return filePath;
    }
}
