package com.tian.cloud.service.service.impl;

import com.tian.cloud.service.service.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author tianguang
 * 2018/9/17 下午3:17
 **/
@Service
public class FileServiceImpl implements FileService {
    
    @Override
    public String uploadFile(MultipartFile file, String extraData) {
        return null;
    }
}
