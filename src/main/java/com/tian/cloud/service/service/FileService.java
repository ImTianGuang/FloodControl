package com.tian.cloud.service.service;

import com.tian.cloud.service.enums.UploadType;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    String uploadFile(MultipartFile file, String extraData);

    String encryptExtra(UploadType uploadType, Integer refId);
}
