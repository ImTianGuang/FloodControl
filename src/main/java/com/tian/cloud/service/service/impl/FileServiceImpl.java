package com.tian.cloud.service.service.impl;


import com.tian.cloud.service.config.UploadConfig;
import com.tian.cloud.service.service.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @author tianguang
 * 2018/9/17 下午3:17
 **/
@Service
public class FileServiceImpl implements FileService {

    @Resource
    private UploadConfig uploadConfig;

    @Override
    public String uploadFile(MultipartFile file, String directory) throws Exception {

        return null;
    }
}
