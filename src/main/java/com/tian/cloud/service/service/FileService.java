package com.tian.cloud.service.service;

import com.tian.cloud.service.enums.UploadType;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    String uploadFile(MultipartFile file, String extraData) throws Exception;

    String encryptExtra(UploadType uploadType, Integer refId) throws Exception;
}
