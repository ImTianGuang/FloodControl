package com.tian.cloud.service.service;

import org.springframework.web.multipart.MultipartFile;

public interface UploadService {

    boolean upload(MultipartFile file, String extraData);
}
