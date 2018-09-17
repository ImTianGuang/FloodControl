package com.tian.cloud.service.service.impl;

import com.tian.cloud.service.service.FileService;
import com.tian.cloud.service.service.UploadService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @author tianguang
 * 2018/9/17 下午3:49
 **/
@Service
public class UploadServiceImpl implements UploadService {
    @Resource
    private FileService fileService;

    @Override
    public boolean upload(MultipartFile file, String extraData) {
        return false;
    }
}
